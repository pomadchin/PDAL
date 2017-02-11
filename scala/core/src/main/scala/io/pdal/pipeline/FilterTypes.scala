
package io.pdal.pipeline

sealed trait FilterType extends ExprType { val `type` = "filters" }

object FilterTypes {
  case object chipper extends FilterType
  case object sort extends FilterType
  case object transformation extends FilterType
  case object radialdensity extends FilterType
  case object eigenvalues extends FilterType
  case object kdistance extends FilterType
  case object splitter extends FilterType
  case object programmable extends FilterType
  case object mortonorder extends FilterType
  case object decimation extends FilterType
  case object merge extends FilterType
  case object stats extends FilterType
  case object ferry extends FilterType
  case object hag extends FilterType
  case object outlier extends FilterType
  case object lof extends FilterType
  case object sample extends FilterType
  case object attribute extends FilterType
  case object mad extends FilterType
  case object crop extends FilterType
  case object range extends FilterType
  case object iqr extends FilterType
  case object computerange extends FilterType
  case object estimaterank extends FilterType
  case object pmf extends FilterType
  case object smrf extends FilterType
  case object approximatecoplanar extends FilterType
  case object reprojection extends FilterType
  case object divider extends FilterType
  case object predicate extends FilterType
  case object normal extends FilterType
  case object colorization extends FilterType
  case object colorinterp extends FilterType
  case object mongus extends FilterType

  lazy val all = List(
    chipper, sort, transformation, radialdensity, eigenvalues, kdistance, splitter, programmable, mortonorder, decimation, merge, stats, ferry, hag, outlier, lof, sample, attribute, mad, crop, range, iqr, computerange, estimaterank, pmf, smrf, approximatecoplanar, reprojection, divider, predicate, normal, colorization, colorinterp, mongus
  )
}
       