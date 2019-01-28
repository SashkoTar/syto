import com.sparja.syto.filter.butterworth.digital.DigitalButterworthLowPassFilter

def sigma(n: Int):Int = if(n == 0) 1 else 0

/*
def y(n: Int): Double = {
  if (n == -1)
     0
  else
    //rightSide(n)
  rule413(n)
}

def rightSide(n: Int) = 2*sigma(n) + 3*sigma(n-1) + 0.5 * y(n - 1)

def rule413(n: Int) = 3*sigma(n) - y(n - 1)/3

0 to 5 foreach {i => println(s"y($i) = ${y(i)}") }
*/

val filter = DigitalButterworthLowPassFilter(3, 10f, 40f)
val (b, a) = filter.calculateCoefficients()

val xseq = (0 to 31).map(ang => {Math.sin(ang * 2 * Math.PI/6.0) + Math.sin(ang * 2 * Math.PI/32.0)}).toList

def x(n:Int) = {
  if (n < 0)
    0.0
  else
    Math.sin(n * 2 * Math.PI/6.0) + Math.sin(n * 2 * Math.PI/32.0)
}

x(0)
x(31)


def y(n: Int): Double = {
  if (n < 0)
    0
  else
  -a(1)*y(n-1)-a(2)*y(n-2)-a(3)*y(n-3) + b(0)*x(n) + b(1)*x(n-1) + b(2)*x(n-2) + b(3)*x(n-3)
}

//0 to 20 foreach {i => println(s"y($i) = ${y(i)}") }

