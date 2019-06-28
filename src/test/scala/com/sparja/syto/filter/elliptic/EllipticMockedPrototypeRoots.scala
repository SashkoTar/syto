package com.sparja.syto.filter.elliptic

import breeze.math.Complex
import com.sparja.syto.filter.Roots

object EllipticMockedPrototypeRoots {

  val twoOrder = Roots(
    List(Complex(0, 5.87377172911), Complex(0, -5.87377172911)),
    List(Complex(-0.2266968371, -0.749762526686), Complex(-0.2266968371, 0.749762526686)),
    0.0100001249734
  )

  val threeOrder = Roots(
    List(Complex(0, 2.00510018555), Complex(0, -2.00510018555)),
    List(Complex(-0.0935075113306, -0.905385482998), Complex(-0.0935075113306, 0.905385482998), Complex(-0.235554376355, 0.0)),
    0.0485393536936
  )

}

