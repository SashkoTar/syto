package com.sparja.syto.filter.core

import breeze.math.Complex

trait Approximation {

  // poles of filter with cutoff = 1 rad/s
  def normalizedPoles: List[Complex]
}
