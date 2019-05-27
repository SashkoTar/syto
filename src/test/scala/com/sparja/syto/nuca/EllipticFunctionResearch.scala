package com.sparja.syto.nuca

import breeze.math.Complex
import com.sparja.syto.common.Math.{acos, pow, sqrt}
import com.sparja.syto.nuca.EllipticFunction.{F, K}
import com.sparja.syto.nuca.JacobiEllipticFunction.{am, sn}
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
  def testAcde() = {
    val k = 0.76676
    val u = acde(Complex(-2.03459, 0.83896), k)

    println(s"U = $u")

  }

  @Test
  def testAcde2() = {
    val k = 0.93
    val u = acde(Complex(0.7618000, 0), k)

    println(s"U = $u")

  }

  def acde(wc: Complex, k: Double) = {
    val v = landen(List(k), 0.0000000001).reverse
    //println(v)
    var w = wc //
    var v1 = k
    (0 until  v.size).foreach(n => {
      v1 = if (n == 0 ) k else v(n-1)
      println(s"W = $w")
      w = w/(1 + (1 - w*w*v1*v1).pow(0.5)) * 2/(1+v(n))
    })
    println(s"W = $w")
    val u = 2/Math.PI * acos(w)
    u
  }

  def landen(k: List[Double], err: Double):List[Double] = {
    //k = (k/(1+sqrt(1-k^2)))^2;
    println(s"k = ${k.head}")
    if (k.head > err)
      landen(pow(k.head/(1 + sqrt(1 - k.head*k.head)), 2)::k, err)
    else
      k.dropRight(1)
  }
  @Test
  def newLanden() = {

    landen(List(0.93), 0.000001)

    println("K ->" + landen(List(0.93), 0.000001))
  }

  @Test
  def acos1() = {
    val z = Complex(0.34760254636020216, 0.274579463199337)
    println(acos(z))
  }

}
