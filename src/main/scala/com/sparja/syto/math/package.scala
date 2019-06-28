package com.sparja.syto

import breeze.linalg.{DenseMatrix, DenseVector, diag}
import breeze.math.Complex

package object math {

  def sin(x: Double) = scala.math.sin(x)

  def cos(x: Double) = scala.math.cos(x)

  def tan(x: Double) = scala.math.tan(x)

  def asin(x: Double) = scala.math.asin(x)

  def sqrt(x: Double) = scala.math.sqrt(x)

  def pow(x: Double, y: Double) = scala.math.pow(x, y)

  def max(x: Double, y: Double) = scala.math.max(x,y)
  def max(x: Int, y: Int) = scala.math.max(x,y)

  val PI = scala.math.Pi


  private def log(z: Complex) = {
    z.log
  }

  private def sqrt(z: Complex) = {
    z.pow(0.5)
  }


  def acos(z: Complex): Complex = {
    val zz = new org.apache.commons.math3.complex.Complex(z.real, z.imag)
    val result = zz.acos()
    Complex(result.getReal, result.getImaginary)
  }


  def asin(z: Complex): Complex = {
    val zz = new org.apache.commons.math3.complex.Complex(z.real, z.imag)
    val result = zz.asin()
    Complex(result.getReal, result.getImaginary)
  }

  def atan(x: Double) = scala.math.atan(x)

  //TODO Review n calculation,
  def companionByPolynomial(a: List[Double]) = {
    val n  = a.size
    val v1 = diag(DenseVector.ones[Double](n-2))
    val zeros = DenseVector.zeros[Double](n-2).asDenseMatrix.reshape(n-2, 1)
    val v2 = DenseMatrix.horzcat(v1,zeros)
    val aRow = DenseMatrix(a.tail)
    DenseMatrix.vertcat(-aRow, v2)
  }

}
