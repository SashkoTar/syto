package com.sparja.syto.polynomial

import org.junit.Test

class BesselPolynomialTest {

  @Test
  def shouldGenerateThreeOrderBesselPolynomial() = {
    val polynomial = BesselPolynomial.calculate(3)
    //println(polynomial)
  }

  @Test
  def shouldGenerateTwoOrderBesselPolynomial() = {
    val polynomial = BesselPolynomial.calculate(2)
   // println(polynomial)
  }

  @Test
  def shouldGenerateOneOrderBesselPolynomial() = {
    val polynomial = BesselPolynomial.calculate(1)
   // println(polynomial)
  }

  @Test
  def shouldGenerateZeroOrderBesselPolynomial() = {
    val polynomial = BesselPolynomial.calculate(0)
   // println(polynomial)
  }

  @Test
  def shouldGenerateFiveOrderBesselPolynomial() = {
    val polynomial = BesselPolynomial.calculate(5)
   // println(polynomial)
  }
}
