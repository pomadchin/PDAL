
package io.pdal.pipeline

import io.circe.Json

sealed trait PipelineExpr {
  def ~(other: PipelineExpr): PipelineConstructor = this :: other :: Nil

  def ~(other: Option[PipelineExpr]): PipelineConstructor =
    other.fold(this :: Nil)(o => this :: o :: Nil)
}

case class RawExpr(json: Json) extends PipelineExpr

case class ApproximatecoplanarFilter(log: String, knn: Some[Int] = Some(8), thresh1: Some[Int] = Some(25), thresh2: Some[Int] = Some(6), `type`: ExprType = FilterTypes.approximatecoplanar) extends PipelineExpr

case class AttributeFilter(log: String, dimension: String, value: Some[String] = Some("nan"), datasource: String, column: String, query: String, layer: String, `type`: ExprType = FilterTypes.attribute) extends PipelineExpr

case class ChipperFilter(log: String, capacity: Some[Int] = Some(5000), `type`: ExprType = FilterTypes.chipper) extends PipelineExpr

case class ColorinterpFilter(log: String, dimension: Some[String] = Some("Z"), minimum: String, maximum: String, ramp: Some[String] = Some("pestel_shades"), invert: Some[String] = Some("false"), mad: Some[String] = Some("false"), mad_multiplier: Some[Double] = Some(1.4862), k: Some[Int] = Some(0), `type`: ExprType = FilterTypes.colorinterp) extends PipelineExpr

case class ColorizationFilter(log: String, raster: String, dimensions: String, `type`: ExprType = FilterTypes.colorization) extends PipelineExpr

case class ComputerangeFilter(log: String, `type`: ExprType = FilterTypes.computerange) extends PipelineExpr

case class CropFilter(log: String, outside: String, a_srs: String, bounds: String, point: String, distance: String, polygon: String, `type`: ExprType = FilterTypes.crop) extends PipelineExpr

case class DecimationFilter(log: String, step: Some[Int] = Some(1), offset: String, limit: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = FilterTypes.decimation) extends PipelineExpr

case class DividerFilter(log: String, mode: Some[String] = Some("partition"), count: String, capacity: String, `type`: ExprType = FilterTypes.divider) extends PipelineExpr

case class EigenvaluesFilter(log: String, knn: Some[Int] = Some(8), `type`: ExprType = FilterTypes.eigenvalues) extends PipelineExpr

case class EstimaterankFilter(log: String, knn: Some[Int] = Some(8), thresh: Some[Double] = Some(0.01), `type`: ExprType = FilterTypes.estimaterank) extends PipelineExpr

case class FerryFilter(log: String, dimensions: String, `type`: ExprType = FilterTypes.ferry) extends PipelineExpr

case class HagFilter(log: String, `type`: ExprType = FilterTypes.hag) extends PipelineExpr

case class IqrFilter(log: String, k: Some[Double] = Some(1.5), dimension: String, `type`: ExprType = FilterTypes.iqr) extends PipelineExpr

case class KdistanceFilter(log: String, k: Some[Int] = Some(10), `type`: ExprType = FilterTypes.kdistance) extends PipelineExpr

case class LofFilter(log: String, minpts: Some[Int] = Some(10), `type`: ExprType = FilterTypes.lof) extends PipelineExpr

case class MadFilter(log: String, k: Some[Int] = Some(2), dimension: String, mad_multiplier: Some[Double] = Some(1.4862), `type`: ExprType = FilterTypes.mad) extends PipelineExpr

case class MergeFilter(log: String, `type`: ExprType = FilterTypes.merge) extends PipelineExpr

case class MongusFilter(log: String, cell: Some[Int] = Some(1), k: Some[Int] = Some(3), l: Some[Int] = Some(8), classify: Some[String] = Some("true"), extract: String, `type`: ExprType = FilterTypes.mongus) extends PipelineExpr

case class MortonorderFilter(log: String, `type`: ExprType = FilterTypes.mortonorder) extends PipelineExpr

case class NormalFilter(log: String, knn: Some[Int] = Some(8), `type`: ExprType = FilterTypes.normal) extends PipelineExpr

