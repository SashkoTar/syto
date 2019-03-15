package com.sparja.syto.polynomial.root

import breeze.math.Complex
import com.sparja.syto.polynomial.Polynomial


object WeierstrassRootFinder {

  def solve(polynomial: Polynomial): List[Complex] = {
    val complexRoot = Complex(0.4, 0.7)
    val realRoot = Complex.one

    val maxDegreeCoefficient = polynomial.coefficients.head
    val initialRoots = realRoot :: (1 to maxDegreeCoefficient.degree - 1).map(complexRoot.pow(_)).toList

    correct(maxDegreeCoefficient.real, initialRoots, 0.0001, polynomial.v)
  }
  //Weierstrass  correction
  def correct(aN: Double, roots: List[Complex], epsilon: Double, func: (Complex) => Complex): List[Complex] = {

  //  printRoots(roots)

    val correctedRoos = roots.map(x => (x, roots.filter(_ != x).map(x - _).product))
      .map { case (x, root) => x - func(x) / (aN * root) }

    val maxError = correctedRoos.map(x => func(x).abs).max

    if (maxError < epsilon)
      correctedRoos
    else
      correct(aN, correctedRoos, epsilon, func)
  }

  def printRoots(roots: List[Complex]) = {
    println(roots
      .zipWithIndex.map { case (root, i) => f"x$i = ${root.real}%.6f + ${root.imag}%.6fi" }
      .mkString("Roots: ", "  |  ", "\n")
    )
  }

}
