package com.sparja.sonia.polynomial

import breeze.math.Complex

case class Coefficient(real: Double, imaginary: Double, degree: Int) {
  def multiply(p: Coefficient): Coefficient = {
    val a = Complex(real, imaginary)
    val b = Complex(p.real, p.imaginary)
    val ab = a * b
    Coefficient(ab.real, ab.imag, p.degree + degree)
  }

  //add check if degrees of operands are the same
  def add(p: Coefficient): Coefficient = {
    val a = Complex(real, imaginary)
    val b = Complex(p.real, p.imaginary)
    val ab = a + b
    Coefficient(ab.real, ab.imag, degree)
  }

}
