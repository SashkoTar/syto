package com.sparja.syto.filter.core

import junit.framework.TestCase.assertEquals
import org.junit.Test

class EllipticPrototypeTest {

  def calculateRoots(order: Int) = Prototype.elliptic(order, 5, 40)

  @Test
  def shouldCalculateTwoOrderPrototype() = {
    val roots = calculateRoots(2)
    assertEquals(roots.scale, 0.0100001249734, 0.001)
    assertEquals(roots.zeros(0).imag, 5.8737717, 0.001)
    assertEquals(roots.zeros(1).imag, -5.873771, 0.001)

    assertEquals(roots.poles(0).real, -0.2266968371, 0.001)
    assertEquals(roots.poles(0).imag, -0.749762526686, 0.001)
    assertEquals(roots.poles(1).real, -0.2266968371, 0.001)
    assertEquals(roots.poles(1).imag,  0.749762526686, 0.001)
  }


}
