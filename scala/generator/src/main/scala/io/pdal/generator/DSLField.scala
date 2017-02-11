package io.pdal.generator

case class DSLField(
  description: String,
  name: String,
  `type`: String,
  default: Option[Any] = None
) {
  def getTypedField: String = default match {
    case Some(s) if `type` == "String" => s"""$name: Option[${`type`}] = Some("${s.toString.trim}")"""
    case Some(s) => s"$name: Option[${`type`}] = Some($s)"
    case _       => s"$name: ${`type`}"
  }
}
