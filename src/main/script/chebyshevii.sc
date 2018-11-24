import breeze.math.Complex
import breeze.numerics.{cos, sin}


val order = 5

val kList = List(-4, -2,  2,  4)

List(-4, -2,  2,  4).
  map(_ * Math.PI / (2 * order)).
  map(a => (Complex.i / sin(a)).conjugate).
  toList
