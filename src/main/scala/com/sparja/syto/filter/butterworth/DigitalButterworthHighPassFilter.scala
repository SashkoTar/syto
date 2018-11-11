package com.sparja.syto.filter.butterworth

import breeze.math.Complex
import org.apache.commons.math3.util.FastMath.tan


object DigitalButterworthHighPassFilter {
  def apply(order: Int, cutoffFrequency: Float, sampleFrequency: Float): DigitalButterworthHighPassFilter =
    new DigitalButterworthHighPassFilter(order, cutoffFrequency, sampleFrequency)
}

class DigitalButterworthHighPassFilter(order: Int, cutoffFrequency: Float, sampleFrequency: Float)
  extends DigitalButterworthFilter(order, sampleFrequency) {

  override val zeros = List.fill(order)(Complex.one)

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