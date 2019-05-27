package com.sparja.syto.common

import breeze.math.Complex

//import scala.math.{asin, cos, tan, sin}

object Math {

  def sin(x: Double) = scala.math.sin(x)

  def cos(x: Double) = scala.math.cos(x)

  def tan(x: Double) = scala.math.tan(x)

  def asin(x: Double) = scala.math.asin(x)

  def sqrt(x: Double) = scala.math.sqrt(x)

  def pow(x: Double, y: Double) = scala.math.pow(x, y)

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

}
