
package io.pdal.pipeline

import io.circe.Json

sealed trait PipelineExpr {
  def ~(other: PipelineExpr): PipelineConstructor = this :: other :: Nil

  def ~(other: Option[PipelineExpr]): PipelineConstructor =
    other.fold(this :: Nil)(o => this :: o :: Nil)
}

case class RawExpr(json: Json) extends PipelineExpr

case class ApproximatecoplanarFilter(log: String, knn: Option[Int] = Some(8), thresh1: Option[Int] = Some(25), thresh2: Option[Int] = Some(6), `type`: ExprType = FilterTypes.approximatecoplanar) extends PipelineExpr

case class AttributeFilter(log: String, dimension: String, value: Option[String] = Some("nan"), datasource: String, column: String, query: String, layer: String, `type`: ExprType = FilterTypes.attribute) extends PipelineExpr

case class ChipperFilter(log: String, capacity: Option[Int] = Some(5000), `type`: ExprType = FilterTypes.chipper) extends PipelineExpr

case class ColorinterpFilter(log: String, dimension: Option[String] = Some("Z"), minimum: String, maximum: String, ramp: Option[String] = Some("pestel_shades"), invert: Option[String] = Some("false"), mad: Option[String] = Some("false"), mad_multiplier: Option[Double] = Some(1.4862), k: Option[Int] = Some(0), `type`: ExprType = FilterTypes.colorinterp) extends PipelineExpr

case class ColorizationFilter(log: String, raster: String, dimensions: String, `type`: ExprType = FilterTypes.colorization) extends PipelineExpr

case class ComputerangeFilter(log: String, `type`: ExprType = FilterTypes.computerange) extends PipelineExpr

case class CropFilter(log: String, outside: String, a_srs: String, bounds: String, point: String, distance: String, polygon: String, `type`: ExprType = FilterTypes.crop) extends PipelineExpr

case class DecimationFilter(log: String, step: Option[Int] = Some(1), offset: String, limit: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = FilterTypes.decimation) extends PipelineExpr

case class DividerFilter(log: String, mode: Option[String] = Some("partition"), count: String, capacity: String, `type`: ExprType = FilterTypes.divider) extends PipelineExpr

case class EigenvaluesFilter(log: String, knn: Option[Int] = Some(8), `type`: ExprType = FilterTypes.eigenvalues) extends PipelineExpr

case class EstimaterankFilter(log: String, knn: Option[Int] = Some(8), thresh: Option[Double] = Some(0.01), `type`: ExprType = FilterTypes.estimaterank) extends PipelineExpr

case class FerryFilter(log: String, dimensions: String, `type`: ExprType = FilterTypes.ferry) extends PipelineExpr

case class HagFilter(log: String, `type`: ExprType = FilterTypes.hag) extends PipelineExpr

case class IqrFilter(log: String, k: Option[Double] = Some(1.5), dimension: String, `type`: ExprType = FilterTypes.iqr) extends PipelineExpr

case class KdistanceFilter(log: String, k: Option[Int] = Some(10), `type`: ExprType = FilterTypes.kdistance) extends PipelineExpr

case class LofFilter(log: String, minpts: Option[Int] = Some(10), `type`: ExprType = FilterTypes.lof) extends PipelineExpr

case class MadFilter(log: String, k: Option[Int] = Some(2), dimension: String, mad_multiplier: Option[Double] = Some(1.4862), `type`: ExprType = FilterTypes.mad) extends PipelineExpr

case class MergeFilter(log: String, `type`: ExprType = FilterTypes.merge) extends PipelineExpr

case class MongusFilter(log: String, cell: Option[Int] = Some(1), k: Option[Int] = Some(3), l: Option[Int] = Some(8), classify: Option[String] = Some("true"), extract: String, `type`: ExprType = FilterTypes.mongus) extends PipelineExpr

case class MortonorderFilter(log: String, `type`: ExprType = FilterTypes.mortonorder) extends PipelineExpr

case class NormalFilter(log: String, knn: Option[Int] = Some(8), `type`: ExprType = FilterTypes.normal) extends PipelineExpr

