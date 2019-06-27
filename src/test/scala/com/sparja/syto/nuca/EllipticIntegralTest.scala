package com.sparja.syto.nuca


import com.sparja.syto.nuca.EllipticIntegral.ellipInc
import junit.framework.TestCase.assertEquals
import org.junit.Test

import scala.math.Pi

class EllipticIntegralTest {


  def ellipkIncDegree(angle: Double, am: Double) = ellipInc(angle * Pi / 180, am * Pi / 180)

  //Contains values F(phi, k) for first kind of Elliptic integral
  val controlSet:List[(Double, Double, Double)] /*phi, modular angle, u*/ = List(
    (5.0, 60.0, 0.08735),
    (20.0, 60.0, 0.35447),
    (1.0, 54.0, 0.01745),
    (45, 48, 0.83096),
    (45, 24, 0.79768),
    (66, 17, 1.16921),
    (89, 7, 1.55909),
    (90, 6, 1.57511),
    (90, 10, 1.58284),
    (90, 10, 1.58284),
    (0, 30, 0),
    (90, 30, 1.68575),
    (90, 35, 1.73125),
    (89, 35, 1.70994),
    (20, 71, 0.35556),
    (69, 71, 1.56871),
    (88, 71, 2.44370),
    (15.0, 65.0, 0.26428)
  )

  @Test
  def shouldValidateWholeControlSet() = {
    controlSet
      .map(cs =>(ellipkIncDegree(cs._2, cs._1), cs._3))
      .foreach(i => assertEquals(i._1, i._2, 0.0001))
  }

}
