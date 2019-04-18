package com.sparja.syto.nuca

object EllipticFunction {

  def ellipk(k: Double) = {

    def nextK(k: Double) = math.pow(k /(1 + math.sqrt(1 - k*k)), 2)

    def kSeq(m: Int, ks: List[Double]): List[Double] = {
      if (m == 0)
        ks
      else
        kSeq(m - 1,  nextK(ks.head)::ks)
    }

    math.Pi/2 * kSeq(11, List(k)).reverse.tail.map(_ + 1).product

  }


  def ellipInc(k: Double, am: Double) = {

    def nextK(k: Double) = math.pow(k /(1 + math.sqrt(1 - k*k)), 2)

    def kSeq(m: Int, ks: List[Double]): List[Double] = {
      if (m == 0)
        ks
      else
        kSeq(m - 1,  nextK(ks.head)::ks)
    }

    am * kSeq(11, List(k)).reverse.tail.map(_ + 1).product

  }
}
