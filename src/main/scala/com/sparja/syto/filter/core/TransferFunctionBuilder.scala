package com.sparja.syto.filter.core

import breeze.math.Complex
import com.sparja.syto.polynomial.PolynomialSupport
import org.apache.commons.math3.util.FastMath.tan

object TransferFunctionBuilder {
  def apply: TransferFunctionBuilder = new TransferFunctionBuilder()
}

//Music: Splin, Tantsui.
class TransferFunctionBuilder {


  var prototypesRoots = Roots(List.empty[Complex], List.empty[Complex], 1.0)
  var roots = Roots(List.empty[Complex], List.empty[Complex], 1.0)
  var isDigital = false
  var samplingFrequency = 1.0

  def prototype(f:(Int) => Roots, order: Int) = {
    prototypesRoots = f(order)
    roots = f(order)
    this
  }


  def prototype(f:() => Roots) = {
    prototypesRoots = f()
    roots = f()
    this
  }
  def prototype(f:(Int, Double) => Roots, order: Int, ripple: Double ) = {
    prototypesRoots = f(order, ripple)
    roots = f(order, ripple)
    this
  }




  def transformBilinear(roots: List[Complex]) = {
    roots.map(p => {
      (4 + p) / (4 - p)
    })
  }

  def transformBilinearlyRoots(roots: Roots): Roots = {
    val degree = roots.poles.size - roots.zeros.size
    val fs = 2.0 //TODO What is the magic number???
    val transformedZeros = transformBilinear(roots.zeros):::List.fill(degree)(-Complex.one)
    val transformedPoles = transformBilinear(roots.poles)

    val k = roots.scale * roots.zeros.map(2 * fs - _).product/roots.poles.map(2 * fs - _).product

    Roots(transformedZeros, transformedPoles, k.real)
  }

  def preWrapFrequency(frequency: Double): Double = {
    val fs = 2.0 //TODO What is the magic number???
    val wn = 2 * frequency / samplingFrequency
    2 * fs * tan(Math.PI * wn / fs)
  }

  def transformTo(f:(Roots, Double) => Roots, cutOffFrequency: Double) = {
    if (isDigital) {
      val preWrapedFrequency = preWrapFrequency(cutOffFrequency)
      val analogRoots =  f(prototypesRoots, preWrapedFrequency)
      roots = transformBilinearlyRoots(analogRoots)
    } else {
      roots = f(prototypesRoots, cutOffFrequency)
    }
    this
  }

  def transformTo(f:(Roots, Double, Double) => Roots, lowFrequency: Double, highFrequency: Double) = {
    if (isDigital) {
      val preWrapedLowFrequency = preWrapFrequency(lowFrequency)
      val preWrapedHighFrequency = preWrapFrequency(highFrequency)
      val analogRoots =  f(prototypesRoots, preWrapedLowFrequency, preWrapedHighFrequency)
      roots = transformBilinearlyRoots(analogRoots)
    } else {
      roots = f(prototypesRoots, lowFrequency, highFrequency)
    }
    this
  }

  def transformToHighPass(cutOffFrequency: Double) = {
     transformTo(FilterType.highPass, cutOffFrequency)
  }

  def transformToLowPass(cutOffFrequency: Double) = {
    transformTo(FilterType.lowPass, cutOffFrequency)
  }

  def transformToBandPass(lowFrequency: Double, highFrequency: Double) = {
    transformTo(FilterType.bandPass, lowFrequency, highFrequency)
  }

  def transformToBandStop(lowFrequency: Double, highFrequency: Double) = {
    transformTo(FilterType.bandStop, lowFrequency, highFrequency)
  }

  //TODO a pretty weird solution should be refactored
  def digitalize(sampleFreq: Double) = {
    isDigital = true
    samplingFrequency = sampleFreq
    this
  }


  def coefficients = {
    val b = PolynomialSupport.calculateCoefficients(roots.zeros).map(_.real)
    val a = PolynomialSupport.calculateCoefficients(roots.poles).map(_.real)
    val k = roots.scale
    (b.map(_ * k), a)
  }

}




