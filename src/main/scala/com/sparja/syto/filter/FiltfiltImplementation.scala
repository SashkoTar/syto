package com.sparja.syto.filter

import breeze.linalg.{DenseMatrix, DenseVector, diag}

import scala.collection.Iterator
import scala.collection.generic.CanBuildFrom
import scala.collection.immutable.{IndexedSeq, Seq}
import scala.reflect.ClassTag
import breeze.linalg.DenseMatrix.{ eye, horzcat, vertcat, zeros }

object FiltfiltImplementation {



  private def companionByPolynomial(a: List[Double]) = {
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
    val companion = companionByPolynomial(a).t
    val iminusA = DenseMatrix.eye[Double](n-1) - companion
    val bV = DenseVector(b.tail.toArray)
    val aV = DenseVector(a.tail.toArray)
    val B =  bV - aV*b.head
    val zi =iminusA\B

    zi
  }



  //TODO Change to use Double, Float
  def filtfilt(b: List[Double], a: List[Double], x: List[Double]): List[Double] = {

    val (edge, ext) = generatePad(b, a, x)

    if(edge > x.size) {
      throw new IllegalArgumentException(s"Input too short. It should be more than ${edge} elements")
    }
    val zi = calculateZi(b, a).toArray.toList
    val x0 = ext.head
    val yFwd = FilterImplementation.filter(b, a, ext, Some(zi.map(_ * x0)))
    val y0 = yFwd.reverse.head

    val yBack = FilterImplementation.filter(b, a, yFwd.reverse, Some(zi.map(_ * y0)))
    yBack.reverse.drop(edge).dropRight(edge)
  }

  private[filter] def generatePad(b: List[Double], a: List[Double], x: List[Double]) = {

    val filterOrder = math.max(b.size, a.size)
    val tL = 3 * filterOrder // transient length

    val t2 = 2.0
    val xStart = x.drop(1).take(tL).toIndexedSeq.reverse.map { x.head * t2 - _ }
    val xEnd = x.takeRight(tL + 1).toIndexedSeq.reverse.drop(1).map { x.last * t2 - _ }
    val xx = xStart ++ x ++ xEnd
    (tL, xx.toList)
  }

}
