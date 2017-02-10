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
         |
         |${vec.map(_.generateCaseClass).mkString("\n| ")}
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
           |  ${types.collect { case t if t.isReader => t.generateCaseObjectType }.mkString("\n|  ")}
           |
           |  lazy val all = List(
           |    ${types.collect { case t if t.isReader => t.name }.mkString(",")}
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
           |  ${types.collect { case t if t.isWriter => t.generateCaseObjectType }.mkString("\n|  ")}
           |
           |  lazy val all = List(
           |    ${types.collect { case t if t.isWriter => t.name }.mkString(",")}
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
           |  ${types.collect { case t if t.isFilter => t.generateCaseObjectType }.mkString("\n|  ")}
           |
           |  lazy val all = List(
           |    ${types.collect { case t if t.isFilter => t.name }.mkString(",")}
           |  )
           |}
       """.stripMargin

      ("./core/src/main/scala/io/pdal/pipeline/ReaderTypes.scala" -> readers,
       "./core/src/main/scala/io/pdal/pipeline/WriterTypes.scala" -> writers,
       "./core/src/main/scala/io/pdal/pipeline/FilterTypes.scala" -> filters)
    }

  }

  implicit val decoder: Decoder[DSLField] = Decoder.instance { c =>
    println(c.as[Json].right.toString)

    val (tpe, default) = c.get[Option[Int]]("default") match {
      case Right(i) => "Int" -> Some(i: Any)
      case Left(_) => c.get[Option[Double]]("default") match {
        case Right(d) => "Double" -> Some(d: Any)
        case Left(_) => c.get[Option[String]]("default") match {
          case Right(s) => "String" -> Some(s: Any)
          case Left(_) => "String" -> None
        }
      }
    }

    val description = c.get[String]("description") match {
      case Right(d) => d
      case Left(e) => { println("No Description field"); "" }
    }

    val name = c.get[String]("name") match {
      case Right(n) => n
      case Left(e) => throw e
    }

    Right(DSLField(description, name, tpe, default))
  }
}
