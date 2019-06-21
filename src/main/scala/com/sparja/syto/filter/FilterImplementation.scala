package com.sparja.syto.filter

import breeze.linalg.{DenseMatrix, DenseVector, diag}

import scala.collection.Iterator
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.{IndexedSeq, Seq}
import scala.reflect.ClassTag

object FilterImplementation {


  def filter[ /* @specialized(Float, Double) */ T, A, B, C, Repr]
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
      private val abSz = math.max(a.size, b.size)
      private val aNorm = a.map(implicitly[T](_) / a0).toIndexedSeq.
        padTo(abSz, n.zero)
      private val bNorm = b.map(implicitly[T](_) / a0).toIndexedSeq.
        padTo(abSz, n.zero)

      // create initial state array; use zeroes if it's not provided
      private val z: Array[T] = (if (si.isDefined) {
        si.get.map { implicitly[T](_) }
      } else {
        List.fill(abSz - 1)(n.zero)
      }).toArray
      if (z.size != abSz - 1) {
        throw new IllegalArgumentException(
          "si.size must be (max(a.size, b.size) - 1)")
      }

      // method to update the state array (z) on each iteration
      private def updateZ(z: Array[T],
                          bTail: IndexedSeq[T], aTail: IndexedSeq[T],
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


}
