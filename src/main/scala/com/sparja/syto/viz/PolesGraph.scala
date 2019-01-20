package com.sparja.syto.viz

import breeze.linalg.linspace
import breeze.math.Complex
import breeze.numerics.{cos, log10, pow, sin}
import breeze.plot.{Figure, plot}
import org.jfree.chart.axis.NumberTickUnit
import org.jfree.chart.plot.ValueMarker

object PolesGraph {

  def graph() = {
    val f = Figure()
    val p = f.subplot(0)
    val x = linspace(0.0,2.0)
    val an = linspace(0, 2 * Math.PI, 100)
    //p += plot(x, x :^ 2.0)
    //p += plot(x, x :^ 3.0, '.')
    p += plot(cos(an), sin(an))
    p += plot(List(1,1.3,1.44, 1.6), List(2, 1.3, 2, 1), '+', colorcode="red")
    f.height = 600
    f.width = 600

    p.xlabel = "a"
    p.ylabel = "jw"
    f.refresh()
  }


  def plotButterworthPoles() = {

    val order = 4
    val cutOffFreq = 1.0

    def poles(order: Int, cutOffFreq: Double) = {
      (1 to order)
        .map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex(-sin(theta), cos(theta))).map(_ * cutOffFreq).toList
    }

    def real(points: List[Complex]) = points.map(_.real)
    def imag(points: List[Complex]) = points.map(_.imag)

    val an = linspace(0, 2 * Math.PI, 300)
    val fig = Figure()
    val plt = fig.subplot(0)
    plt.xlim = (-2.0, 2.0)
    plt.ylim = (-2.0, 2.0)

    plt += plot(cos(an), sin(an))

    plt += plot(real(poles(order, cutOffFreq)), imag(poles(order, cutOffFreq)) , '+', colorcode = "red", name="normalized poles")

    plt += plot(real(poles(order, 2.0)), imag(poles(order, 2.0)) , '+', colorcode = "green", name="transformed poles")


    fig.height = 800
    fig.width = 800

    plt.plot.addDomainMarker(new ValueMarker(0.0))
    plt.plot.addRangeMarker(new ValueMarker(0.0))

    plt.legend = true
    plt.xlabel = "Real"
    plt.ylabel = "Imaginary"

    plt.yaxis
    plt.yaxis.setTickUnit(new NumberTickUnit(0.2))

    plt.xaxis
    plt.xaxis.setTickUnit(new NumberTickUnit(0.2))

    fig.refresh()

  }


  def plotButterworthMagnitudeSquaredFrequencyResponse() = {
    val order = 4
    val x = linspace(0.0, 5.0, 100)
    val f = butterworthApproximation(4, 1)

    val fig = Figure()
    val plt = fig.subplot(0)
    plt += plot(x, x.map(f))

    fig.height = 400
    fig.width = 400
    fig.refresh()
  }

  def plotButterworthDbMagnitudeFrequencyResponse() = {
    val order = 4
    val x = linspace(0.0, 10.0, 100)
    val f = butterworthApproximation(4, 1)

    val fig = Figure()
    val plt = fig.subplot(0)
    plt.xlim = (-1.0, 17.0)
    plt.ylim = (-10, 1.0)

    plt += plot(x, x.map(f).map(10*log10(_)))

    plt.plot.addDomainMarker(new ValueMarker(0.0))
    plt.plot.addRangeMarker(new ValueMarker(0.0))

    fig.height = 400
    fig.width = 400
    fig.refresh()
  }

  def butterworthApproximation(order: Int, Ep: Double) = {
    (freq: Double) => 1f/(1f + Ep * Ep * pow(freq, 2*order))
  }

  def main(args: Array[String]): Unit = {
      //graph()
    //plotButterworthPoles()
    //plotButterworthMagnitudeSquaredFrequencyResponse()
    plotButterworthDbMagnitudeFrequencyResponse()
  }

}
