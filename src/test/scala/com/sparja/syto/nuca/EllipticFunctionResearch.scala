package com.sparja.syto.nuca

import breeze.math.Complex
import com.sparja.syto.common.Math.{acos, asin, pow, sqrt}
import com.sparja.syto.nuca.EllipticFunction.{F, K}
import com.sparja.syto.nuca.JacobiEllipticFunction.{am, asn, sn}
import org.junit.Test


class EllipticFunctionResearch {

  @Test
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





  @Test
  def acos1() = {
    val z = Complex(0.34760254636020216, 0.274579463199337)
    println(acos(z))
  }





  @Test
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

  @Test
  def shouldRunInverseSNComplex() = {
    val k = 0.93
    val z = Complex(0.91792, 0)
    println(asn(z, k))
  }

  @Test
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


}
