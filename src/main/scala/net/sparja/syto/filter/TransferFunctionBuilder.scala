package net.sparja.syto.filter

import breeze.math.Complex
import net.sparja.syto.math.Polynomial
import net.sparja.syto.math.tan
import net.sparja.syto.math.Polynomial

import scala.collection.immutable.Seq

object TransferFunctionBuilder {
  def apply: TransferFunctionBuilder = new TransferFunctionBuilder()
}


class TransferFunctionBuilder {

  private var prototypesRoots = Roots(List.empty[Complex], List.empty[Complex], 1.0)
  private var roots = Roots(List.empty[Complex], List.empty[Complex], 1.0)
  private var isDigital = false
  private var samplingFrequency = 1.0


  private def doBilinearTransformation(roots: Seq[Complex]) = {
    roots.map(p => {
      (4 + p) / (4 - p)
    })
  }

  private def doBilinearTransformation(roots: Roots): Roots = {
    val degree = roots.poles.size - roots.zeros.size
    val fs = 2.0 //TODO What is the magic number???
    val transformedZeros = doBilinearTransformation(roots.zeros) ++ List.fill(degree)(-Complex.one)
    val transformedPoles = doBilinearTransformation(roots.poles)

    val k = roots.scale * roots.zeros.map(2 * fs - _).product / roots.poles.map(2 * fs - _).product

    Roots(transformedZeros, transformedPoles, k.real)
  }

  private def preWrapFrequency(frequency: Double): Double = {
    val fs = 2.0 //TODO What is the magic number???
    val wn = 2 * frequency / samplingFrequency
    2 * fs * tan(Math.PI * wn / fs)
  }

  private def transformTo(f: (Roots, Double) => Roots, cutOffFrequency: Double) = {
    if (isDigital) {
      val preWrapedFrequency = preWrapFrequency(cutOffFrequency)
      val analogRoots = f(prototypesRoots, preWrapedFrequency)
      roots = doBilinearTransformation(analogRoots)
    } else {
      roots = f(prototypesRoots, cutOffFrequency)
    }
    this
  }

  private def transformTo(f: (Roots, Double, Double) => Roots, lowFrequency: Double, highFrequency: Double) = {
    if (isDigital) {
      val preWrapedLowFrequency = preWrapFrequency(lowFrequency)
      val preWrapedHighFrequency = preWrapFrequency(highFrequency)
      val analogRoots = f(prototypesRoots, preWrapedLowFrequency, preWrapedHighFrequency)
      roots = doBilinearTransformation(analogRoots)
    } else {
      roots = f(prototypesRoots, lowFrequency, highFrequency)
    }
    this
  }

  /** This method sets transfer transfer function to use Butterworth approximation
    *  @param order desired order of normalized analog filter
    *  @return  TransferFunctionBuilder prepared to generate Butterworth normalized analog filter
    *  @author  Oleksandr Tarasenko
    */
  def butterworthApproximation(order: Int) = {
    prototypesRoots = Approximation.butterworth(order)
    roots = Approximation.butterworth(order)
    this
  }

  /** This method sets transfer transfer function to use Bessel approximation
    *  @param order desired order of normalized analog filter
    *  @return  TransferFunctionBuilder prepared to generate Bessel normalized analog filter
    *  @author  Oleksandr Tarasenko
    */
  def besselApproximation(order: Int, norm: String = "phase"): TransferFunctionBuilder = {
    prototypesRoots = Approximation.bessel(order, norm)
    roots = Approximation.bessel(order, norm)
    this
  }

  /** This method sets transfer transfer function to use Chebyshev approximation
    *  @param order desired order of normalized analog filter
    *  @param ripple The maximum ripple allowed below unity gain in the passband. Specified in decibels, as a positive number.
    *  @return  TransferFunctionBuilder prepared to generate Chebyshev normalized analog filter
    *  @author  Oleksandr Tarasenko
    */
  def chebyshevApproximation(order: Int, ripple: Double) = {
    prototypesRoots = Approximation.chebyshev(order, ripple)
    roots = Approximation.chebyshev(order, ripple)
    this
  }

