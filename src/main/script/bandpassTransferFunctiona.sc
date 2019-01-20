import breeze.math.Complex


val bandWidth = 2
val w0 = 5
val pa = List(Complex(1,1), Complex(1,-1))


def transformPoint(point: Complex):Complex = {
  (-bandWidth * point + (bandWidth*bandWidth*point*point - 4*w0*w0).pow(0.5))/2
}

pa.map(transformPoint)