package com.sparja.syto.filter.chebyshev.digital

import breeze.math.Complex
import breeze.numerics.{cos, pow, sin}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport
import org.apache.commons.math3.util.FastMath
import org.apache.commons.math3.util.FastMath.tan

object DigitalChebyshevSecondTypeLowPassFilter {

  def apply(order: Int, rp: Double, cutoffFrequency: Double, sampleFrequency: Double): TransferFunction = {
    new SecondTypeLowPassTransferFunction(order, rp, cutoffFrequency, sampleFrequency)
  }


  class SecondTypeLowPassTransferFunction(order: Int, rp: Double, cutoffFrequency: Double, sampleFrequency: Double) extends TransferFunction {

    val zeros = {
      val n = if (order % 2 == 0) order / 2 else (order - 1) / 2
      val first = (1 to n).map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex.i / cos(theta)).toList

      first ::: first.map(_.conjugate)

    }

    val poles = {
      val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
      val mu = FastMath.asinh(eps) / order

      def r(theta: Double) = -sin(theta) * Math.sinh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      def im(theta: Double) = cos(theta) * Math.cosh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      (1 to order)
        .map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex(r(theta), im(theta))).toList

    }

    def transformBilinear(poles: List[Complex]) = {
      poles.map(p => {
        val num = 1 + p / (2 * sampleFrequency)
        val denum = 1 - p / (2 * sampleFrequency)
        num / denum
      })
    }


    def transformBilinear2(poles: List[Complex]) = {
      poles.map(p => {
        (4 + p) / (4 - p)
      })
    }

    val degree = poles.size - zeros.size

    //TODO Refactore to common schema
    var k = Math.pow(-1, order) * poles.product/zeros.product

    def transformLowPassToLowPass(scaleCoefficient: Complex, frequency: Double): (List[Complex], List[Complex], Complex) = {
      val z = zeros.map(_ * frequency)
      val p = poles.map(_ *  frequency)
      val k = scaleCoefficient * pow(frequency, degree)
      (z, p, k)
    }

    def calculateCoefficients(): (List[Double], List[Double]) = {
      //poles:  [-0.1559156 -0.61087032j -0.52479948-0.48538901j -0.78777027-0.j  -0.52479948+0.48538901j -0.1559156 +0.61087032j]
      //zeros:  [-1.0514622242382672j, -1.7013016167040798j, (-0+1.7013016167040798j), (-0+1.0514622242382672j)]
      //k: 0.050002500187515626

     val fs = 2.0
     // warped = 2 * fs * tan(pi * Wn / fs)

      //val preWarpedFrequency = sampleFrequency / Math.PI * tan(Math.PI * cutoffFrequency / sampleFrequency)
      val wn = 2 * cutoffFrequency / sampleFrequency
      val preWarpedFrequency = 2 * fs * tan(Math.PI * wn / fs)
      //warped 0.09819448843570178

      val (lpZeros, lpPoles, lpK) = transformLowPassToLowPass(k, preWarpedFrequency)

      // zeros: [ 0.-0.1032478j   0.-0.16705844j -0.+0.16705844j -0.+0.1032478j ]
      // poles: [-0.01531005-0.0599841j  -0.05153242-0.04766253j -0.0773547  -0.j -0.05153242+0.04766253j -0.01531005+0.0599841j ]
      // k: 0.004909969926419179


      val transformedPoles = transformBilinear2(lpPoles)
      val transformedZeros = transformBilinear2(lpZeros):::List.fill(degree)(-Complex.one)

       k = lpK * lpZeros.map(2 * fs - _).product/lpPoles.map(2 * fs - _).product

      //k: 0.001167219713616433
      //zeros: [ 0.99866837-0.05158953j  0.99651751-0.08338378j  0.99651751+0.08338378j        0.99866837+0.05158953j -1.        +0.j        ]
      //poles: [0.99192962-0.02975713j 0.97428829-0.02322567j 0.96205643+0.j   0.97428829+0.02322567j 0.99192962+0.02975713j]
      val a = PolynomialSupport.calculateCoefficients(transformedPoles).map(_.real)
      val b = PolynomialSupport.calculateCoefficients(transformedZeros).map(_.real)

      //k = Math.pow(-1, order) * k * poles.product / zeros.product

      (b.map(_ * k.real), a)

    }

  }


}