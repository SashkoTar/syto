package com.sparja.syto.filter.chebyshev

import com.sparja.syto.filter.core.{HighPassTransferFunction, TransferFunction}

object DigitalChebyshevFirstTypeHighPassFilter {
  def apply(order: Int, rp: Float, cutoffFrequency: Float, sampleFrequency: Float): TransferFunction = {
    val approximation = new ChebyshevFirstTypeApproximation(order, rp)
    new HighPassTransferFunction(approximation, order, cutoffFrequency, sampleFrequency)
  }
}
