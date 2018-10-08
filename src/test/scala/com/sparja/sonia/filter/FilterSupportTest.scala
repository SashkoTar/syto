package com.sparja.sonia.filter

import org.junit.Test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue


class FilterSupportTest {

  @Test
  def shouldCalculate() = {
    val result = FilterSupport.buttap(5)
    assertTrue(result._1.isEmpty)
    assertEquals(result._2.size, 5)
    assertEquals(result._3, 1)
  }

}
