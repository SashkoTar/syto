package com.sparja.syto.filter.butterworth.analog

import breeze.math.Complex
import breeze.numerics.{cos, pow, sin}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport

object AnalogButterworthBandStopFilter {

  def apply(order: Int, lowFreq: Double, highFreq: Double) = new BandStopTransferFunction(order, lowFreq, highFreq)

  class BandStopTransferFunction(order: Int, lowFreq: Double, highFreq: Double)  extends TransferFunction {

    val poles = (1 to order)
      .map(k => (2 * k - 1) * Math.PI / (2 * order))
      .map(theta => Complex(-sin(theta), cos(theta))).toList

    val zeros = List.empty[Complex]

    val scale = 1.0

    val bandWidth = highFreq - lowFreq

    val wo = Math.sqrt(lowFreq * highFreq)

    def transformPoint(point: Complex):Complex = {
      (-bandWidth / point + (bandWidth*bandWidth / (point*point) - 4*wo*wo).pow(0.5))/2
    }

    def transformPoint2(point: Complex):Complex = {
      (-bandWidth / point - (bandWidth*bandWidth / (point*point) - 4*wo*wo).pow(0.5))/2
    }


    override def calculateCoefficients(): (List[Double], List[Double]) = {

      /*
      z_bs = append(z_bs, +1j*wo * ones(degree))
      z_bs = append(z_bs, -1j*wo * ones(degree))

      # Cancel out gain change caused by inversion
      k_bs = k * real(prod(-z) / prod(-p))
      */
      val degree = poles.size - zeros.size

      val transformedPoles = (poles.map(transformPoint) ::: poles.map(transformPoint2)).map(_.unary_-)
      val transformedZeros = List.fill(degree)(wo*Complex.i) ::: List.fill(degree)(wo*Complex.i.unary_-)

      val b = PolynomialSupport.calculateCoefficients(transformedZeros).map(_.real)
      val a = PolynomialSupport.calculateCoefficients(transformedPoles).map(_.real)

      val k = Math.pow(-1, order)* scale * zeros.product/poles.product
      (b.map(_ * k.real), a)
    }
  }

}
