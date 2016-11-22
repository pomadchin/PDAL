package io.pdal

import java.nio.ByteBuffer

class PointView extends Native {
  def getPackedPointWithMetadata(idx: Long, metadata: String, schema: String): PackedPoints =
    getPackedPoint(idx).copy(metadata = metadata, schema = schema)
  def getPackedPoint(idx: Long): PackedPoints = getPackedPoint(idx, layout.dimTypes())
  def getPackedPoint(idx: Long, dims: Array[DimType]): PackedPoints =
    PackedPoints(
      bytes       = getPackedPointBytes(idx, dims),
      dimTypes    = layout.toSizedDimTypes(dims)
    )

  def getPackedPointsWithMetadata(metadata: String, schema: String): PackedPoints =
    getPackedPoints.copy(metadata = metadata, schema = schema)
  def getPackedPoints: PackedPoints = getPackedPoints(layout.dimTypes())
  def getPackedPoints(dims: Array[DimType]): PackedPoints =
    PackedPoints(
      bytes       = getPackedPointsBytes(dims),
      dimTypes    = layout.toSizedDimTypes(dims)
    )

  def getPackedPointBytes(idx: Long): Array[Byte] = getPackedPointBytes(idx, layout.dimTypes())
  def getPackedPointsBytes: Array[Byte] = getPackedPointsBytes(layout.dimTypes())
  def findDimType(name: String): DimType = layout.findDimType(name)
  def length: Int = size()
  def getCrsWKT(mode_flag: Int): String = getCrsWKT(mode_flag, pretty = false)
  def getCrsWKT: String = getCrsWKT(1, pretty = false)

  /**
    * Reads a packed point by point id from a set of packed points.
    */
  def get(idx: Int, packedPoints: Array[Byte]): Array[Byte] = get(idx, packedPoints, layout.dimTypes())
  def get(idx: Int, packedPoints: Array[Byte], dims: Array[DimType]): Array[Byte] = {
    val pointSize = dims.map(layout.dimSize(_)).sum
    val from = (idx * pointSize).toInt
    val to = {
      val t = (from + pointSize).toInt
      if(t > length) length else t
    }

    packedPoints.slice(from, to)
  }

  /**
    * Reads dim from a packed point, point should contain all layout dims.
    */
  def get(packedPoint: Array[Byte], dim: DimType): ByteBuffer = {
    val from = layout.dimPackedOffset(dim).toInt
    val to = from + layout.dimSize(dim).toInt
    ByteBuffer.wrap(packedPoint.slice(from, to))
  }
  /**
    * One dimension read; for multiple dims custom logic required.
    */
  def get(idx: Int, dim: String): ByteBuffer = get(idx, findDimType(dim))
  def get(idx: Int, dim: DimType): ByteBuffer =
    ByteBuffer.wrap(getPackedPointBytes(idx, Array(dim)))

  def getX(idx: Int): Double = get(idx, DimType.X).getDouble
  def getY(idx: Int): Double = get(idx, DimType.Y).getDouble
  def getZ(idx: Int): Double = get(idx, DimType.Z).getDouble

  def getX(packedPoint: Array[Byte]): Double = get(packedPoint, DimType.X).getDouble
  def getY(packedPoint: Array[Byte]): Double = get(packedPoint, DimType.Y).getDouble
  def getZ(packedPoint: Array[Byte]): Double = get(packedPoint, DimType.Z).getDouble

  @native def layout: PointLayout
  @native def size(): Int
  @native def empty(): Boolean
  @native def getCrsProj4: String
  @native def getCrsWKT(mode_flag: Int, pretty: Boolean): String
  @native def getPackedPointBytes(idx: Long, dims: Array[DimType]): Array[Byte]
  @native def getPackedPointsBytes(dims: Array[DimType]): Array[Byte]
  @native def dispose(): Unit
}
