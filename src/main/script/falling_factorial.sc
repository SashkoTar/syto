

val fac = 2
val x = 5


def fallingFactorial(x: Int, n: Int) = (0 to n-1).map(x - _).product

fallingFactorial(5, 3)
fallingFactorial(5, 2)
fallingFactorial(4, 2)