case class OutlierFilter(log: String, method: Some[String] = Some("statistical"), min_k: Some[Int] = Some(2), radius: Some[Int] = Some(1), mean_k: Some[Int] = Some(8), multiplier: Some[Int] = Some(2), classify: Some[String] = Some("true"), extract: String, `type`: ExprType = FilterTypes.outlier) extends PipelineExpr

case class PmfFilter(log: String, max_window_size: Some[Int] = Some(33), slope: Some[Int] = Some(1), max_distance: Some[Double] = Some(2.5), initial_distance: Some[Double] = Some(0.15), cell_size: Some[Int] = Some(1), classify: Some[String] = Some("true"), extract: String, approximate: String, `type`: ExprType = FilterTypes.pmf) extends PipelineExpr

case class PredicateFilter(log: String, source: String, script: String, module: String, function: String, `type`: ExprType = FilterTypes.predicate) extends PipelineExpr

case class ProgrammableFilter(log: String, source: String, script: String, module: String, function: String, add_dimension: String, `type`: ExprType = FilterTypes.programmable) extends PipelineExpr

case class RadialdensityFilter(log: String, radius: Some[Int] = Some(1), `type`: ExprType = FilterTypes.radialdensity) extends PipelineExpr

case class RangeFilter(log: String, limits: String, `type`: ExprType = FilterTypes.range) extends PipelineExpr

case class ReprojectionFilter(log: String, out_srs: String, in_srs: String, `type`: ExprType = FilterTypes.reprojection) extends PipelineExpr

case class SampleFilter(log: String, radius: Some[Int] = Some(1), `type`: ExprType = FilterTypes.sample) extends PipelineExpr

case class SmrfFilter(log: String, classify: Some[String] = Some("true"), extract: String, cell: Some[Int] = Some(1), slope: Some[Double] = Some(0.15), window: Some[Int] = Some(18), scalar: Some[Double] = Some(1.25), threshold: Some[Double] = Some(0.5), cut: Some[Int] = Some(0), outdir: String, `type`: ExprType = FilterTypes.smrf) extends PipelineExpr

case class SortFilter(log: String, dimension: String, `type`: ExprType = FilterTypes.sort) extends PipelineExpr

case class SplitterFilter(log: String, length: Some[Int] = Some(1000), origin_x: Some[String] = Some("nan"), origin_y: Some[String] = Some("nan"), `type`: ExprType = FilterTypes.splitter) extends PipelineExpr

case class StatsFilter(log: String, dimensions: String, enumerate: String, global: String, count: String, `type`: ExprType = FilterTypes.stats) extends PipelineExpr

case class TransformationFilter(log: String, matrix: String, `type`: ExprType = FilterTypes.transformation) extends PipelineExpr

case class BpfReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.bpf) extends PipelineExpr

case class FauxReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), bounds: Some[String] = Some("([0, 1], [0, 1], [0, 1])"), mean_x: String, mean_y: String, mean_z: String, stdev_x: Some[Int] = Some(1), stdev_y: Some[Int] = Some(1), stdev_z: Some[Int] = Some(1), mode: String, number_of_returns: String, `type`: ExprType = ReaderTypes.faux) extends PipelineExpr

case class GdalReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.gdal) extends PipelineExpr

case class Ilvis2Reader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), mapping: Some[String] = Some("All"), metadata: String, `type`: ExprType = ReaderTypes.ilvis2) extends PipelineExpr

case class LasReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), spatialreference: String, extra_dims: String, compression: Some[String] = Some("EITHER"), `type`: ExprType = ReaderTypes.las) extends PipelineExpr

case class OptechReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.optech) extends PipelineExpr

case class PgpointcloudReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), table: String, connection: String, column: Some[String] = Some("pa"), schema: String, where: String, spatialreference: String, `type`: ExprType = ReaderTypes.pgpointcloud) extends PipelineExpr

case class PlyReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.ply) extends PipelineExpr

case class PtsReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.pts) extends PipelineExpr

case class QfitReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), flip_coordinates: String, scale_z: Some[Double] = Some(0.001), `type`: ExprType = ReaderTypes.qfit) extends PipelineExpr

