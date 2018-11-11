package com.sparja.syto.filter.chebyshev

import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.filter.butterworth.DigitalButterworthFilter
import org.apache.commons.math3.util.FastMath
import org.apache.commons.math3.util.FastMath.tan

object DigitalChebyshevFirstTypeLowPassFilter {
  def apply(order: Int, rp: Float, cutoffFrequency: Float, sampleFrequency: Float): DigitalChebyshevFirstTypeLowPassFilter =
    new DigitalChebyshevFirstTypeLowPassFilter(order, rp, cutoffFrequency, sampleFrequency)
}

class DigitalChebyshevFirstTypeLowPassFilter(order: Int, rp: Float, cutoffFrequency: Float, sampleFrequency: Float)
  extends DigitalButterworthFilter(order, sampleFrequency){


  val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
  val mu =  FastMath.asinh(1 / eps) / order

 override val normalizedPoles = (1 to order)
    .map(k => (2 * k - 1) * Math.PI / (2 * order))
    .map(theta => Complex(-sin(theta) * Math.sinh(mu), cos(theta) * Math.cosh(mu))).toList


  def preWarpAndTransformToSpecificFilterType(pa: List[Complex]) = {
    //continuous pre-warped frequency
    val preWarpedFrequency = sampleFrequency / Math.PI * tan(Math.PI * cutoffFrequency / sampleFrequency)

    //scale poles by 2*pi*Fc
    pa.map(_ * (2 * Math.PI * preWarpedFrequency)).toList
  }
}
/*
0 = {complex128} (-0.3519383873010394+1.441434427712523j)
1 = {complex128} (-0.9213866598999455+0.8908554688805927j)
2 = {complex128} (-1.1388965451978121-0j)
3 = {complex128} (-0.9213866598999455-0.8908554688805927j)
4 = {complex128} (-0.3519383873010394-1.441434427712523j)
*/