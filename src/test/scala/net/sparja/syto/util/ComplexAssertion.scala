package net.sparja.syto.util

import breeze.math.Complex
import junit.framework.TestCase.{assertFalse, assertTrue}
import scala.collection.immutable.Seq

object ComplexAssertion {

  def containAny(actual: Seq[Complex], expected: Seq[Complex]): Boolean = {
    expected.map(element => contains(actual, element)).reduce( _ || _)
  }

  def containAll(actual: Seq[Complex], expected: Seq[Complex]): Boolean = {
   expected.map(element => contains(actual, element)).reduce( _ && _)
  }

  def isEqual(actual: Complex, expected: Complex, error: Double): Boolean = {
    (actual.abs - expected.abs).abs <  error
  }

  //TODO Consider private modifier
  def contains(actual: Seq[Complex], element: Complex): Boolean = {
    actual.find(item => isEqual(item, element, 0.001)) match {
      case Some(element) => true
      case None => false
    }
  }

  def assertEqual(actual: Complex, expected: Complex, d: Double) = assertTrue(isEqual(actual, expected, 0.001))

  def assertContainAll(actual: Seq[Complex], expected: Seq[Complex]) = assertTrue(containAll(actual, expected))

  def assertContainAny(actual: Seq[Complex], expected: Seq[Complex]) = assertTrue(containAny(actual, expected))

  def assertContains(actual: Seq[Complex], element: Complex) = assertTrue(contains(actual, element))

  def assertNotContain(actual: Seq[Complex], element: Complex) = assertFalse(contains(actual, element))


}
