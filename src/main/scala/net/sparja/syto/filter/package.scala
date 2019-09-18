package net.sparja.syto

import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.math.Complex
import net.sparja.syto.math.{companionByPolynomial, cos, max, sin}
import scala.collection.immutable.Seq

package object filter {


  /**
    * Filters a data sequence, "inputSeq", using digital filter, defined by transfer function.
    * It's direct form II transposed implementation which solves the difference equation
    * @param b Coefficients of polynomial from numerator of transfer function
    * @param a Coefficients of denominator from numerator of transfer function
    * @param inputSeq Input sequence to be filtered
    * @param initState Initial conditions for the filter delays
    * @return Filter output
    * @author Oleksandr Tarasenko <sashko.tarasenko@gmail.com>
    */

  def filterForward(b: Seq[Double], a: Seq[Double], inputSeq: Seq[Double], initState: Option[Seq[Double]] = None): Seq[Double] = {
    val delaysLength = max(a.size, b.size)
    val filterOrder = delaysLength - 1

    val delays = initState match {
      case Some(zeroPoint) => zeroPoint.padTo(delaysLength, 0.0).toArray // To cast "delays" updating loop to general form
      case None => Array.fill(delaysLength)(0.0)
    }
    if(delays.length != delaysLength) throw
      new IllegalArgumentException(s"The length of initState must be equal to filter's order which is ${filterOrder} ")

    def calcNext(x: Double) = {
      val y = b.head * x + delays.head

      for (i <- 1 to filterOrder) {
        delays(i - 1) = b(i) * x - a(i) * y + delays(i)
      }

      y
    }

    inputSeq map calcNext
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

  /**
    * Filters a data sequence, "inputSeq", using digital filter, defined by transfer function.
    * This method filters the input signal twice, forward and backwards. Such filter has zero phase.
    * @param b Coefficients of polynomial from numerator of transfer function
    * @param a Coefficients of denominator from numerator of transfer function
    * @param inputSeq Input sequence to be filtered
    * @return Filter output
    * @author Oleksandr Tarasenko <sashko.tarasenko@gmail.com>
    */

  //TODO Change to use Double, Float
  def filtfilt(b: Seq[Double], a: Seq[Double], inputSeq: Seq[Double]): Seq[Double] = {
    val (edge, ext) = generatePad(b, a, inputSeq)

    if (edge > inputSeq.size) {
      throw new IllegalArgumentException(s"Input too short. It should be more than ${edge} elements")
    }
    val zi = calculateZi(b, a).toArray.toList
    val x0 = ext.head
    val yFwd = filterForward(b, a, ext, Some(zi.map(_ * x0)))
    val y0 = yFwd.reverse.head

    val yBack = filterForward(b, a, yFwd.reverse, Some(zi.map(_ * y0)))
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

  //TODO Build graph and use in next release
  private[filter] def frequencyResponse(a: Seq[Double], b: Seq[Double], f0: Double, fs: Float): Double = {
    val angle = 2 * Math.PI * f0 / fs
    val z = Complex(cos(angle), sin(angle))
    val numerator = b.zipWithIndex.map(e => e._1 * z.pow(-e._2)).sum
    val denominator = a.zipWithIndex.map(e => e._1 * z.pow(-e._2)).sum
    (numerator / denominator).abs
  }


}
