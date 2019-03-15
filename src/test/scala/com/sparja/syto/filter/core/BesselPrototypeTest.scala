package com.sparja.syto.filter.core

import breeze.math.Complex
import com.sparja.syto.polynomial.BesselPolynomial
import com.sparja.syto.polynomial.root.WeierstrassRootFinder
import com.sparja.syto.util.ComplexAssertion
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BesselPrototypeTest {

  def calculateRoots(order: Int) = Prototype.bessel(order)

  @Test
  def shouldCalculateTwoOrderPrototype() = {
    val expectedPoles = List(Complex(-0.8660, 0.5), Complex(-0.8660, -0.5))
    val roots = calculateRoots(2)
    assertEquals(roots.scale, 1.0, 0.001)
    ComplexAssertion.assertContainAll(roots.poles, expectedPoles)
  }

  @Test
  def shouldCalculateFiveOrderPrototype() = {
    val expectedPoles = List(Complex(-0.5905, 0.9072), Complex(-0.8515, 0.4427), Complex(-0.9264, 0.0), Complex(-0.5905, -0.9072), Complex(-0.8515, -0.4427))

    val roots = calculateRoots(5)
    assertEquals(roots.scale, 1.0, 0.001)
    ComplexAssertion.assertContainAll(roots.poles, expectedPoles)

  }

  @Test
  def shouldCalculateNormFactor() = {

    def fallingFactorial(x: Int, n: Int) = (0 to n-1).map(x - _).product

    val order = 3
    val pol = BesselPolynomial.calculate(order)
    val roots = WeierstrassRootFinder.solve(pol)
    val a_last = fallingFactorial(2*order, order) / math.pow(2, order).toInt
    val reversedPoles = roots.map(1/_)


    assertEquals(Prototype.normFactor(reversedPoles, a_last), 1.7556723686, 0.001)
  }




}
