import breeze.linalg.{DenseMatrix, DenseVector, diag}


val a = List(1.0, -2.6861574, 2.41965511, -0.73016535)
val b = List(0.00041655, 0.00124964, 0.00124964, 0.00041655)
val n  = a.size

val v1 = diag(DenseVector.ones[Double](2))

val zeros = DenseVector.zeros[Double](2).asDenseMatrix.reshape(2, 1)

val v2 = DenseMatrix.horzcat(v1,zeros)

val aRow = DenseMatrix(a.tail)

/*
[[ 2.6861574   1.          0.        ]
 [-2.41965511  0.          1.        ]
 [ 0.73016535  0.          0.        ]]
 */

val companion = DenseMatrix.vertcat(-aRow, v2).t


/*
[[-1.6861574  -1.          0.        ]
 [ 2.41965511  1.         -1.        ]
 [-0.73016535  0.          1.        ]]
 */
val iminusA = DenseMatrix.eye[Double](n-1) - companion

//B = b[1:] - a[1:] * b[0]

val bV = DenseVector(b.tail.toArray)
val aV = DenseVector(a.tail.toArray)

/*
[0.00236855 0.00024174 0.00072069]
 */
val B =  bV - aV*b.head


/*
[ 0.99958345 -1.68782358  0.73058189]
 */
val zi =iminusA\B
println(iminusA\B)

