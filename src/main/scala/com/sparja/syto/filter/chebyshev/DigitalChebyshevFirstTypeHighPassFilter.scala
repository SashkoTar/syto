package com.sparja.syto.filter.chebyshev

import com.sparja.syto.filter.core.{HighPassTransferFunction, TransferFunction}

object DigitalChebyshevFirstTypeHighPassFilter {
  def apply(order: Int, rp: Double, cutoffFrequency: Double, sampleFrequency: Double): TransferFunction = {
    val approximation = new ChebyshevFirstTypeApproximation(order, rp)
    new HighPassTransferFunction(approximation, order, cutoffFrequency, sampleFrequency)
  }
}
