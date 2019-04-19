package com.sparja.syto.nuca


import com.sparja.syto.nuca.EllipticFunction.{ellipInc, ellipk, nextAm}
import junit.framework.TestCase.assertEquals
import org.junit.Test

import scala.math.{Pi, asin, sin, tan, cos}

class EllipticFunctionTest {

  def ellipkDegree(angle: Double) = ellipk(sin(angle * Pi / 180))

  def ellipkIncDegree(angle: Double, am: Double) = ellipInc(sin(angle * math.Pi / 180), am * math.Pi / 180)

  @Test
  def shouldCalculateCompleteFirstKind = {
    def f = ellipk(0.5) //sin 30
    assertEquals(f, 1.68575, 0.001)
  }

  @Test
  def shouldCalculateCompleteFirstKind45 = {
    def f = ellipkDegree(45)

    assertEquals(f, 1.8541, 0.001)
  }

  @Test
  def shouldCalculateCompleteFirstKind60 = {
    def f = ellipkDegree(60)

    assertEquals(f, 2.1565, 0.001)
  }

  @Test
  def shouldCalculateIncompleteCompleteFirstKind90 = {
    def f = ellipkIncDegree(60, 90)

    assertEquals(f, 2.1565, 0.001)
  }

  @Test
  def shouldCalculateIncompleteCompleteFirstKind60 = {
    def f = ellipkIncDegree(60, 40)

    assertEquals(f, 1.1226, 0.001)
  }

  @Test
  def shouldCalculateIncompleteCompleteFirstKind45 = {
    def f = ellipkIncDegree(45, 30)

    assertEquals(f, 0.53562, 0.001)
  }


  @Test
  def calculatePhi() = {
    println(nextAm(1 / math.sqrt(2), math.Pi / 6))
  }

  def nextTheta(phi: Double) = asin(tan(phi / 2) * tan(phi / 2))

  def printAngle(angle: Double) = {
    val degrees=angle * 180 / math.Pi
    val d = degrees.asInstanceOf[Int] // Truncate the decimals
    val t1 = (degrees - d) * 60
    val m = t1.asInstanceOf[Int]
    val s = (t1 - m) * 60

    //println(s"Radians = $angle, DegreesDecimal=$degrees, Degrees=$d $m' $s''")
    println(s"Degrees=$d $m' ")
  }

  def nextAmpl(theta: Double, am: Double) = {
    val a = cos(theta)*tan(am)
    val b =  math.atan(a)
    val c = b + am
    c
    // c * 180 / math.Pi
  }

  @Test
  def shouldPrint() = {
    val theta = Pi / 4
    val phi = Pi / 6

    val x = nextTheta(theta)
    val am1 = nextAmpl(theta, phi)
    printAngle(x)
    printAngle(am1)
    println("-----------------------------------------------------")

    val x2 = nextTheta(x)
    val am2 = nextAmpl(x, am1)
    printAngle(x2)
    printAngle(am2)
    println("-----------------------------------------------------")

    val x3 = nextTheta(x2)
    val am3 = nextAmpl(x2, am2)
    printAngle(x3)
    printAngle(am3)
    println("-----------------------------------------------------")

    val x4 = nextTheta(x3)
    val am4 = nextAmpl(x3, am3)
    printAngle(x4)
    printAngle(am4)

  }

}
