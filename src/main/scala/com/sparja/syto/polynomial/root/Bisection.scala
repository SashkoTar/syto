package com.sparja.syto.polynomial.root

class Bisection {



}

object Bisection {

  def findRoot(xLow: Double, xUp: Double, f:(Double) => Double): Double = {
    val xRoot = (xLow + xUp) / 2
    val E = 100 * (xUp - xLow)/(xUp + xLow)
    println(s"Current Root=$xRoot, f($xRoot)=${f(xRoot)}, xLow=$xLow, xUp=$xUp, Epsilon=$E")

    if (E < 1) return  xRoot
    val fx = f(xRoot)
    fx match {
      case 0 =>  xRoot
      case fx if fx * f(xLow) < 0 => findRoot(xLow, xRoot, f)
      case fx if fx * f(xUp) < 0 => findRoot(xRoot, xUp, f)
    }

  }


  def func(x: Double) = Math.exp(x) - 3 * x

  def main(args: Array[String]): Unit = {
   val root = findRoot(0, 1, func)
    println(s"Final root $root")
  }

}
