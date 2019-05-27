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

  def acos(z: Complex): Complex = {
    val sqrt1z:Complex = (1 - z.pow(2)).pow(0.5)

    -Complex.i*(z + Complex.i*(sqrt1z)).log

  }
}
