package com.sparja.syto.filter

trait TransferFunction[A] {

  def calculateCoefficients(): (Seq[A], Seq[A])

  def getNominator(): Seq[A]

  def getDenominator(): Seq[A]


}
