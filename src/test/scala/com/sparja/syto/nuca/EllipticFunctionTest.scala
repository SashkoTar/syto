package com.sparja.syto.nuca

import breeze.math.Complex
import breeze.numerics.sin
import com.sparja.syto.polynomial.PolynomialSupport
import junit.framework.TestCase.assertEquals
import com.sparja.syto.nuca.EllipticFunction.{ellipk, ellipInc}
import org.junit.Test

class EllipticFunctionTest {

  def ellipkDegree(angle: Double) = ellipk(sin(angle * math.Pi / 180))

  def ellipkIncDegree(angle: Double, am: Double) = ellipInc(sin(angle * math.Pi / 180), am * math.Pi / 180)

  @Test
  def shouldCalculateCompleteFirstKind = {
    def f = ellipk(0.5) //sin 30
    assertEquals(f, 1.68575, 0.001)
  }

  @Test
  def shouldCalculateCompleteFirstKind45 = {
    def f = ellipkDegree(45)
    assertEquals(f, 1.8541, 0.001)
  }

  @Test
  def shouldCalculateCompleteFirstKind60 = {
    def f = ellipkDegree(60)
    assertEquals(f, 2.1565, 0.001)
  }

  @Test
  def shouldCalculateIncompleteCompleteFirstKind90 = {
    def f = ellipkIncDegree(60, 90)
    assertEquals(f, 2.1565, 0.001)
  }

  @Test
  def shouldCalculateIncompleteCompleteFirstKind60 = {
    def f = ellipkIncDegree(60, 40)
    assertEquals(f, 1.1226, 0.001)
  }



}
