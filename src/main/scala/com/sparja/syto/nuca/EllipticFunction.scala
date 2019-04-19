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


  def nextAm(k: Double, am: Double) = {
    val a = math.sqrt(1 - k*k)*math.tan(am)
    val b =  math.atan(a)
    val c = b + am
    c
    // c * 180 / math.Pi
  }

  def nextK(k: Double) = math.pow(k /(1 + math.sqrt(1 - k*k)), 2)

  def ellipInc(k: Double, am: Double) = {

    def calculateKam(kam: List[(Double, Double)]): List[(Double, Double)] = {
      val currentKAM = kam.head
      val k = nextK(currentKAM._1)
      val am = nextAm(currentKAM._1, currentKAM._2)
      if(math.abs(k - currentKAM._1) < 0.000009)
        kam
      else
        calculateKam((k, am)::kam)
    }

    val kam = calculateKam(List((k, am))).reverse
    println(kam)

    kam.head._2 * kam.map(i => (i._1 + 1)/2).product

  }



  def printAngle(x: Double) = {
    println(s"Radians = $x, Degrees=${x * 180 / math.Pi}")
  }


}
