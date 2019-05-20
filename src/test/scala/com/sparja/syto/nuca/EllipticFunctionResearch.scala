package com.sparja.syto.nuca

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

}
