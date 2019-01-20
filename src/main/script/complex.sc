import breeze.math.Complex
import org.apache.commons.math3.util.FastMath.{cos, sin, tan}

val sampleFrequency = 100
val lowCutoffFrequency = 18
val highCutoffFrequency = 22

val order = 2
val thetas = (1 to order).map(k => (2 * k - 1) * Math.PI / (2 * order))
val pa = thetas.map(theta => Complex(-sin(theta), cos(theta)))


val preWarpedLowCutoffFrequencyFrequency = sampleFrequency / Math.PI * tan(Math.PI * lowCutoffFrequency / sampleFrequency)
val preWarpedHighCutoffFrequencyFrequency = sampleFrequency / Math.PI * tan(Math.PI * highCutoffFrequency / sampleFrequency)
// Width of band
val bandWidth = preWarpedHighCutoffFrequencyFrequency - preWarpedLowCutoffFrequencyFrequency
//the geometric mean of lowCutoffFrequency and highCutoffFrequency
val Fo = Math.sqrt(preWarpedLowCutoffFrequencyFrequency * preWarpedHighCutoffFrequencyFrequency)

def someValue(c: Complex) = bandWidth * c/Fo * 0.5

val scaledPa = pa.map(p => (2 * Math.PI * Fo * someValue(p), 2 * Math.PI * Fo * ( (1 - someValue(p).pow(2)).pow(0.1) )))

val positivePa = scaledPa.map(p => p._1 + p._2 * Complex.i).toList
val negativePa = scaledPa.map(p => p._1 - p._2 * Complex.i).toList

//val pas =

val poles = (positivePa:::negativePa).map(p => {
  val num = 1 + p/(2 * sampleFrequency)
  val denum = 1 - p/(2 * sampleFrequency)
  num / denum
}).toList



