package io.pdal.generator

import io.circe.parser._

import scala.sys.process._

import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets

/**
  * Note: PDAL should be installed
  */
object DSLGenerator {
  val cmd = "pdal --options all --showjson"
  val output = parse(cmd.!!)

  def run: Vector[DSLClass] = output match {
    case Right(json) =>
      val vec = json.asArray.getOrElse(Vector())
      vec.map { json =>
        val map = json.asObject.map(_.toMap).getOrElse(sys.error("Json can't be represented as a Map."))

        DSLClass(
          map.keys.head,
          map.values.flatMap { _.as[List[DSLField]] match {
            case Right(list) => list
            case Left(e) => sys.error("Can't convert fields descriptions into List[DSLField].")
          } }
        )
      }

    case Left(e) =>
      sys.error(s"Output of the command ($cmd) call is not a valid JSON.")
  }

  def generate(root: String = "."): Unit = {
    val vec = run
    val (exprs, (readers, writers, filters)) = vec.generateCaseClasses(root) -> vec.generateTypes(root)

    exprs :: readers :: writers :: filters :: Nil foreach { case (path, src) =>
      Files.write(Paths.get(path), src.getBytes(StandardCharsets.UTF_8))
    }
  }
}
