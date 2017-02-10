package io.pdal.generator

import io.circe._

object Implicits extends Implicits

trait Implicits {
  implicit class withDSLClassMethods(vec: Vector[DSLClass]) {
    def generateCaseClasses: (String, String) = {
      "./core/src/main/scala/io/pdal/pipeline/PipelineExpressions.scala" -> s"""
         |package io.pdal.pipeline
         |
         |import io.circe.Json
         |
         |sealed trait PipelineExpr {
         |  def ~(other: PipelineExpr): PipelineConstructor = this :: other :: Nil
         |
         |  def ~(other: Option[PipelineExpr]): PipelineConstructor =
         |    other.fold(this :: Nil)(o => this :: o :: Nil)
         |}
         |
         |case class RawExpr(json: Json) extends PipelineExpr
         |${vec.collect{ case t if t.pluginName != "null" => t }.map(_.generateCaseClass).mkString("|")}
         |
       """.stripMargin
    }

    def generateTypes: ((String, String), (String, String), (String, String)) = {
      val types = vec.groupBy(_.name).values.flatten

      val readers =
        s"""
           |package io.pdal.pipeline
           |
           |sealed trait ReaderType extends ExprType { val `type` = "filters" }
           |
           |object ReaderTypes {
           |  ${types.collect { case t if t.isReader && t.pluginName != "null" => t.generateCaseObjectType }.mkString("\n|  ")}
           |
           |  lazy val all = List(
           |    ${types.collect { case t if t.isReader && t.pluginName != "null" => t.pluginName }.mkString(", ")}
           |  )
           |}
       """.stripMargin

      val writers =
        s"""
           |package io.pdal.pipeline
           |
           |sealed trait WriterType extends ExprType { val `type` = "writers" }
           |
           |object WriterTypes {
           |  ${types.collect { case t if t.isWriter && t.pluginName != "null" => t.generateCaseObjectType }.mkString("\n|  ")}
           |
           |  lazy val all = List(
           |    ${types.collect { case t if t.isWriter && t.pluginName != "null" => t.pluginName }.mkString(", ")}
           |  )
           |}
       """.stripMargin

      val filters =
        s"""
           |package io.pdal.pipeline
           |
           |sealed trait FilterType extends ExprType { val `type` = "filters" }
           |
           |object FilterTypes {
           |  ${types.collect { case t if t.isFilter && t.pluginName != "null" => t.generateCaseObjectType }.mkString("\n|  ")}
           |
           |  lazy val all = List(
           |    ${types.collect { case t if t.isFilter && t.pluginName != "null" => t.pluginName }.mkString(", ")}
           |  )
           |}
       """.stripMargin

      ("./core/src/main/scala/io/pdal/pipeline/ReaderTypes.scala" -> readers,
       "./core/src/main/scala/io/pdal/pipeline/WriterTypes.scala" -> writers,
       "./core/src/main/scala/io/pdal/pipeline/FilterTypes.scala" -> filters)
    }

  }

  implicit val decoder: Decoder[DSLField] = Decoder.instance { c =>
    println(c.as[Json])

    val (tpe, default) = c.get[Option[Int]]("default") match {
      case Right(Some(i)) => "Int" -> Some[Any](i)
      case Right(_) | Left(_) => c.get[Option[Double]]("default") match {
        case Right(Some(d)) => "Double" -> Some[Any](d)
        case Right(_) | Left(_) => c.get[Option[String]]("default") match {
          case Right(Some(s)) => "String" -> Some[Any](s)
          case Right(_) | Left(_) => "String" -> None
        }
      }
    }

    println(s"tpe -> default: ${tpe -> default}")

    val description = c.get[String]("description") match {
      case Right(d) => d
      case Left(_) => { println("No Description field"); "" }
    }

    val name = c.get[String]("name") match {
      case Right(n) => n
      case Left(e) => throw e
    }

    Right(DSLField(description, name, tpe, default))
  }
}