case class OutlierFilter(log: String, method: Option[String] = Some("statistical"), min_k: Option[Int] = Some(2), radius: Option[Int] = Some(1), mean_k: Option[Int] = Some(8), multiplier: Option[Int] = Some(2), classify: Option[String] = Some("true"), extract: String, `type`: ExprType = FilterTypes.outlier) extends PipelineExpr

case class PmfFilter(log: String, max_window_size: Option[Int] = Some(33), slope: Option[Int] = Some(1), max_distance: Option[Double] = Some(2.5), initial_distance: Option[Double] = Some(0.15), cell_size: Option[Int] = Some(1), classify: Option[String] = Some("true"), extract: String, approximate: String, `type`: ExprType = FilterTypes.pmf) extends PipelineExpr

case class PredicateFilter(log: String, source: String, script: String, module: String, function: String, `type`: ExprType = FilterTypes.predicate) extends PipelineExpr

case class ProgrammableFilter(log: String, source: String, script: String, module: String, function: String, add_dimension: String, `type`: ExprType = FilterTypes.programmable) extends PipelineExpr

case class RadialdensityFilter(log: String, radius: Option[Int] = Some(1), `type`: ExprType = FilterTypes.radialdensity) extends PipelineExpr

case class RangeFilter(log: String, limits: String, `type`: ExprType = FilterTypes.range) extends PipelineExpr

case class ReprojectionFilter(log: String, out_srs: String, in_srs: String, `type`: ExprType = FilterTypes.reprojection) extends PipelineExpr

case class SampleFilter(log: String, radius: Option[Int] = Some(1), `type`: ExprType = FilterTypes.sample) extends PipelineExpr

case class SmrfFilter(log: String, classify: Option[String] = Some("true"), extract: String, cell: Option[Int] = Some(1), slope: Option[Double] = Some(0.15), window: Option[Int] = Some(18), scalar: Option[Double] = Some(1.25), threshold: Option[Double] = Some(0.5), cut: Option[Int] = Some(0), outdir: String, `type`: ExprType = FilterTypes.smrf) extends PipelineExpr

case class SortFilter(log: String, dimension: String, `type`: ExprType = FilterTypes.sort) extends PipelineExpr

case class SplitterFilter(log: String, length: Option[Int] = Some(1000), origin_x: Option[String] = Some("nan"), origin_y: Option[String] = Some("nan"), `type`: ExprType = FilterTypes.splitter) extends PipelineExpr

case class StatsFilter(log: String, dimensions: String, enumerate: String, global: String, count: String, `type`: ExprType = FilterTypes.stats) extends PipelineExpr

case class TransformationFilter(log: String, matrix: String, `type`: ExprType = FilterTypes.transformation) extends PipelineExpr

case class BpfReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.bpf) extends PipelineExpr

case class FauxReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), bounds: Option[String] = Some("([0, 1], [0, 1], [0, 1])"), mean_x: String, mean_y: String, mean_z: String, stdev_x: Option[Int] = Some(1), stdev_y: Option[Int] = Some(1), stdev_z: Option[Int] = Some(1), mode: String, number_of_returns: String, `type`: ExprType = ReaderTypes.faux) extends PipelineExpr

case class GdalReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.gdal) extends PipelineExpr

case class Ilvis2Reader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), mapping: Option[String] = Some("All"), metadata: String, `type`: ExprType = ReaderTypes.ilvis2) extends PipelineExpr

case class LasReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), spatialreference: String, extra_dims: String, compression: Option[String] = Some("EITHER"), `type`: ExprType = ReaderTypes.las) extends PipelineExpr

case class OptechReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.optech) extends PipelineExpr

case class PgpointcloudReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), table: String, connection: String, column: Option[String] = Some("pa"), schema: String, where: String, spatialreference: String, `type`: ExprType = ReaderTypes.pgpointcloud) extends PipelineExpr

case class PlyReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.ply) extends PipelineExpr

case class PtsReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.pts) extends PipelineExpr

