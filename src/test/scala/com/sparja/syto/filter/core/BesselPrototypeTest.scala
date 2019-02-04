package com.sparja.syto.filter.core

import org.junit.Test
import junit.framework.TestCase.assertEquals

class BesselPrototypeTest {

  def calculateRoots(order: Int) = Prototype.bessel(order)

  //@Test
  def shouldCalculateTwoOrderPrototype() = {
    val roots = calculateRoots(2)
    assertEquals(roots.scale, 1.0, 0.001)
    assertEquals(roots.poles(0).real, -0.866025403784, 0.001)
    assertEquals(roots.poles(0).imag, 0.5, 0.001)
    assertEquals(roots.poles(1).real, -0.866025403784, 0.001)
    assertEquals(roots.poles(1).imag, -0.5, 0.001)
  }


}
