package com.sparja.syto.filter.core

trait TransferFunction {

  def calculateCoefficients(): (List[Double], List[Double])

}
