package com.sparja.syto.polynomial

import breeze.math.Complex

class Polynomial(val coefficients: List[Coefficient]) {

  //TODO find generic way for different types, implicit?
 def v(x: Double): Double = {
    coefficients.map(c => c.backedValue * Complex(x, 0).pow(c.degree)).sum.real
  }

  def v(x: Complex): Complex = {
    coefficients.map(c => c.backedValue * x.pow(c.degree)).sum
  }


  override def toString = {
    coefficients.map(c => (if (c.real > 0) "+" else "") + s"$c").mkString("")
  }
}

object Polynomial {

  def apply(coefficients: List[Coefficient]): Polynomial = new Polynomial(coefficients)

}
