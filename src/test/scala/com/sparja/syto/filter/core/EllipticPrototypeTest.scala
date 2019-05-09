package com.sparja.syto.filter.core

import breeze.math.Complex
import com.sparja.syto.common.Math.{PI, cos, sin, sqrt, asin}
import com.sparja.syto.nuca.EllipticFunction.{ellipInc, am}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EllipticPrototypeTest {

  def calculateRoots(order: Int) = Prototype.elliptic(order, 5, 40)

  //@Test
  def shouldCalculateTwoOrderPrototype() = {
    val roots = calculateRoots(2)
    assertEquals(roots.scale, 0.0100001249734, 0.001)
    assertEquals(roots.zeros(0).imag, 5.8737717, 0.001)
    assertEquals(roots.zeros(1).imag, -5.873771, 0.001)

    assertEquals(roots.poles(0).real, -0.2266968371, 0.001)
    assertEquals(roots.poles(0).imag, -0.749762526686, 0.001)
    assertEquals(roots.poles(1).real, -0.2266968371, 0.001)
    assertEquals(roots.poles(1).imag, 0.749762526686, 0.001)
  }

  def sn(u: Double, k: Double) = sin(am(u, k))

  def cn(u: Double, k: Double) = cos(am(u, k))

  def dn(u: Double, k: Double) = sqrt(1 - k * k * sn(u, k) * sn(u, k))

  def cd(u: Double, k: Double) = cn(u, k) / dn(u, k)

  def K(k: Double) = ellipInc(asin(sqrt(k)), PI / 2)

  def findZero(u: Double, k: Double) = Complex.i / (k * cd(u * K(k), k))

  @Test //(0.19748603438284257, 0.9803057003933715, 0.9820089760833982, 0.19879278412406867)
  def printEF() = {
    val k = 0.93
    val u = 0.2
    println(K(k))
   // println(sn(u, k))
   // println(cn(u, k))
   // println(dn(u, k))
   // println(am(u, k))
    println(cd(u * K(k), k))
  }


  @Test
  def shouldFindZeros() = {
    val k = 0.76676
    val u = List(0.2, 0.6)
    //val zeta_i = u.map(cd(_, k))

    val zeros = u.map(findZero(_, k))

    println(zeros)

  }


}
