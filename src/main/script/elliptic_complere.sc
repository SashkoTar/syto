import breeze.numerics.sin

def nextK(k: Double) = math.pow(k /(1 + math.sqrt(1 - k*k)), 2)

val u = sin(math.Pi * 0.3) //0.6

def nextAm(k: Double, am: Double) = {
    val a = math.sqrt(1 - k*k)*math.tan(am)
    val b =  math.atan(a)
    val c = b + am
    c
}

val k = 1/math.sqrt(2)
val am = scala.math.Pi/6


nextK(0.007469666729509583)
nextAm(0.007469666729509583, 1.815143076528948)


