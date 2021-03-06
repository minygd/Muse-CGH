package main

import utilities.{CubicCurve, Vec2}

/**
  * Letter Segment, An extension to CubicCurve with Drawing information
  */
case class LetterSeg(curve: CubicCurve, startWidth: Double, endWidth: Double,
                     alignTangent: Boolean = true, isStrokeBreak: Boolean){
  def setPoint(id: Int, p: Vec2) = {
    val nc = curve.setPoint(id, p)
    this.copy(curve = nc)
  }

  def dragPoint(id: Int, delta: Vec2) = {
    val c = curve.dragPoint(id, delta)
    this.copy(curve = c)
  }

  def connectNext = !isStrokeBreak

  def pointsMap(f: Vec2 => Vec2) = copy(curve = curve.pointsMap(f))

  def dragEndpoints(p0: Vec2, p3: Vec2) = {
    val scale = (p3-p0).length / curve.straightLineLength
    val p1 = (curve.p1 - curve.p0) * scale + p0
    val p2 = (curve.p2 - curve.p3) * scale + p3
    this.copy(curve = CubicCurve(p0,p1,p2,p3))
  }

}

object LetterSeg{
  val minimalWidth = 0.001

  val initWidth = 0.05

  val initCurve = LetterSeg(CubicCurve(Vec2.zero, Vec2(0.5,0), Vec2(0.5,-1), Vec2(1,-1)), initWidth, initWidth, isStrokeBreak = false)
}