case class SbetReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.sbet) extends PipelineExpr

case class TerrasolidReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.terrasolid) extends PipelineExpr

case class TextReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), `type`: ExprType = ReaderTypes.text) extends PipelineExpr

case class TindexReader(log: String, filename: String, count: Some[Double] = Some(1.8446744073709552E19), lyr_name: Some[String] = Some("pdal"), srs_column: Some[String] = Some("srs"), tindex_name: Some[String] = Some("location"), sql: String, bounds: String, wkt: String, t_srs: Some[String] = Some("EPSG:4326"), filter_srs: String, where: String, dialect: Some[String] = Some("OGRSQL"), `type`: ExprType = ReaderTypes.tindex) extends PipelineExpr

case class BpfWriter(log: String, filename: String, compression: String, header_data: String, format: Some[String] = Some("Dimension"), coord_id: Some[Int] = Some(-9999), bundledfile: String, output_dims: String, offset_x: String, offset_y: String, offset_z: String, scale_x: Some[Int] = Some(1), scale_y: Some[Int] = Some(1), scale_z: Some[Int] = Some(1), `type`: ExprType = WriterTypes.bpf) extends PipelineExpr

case class DerivativeWriter(log: String, filename: String, edge_length: Some[Int] = Some(15), primitive_type: Some[String] = Some("slope_d8"), altitude: Some[Int] = Some(45), azimuth: Some[Int] = Some(315), driver: Some[String] = Some("GTiff"), `type`: ExprType = WriterTypes.derivative) extends PipelineExpr

case class GdalWriter(log: String, filename: String, resolution: String, radius: String, gdaldriver: Some[String] = Some("GTiff"), gdalopts: String, output_type: Some[String] = Some("all"), window_size: String, nodata: Some[Int] = Some(-9999), dimension: Some[String] = Some("Z"), `type`: ExprType = WriterTypes.gdal) extends PipelineExpr

case class LasWriter(log: String, filename: String, a_srs: String, compression: Some[String] = Some("None"), discard_high_return_numbers: String, extra_dims: String, forward: String, major_version: Some[String] = Some(""), minor_version: Some[String] = Some(""), dataformat_id: Some[String] = Some(""), format: Some[String] = Some(""), global_encoding: String, project_id: String, system_id: Some[String] = Some("PDAL"), software_id: Some[String] = Some("PDAL 1.4.0 (Releas)"), creation_doy: Some[Int] = Some(40), creation_year: Some[Int] = Some(2017), scale_x: Some[String] = Some(".01"), scale_y: Some[String] = Some(".01"), scale_z: Some[String] = Some(".01"), offset_x: String, offset_y: String, offset_z: String, `type`: ExprType = WriterTypes.las) extends PipelineExpr

case class PgpointcloudWriter(log: String, output_dims: String, offset_x: String, offset_y: String, offset_z: String, scale_x: Some[Int] = Some(1), scale_y: Some[Int] = Some(1), scale_z: Some[Int] = Some(1), connection: String, table: String, column: Some[String] = Some("pa"), schema: String, compression: Some[String] = Some("dimensional"), overwrite: Some[String] = Some("true"), srid: Some[Int] = Some(4326), pcid: String, pre_sql: String, post_sql: String, `type`: ExprType = WriterTypes.pgpointcloud) extends PipelineExpr

case class PlyWriter(log: String, filename: String, storage_mode: Some[String] = Some("default"), `type`: ExprType = WriterTypes.ply) extends PipelineExpr

case class SbetWriter(log: String, filename: String, `type`: ExprType = WriterTypes.sbet) extends PipelineExpr

case class TextWriter(log: String, filename: String, format: Some[String] = Some("csv"), jscallback: String, keep_unspecified: Some[String] = Some("true"), order: String, write_header: Some[String] = Some("true"), newline: Some[String] = Some(""), delimiter: Some[String] = Some(","), quote_header: Some[String] = Some("true"), precision: Some[Int] = Some(3), `type`: ExprType = WriterTypes.text) extends PipelineExpr
      

       