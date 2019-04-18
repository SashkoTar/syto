import breeze.numerics.sin

def nextK(k: Double) = math.pow(k /(1 + math.sqrt(1 - k*k)), 2)


def kSeq(m: Int, ks: List[Double]): List[Double] = {
  if (m == 0)
    ks
  else
    kSeq(m - 1,  nextK(ks.head)::ks)
}


def ellipk(k: Double) = math.Pi/2 * kSeq(11, List(k)).reverse.tail.map(_ + 1).product

ellipk(0.93)


val b = kSeq(6, List(0.93)).reverse
val c = b.map(_ + 1)
val d = c.tail.product * math.Pi/2


def wSeq(w: Double, kSequence: List[Double]):Double = kSequence match {
    case Nil => w
    case k::tail => wSeq((1 + k)/(1/w + k*w), tail)
}


val kseq = kSeq(11, List(0.93)).reverse
val u = sin(math.Pi * 0.3) //0.6

wSeq(u, kseq)

