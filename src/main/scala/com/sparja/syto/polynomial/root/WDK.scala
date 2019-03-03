package com.sparja.syto.polynomial.root

import breeze.math.Complex


object WDK {

  def f(x: Complex) =  x.pow(3) - 3 * x.pow(2) + 3 * x - 5

  //x^3 + 3x^2 + 3x + 5
  def f2(x: Complex) = 5 + 3 * x + 3 * x.pow(2) + x.pow(3)

  // x^3 + 100x^2 + 3x + 5
  def f3(x: Complex) = x.pow(3) + 100 * x.pow(2) + 3 * x + 5


  // 1 + 6x + 15x^2 + 15x^3
  // [(-0.2846855768838879-0.2715998514163076j), (-0.43062884623222436+0j), (-0.2846855768838879+0.2715998514163076j)]
  //def y3(x: Complex) = 1 + 6 * x + 15 * x.pow(2) + 15 * x.pow(3)
  def y3(x: Complex) = 1 + 6 * x + 15 * x * x + 15 * x * x * x

  //1 + 10x + 45x^2 + 105x^3 + 105x^4
  // [(-0.1831324805314353-0.23132522602625527j), (-0.31686751946856473-0.09488202514221689j), (-0.31686751946856473+0.09488202514221689j), (-0.1831324805314353+0.23132522602625527j)
  def y4(x: Complex) = 1 + 10 * x + 45 * x.pow(2) + 105 * x.pow(3) + 105 * x.pow(4)

  //1 + 15x + 105x^2 + 420x^3 + 945x^4 + 945x^5
 //[(-0.12803-0.1966j), (-0.2348-0.12209j), (-0.2742+0j), (-0.2348+0.12209j), (-0.12803+0.1966j)]
  def y5(x: Complex) = 1 + 15 * x + 105 * x.pow(2) + 420 * x.pow(3) + 945 * x.pow(4) + 945 * x.pow(5)

  //1 + 15x + 105x^2 + 420x^3 + 945x^4 + x^5
  def y52(x: Complex) = 1 + 15 * x + 105 * x.pow(2) + 420 * x.pow(3) + 945 * x.pow(4) + x.pow(5)

  def makeWDK(aN: Double, esRoots: List[Complex], func: (Complex) => Complex): List[Complex] = {

    esRoots.map(x => (x, esRoots.filter(_ != x).map(x - _).product))
      .map{case(x, root) => x - func(x) / (aN * root)}

  }


  def main(args: Array[String]): Unit = {
    var roots = List(Complex(1, 0), Complex(1, 0.2), Complex(0.5, 0.5))
    for (elem <- 0 to 25) {
      roots = makeWDK(15, roots, y3)
      printRoots(roots)
    }

  }

  def printRoots(roots: List[Complex]) = {
    println(roots
      .zipWithIndex.map { case (root, i) => f"x$i = ${root.real}%.6f + ${root.imag}%.6fi" }
      .mkString("Roots: ", "  |  ", "\n")
    )
  }

  /*
  def makeWDK(p: Complex, q: Complex, r: Complex, func: (Complex) => Complex): (Complex, Complex, Complex) = {
    val pp = p - func(p) / ((p - q) * (p - r))
    val qq = q - func(q) / ((q - pp) * (q - r))
    val rr = r - func(r) / ((r - pp) * (r - qq))
    (pp, qq, rr)
  }

  def main(args: Array[String]): Unit = {
    var p = Complex.one
    var q = Complex(0.4, 0.9)
    var r = q.pow(2)

    for (elem <- 0 to 10) {
      val roots = makeWDK(p, q, r, f2)
      p = roots._1
      q = roots._2
      r = roots._3
      println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
      println(s"p = $p, | q = $q, | r = $r")
    }
  }
  */
}


