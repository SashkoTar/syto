package com.sparja.syto.filter

import breeze.linalg.{DenseMatrix, DenseVector, diag}

object FilterImplementation {


  def filter(a: List[Double], b: List[Double], inputData: List[Int]) = {
    val output = new Array[Double](inputData.size)

    def y(i: Int) = if (i >=0 && i <= output.size - 1) output(i) else 0.0

    def x(i: Int) = if (i >=0 && i <= inputData.size - 1) inputData(i) else 0

    inputData.indices.foreach(n => {
      val xn = x(n)
      val xn_1 = x(n-1)
      val xn_2 = x(n-2)
      val xn_3 = x(n-3)
      val yn_1 = y(n-1)
      val yn_2 = y(n-2)
      val yn_3 = y(n-3)
      output(n) = b(0)*xn + b(1)*xn_1 + b(2)*xn_2 + b(3)*xn_3 - a(1)*yn_1 - a(2)*yn_2 - a(3)*yn_3
      println(s"output($n)=${output(n)}")
    })

    output
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
