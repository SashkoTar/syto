package com.sparja.syto.filter

import breeze.linalg.{DenseMatrix, DenseVector, diag}

import scala.collection.Iterator
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.{IndexedSeq, Seq}
import scala.reflect.ClassTag
import breeze.linalg.DenseMatrix.{ eye, horzcat, vertcat, zeros }

object FiltfiltImplementation {


  /** Computes the stable state of the filter.
    *
    *  The method used here is described in the following paper:
    *
    *  Gustafsson, F. (1996) Determining the Initial States in Forward-Backward
    *   Filtering.  IEEE Transactions on Signal Processing.  44(4):988-992.
    *
    *  @param bNorm numerator coefficients divided by a(0) denominator
    *  	coefficient
    *  @param aNorm denominator coefficients divided by a(0) denominator
    *  	coefficient
    *  @return initial filter states
    *  @author Jonathan Merritt <merritt@unimelb.edu.au>
    */

  private[filter] def computeZi(bNorm: IndexedSeq[Double], aNorm: IndexedSeq[Double]): List[Double] = {
    require(aNorm.size == bNorm.size)
    val fo = aNorm.size - 1   // filter order

    val b0 = bNorm.head
    val aTail = DenseMatrix.create(fo, 1, aNorm.tail.toArray)  // aTail as column vector
    val bTail = DenseMatrix.create(fo, 1, bNorm.tail.toArray)  // bTail as column vector

    val za =
      if (fo > 1) {
        eye[Double](fo) - horzcat(-aTail, vertcat(eye[Double](fo - 1), zeros[Double](1, fo - 1)))
      } else {
        eye[Double](1) + aTail
      }
    val zb = bTail - aTail * b0
    val zi = za \ zb

    zi(::, 0).toArray.toList
  }

  /** Forward-reverse, zero phase lag digital filtering.
    *
    *  Applies a digital filter first in the forward direction and then in the
    *  reverse direction to the signal contained in `x`.  The filter's numerator
    *  coefficients are contained in `b`, while the denominator coefficients
    *  are contained in `a`.  Due to the double application of the filter, the
    *  magnitude of its transfer function is squared, while the phase is zero.
    *
    *  Transient responses of the signal are removed from the start and end
    *  using the method described by:
    *  - Gustafsson, F. (1996) Determining the Initial States in
    *    Forward-Backward Filtering.
    *    ''IEEE Transactions on Signal Processing''.  '''44(4)''':988-992.
    *
    *  @param b numerator coefficients of the filter
    *  @param a denominator coefficients of the filter
    *  @param x signal to filter.  `x.size` must be greater than 3 times the
    *   order of the filter.
    *  @see [[scala.signal.Filter.filter]]
    *  @author Jonathan Merritt <merritt@unimelb.edu.au>
    */
  def filtfilt(b: Seq[Double], a: Seq[Double], x: Seq[Double]): IndexedSeq[Double] = {

    // compute the filter order, and the approximate length of transients
    val filterOrder = math.max(b.size, a.size) - 1
    val tL = 3 * filterOrder // transient length

    // check that the number of samples exceeds the expected transient length.
    //  we need this so that the startup transients can be removed effectively.
    if (x.size <= tL) throw new IllegalArgumentException(
      "x.size must be > (3 * filter order)")

    // normalize a and b (divide by a0), and pad them out to the same length
    val a0 = a.head
    val aNorm = a.map(_ / a0).toIndexedSeq.padTo(filterOrder + 1, 0.0)
    val bNorm = b.map(_ / a0).toIndexedSeq.padTo(filterOrder + 1, 0.0)

    // compute the stable conditions for the filter's state variable
    //  (si / x(0)).
    //  see Filter.filter() for more information on the state variable.
    val zi = computeZi(bNorm, aNorm)

    // now we reverse and reflect the ends of the signal, appending and
    //  pre-pending the reversed and reflected data.  this helps to reduce the
    //  startup transients in the filtering by allowing the filter to
    //	accomodate itself to the artificially-added ends before it reaches the
    //  true signal in the center.
    val t2 = 2.0
    val xStart = x.drop(1).take(tL).toIndexedSeq.reverse.map { x.head * t2 - _ }
    val xEnd = x.takeRight(tL + 1).toIndexedSeq.reverse.drop(1).map { x.last * t2 - _ }
    val xx = xStart ++ x ++ xEnd

    // apply the filter in forward and reverse, computing the initial state
    //  vector as appropriate.
    val fwd = FilterImplementation.filter(b, a, xx, Some(zi.map(_ * xx.head)))
    val rev = FilterImplementation.filter(b, a, fwd.reverse, Some(zi.map(_ * fwd.last)))

    // trim out the central region of the signal (minus the padding for
    //   transients)
    val y = rev.reverse.slice(tL, rev.size - tL)
    y

  }


  private def companionByPolinomial(a: List[Double]) = {
    val n  = a.size

    val v1 = diag(DenseVector.ones[Double](n-2))

    val zeros = DenseVector.zeros[Double](n-2).asDenseMatrix.reshape(n-2, 1)

    val v2 = DenseMatrix.horzcat(v1,zeros)

    val aRow = DenseMatrix(a.tail)

    /*
    [[ 2.6861574   1.          0.        ]
     [-2.41965511  0.          1.        ]
     [ 0.73016535  0.          0.        ]]
     */

    DenseMatrix.vertcat(-aRow, v2)
  }


  /*
    IminusA = np.eye(n - 1) - linalg.companion(a).T
    B = b[1:] - a[1:] * b[0]
    # Solve zi = A*zi + B
    zi = np.linalg.solve(IminusA, B)
   */
  //TODO cover by unit tests and check manually, find steady-state by impulse response
  def lfilter_zi(a: List[Double], b: List[Double]) = {
   val n  = a.size

    val companion = companionByPolinomial(a).t

    val iminusA = DenseMatrix.eye[Double](n-1) - companion

    val bV = DenseVector(b.tail.toArray)
    val aV = DenseVector(a.tail.toArray)
    val B =  bV - aV*b.head
    val zi =iminusA\B

    zi
  }

}
