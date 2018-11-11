package com.sparja.syto.polynomial

import breeze.math.Complex

case class Coefficient(real: Double, imaginary: Double, degree: Int) {

  val backedValue = Complex(real, imaginary)

  def multiply(p: Coefficient): Coefficient = {
    val ab = backedValue * p.backedValue
    Coefficient(ab.real, ab.imag, p.degree + degree)
  }

  def add(p: Coefficient): Coefficient = {
    val ab = backedValue + p.backedValue
    Coefficient(ab.real, ab.imag, degree)
  }

}
