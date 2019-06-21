package com.sparja.syto.filter

import breeze.linalg.{DenseMatrix, DenseVector, diag}

import scala.collection.Iterator
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.{IndexedSeq, Seq}
import scala.reflect.ClassTag
import breeze.linalg.DenseMatrix.{ eye, horzcat, vertcat, zeros }

object FiltfiltImplementation {


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
  def filtfilt_legacy(b: Seq[Double], a: Seq[Double], x: Seq[Double]): IndexedSeq[Double] = {

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
    val zi = calculateZi(bNorm.toList, aNorm.toList)

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
    val fwd = FilterImplementation.filter(b, a, xx)
    val rev = FilterImplementation.filter(b, a, fwd.reverse)

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
    DenseMatrix.vertcat(-aRow, v2)
  }


  /**
    * Computes the stady state of the filter.
    *
    *  The method used here is described in the following paper:
    *
    *  Gustafsson, F. (1996) Determining the Initial States in Forward-Backward
    *   Filtering.  IEEE Transactions on Signal Processing.  44(4):988-992.
    *
    *  @param b numerator coefficients divided by a(0) denominator
    *  	coefficient
    *  @param a denominator coefficients divided by a(0) denominator
    *  	coefficient
    *  @return initial filter states
    *  @author Oleksandr Tarasenko <sashko.tarasenko@gmail.com>
    *
    */
  //TODO cover by unit tests and check manually, find steady-state by impulse response
  private[filter] def calculateZi(b: List[Double], a: List[Double]) = {
    require(a.size == b.size)
   val n  = a.size
    val companion = companionByPolinomial(a).t
    val iminusA = DenseMatrix.eye[Double](n-1) - companion
    val bV = DenseVector(b.tail.toArray)
    val aV = DenseVector(a.tail.toArray)
    val B =  bV - aV*b.head
    val zi =iminusA\B

    zi
  }


  /*
      b = np.atleast_1d(b)
    a = np.atleast_1d(a)
    x = np.asarray(x)

    if method not in ["pad", "gust"]:
        raise ValueError("method must be 'pad' or 'gust'.")

    if method == "gust":
        y, z1, z2 = _filtfilt_gust(b, a, x, axis=axis, irlen=irlen)
        return y

    # method == "pad"
    edge, ext = _validate_pad(padtype, padlen, x, axis,
                              ntaps=max(len(a), len(b)))

    # Get the steady state of the filter's step response.
    zi = lfilter_zi(b, a)

    # Reshape zi and create x0 so that zi*x0 broadcasts
    # to the correct value for the 'zi' keyword argument
    # to lfilter.
    zi_shape = [1] * x.ndim
    zi_shape[axis] = zi.size
    zi = np.reshape(zi, zi_shape)
    x0 = axis_slice(ext, stop=1, axis=axis)

    # Forward filter.
    (y, zf) = lfilter(b, a, ext, axis=axis, zi=zi * x0)

    # Backward filter.
    # Create y0 so zi*y0 broadcasts appropriately.
    y0 = axis_slice(y, start=-1, axis=axis)
    (y, zf) = lfilter(b, a, axis_reverse(y, axis=axis), axis=axis, zi=zi * y0)

    # Reverse y.
    y = axis_reverse(y, axis=axis)

    if edge > 0:
        # Slice the actual signal from the extended signal.
        y = axis_slice(y, start=edge, stop=-edge, axis=axis)

    return y

   */

  //TODO Change to use Double, Float
  def filtfilt(b: List[Double], a: List[Double], x: List[Double]): List[Double] = {

    val (edge, ext) = generatePad(b, a, x)

    val zi = calculateZi(b, a).toArray.toList
    val x0 = ext.head
    val yFwd = FilterImplementation.filter(b, a, ext, Some(zi.map(_ * x0)))
    val y0 = yFwd.reverse.head

    val yBack = FilterImplementation.filter(b, a, yFwd.reverse, Some(zi.map(_ * y0)))
    yBack.reverse.drop(edge).dropRight(edge)
  }

  private def generatePad(b: List[Double], a: List[Double], x: List[Double]) = {

    val filterOrder = math.max(b.size, a.size)
    val tL = 3 * filterOrder // transient length

    val t2 = 2.0
    val xStart = x.drop(1).take(tL).toIndexedSeq.reverse.map { x.head * t2 - _ }
    val xEnd = x.takeRight(tL + 1).toIndexedSeq.reverse.drop(1).map { x.last * t2 - _ }
    val xx = xStart ++ x ++ xEnd
    (tL, xx.toList)
  }

}
