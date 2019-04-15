
def nextK(k: Double) = math.pow(k /(1 + math.sqrt(1 - k*k)), 2)


def calculateK(m: Int, ks: List[Double]): List[Double] = {
  if (m == 0)
    ks
  else
    calculateK(m - 1,  nextK(ks.head)::ks)
}


def ellipk(k: Double) = math.Pi * calculateK(11, List(k)).map(_ + 1).product

ellipk(0.93)


val b = calculateK(6, List(0.93)).reverse
val c = b.map(_ + 1)
val d = c.tail.product * math.Pi/2
