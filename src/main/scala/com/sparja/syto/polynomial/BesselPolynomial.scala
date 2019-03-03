package com.sparja.syto.polynomial

object BesselPolynomial {

  //TODO refactor to be Polynomial. Quit ugly solution however workable
  def calculate(order: Int): Polynomial = {
      //yn+i = (2m + l)xyn + yn-x
   // println(s"Current order is $order")
    if(order < 0)
      return Polynomial(List(Coefficient(0, 0, 0)))
    if (order == 0)
      return Polynomial(List(Coefficient(1, 0, 0)))
    if (order == 1)
      return  Polynomial(List(Coefficient(1, 0, 1), Coefficient(1, 0, 0)))
    else {
      val coeff = Coefficient(2 * order - 1, 0, 1)
      //println(s"Next coeff is $coeff")
      val p1 = calculate(order - 1).coefficients.map(_.multiply(coeff))
      val p2 = calculate(order - 2).coefficients
      val polSum = (p1:::p2).groupBy(_.degree)
        .mapValues(_.reduce((a, b) => a.add(b))).values.toList.sortWith(_.degree > _.degree)
      return Polynomial(polSum)
    }




  }

}
