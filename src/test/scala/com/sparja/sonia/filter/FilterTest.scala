package com.sparja.sonia.filter

import junit.framework.TestCase.{assertEquals, assertTrue}
import org.junit.Test

class FilterTest {

  @Test
  def shouldCalculateButterworthCoefficients() = {
    val (b, a) = Filter.butterworthCoefficients(2, 3.667f, 30)
    assertEquals(a.head, 1, 0.1)
    assertEquals(a(1), -0.96469328, 0.1)
    assertEquals(a(2), 0.3411719, 0.1)

    assertEquals(b.head, 0.09411965, 0.1)
    assertEquals(b(1), 0.18823931, 0.1)
    assertEquals(b(2), 0.09411965, 0.1)
  }




}
