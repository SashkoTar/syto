package com.sparja.syto.filter.butterworth

import com.sparja.syto.filter.core.{HighPassTransferFunction, TransferFunction}

object DigitalButterworthHighPassFilter {
  def apply(order: Int, cutoffFrequency: Float, sampleFrequency: Float): TransferFunction = {
    val approximation = new ButterworthApproximation(order)
    new HighPassTransferFunction(approximation, order, cutoffFrequency, sampleFrequency)
  }
}
