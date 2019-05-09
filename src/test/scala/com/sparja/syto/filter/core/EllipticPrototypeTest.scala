package com.sparja.syto.filter.core

import breeze.math.Complex
import com.sparja.syto.common.Math.{PI, asin, cos, sin, sqrt}
import com.sparja.syto.nuca.EllipticFunction.{am, ellipInc}
import junit.framework.TestCase.assertEquals
import org.junit.Test

import scala.collection.mutable.ListBuffer

class EllipticPrototypeTest {

  def calculateRoots(order: Int) = Prototype.elliptic(order, 5, 40)

  //@Test
  def shouldCalculateTwoOrderPrototype() = {
    val roots = calculateRoots(2)
    assertEquals(roots.scale, 0.0100001249734, 0.001)
    assertEquals(roots.zeros(0).imag, 5.8737717, 0.001)
    assertEquals(roots.zeros(1).imag, -5.873771, 0.001)

    assertEquals(roots.poles(0).real, -0.2266968371, 0.001)
    assertEquals(roots.poles(0).imag, -0.749762526686, 0.001)
    assertEquals(roots.poles(1).real, -0.2266968371, 0.001)
    assertEquals(roots.poles(1).imag, 0.749762526686, 0.001)
  }

  def sn(u: Double, k: Double) = sin(am(u, k))

  def cn(u: Double, k: Double) = cos(am(u, k))

  def dn(u: Double, k: Double) = sqrt(1 - k * k * sn(u, k) * sn(u, k))

  def cd(u: Double, k: Double) = cn(u, k) / dn(u, k)

  def K(k: Double) = F(PI/2, k)

  def F(z: Double, k: Double) = ellipInc(asin(sqrt(k)), z)

  def findZero(u: Double, k: Double) = Complex.i / (k * cd(u * K(k*k), k))

  @Test //(0.19748603438284257, 0.9803057003933715, 0.9820089760833982, 0.19879278412406867)
  def printEF() = {
    val k = 0.93
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
    val k = 0.93
    println(K(k))
    println(PI/2)
    println(am(2.7470730, sqrt(k)))
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
