package com.sparja.syto.filter.core

import breeze.math.Complex
import breeze.numerics.tan
import com.sparja.syto.filter.FilterSupport

class BandPassTransferFunction(approximation: Approximation, order: Int, lowCutoffFrequency: Float, highCutoffFrequency: Float, sampleFrequency: Float)
  extends DigitalTransferFunction(approximation, order, sampleFrequency){


  def preWarpAndTransformToSpecificFilterType(pa: List[Complex]) = {
    //continuous pre-warped frequency
    val preWarpedLowCutoffFrequencyFrequency = sampleFrequency / Math.PI * tan(Math.PI * lowCutoffFrequency / sampleFrequency)
    val preWarpedHighCutoffFrequencyFrequency = sampleFrequency / Math.PI * tan(Math.PI * highCutoffFrequency / sampleFrequency)
    // Width of band
    val bandWidth = preWarpedHighCutoffFrequencyFrequency - preWarpedLowCutoffFrequencyFrequency
    //the geometric mean of lowCutoffFrequency and highCutoffFrequency
    val wo = Math.sqrt(preWarpedLowCutoffFrequencyFrequency * preWarpedHighCutoffFrequencyFrequency)

    /*
    BW_hz= F2-F1;              % Hz prewarped -3 dB bandwidth
    F0= sqrt(F1*F2);           % Hz geometric mean frequency
    % transform poles for bpf centered at W0
    % note:  alpha and beta are vectors of length N; pa is a vector of length 2N
    alpha= BW_hz/F0 * 1/2*p_lp;
    beta= sqrt(1- (BW_hz/F0*p_lp/2).^2);
    pa= 2*pi*F0*[alpha+j*beta  alpha-j*beta];
    */

    def im(c: Complex):Complex = {
      (1 - (bandWidth/wo*c/2).pow(2)).pow(0.5)
    }

    val scaledPa = pa.map(p => (Math.PI*bandWidth*p, 2 * Math.PI * wo * im(p)))
    val positivePa = scaledPa.map(p => p._1 + p._2 * Complex.i).toList
    val negativePa = scaledPa.map(p => p._1 - p._2 * Complex.i).toList
    positivePa:::negativePa
  }

  override def scaleFactor(a: List[Double], b:List[Double]) = {
    1 / FilterSupport.frequencyResponse(a, b, Math.sqrt(lowCutoffFrequency * highCutoffFrequency), sampleFrequency)
  }

  override val zeros = List.fill(order)(Complex.one.unary_-):::List.fill(order)(Complex.one)
}