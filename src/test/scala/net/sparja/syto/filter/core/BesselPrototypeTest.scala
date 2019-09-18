package net.sparja.syto.filter.core

import breeze.math.Complex
import net.sparja.syto.filter.Approximation
import net.sparja.syto.math.BesselPolynomial
import net.sparja.syto.util.ComplexAssertion
import junit.framework.TestCase.assertEquals
import net.sparja.syto.util.ComplexAssertion
import org.junit.Test

class BesselPrototypeTest {

  def calculateRoots(order: Int) = Approximation.bessel(order)

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
    val roots = BesselPolynomial.calculate(order).findRoots
    val a_last = fallingFactorial(2*order, order) / math.pow(2, order).toInt
    val reversedPoles = roots.map(1/_)


    assertEquals(Approximation.normFactor(reversedPoles, a_last), 1.7556723686, 0.001)
  }




}
