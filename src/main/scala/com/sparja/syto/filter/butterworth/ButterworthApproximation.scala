package com.sparja.syto.filter.butterworth

import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.filter.core.Approximation

class ButterworthApproximation(val order: Int) extends Approximation {

  // poles of filter with cutoff = 1 rad/s
  override def normalizedPoles = (1 to order)
    .map(k => (2 * k - 1) * Math.PI / (2 * order))
    .map(theta => Complex(-sin(theta), cos(theta))).toList

}
