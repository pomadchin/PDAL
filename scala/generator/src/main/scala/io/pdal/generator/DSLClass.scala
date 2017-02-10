package io.pdal.generator

case class DSLClass(
  name: String,
  fields: Iterable[DSLField]
) {
  lazy val Array(pluginType, pluginName) = name.split("\\.")

  def isWriter = pluginType == "writers"
  def isReader = pluginType == "readers"
  def isFilter = pluginType == "filters"

  def caseClassName = s"${pluginName.capitalize}${pluginType.init.capitalize}"

  def generateCaseObjectType = s"case object $name extends ${pluginType.init.capitalize}Type"

  def generateCaseClass = {
    s"""
       |case class $caseClassName(${fields.map { _.getTypedField }.mkString(", ")}, `type`: ExprType = ${name}) extends PipelineExpr
      """.stripMargin
  }
}
