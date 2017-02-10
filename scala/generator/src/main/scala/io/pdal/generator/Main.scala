package io.pdal.generator

object Main {
  def main(args: Array[String]): Unit =
    DSLGenerator.generate(args.headOption.getOrElse(".."))
}
