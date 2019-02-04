package com.sparja.syto.filter.bessel

import breeze.math.Complex
import com.sparja.syto.filter.core.Roots

object BesselMockedPrototypeRoots {

  val twoOrder = Roots(
    List.empty[Complex],
    List(Complex(-0.866025403784, 0.5), Complex(-0.866025403784, -0.5)),
    1.0
  )

  val threeOrder = Roots(
    List.empty[Complex],
    List(Complex(-0.745640385848, 0.711366624973), Complex(-0.941600026533, 0), Complex(-0.745640385848, -0.711366624973)),
    1.0
  )

}
