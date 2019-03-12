package com.sparja.syto.util

import breeze.math.Complex
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse

object ComplexAssertion {

  def containAny(actual: List[Complex], expected: List[Complex]): Boolean = {
    expected.map(element => contains(actual, element)).reduce( _ || _)
  }

  def containAll(actual: List[Complex], expected: List[Complex]): Boolean = {
   expected.map(element => contains(actual, element)).reduce( _ && _)
  }

  def isEqual(actual: Complex, expected: Complex, error: Double): Boolean = {
    (actual.abs - expected.abs).abs <  error
  }

  //TODO Consider private modifier
  def contains(actual: List[Complex], element: Complex): Boolean = {
    actual.find(item => isEqual(item, element, 0.01)) match {
      case Some(element) => true
      case None => false
    }
  }

  def assertEqual(actual: Complex, expected: Complex, d: Double) = assertTrue(isEqual(actual, expected, 0.001))

  def assertContainAll(actual: List[Complex], expected: List[Complex]) = assertTrue(containAll(actual, expected))

  def assertContainAny(actual: List[Complex], expected: List[Complex]) = assertTrue(containAny(actual, expected))

  def assertContains(actual: List[Complex], element: Complex) = assertTrue(contains(actual, element))

  def assertNotContain(actual: List[Complex], element: Complex) = assertFalse(contains(actual, element))


}
