package com.sparja.syto.filter.core

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ChebyshevIIPrototypeTest {

  @Test
  def shouldFindZerosAndPoles() = {
    val roots = Prototype.chebyshevII(2, 10f)
    assertEquals(roots.zeros.size, 2)
    assertEquals(roots.zeros(0).real, 0, 0.001)
    assertEquals(roots.zeros(0).imag, 1.4142, 0.001)
    assertEquals(roots.zeros(1).real, 0, 0.001)
    assertEquals(roots.zeros(1).imag, -1.4142, 0.001)

    assertEquals(roots.poles.size, 2)
    assertEquals(roots.poles(0).real, -0.465002, 0.001)
    assertEquals(roots.poles(0).imag, 0.64515, 0.001)
    assertEquals(roots.poles(1).real, -0.465002, 0.001)
    assertEquals(roots.poles(1).imag, -0.64515, 0.001)
  }


  @Test
  def shouldFindZeros() = {
    val roots = Prototype.chebyshevII(3, 10f)
    assertEquals(roots.zeros.size, 2)
    assertEquals(roots.zeros(0).real, 0, 0.001)
    assertEquals(roots.zeros(0).imag, 1.15470054, 0.001)
    assertEquals(roots.zeros(1).real, 0, 0.001)
    assertEquals(roots.zeros(1).imag, -1.15470054, 0.001)
  }


  @Test
  def shouldFindZerosFiveOrder() = {
    val roots = Prototype.chebyshevII(5, 10f)
    assertEquals(roots.zeros.size, 4)
    assertEquals(roots.zeros(0).imag, 1.05146222, 0.001)
    assertEquals(roots.zeros(1).imag, 1.70130162, 0.001)
    assertEquals(roots.zeros(2).imag, -1.05146222, 0.001)
    assertEquals(roots.zeros(3).imag, -1.70130162, 0.001)
  }

}
