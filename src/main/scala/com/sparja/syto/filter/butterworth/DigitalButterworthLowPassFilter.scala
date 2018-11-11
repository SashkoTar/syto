package com.sparja.syto.filter.butterworth

import breeze.math.Complex
import org.apache.commons.math3.util.FastMath.tan

object DigitalButterworthLowPassFilter {

  def apply(order: Int, cutoffFrequency: Float, sampleFrequency: Float): DigitalButterworthLowPassFilter =
    new DigitalButterworthLowPassFilter(order, cutoffFrequency, sampleFrequency)

}


class DigitalButterworthLowPassFilter(order: Int, cutoffFrequency: Float, sampleFrequency: Float)
  extends DigitalButterworthFilter(order, sampleFrequency) {

  def preWarpAndTransformToSpecificFilterType(pa: List[Complex]) = {
    //continuous pre-warped frequency
    val preWarpedFrequency = sampleFrequency / Math.PI * tan(Math.PI * cutoffFrequency / sampleFrequency)

    //scale poles by 2*pi*Fc
     pa.map(_ * (2 * Math.PI * preWarpedFrequency)).toList
  }

}

/*
  k= 1:N;
  theta= (2*k -1)*pi/(2*N);
  pa= -sin(theta) + j*cos(theta);     % poles of filter with cutoff = 1 rad/s
    %
  % II.  scale poles in frequency
    Fc= fs/pi * tan(pi*fc/fs);          % continuous pre-warped frequency
    pa= pa*2*pi*Fc;                     % scale poles by 2*pi*Fc
  %
  % III.  Find coeffs of digital filter
  % poles and zeros in the z plane
    p= (1 + pa/(2*fs))./(1 - pa/(2*fs));      % poles by bilinear transform
  q= -ones(1,N);                   % zeros

  % convert poles and zeros to polynomial coeffs
a= poly(p);                   % convert poles to polynomial coeffs a
a= real(a);
b= poly(q);                   % convert zeros to polynomial coeffs b
K= sum(a)/sum(b);             % amplitude scale factor
b= K*b;

*/