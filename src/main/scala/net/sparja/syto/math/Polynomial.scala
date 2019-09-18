package net.sparja.syto.math

import breeze.math.Complex
import scala.collection.immutable.Seq

private[syto] class Polynomial(val coefficients: Seq[Coefficient]) {

  //TODO find generic way for different types, implicit?
 def v(x: Double): Double = {
    coefficients.map(c => c.backedValue * Complex(x, 0).pow(c.degree)).sum.real
  }

  def v(x: Complex): Complex = {
    coefficients.map(c => c.backedValue * x.pow(c.degree)).sum
  }

  //Weierstrass root-finding algorithm
  def findRoots: Seq[Complex] = {

    def correct(aN: Double, roots: Seq[Complex], epsilon: Double, func: (Complex) => Complex): Seq[Complex] = {
      val correctedRoots = roots.map(x => (x, roots.filter(_ != x).map(x - _).product))
        .map { case (x, root) => x - func(x) / (aN * root) }
      val maxError = correctedRoots.map(x => func(x).abs).max
      if (maxError < epsilon)
        correctedRoots
      else
        correct(aN, correctedRoots, epsilon, func)
    }

    val complexRoot = Complex(0.4, 0.7) //TODO Check this initial root
    val realRoot = Complex.one

    val maxDegreeCoefficient = this.coefficients.head
    val initialRoots = realRoot :: (1 to maxDegreeCoefficient.degree - 1).map(complexRoot.pow(_)).toList

    correct(maxDegreeCoefficient.real, initialRoots, 0.0001, this.v)
  }


  override def toString = {
    coefficients.map(c => (if (c.real > 0) "+" else "") + s"$c").mkString("")
  }
}

object Polynomial {

  def apply(coefficients: Seq[Coefficient]): Polynomial = new Polynomial(coefficients)

  def calculateCoefficients(roots: Seq[Complex]): Seq[Complex] = {
    val z = Coefficient(1.0, 0.0, 1)
    val r = roots.map(_.unary_-).map(root => Coefficient(root.real, root.imag, 0))
    def multiply(acc: Seq[Coefficient], remainingRoots: Seq[Coefficient]): Seq[Coefficient] = {
      if (remainingRoots.nonEmpty) {
        val zacc = acc.map(_.multiply(z))
        val pacc = acc.map(_.multiply(remainingRoots.head))
        multiply(zacc++pacc, remainingRoots.tail)
      }
      else
        acc.groupBy(_.degree)
          .mapValues(_.reduce((a, b) => a.add(b))).values.toList.sortWith(_.degree > _.degree)
    }
    //TODO Review this calculations
    val coefficients = if (r.isEmpty) List(z) else multiply(List(z, r.head), r.tail)
    coefficients.map(coef => Complex(coef.real, coef.imaginary))
  }

}
