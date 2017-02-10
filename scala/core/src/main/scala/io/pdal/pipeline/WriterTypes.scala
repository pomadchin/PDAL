
package io.pdal.pipeline

sealed trait WriterType extends ExprType { val `type` = "writers" }

object WriterTypes {
  case object derivative extends WriterType
  case object sbet extends WriterType
  case object bpf extends WriterType
  case object las extends WriterType
  case object ply extends WriterType
  case object gdal extends WriterType
  case object pgpointcloud extends WriterType
  case object text extends WriterType

  lazy val all = List(
    derivative, sbet, bpf, las, ply, gdal, pgpointcloud, text
  )
}
       