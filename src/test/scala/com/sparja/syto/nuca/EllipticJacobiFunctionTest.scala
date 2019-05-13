package com.sparja.syto.nuca

import breeze.numerics.{asin, sin}
import com.sparja.syto.nuca.EllipticFunction.{ellipInc, am}
import junit.framework.TestCase.assertEquals
import org.junit.Test

import scala.math.Pi

class EllipticJacobiFunctionTest {





  @Test
  def shouldCalculateAm02() = {
    val phi = am(0.2, 0.9143)
    assertEquals(phi, 0.198792784, 0.00001)
  }





  /*
  @Test
  def shouldCalclulateIntegral() = {
    val theta = 58 * Pi / 180
    val phi = 90 * Pi / 180
    printAngle("init moduli", theta)
    printAngle("init amplitude", phi)
   // val result = am3/8 * cos(x3)  * sqrt(cos(x2)*cos(x1)/cos(theta))
    println(s"Result is ${ellipInc(theta, phi)}")
  }


  @Test
  def shouldCompareNextAmpl() = {
    val theta =  1.5 * Pi / 180
    val phi = 181 * Pi / 180
    val nextPhi = nextAmpl(theta, phi)
    printAngle("next amplitude", nextPhi)
  }

 */
}