case class QfitReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), flip_coordinates: String, scale_z: Option[Double] = Some(0.001), `type`: ExprType = ReaderTypes.qfit) extends PipelineExpr

case class SbetReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.sbet) extends PipelineExpr

case class TerrasolidReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.terrasolid) extends PipelineExpr

case class TextReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.text) extends PipelineExpr

case class TindexReader(log: String, filename: String, count: Option[Double] = Some(1.8446744073709552E19), lyr_name: Option[String] = Some("pdal"), srs_column: Option[String] = Some("srs"), tindex_name: Option[String] = Some("location"), sql: String, bounds: String, wkt: String, t_srs: Option[String] = Some("EPSG:4326"), filter_srs: String, where: String, dialect: Option[String] = Some("OGRSQL"), `type`: ExprType = ReaderTypes.tindex) extends PipelineExpr

case class BpfWriter(log: String, filename: String, compression: String, header_data: String, format: Option[String] = Some("Dimension"), coord_id: Option[Int] = Some(-9999), bundledfile: String, output_dims: String, offset_x: String, offset_y: String, offset_z: String, scale_x: Option[Int] = Some(1), scale_y: Option[Int] = Some(1), scale_z: Option[Int] = Some(1), `type`: ExprType = WriterTypes.bpf) extends PipelineExpr

case class DerivativeWriter(log: String, filename: String, edge_length: Option[Int] = Some(15), primitive_type: Option[String] = Some("slope_d8"), altitude: Option[Int] = Some(45), azimuth: Option[Int] = Some(315), driver: Option[String] = Some("GTiff"), `type`: ExprType = WriterTypes.derivative) extends PipelineExpr

case class GdalWriter(log: String, filename: String, resolution: String, radius: String, gdaldriver: Option[String] = Some("GTiff"), gdalopts: String, output_type: Option[String] = Some("all"), window_size: String, nodata: Option[Int] = Some(-9999), dimension: Option[String] = Some("Z"), `type`: ExprType = WriterTypes.gdal) extends PipelineExpr

case class LasWriter(log: String, filename: String, a_srs: String, compression: Option[String] = Some("None"), discard_high_return_numbers: String, extra_dims: String, forward: String, major_version: Option[String] = Some(""), minor_version: Option[String] = Some(""), dataformat_id: Option[String] = Some(""), format: Option[String] = Some(""), global_encoding: String, project_id: String, system_id: Option[String] = Some("PDAL"), software_id: Option[String] = Some("PDAL 1.4.0 (Releas)"), creation_doy: Option[Int] = Some(41), creation_year: Option[Int] = Some(2017), scale_x: Option[String] = Some(".01"), scale_y: Option[String] = Some(".01"), scale_z: Option[String] = Some(".01"), offset_x: String, offset_y: String, offset_z: String, `type`: ExprType = WriterTypes.las) extends PipelineExpr

case class PgpointcloudWriter(log: String, output_dims: String, offset_x: String, offset_y: String, offset_z: String, scale_x: Option[Int] = Some(1), scale_y: Option[Int] = Some(1), scale_z: Option[Int] = Some(1), connection: String, table: String, column: Option[String] = Some("pa"), schema: String, compression: Option[String] = Some("dimensional"), overwrite: Option[String] = Some("true"), srid: Option[Int] = Some(4326), pcid: String, pre_sql: String, post_sql: String, `type`: ExprType = WriterTypes.pgpointcloud) extends PipelineExpr

case class PlyWriter(log: String, filename: String, storage_mode: Option[String] = Some("default"), `type`: ExprType = WriterTypes.ply) extends PipelineExpr

case class SbetWriter(log: String, filename: String, `type`: ExprType = WriterTypes.sbet) extends PipelineExpr

case class TextWriter(log: String, filename: String, format: Option[String] = Some("csv"), jscallback: String, keep_unspecified: Option[String] = Some("true"), order: String, write_header: Option[String] = Some("true"), newline: Option[String] = Some(""), delimiter: Option[String] = Some(","), quote_header: Option[String] = Some("true"), precision: Option[Int] = Some(3), `type`: ExprType = WriterTypes.text) extends PipelineExpr
      

       