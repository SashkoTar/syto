package com.sparja.syto.filter.butterworth.analog

import breeze.math.Complex
import breeze.numerics.{cos, pow, sin}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport

object AnalogButterworthHighPassFilter {

  def apply(order: Int, cutOffFreq: Double) = new HighPassTransferFunction(order, cutOffFreq)

  class HighPassTransferFunction(order: Int, cutOffFreq: Double)  extends TransferFunction {

    //TODO Move Poles transformation to calculateCoefficient
    val poles = (1 to order)
      .map(k => (2 * k - 1) * Math.PI / (2 * order))
      .map(theta => Complex(-sin(theta), cos(theta))).map(cutOffFreq / _).toList

    val zeros = List.fill(order)(Complex.zero)

    override def calculateCoefficients(): (List[Double], List[Double]) = {
      val b = PolynomialSupport.calculateCoefficients(zeros).map(_.real)
      val a = PolynomialSupport.calculateCoefficients(poles).map(_.real)

      (b, a)
    }
  }

}
