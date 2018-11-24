package com.sparja.syto.filter.chebyshev

import com.sparja.syto.filter.core.{LowPassTransferFunction, TransferFunction}

object DigitalChebyshevFirstTypeLowPassFilter {
  def apply(order: Int, rp: Float, cutoffFrequency: Float, sampleFrequency: Float): TransferFunction = {
    val approximation = new ChebyshevFirstTypeApproximation(order, rp)
    new LowPassTransferFunction(approximation, order, cutoffFrequency, sampleFrequency)
  }
}