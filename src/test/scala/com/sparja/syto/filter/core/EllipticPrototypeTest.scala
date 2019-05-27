package com.sparja.syto.filter.core

import breeze.math.Complex
import com.sparja.syto.common.Math.{PI, asin, sin, sqrt}
import com.sparja.syto.nuca.EllipticFunction.K
import com.sparja.syto.nuca.JacobiEllipticFunction.{am, cd}
import com.sparja.syto.util.ComplexAssertion
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EllipticPrototypeTest {

  def calculateRoots(order: Int) = Prototype.elliptic(order, 1, 45)

 @Test
  def shouldCalculateThreeOrderPrototype() = {
   /*
   val (z, p, k) = calculateRoots(3)
   assertEquals(k, 0.0471420077504, 0.001)
   assertEquals(z(0), 3.30569332109j, 0.001)
   assertEquals(z(1), -3.30569332109j, 0.001)
   assertEquals(p(0), (-0.514143577259+0j), 0.001)
   assertEquals(p(1), (-0.233500784754-0.973362052005j), 0.001)
   assertEquals(p(2), (-0.233500784754+0.973362052005j), 0.001)
    */
   val roots = calculateRoots(3) //TODO check the accuracy
   println(roots)
   /*
   val expectedZeros = List(Complex(0, 4.05301771243), Complex(0, 1.80715694465), Complex(0, -4.05301771243), Complex(0, -1.80715694465))
   ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

   val expectedPoles = List(Complex(-0.35789, -0.4602247), Complex(-0.113251, -0.9915598), Complex(-0.35789, 0.4602247), Complex(-0.113251, 0.9915598))
   ComplexAssertion.assertContainAll(roots.poles, expectedPoles)
  */
 }


  @Test
  def shouldCalculateFourOrderPrototype() = {
   val roots = calculateRoots(4) //TODO check the accuracy
   val expectedZeros = List(Complex(0, 4.05301771243), Complex(0, 1.80715694465), Complex(0, -4.05301771243), Complex(0, -1.80715694465))
   ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

   val expectedPoles = List(Complex(-0.35789, -0.4602247), Complex(-0.113251, -0.9915598), Complex(-0.35789, 0.4602247), Complex(-0.113251, 0.9915598))
   ComplexAssertion.assertContainAll(roots.poles, expectedPoles)

 }

  @Test
  def shouldCalculateFiveOrderPrototype() = {
    val roots = calculateRoots(5)
    val expectedZeros = List(Complex(0, 1.94559171398), Complex(0, 1.34685649439), Complex(0, -1.94559171398), Complex(0, -1.34685649439))
    ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

    val expectedPoles = List(Complex(-0.364113, 0), Complex(-0.22392, -0.715617), Complex(-0.05673, -0.99707), Complex(-0.22392, 0.715617), Complex(-0.05673, 0.99707))
    ComplexAssertion.assertContainAll(roots.poles, expectedPoles)
  }



/*

a[0] = 1.0;
b = sqrt(1.0 - m);
c[0] = sqrt(m);
twon = 1.0;
i = 0;

while( fabs(c[i]/a[i]) > MACHEP )
	{
	if( i > 7 )
		{
		mtherr( "ellpj", OVERFLOW );
		goto done;
		}
	ai = a[i];
	++i;
	c[i] = ( ai - b )/2.0;
	t = sqrt( ai * b );
	a[i] = ( ai + b )/2.0;
	b = t;
	twon *= 2.0;
	}

done:

/* backward recurrence */
phi = twon * a[i] * u;
do
	{
	t = c[i] * sin(phi) / a[i];
	b = phi;
	phi = (asin(t) + phi)/2.0;
	}
while( --i );


 */

  @Test
  def testCephesAm() = {
    val k = 0.93
    println(cephesAm(2.7470730, k))
  }

  def cephesAm(u: Double, k: Double) = {
    var a = new Array[Double](9)
    var c = new Array[Double](9)
    a(0) = 1.0
    var b = sqrt(1 - k)
    c(0) = sqrt(k)
    var twon = 1.0
    var i = 0
    var ai = 0.0
    var t: Double = 0

    while (c(i)/a(i) > 0.0000000001) {
      ai = a(i)
      i += 1
      c(i) = ( ai - b )/2.0
      t = sqrt( ai * b )
      a(i) = ( ai + b )/2.0
      b = t
      twon *= 2.0
    }

    var phi = twon * a(i) * u


    while(i != 0){
      t = c(i) * sin(phi) / a(i);
      b = phi;
      phi = (asin(t) + phi)/2.0;
      i -= 1
    }

    phi

  }

}
