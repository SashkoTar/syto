import breeze.math.Complex
import breeze.numerics.{cos, sin}

def frequez(z: Complex) = {
  val root1 = Complex(0.205, 0.889)
  val root2 = Complex(0.362, 0.842)
  val root3 = Complex(0.205, 0.889).conjugate
  val root4 = Complex(0.362, 0.842).conjugate

  val numerator = ((z + 1).pow(2)) * ((z - 1).pow(2))
  val denumerator = (z - root1)*(z - root2)*(z - root3)*(z - root4)

  1/((numerator/denumerator).abs)
}


val sampleFrequency = 100
val f0 = Math.sqrt(18*22)
val angle = 2 * Math.PI * f0/sampleFrequency

val z = Complex(cos(angle), sin(angle))

val root1 = Complex(0.205, 0.889)
val root2 = Complex(0.362, 0.842)
val root3 = Complex(0.205, 0.889).conjugate
val root4 = Complex(0.362, 0.842).conjugate

val numerator = ((z + 1).pow(2)) * ((z - 1).pow(2))
val denumerator = (z - root1)*(z - root2)*(z - root3)*(z - root4)

val res = 1/((numerator/denumerator).abs)



val numm = 1 - 2 * z.pow(-2) + z.pow(-4)
val denumm = 1-1.13608549391 * z.pow(-1)  + 1.97230236061 * z.pow(-2) -0.9497603088 * z.pow(-3) + 0.700896781188 * z.pow(-4)

val a = 1/(numm / denumm).abs