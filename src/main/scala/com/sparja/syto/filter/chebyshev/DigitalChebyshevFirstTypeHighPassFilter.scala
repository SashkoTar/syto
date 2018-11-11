package com.sparja.syto.filter.chebyshev

import breeze.math.Complex
import breeze.numerics.{cos, sin}

import com.sparja.syto.filter.butterworth.DigitalButterworthFilter
import org.apache.commons.math3.util.FastMath
import org.apache.commons.math3.util.FastMath.tan

object DigitalChebyshevFirstTypeHighPassFilter {
  def apply(order: Int, rp: Float, cutoffFrequency: Float, sampleFrequency: Float): DigitalChebyshevFirstTypeHighPassFilter =
    new DigitalChebyshevFirstTypeHighPassFilter(order, rp, cutoffFrequency, sampleFrequency)
}

class DigitalChebyshevFirstTypeHighPassFilter(order: Int, rp: Float, cutoffFrequency: Float, sampleFrequency: Float)
  extends DigitalButterworthFilter(order, sampleFrequency){

  override val zeros = List.fill(order)(Complex.one)

  val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
  val mu =  FastMath.asinh(1 / eps) / order

  override val normalizedPoles = (1 to order)
    .map(k => (2 * k - 1) * Math.PI / (2 * order))
    .map(theta => Complex(-sin(theta) * Math.sinh(mu), cos(theta) * Math.cosh(mu))).toList


  def preWarpAndTransformToSpecificFilterType(pa: List[Complex]) = {
    //continuous pre-warped frequency
    val preWarpedFrequency = sampleFrequency / Math.PI * tan(Math.PI * cutoffFrequency / sampleFrequency)

    //Transform the normalized analog lowpass poles to analog highpass poles
    pa.map(2 * Math.PI * preWarpedFrequency/_).toList
  }

  override def scaleFactor(a: List[Double], b:List[Double]) = {
    //% amplitude scale factor for gain = 1 at f = fs/2  (z = -1)
    //m= 0:N;
    //K= sum((-1).^m .*a)/sum((-1).^m .*b);        % amplitude scale factor
    val numerator = a.zipWithIndex.map(e => Math.pow(-1, e._2) * e._1)
    val denumerator = b.zipWithIndex.map(e => Math.pow(-1, e._2) * e._1)
    numerator.sum/denumerator.sum
  }
}

