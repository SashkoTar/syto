package com.sparja.sonia.filter

import org.apache.commons.math3.complex.{Complex, ComplexUtils}

object FilterSupport {

  def buttap(order: Int) = {

    val zeros = List.empty
    val poles = -order+1 to order by 2 map(findPoles(_, order))
    val k = 1

    (zeros, poles, k)
  }

  private def findPoles(value: Int, order: Int): Complex = {
    ComplexUtils.polar2Complex(1.0, value * Math.PI /(2 * order))
  }

  def bilinearZpk(zeros: List[Complex], poles: List[Complex], gainCoeficient: Float): (List[Complex], List[Complex]) = {
    (List.empty, List.empty)
  }

}

