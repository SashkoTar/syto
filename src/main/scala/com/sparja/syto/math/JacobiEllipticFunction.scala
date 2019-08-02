package com.sparja.syto.math

import breeze.math.Complex

private[syto] object JacobiEllipticFunction {


  private def iterateAB(coef: List[(Double, Double, Double)]): List[(Double, Double, Double)] = {
    val a = coef.head._1
    val b = coef.head._2

    val aNext = (a + b) / 2
    val bNext = sqrt(a * b)
    val cNext = (a - b) / 2

    if (cNext < 0.00000001)
      (aNext, bNext, cNext)::coef
    //coef
    else
      iterateAB((aNext, bNext, cNext)::coef)
  }

  private def correctPhi(phi: Double, coeff: List[(Double, Double, Double)]): Double = {
    if (coeff.size == 1)
      phi
    else {
      val a = coeff.head._1
      val b = coeff.head._2
      val c = coeff.head._3
      val ca = c/a
      val acSinPhi = ca * sin(phi)
      val asinus = asin(acSinPhi)
      val phiNext = phi/2 + asinus/2
      //println(s"a = $a, b = $b,  c = $c, phi = $phiNext")
      correctPhi(phiNext, coeff.tail)
    }
  }

  def am(u: Double, k: Double): Double = {
    val coeffs = iterateAB(List((1, sqrt(1 - k*k), k)))
    val finalPhi = coeffs.head._1 * pow(2, coeffs.size-1) * u
    //println(s"Final phi = $finalPhi")
    correctPhi(finalPhi, coeffs)
  }

  def sn(u: Double, k: Double): Double = sin(am(u, k))

  //TODO overload
  def snComp(z: Complex, k: Double) = {
    val u = z.real
    val v = z.imag
    val kc = sqrt(1 - k*k)

    val denuminator = 1 - dn(u,k) * dn(u,k) * sn(v, kc) * sn(v, kc)

    val realPart = sn(u, k) * dn (v, kc) / denuminator
    val imagPart = cn(u, k) * dn(u, k) * sn(v, kc) * cn(v, kc) / denuminator

    Complex(realPart, imagPart)
  }

  def cn(u: Double, k: Double) = cos(am(u, k))

  def cnComp(z: Complex, k: Double) = {
    val u = z.real
    val v = z.imag
    val kc = sqrt(1 - k*k)

    val denuminator = 1 - dn(u,k) * dn(u,k) * sn(v, kc) * sn(v, kc)

    val realPart = cn(u, k) * cn(v, kc) / denuminator
    val imagPart = sn(u, k) * dn(u, k) * sn(v, kc) * dn(v, kc) / denuminator

    Complex(realPart, -imagPart)
  }

  def dn(u: Double, k: Double) :Double = sqrt(1 - k * k * sn(u, k) * sn(u, k))

  def dnComp(z: Complex, k: Double) = {
    val u = z.real
    val v = z.imag
    val kc = sqrt(1 - k*k)

    val denuminator = 1 - dn(u,k) * dn(u,k) * sn(v, kc) * sn(v, kc)

    val realPart = dn(u, k) * cn(v, kc)  * dn(v, kc)/ denuminator
    val imagPart = k * k * sn(u, k) * cn(u, k) * sn(v, kc) / denuminator

    Complex(realPart, -imagPart)
  }

  def cd(u: Double, k: Double) = cn(u, k) / dn(u, k)

  def cdComp(z: Complex, k: Double) = cnComp(z, k) / dnComp(z, k)

  def asn(z: Complex, k: Double):Complex = {
    def landen_(k: List[Double], err: Double):List[Double] = {
      if (k.head > err)
        landen_(pow(k.head/(1 + sqrt(1 - k.head*k.head)), 2)::k, err)
      else
        k.reverse
    }

    //TODO switch to case
    def nextU(currentU: Complex, currentK: Double, ks: List[Double]): Complex = {
      if(ks.isEmpty)
        currentU
      else {
        val sqrt1z = (1 - currentK*currentK*currentU*currentU).pow(0.5)
        val u = 2 * currentU / ((1 + ks.head) * (1 + sqrt1z))
        nextU(u, ks.head, ks.tail)
      }
    }

    val kList = landen_(List(k), 0.000000000001)

    val um = nextU(z, kList.head, kList.tail)
    val u = 2/Math.PI * asin(um)  // Inverse SN
    u
  }

}
