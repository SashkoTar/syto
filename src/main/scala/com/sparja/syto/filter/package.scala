package com.sparja.syto

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.math.companionByPolynomial
import scala.collection.Iterator
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.Seq
import scala.reflect.ClassTag

package object filter {

  def filter[ /*@specialized(Float, Double)*/ T, A, B, C, Repr]
  (b: Seq[B], a: Seq[A], x: Repr, si: Option[Seq[C]] = None)
  (implicit seqX: Repr => Seq[T],
   n: Fractional[T],
   aToT: A => T,
   bToT: B => T,
   cToT: C => T,
   bf: CanBuildFrom[Repr, T, Repr],
   m: ClassTag[T]): Repr = {

    import n._

    val filterIterator = new Iterator[T] {
      // normalize a and b; dividing both by a(0), and pad them with zeros so
      //  they're the same length as each other
      private val a0: T = a.head
      private val abSz = com.sparja.syto.math.max(a.size, b.size)
      private val aNorm = a.map(implicitly[T](_) / a0).toIndexedSeq.
        padTo(abSz, n.zero)
      private val bNorm = b.map(implicitly[T](_) / a0).toIndexedSeq.
        padTo(abSz, n.zero)

      // create initial state array; use zeroes if it's not provided
      private val z: Array[T] = (if (si.isDefined) {
        si.get.map {
          implicitly[T](_)
        }
      } else {
        List.fill(abSz - 1)(n.zero)
      }).toArray
      if (z.size != abSz - 1) {
        throw new IllegalArgumentException(
          "si.size must be (max(a.size, b.size) - 1)")
      }

      // method to update the state array (z) on each iteration
      private def updateZ(z: Array[T],
                          bTail: Seq[T], aTail: Seq[T],
                          xm: T, ym: T) {
        for (i <- 0 until (z.size - 1)) {
          z(i) = bTail(i) * xm + z(i + 1) - aTail(i) * ym
        }
        z(z.size - 1) = bTail(z.size - 1) * xm - aTail(z.size - 1) * ym
      }

      private val b0 = bNorm.head
      private val aTail = aNorm.tail
      private val bTail = bNorm.tail

      private val xIterator = x.iterator

      override def hasNext: Boolean = xIterator.hasNext

      override def next(): T = {
        val xm: T = xIterator.next
        val ym: T = b0 * xm + z(0)
        updateZ(z, bTail, aTail, xm, ym)
        ym
      }
    }

    // we create a builder here; for lazy collections (eg. Stream), the
    //  filterIterator should also be evaluated lazily
    val builder = bf(x)
    builder ++= filterIterator
    builder.result
  }

  /**
    * Computes the steady state of the filter.
    *
    * The method used here is described in the following paper:
    *
    * Gustafsson, F. (1996) Determining the Initial States in Forward-Backward
    *   Filtering.  IEEE Transactions on Signal Processing.  44(4):988-992.
    *
    * @param b numerator coefficients divided by a(0) denominator
    *          coefficient
    * @param a denominator coefficients divided by a(0) denominator
    *          coefficient
    * @return initial filter states
    * @author Oleksandr Tarasenko <sashko.tarasenko@gmail.com>
    *
    */
  //TODO cover by unit tests and check manually, find steady-state by impulse response
  private[filter] def calculateZi(b: Seq[Double], a: Seq[Double]) = {
    require(a.size == b.size)
    val n = a.size
    val companion = companionByPolynomial(a).t
    val iminusA = DenseMatrix.eye[Double](n - 1) - companion
    val bV = DenseVector(b.tail.toArray)
    val aV = DenseVector(a.tail.toArray)
    val B = bV - aV * b.head
    val zi = iminusA \ B

    zi
  }


  //TODO Change to use Double, Float
  def filtfilt(b: Seq[Double], a: Seq[Double], x: Seq[Double]): Seq[Double] = {

    val (edge, ext) = generatePad(b, a, x)

    if (edge > x.size) {
      throw new IllegalArgumentException(s"Input too short. It should be more than ${edge} elements")
    }
    val zi = calculateZi(b, a).toArray.toList
    val x0 = ext.head
    val yFwd = filter(b, a, ext, Some(zi.map(_ * x0)))
    val y0 = yFwd.reverse.head

    val yBack = filter(b, a, yFwd.reverse, Some(zi.map(_ * y0)))
    yBack.reverse.drop(edge).dropRight(edge)
  }

  private[filter] def generatePad(b: Seq[Double], a: Seq[Double], x: Seq[Double]) = {

    val filterOrder = math.max(b.size, a.size)
    val tL = 3 * filterOrder // transient length

    val t2 = 2.0
    val xStart = x.drop(1).take(tL).toIndexedSeq.reverse.map {
      x.head * t2 - _
    }
    val xEnd = x.takeRight(tL + 1).toIndexedSeq.reverse.drop(1).map {
      x.last * t2 - _
    }
    val xx = xStart ++ x ++ xEnd
    (tL, xx.toList)
  }

  //TODO Build graph and use in next reales
  private[filter] def frequencyResponse(a: Seq[Double], b: Seq[Double], f0: Double, fs: Float): Double = {
    val angle = 2 * Math.PI * f0 / fs
    val z = Complex(cos(angle), sin(angle))
    val numerator = b.zipWithIndex.map(e => e._1 * z.pow(-e._2)).sum
    val denumerator = a.zipWithIndex.map(e => e._1 * z.pow(-e._2)).sum
    (numerator / denumerator).abs
  }


}