  /** This method sets transfer transfer function to use Chebyshev II approximation. This type of
    * an approximation is also know as Chebyshev inverse approximation
    *  @param order desired order of normalized analog filter
    *  @param ripple The minimum attenuation required in the stop band. Specified in decibels, as a positive number.
    *  @return  TransferFunctionBuilder prepared to generate Chebyshev II normalized analog filter
    *  @author  Oleksandr Tarasenko
    */
  def chebyshevIIApproximation(order: Int, ripple: Double) = {
    prototypesRoots = Approximation.chebyshevII(order, ripple)
    roots = Approximation.chebyshev(order, ripple)
    this
  }


  /** This method sets transfer transfer function to use elliptic approximation. This type of
    * an approximation is also know as Cauer approximation
    *  @param order desired order of normalized analog filter
    *  @param ripplePass The maximum ripple allowed below unity gain in the passband. Specified in decibels, as a positive number.
    *  @param rippleStop The minimum attenuation required in the stop band. Specified in decibels, as a positive number.
    *  @return  TransferFunctionBuilder prepared to generate elliptic normalized analog filter
    *  @author  Oleksandr Tarasenko
    */
  def ellipticApproximation(order: Int, ripplePass: Double, rippleStop: Double) = {
    prototypesRoots = Approximation.elliptic(order, ripplePass, rippleStop)
    roots = Approximation.elliptic(order, ripplePass, rippleStop)
    this
  }

  /** This method transforms normalized analog filter to high pass filter.
    *  @param cutOffFrequency desired cutoff, as angular frequency (rad/sec) for analog filter or Hz for digital one
    *  @author  Oleksandr Tarasenko
    *  @return  TransferFunctionBuilder prepared to generate high pass filter coefficients.
    */
  def transformToHighPass(cutOffFrequency: Double) = {
    transformTo(FilterType.highPass, cutOffFrequency)
  }

  /** This method transforms normalized analog filter to low pass filter.
    *  @param cutOffFrequency desired cutoff, as angular frequency (rad/sec) for analog filter or Hz for digital one
    *  @return  TransferFunctionBuilder prepared to generate low pass filter coefficients
    *  @author  Oleksandr Tarasenko
    */
  def transformToLowPass(cutOffFrequency: Double) = {
    transformTo(FilterType.lowPass, cutOffFrequency)
  }

  /** This method transforms normalized analog filter to bandpass filter.
    *  @param lowFrequency the lower boundary of bandpass, as angular frequency (rad/sec) for analog filter or Hz for digital one
    *  @param highFrequency the higher boundary of bandpass, as angular frequency (rad/sec) for analog filter or Hz for digital one
    *  @return   TransferFunctionBuilder prepared to generate band pass filter coefficients
    *  @author  Oleksandr Tarasenko
    */
  def transformToBandPass(lowFrequency: Double, highFrequency: Double) = {
    transformTo(FilterType.bandPass, lowFrequency, highFrequency)
  }

  /** This method transforms normalized analog filter to bandstop filter.
    *  @param lowFrequency the lower boundary of bandstop, as angular frequency (rad/sec) for analog filter or Hz for digital one
    *  @param highFrequency the higher boundary of bandstop, as angular frequency (rad/sec) for analog filter or Hz for digital one
    *  @return   TransferFunctionBuilder prepared to generate bandstop filter coefficients
    *  @author  Oleksandr Tarasenko
    */
  def transformToBandStop(lowFrequency: Double, highFrequency: Double) = {
    transformTo(FilterType.bandStop, lowFrequency, highFrequency)
  }

  //TODO a pretty weird solution should be refactored
  /** This method sets sampling frequency. It is mandatory method if designed filter is expected to be digital
    *  @param sampleFreq sampling frequency of input signal, Hz
    *  @return   TransferFunctionBuilder prepared to generate digital filter
    *  @author  Oleksandr Tarasenko
    */
  def digitalize(sampleFreq: Double) = {
    isDigital = true
    samplingFrequency = sampleFreq
    this
  }


  /** It's a final method in design chain. The method returns transfer function as
    *  numerator and denominator of polynomials coefficients  of designed filter.
    *  @return   (Seq[Double], Seq[Double]) - numerator and denominator respectively
    *  @author  Oleksandr Tarasenko
    */
  def coefficients = {
    val b = Polynomial.calculateCoefficients(roots.zeros).map(_.real)
    val a = Polynomial.calculateCoefficients(roots.poles).map(_.real)
    val k = roots.scale
    (b.map(_ * k), a)
  }

}




