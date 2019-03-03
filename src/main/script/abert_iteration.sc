import breeze.math.Complex

val wo = Complex(2, 2)
val z = Complex(30, 30)

val a = (z - wo) / ((z - wo).abs * (z - wo).abs)
val b = 1 / (z - wo).conjugate