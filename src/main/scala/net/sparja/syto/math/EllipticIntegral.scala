package net.sparja.syto.math

private[syto] object EllipticIntegral {

  private def nextTheta(phi: Double) = asin(tan(phi / 2) * tan(phi / 2))

  private def nextAmpl(theta: Double, am: Double) = {
    val a = cos(theta) * tan(am)
    val b = atan(a)
    def adjustPhi1(phi: Double, phi1: Double): Double = {
      if (phi1 > phi - PI / 2)
        phi1
      else
        adjustPhi1(phi, phi1 + PI)
    }
    am + adjustPhi1(am, b)
  }

  private def printAngle(name: String, angle: Double) = {
    val degrees = angle * 180 / PI
    val d = degrees.asInstanceOf[Int] // Truncate the decimals
    val t1 = (degrees - d) * 60
    val m = t1.asInstanceOf[Int]
    val s = (t1 - m) * 60

    println(s"$name has degrees=$d $m' ")
  }

  def ellipInc(k: Double, am: Double) = {
    def calculateKam(kam: List[(Double, Double)]): List[(Double, Double)] = {
      val currentKAM = kam.head
      val k = nextTheta(currentKAM._1)
      val am = nextAmpl(currentKAM._1, currentKAM._2)
      if (math.abs(k - currentKAM._1) < 0.0000001)
        kam
      else {
        calculateKam((k, am) :: kam)
      }
    }
    val kam = calculateKam(List((k, am))).dropRight(1)
    kam.head._2 * cos(kam.head._1) * sqrt(kam.tail.map(a => cos(a._1)).product / cos(k)) / pow(2, kam.size)
  }


  def K(k: Double) = F(PI/2, k)

  def F(z: Double, k: Double) = ellipInc(asin(k), z)

}
