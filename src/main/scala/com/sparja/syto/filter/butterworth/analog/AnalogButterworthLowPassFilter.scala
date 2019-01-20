package com.sparja.syto.filter.butterworth.analog

import breeze.math.Complex
import breeze.numerics.{cos, pow, sin}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport

object AnalogButterworthLowPassFilter {

  def apply(order: Int, cutOffFreq: Float):LowPassTransferFunction  = new LowPassTransferFunction(order, cutOffFreq)

  class LowPassTransferFunction(order: Int, cutOffFreq: Float)  extends TransferFunction {

    // poles of filter with cutoff = 1 rad/s
    val poles = (1 to order)
      .map(k => (2 * k - 1) * Math.PI / (2 * order))
      .map(theta => Complex(-sin(theta), cos(theta))).map(_ * cutOffFreq).toList

    override def calculateCoefficients(): (List[Double], List[Double]) = {
      val b = List(1.0).map(_ * pow(cutOffFreq, order))
      val a = PolynomialSupport.calculateCoefficients(poles).map(_.real)
      (b, a)
    }
  }

}
