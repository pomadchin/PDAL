
package io.pdal.pipeline

sealed trait ReaderType extends ExprType { val `type` = "filters" }

object ReaderTypes {
  case object pgpointcloud extends ReaderType
  case object ilvis2 extends ReaderType
  case object optech extends ReaderType
  case object qfit extends ReaderType
  case object text extends ReaderType
  case object faux extends ReaderType
  case object gdal extends ReaderType
  case object las extends ReaderType
  case object ply extends ReaderType
  case object pts extends ReaderType
  case object sbet extends ReaderType
  case object tindex extends ReaderType
  case object bpf extends ReaderType
  case object terrasolid extends ReaderType

  lazy val all = List(
    pgpointcloud, ilvis2, optech, qfit, text, faux, gdal, las, ply, pts, sbet, tindex, bpf, terrasolid
  )
}
       