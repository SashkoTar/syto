package com.sparja.syto.math

import breeze.math.Complex
import com.sparja.syto.math.JacobiEllipticFunction._
import com.sparja.syto.util.ComplexAssertion
import junit.framework.TestCase.assertEquals
import org.junit.Test

class JacobiEllipticFunctionTest {


  @Test
  def shouldCalculateAm02() = {
    val phi = am(0.2, 0.9143)
    assertEquals(phi, 0.198792784, 0.0004)
  }


  @Test
  def shouldCalculateSnForComplex() = {
    //sn(2.5-1.5i, 0.76676^2)
    val z = snComp(Complex(2.5, -1.5), 0.76676)
    ComplexAssertion.assertEqual(z, Complex(1.3610, 0.1019), 0.001)
  }

  @Test
  def shouldCalculateCnForComplex() = {
    //cn(2.5-1.5i, 0.76676^2)
    val z = cnComp(Complex(2.5, -1.5), 0.76676)
    ComplexAssertion.assertEqual(z, Complex(-0.1492, 0.9296), 0.001)
  }

  @Test
  def shouldCalculateCDForComplex() = {
    //cd(2.5-1.5i, 0.76676^2)
    val z = cdComp(Complex(2.5, -1.5), 0.76676)
    ComplexAssertion.assertEqual(z, Complex(-2.0345, 0.8389), 0.001)
  }

  //@Test TODO Investigate this outputs
  def shouldCalculateArcSNForComplex() = {
    //InverseJacobiSN(i/0.50885, 0.0028615^2)
    val z = asn(Complex.i/0.50885, 0.0028615)
    ComplexAssertion.assertEqual(z, Complex(0, 1.4279), 0.01)
  }


}
