package com.sparja.sonia.filter

import com.sparja.sonia.polynomial.{Coefficient, PolynomialSupport}
import org.apache.commons.math3.complex.Complex
import org.apache.commons.math3.util.FastMath._

object Filter {

  //TODO Should throw exception if cutoffFrequency > sampleFrequency/2
  def butterworthCoefficients(order: Int, cutoffFrequency: Float, sampleFrequency: Float): (List[Float], List[Float]) = {
    val thetas = (1 to order).map(k => (2 * k - 1) * Math.PI / (2 * order))
    val pa = thetas.map(theta => new Complex(-sin(theta), cos(theta)))
    val preWarpedFrequency = sampleFrequency / Math.PI * tan(Math.PI * cutoffFrequency / sampleFrequency)
    val scaledPa = pa.map(_.multiply(2 * Math.PI * preWarpedFrequency))
    val complexOne = new Complex(1, 0)

    val poles = scaledPa.map(p => {
      val num = p.divide(2 * sampleFrequency).add(complexOne)
      val denum = p.divide(2 * sampleFrequency).negate().add(complexOne)
      num.divide(denum)
    }).toList

    val zeros = List.fill(order)(new Complex(-1, 0))
    val a = PolynomialSupport.calculateCoefficients(poles).map(_.getReal)
    val b = PolynomialSupport.calculateCoefficients(zeros).map(_.getReal)
    val k = a.sum / b.sum
    (b.map(_ * k).map(_.toFloat), a.map(_.toFloat))
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
}
