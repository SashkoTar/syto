package com.sparja.syto.filter.chebyshev

import com.sparja.syto.filter.core.TransferFunction

object DigitalChebyshevSecondTypeLowPassFilter {

  def apply(order: Int, rp: Double, cutoffFrequency: Double, sampleFrequency: Double): TransferFunction = {
    new SecondTypeLowPassTransferFunction(order, rp, cutoffFrequency, sampleFrequency)
  }


  class SecondTypeLowPassTransferFunction(order: Int, rp: Double,  cutoffFrequency: Double, sampleFrequency: Double) extends TransferFunction {

    def calculateCoefficients(): (List[Double], List[Double]) = {

      (List.empty, List.empty)
    }

  }

}