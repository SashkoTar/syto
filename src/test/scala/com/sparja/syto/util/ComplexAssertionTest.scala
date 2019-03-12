package com.sparja.syto.util

import breeze.math.Complex
import org.junit.Test
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse

class ComplexAssertionTest {

  @Test
  def shouldBeEqual() = {
    val expected = Complex(0.9, 0.9)
    val actual = Complex(0.9001, 0.9001)
    ComplexAssertion.assertEqual(actual, expected, 0.001)
  }


  @Test
  def shouldContain() = {
    val actual = List(Complex(0.9, 0.9), Complex(1.9, 1.9))
    val element = Complex(0.9, 0.9)
    ComplexAssertion.assertContains(actual, element)
  }

  @Test
  def shouldNotContain() = {
    val actual = List(Complex(0.9, 0.9), Complex(1.9, 1.9))
    val element = Complex(1.0, 1.0)
    ComplexAssertion.assertNotContain(actual, element)
  }


  @Test
  def shouldContainAll() = {
    val actual = List(Complex(0.9, 0.9), Complex(1.9, 1.9), Complex(2.9, 2.9), Complex(3.9, 3.9))
    val expected = List(Complex(1.9, 1.9), Complex(2.9, 2.9))
    ComplexAssertion.assertContainAll(actual, expected)

  }

  @Test
  def shouldFailForContainAll() = {
    val actual = List(Complex(0.9, 0.9), Complex(1.9, 1.9), Complex(2.9, 2.9), Complex(3.9, 3.9))
    val expected = List(Complex(1.9, 1.9), Complex(2.9, 2.8))
    assertFalse(ComplexAssertion.containAll(actual, expected))
  }

  @Test
  def shouldContainAny() = {
    val actual = List(Complex(0.9, 0.9), Complex(1.9, 1.9), Complex(2.9, 2.9), Complex(3.9, 3.9))
    val expected = List(Complex(1.9, 1.9), Complex(2.9, 2.8))
    ComplexAssertion.assertContainAny(actual, expected)
    assertTrue(ComplexAssertion.containAny(actual, expected))
  }

  @Test
  def shoulFailContainAny() = {
    val actual = List(Complex(0.9, 0.9), Complex(1.9, 1.9), Complex(2.9, 2.9), Complex(3.9, 3.9))
    val expected = List(Complex(1.9, 1.8), Complex(2.9, 2.8))
    assertFalse(ComplexAssertion.containAny(actual, expected))
  }

}
