package com.sparja.syto.nuca

import breeze.math.Complex
import com.sparja.syto.common.Math._
import com.sparja.syto.nuca.EllipticFunction.{F, K}
import com.sparja.syto.nuca.JacobiEllipticFunction.{am, sn}


private class EllipticFunctionResearch {

  //@Test
  def calc() = {

    val k = 0.8
    val u = F(100, k)
    val snVal = sn(u, k)
    val Kval = K(k)
    println(s"u = $u")
    println(s"sn($u) = ${sn(u, k)}")
    println(s"Kval = $Kval")

    var m = 0
    while (u - m*4*Kval > Kval) {
      m += 1
    }

    println(s"m = $m")
    println(s"Next u = ${u - m*4*Kval}")
    println(s"sn(${u - m*4*Kval}) = ${sn(u - m*4*Kval, k)}")
    println(s"Amplitude for ${u - m*4*Kval}, $k  = ${am(u - m*4*Kval, k)}")

  }


/*
  for n = 1:length(v),                   	              % descending Landen/Gauss transformations
  if n==1, v1=k; else v1=v(n-1); end                % v1 stands for v(n-1) = 2*sqrt(v(n))/(1+v(n))
  w = w./(1 + sqrt(1 - w.^2 * v1^2)) * 2/(1+v(n));  % solves 1/w(n-1) = (1/w(n) + v(n)*w(n))/(1+v(n)), for 1/w(n)
  end
*/


 // @Test
  def acos1() = {
    val z = Complex(0.34760254636020216, 0.274579463199337)
    println(acos(z))
  }


  //@Test
  def shouldRunAsInWebPageComplex() = {

    def landen_(k: List[Double], err: Double):List[Double] = {
      if (k.head > err)
        landen_(pow(k.head/(1 + sqrt(1 - k.head*k.head)), 2)::k, err)
      else
        k.reverse
    }

    //TODO switch to case
    def nextU(currentU: Complex, currentK: Double, ks: List[Double]): Complex = {
     // println(s"k=$currentK\tu=$currentU")
      if(ks.isEmpty)
        currentU
      else {
        val sqrt1z = (1 - currentK*currentK*currentU*currentU).pow(0.5)
        val u = 2 * currentU / ((1 + ks.head) * (1 + sqrt1z))
        nextU(u, ks.head, ks.tail)
      }
    }

    val k = 0.93
    val z = Complex(0.7618, 0)
    val kList = landen_(List(k), 0.000000000001)

    val um = nextU(z, kList.head, kList.tail)
    val u = 2/Math.PI * acos(um)  // Inverse CD
    println(s"\n u(M) = $um, u = $u")
  }


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
    println(s"\n u(M) = $um, u = $u")
    u
  }

 // @Test
  def shouldRunInverseSNComplex() = {
    val k = 0.93
    val z = Complex(0.91792, 0)
    println(asn(z, k))
  }

  //@Test
  def shouldFindV0() = {
    val k1 = 0.0028615
    val order = 5
    val Kval = K(k1)
    val z = Complex.i/0.50885

    val asnVal = asn(z, k1)

    val v0 = -Complex.i/(order) * asnVal // 0.18181
  //  val v0 = -Complex.i/5 * asn(z, k)

      println(v0)
  }



  /*

  a[0] = 1.0;
  b = sqrt(1.0 - m);
  c[0] = sqrt(m);
  twon = 1.0;
  i = 0;

  while( fabs(c[i]/a[i]) > MACHEP )
    {
    if( i > 7 )
      {
      mtherr( "ellpj", OVERFLOW );
      goto done;
      }
    ai = a[i];
    ++i;
    c[i] = ( ai - b )/2.0;
    t = sqrt( ai * b );
    a[i] = ( ai + b )/2.0;
    b = t;
    twon *= 2.0;
    }

  done:

  /* backward recurrence */
  phi = twon * a[i] * u;
  do
    {
    t = c[i] * sin(phi) / a[i];
    b = phi;
    phi = (asin(t) + phi)/2.0;
    }
  while( --i );


   */

 // @Test
  def testCephesAm() = {
    val k = 0.93
    println(cephesAm(2.7470730, k))
  }

  private def cephesAm(u: Double, k: Double) = {
    var a = new Array[Double](9)
    var c = new Array[Double](9)
    a(0) = 1.0
    var b = sqrt(1 - k)
    c(0) = sqrt(k)
    var twon = 1.0
    var i = 0
    var ai = 0.0
    var t: Double = 0

    while (c(i) / a(i) > 0.0000000001) {
      ai = a(i)
      i += 1
      c(i) = (ai - b) / 2.0
      t = sqrt(ai * b)
      a(i) = (ai + b) / 2.0
      b = t
      twon *= 2.0
    }

    var phi = twon * a(i) * u


    while (i != 0) {
      t = c(i) * sin(phi) / a(i);
      b = phi;
      phi = (asin(t) + phi) / 2.0;
      i -= 1
    }

    phi

  }



}
