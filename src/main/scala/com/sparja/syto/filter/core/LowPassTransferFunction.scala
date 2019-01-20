package com.sparja.syto.filter.core

import breeze.math.Complex
import org.apache.commons.math3.util.FastMath.tan

class LowPassTransferFunction(approximation: Approximation, order: Int, cutoffFrequency: Double, sampleFrequency: Double)
  extends DigitalTransferFunction(approximation, order, sampleFrequency){

  def preWarpAndTransformToSpecificFilterType(pa: List[Complex]) = {
    //continuous pre-warped frequency
    val preWarpedFrequency = sampleFrequency / Math.PI * tan(Math.PI * cutoffFrequency / sampleFrequency)

    //scale poles by 2*pi*Fc
    pa.map(_ * (2 * Math.PI * preWarpedFrequency)).toList
  }

}
