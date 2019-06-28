package com.sparja.syto.filter.core

import breeze.math.Complex
import com.sparja.syto.filter.Prototype
import com.sparja.syto.util.ComplexAssertion
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EllipticPrototypeTest {

  def calculateRoots(order: Int) = Prototype.elliptic(order, 1, 45)

  @Test
  def shouldCalculateThreeOrderPrototype() = {
    val roots = calculateRoots(3) //TODO check the accuracy

    val expectedZeros = List(Complex(0, 3.30569332109), Complex(0, -3.30569332109))
    ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

    val expectedPoles = List(Complex(-0.514143577259, 0), Complex(-0.233500784754, -0.973362052005), Complex(-0.233500784754, 0.973362052005))
    ComplexAssertion.assertContainAll(roots.poles, expectedPoles)

    assertEquals(roots.scale, 0.0471420077504, 0.0001) //TODO check the accuracy, it's quite low for now
  }


  @Test
  def shouldCalculateFourOrderPrototype() = {
    val roots = calculateRoots(4) //TODO check the accuracy
    val expectedZeros = List(Complex(0, 4.05301771243), Complex(0, 1.80715694465), Complex(0, -4.05301771243), Complex(0, -1.80715694465))
    ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

    val expectedPoles = List(Complex(-0.35789, -0.4602247), Complex(-0.113251, -0.9915598), Complex(-0.35789, 0.4602247), Complex(-0.113251, 0.9915598))
    ComplexAssertion.assertContainAll(roots.poles, expectedPoles)

    assertEquals(roots.scale, 0.00562418320487, 0.000001)
  }

  @Test
  def shouldCalculateFiveOrderPrototype() = {
    val roots = calculateRoots(5)
    val expectedZeros = List(Complex(0, 1.94559171398), Complex(0, 1.34685649439), Complex(0, -1.94559171398), Complex(0, -1.34685649439))
    ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

    val expectedPoles = List(Complex(-0.364113, 0), Complex(-0.22392, -0.715617), Complex(-0.05673, -0.99707), Complex(-0.22392, 0.715617), Complex(-0.05673, 0.99707))
    ComplexAssertion.assertContainAll(roots.poles, expectedPoles)

    assertEquals(roots.scale, 0.0297358859419, 0.00001)
  }



}
