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
  def shouldCalculateFourOrderPrototype() = {
   /*
   val (z, p, k) = calculateRoots(4)
   assertEquals(k, 0.00562418320487, 0.001)
   assertEquals(z(0), 4.05301771243j, 0.001)
   assertEquals(z(1), 1.80715694465j, 0.001)
   assertEquals(z(2), -4.05301771243j, 0.001)
   assertEquals(z(3), -1.80715694465j, 0.001)
   assertEquals(p(0), (-0.357890522369-0.460224795676j), 0.001)
   assertEquals(p(1), (-0.113251415013-0.991559809987j), 0.001)
   assertEquals(p(2), (-0.357890522369+0.460224795676j), 0.001)
   assertEquals(p(3), (-0.113251415013+0.991559809987j), 0.001) */
   val roots = calculateRoots(4) //TODO check the accuracy
   val expectedZeros = List(Complex(0, 4.05301771243), Complex(0, 1.80715694465), Complex(0, -4.05301771243), Complex(0, -1.80715694465))
   ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

  }

  @Test
  def shouldCalculateFiveOrderPrototype() = {
    /*
    val (z, p, k) = calculateRoots(5)
    assertEquals(k, 0.0297358859419, 0.001)
    assertEquals(z(0), 1.94559171398j, 0.001)
    assertEquals(z(1), 1.34685649439j, 0.001)
    assertEquals(z(2), -1.94559171398j, 0.001)
    assertEquals(z(3), -1.34685649439j, 0.001)
    assertEquals(p(0), (-0.36411313623+0j), 0.001)
    assertEquals(p(1), (-0.223925617635-0.715617958499j), 0.001)
    assertEquals(p(2), (-0.0567369924904-0.997075184324j), 0.001)
    assertEquals(p(3), (-0.223925617635+0.715617958499j), 0.001)
    assertEquals(p(4), (-0.0567369924904+0.997075184324j), 0.001)
     */
    val roots = calculateRoots(5)
    val expectedZeros = List(Complex(0, 1.94559171398), Complex(0, 1.34685649439), Complex(0, -1.94559171398), Complex(0, -1.34685649439))
    ComplexAssertion.assertContainAll(roots.zeros, expectedZeros)

    val expectedPoles = List(Complex(-0.364113, 0), Complex(-0.22392, -0.715617), Complex(-0.05673, -0.99707), Complex(-0.22392, 0.715617), Complex(-0.05673, 0.99707))
    ComplexAssertion.assertContainAll(roots.poles, expectedPoles)
  }



  def findZero(u: Double, k: Double) = Complex.i / (k * cd(u * K(k*k), k))

  @Test //(0.19748603438284257, 0.9803057003933715, 0.9820089760833982, 0.19879278412406867)
  def printEF() = {
    val k = sqrt(0.93)
    val u = 0.2
    println(K(k))
   // println(sn(u, k))
   // println(cn(u, k))
   // println(dn(u, k))
    println(am(u, sqrt(k)))
   // println(cd(u * K(k), k))
  }

  @Test
  def compareOutputs() = {
    val k = sqrt(0.93)
    println(K(k))
    println(PI/2)
    println(am(2.7470730, k))
  }

  @Test
  def shouldFindZeros() = {
    val k = 0.76676
    val u = List(0.2, 0.6)
    //val zeta_i = u.map(cd(_, k))

    val zeros = u.map(findZero(_, k))

    println(zeros)

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
