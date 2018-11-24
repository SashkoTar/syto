package com.sparja.syto.filter.chebyshev

import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.filter.core.{Approximation, LowPassTransferFunction, TransferFunction}

object DigitalChebyshevSecondTypeLowPassFilter {
  def apply(order: Int, rp: Float, cutoffFrequency: Float, sampleFrequency: Float): TransferFunction = {
    val approximation = new ChebyshevSecondTypeApproximation(order, rp)
    new SecondTypeLowPassTransferFunction(approximation, order, cutoffFrequency, sampleFrequency)
  }

  class SecondTypeLowPassTransferFunction(approximation: Approximation, order: Int, cutoffFrequency: Float, sampleFrequency: Float)
    extends LowPassTransferFunction(approximation, order, cutoffFrequency, sampleFrequency) {

    override val zeros: List[Complex] = List(-4, -2,  2,  4).
      map(_ * Math.PI / (2 * order)).
      map(a => (Complex.i / sin(a)).conjugate)

  }

}