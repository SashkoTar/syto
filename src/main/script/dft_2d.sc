import breeze.math.Complex
import breeze.numerics.{cos, sin}


def getComplexByAngle(ang: Double): Complex = Complex(cos(ang), sin(ang))

0 to 8 map (_ * 2 * Math.PI /8.0) map getComplexByAngle foreach println

