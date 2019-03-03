package com.sparja.syto.polynomial.root

import breeze.math.Complex
import org.junit.Test

class WDKTest {

  /*
  [1 2 3]
  1 -> (1 - 2) * (1 - 3) = -1 * -2  = 2
  2 -> (2 - 1) * (2 - 3) = -1
  3 -> (3 - 1) * (3 - 2) = 2
  */

  @Test
  def shouldMultiplyRoots() = {
    val roots = List(Complex.one, Complex.one + 1, Complex.one + 2)
    val roots1 = roots.map(x => (x, roots.filter(_ != x).map(x-_).product))
    println(roots1)
  }

}
