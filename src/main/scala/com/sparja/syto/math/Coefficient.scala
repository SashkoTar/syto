package com.sparja.syto.math

import breeze.math.Complex

private[math] case class Coefficient(real: Double, imaginary: Double, degree: Int) {

  val backedValue = Complex(real, imaginary)

  def multiply(p: Coefficient): Coefficient = {
    val ab = backedValue * p.backedValue
    Coefficient(ab.real, ab.imag, p.degree + degree)
  }

  def add(p: Coefficient): Coefficient = {
    val ab = backedValue + p.backedValue
    Coefficient(ab.real, ab.imag, degree)
  }

  override def toString = {
    degree  match {
      case 0 => s"$real"
      case 1 => s"${real}x"
      case _ => s"${real}x^${degree}"
    }
  }
}
