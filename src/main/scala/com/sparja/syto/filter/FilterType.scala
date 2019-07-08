package com.sparja.syto.filter

import breeze.math.Complex
import breeze.numerics.{pow, sqrt}

object FilterType {

  def highPass(roots: Roots, cutOffFrequency: Double) = {
    val zeros = roots.zeros
    val poles = roots.poles
    val scale = roots.scale
    val degree = poles.size - zeros.size
    Roots(
      zeros.map(cutOffFrequency / _) ++ List.fill(degree)(Complex.zero),
      poles.map(cutOffFrequency / _),
      scale * Math.pow(-1, poles.size) * zeros.product.real / poles.product.real
    )
  }


  def lowPass(roots: Roots, cutOffFrequency: Double) = {
    val zeros = roots.zeros
    val poles = roots.poles
    val scale = roots.scale
    val degree = poles.size - zeros.size
    Roots(
      zeros.map(_ * cutOffFrequency),
      poles.map(_ * cutOffFrequency),
      scale * pow(cutOffFrequency, degree)
    )
  }

  def bandPass(roots: Roots, lowFrequency: Double, highFrequency: Double) = {
    val zeros = roots.zeros
    val poles = roots.poles
    val scale = roots.scale
    val degree = poles.size - zeros.size
    val bandWidth = highFrequency - lowFrequency

    val wo = sqrt(lowFrequency * highFrequency)

    def transformPoint(point: Complex): Complex = {
      (-bandWidth * point + (bandWidth * bandWidth * point * point - 4 * wo * wo).pow(0.5)) / 2
    }

    def transformPoint2(point: Complex): Complex = {
      (-bandWidth * point - (bandWidth * bandWidth * point * point - 4 * wo * wo).pow(0.5)) / 2
    }

    Roots(
      (zeros.map(transformPoint) ++ zeros.map(transformPoint2)).map(_.unary_-) ++ List.fill(degree)(Complex.zero),
      (poles.map(transformPoint) ++ poles.map(transformPoint2)).map(_.unary_-),
      scale * pow(bandWidth, degree)
    )
  }

  def bandStop(roots: Roots, lowFrequency: Double, highFrequency: Double) = {
    val zeros = roots.zeros
    val poles = roots.poles
    val scale = roots.scale
    val degree = poles.size - zeros.size
    val bandWidth = highFrequency - lowFrequency

    val wo = Math.sqrt(lowFrequency * highFrequency)

    def transformPoint(point: Complex):Complex = {
      (-bandWidth / point + (bandWidth*bandWidth / (point*point) - 4*wo*wo).pow(0.5))/2
    }

    def transformPoint2(point: Complex):Complex = {
      (-bandWidth / point - (bandWidth * bandWidth / (point * point) - 4 * wo * wo).pow(0.5)) / 2
    }

    Roots(
      (zeros.map(transformPoint) ++ zeros.map(transformPoint2)).map(_.unary_-)  ++ List.fill(degree)(wo*Complex.i) ++ List.fill(degree)(wo*Complex.i.unary_-),
      (poles.map(transformPoint) ++ poles.map(transformPoint2)).map(_.unary_-),
      (scale * zeros.map(_.unary_-).product/poles.map(_.unary_-).product).real
    )
  }


}
