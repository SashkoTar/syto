package com.sparja.syto.viz

import breeze.numerics.sigmoid
import breeze.plot.Figure
import breeze.plot._
import breeze.linalg.linspace
import org.jfree.chart.axis.NumberTickUnit
import org.jfree.chart.plot.ValueMarker

object SigmoidCurve {
  def main(args: Array[String]): Unit = {
    val x = linspace(-4.0, 4.0, 200)
    val fx = sigmoid(x)
    val fig = Figure()
    val plt = fig.subplot(0)
    plt += plot(x, fx)
    fig.refresh()

    //Customizing plots

    val f2x = sigmoid(2.0*x)
    val f10x = sigmoid(10.0*x)
    plt += plot(x, f2x, name="S(2x)")
    plt += plot(x, f10x, name="S(10x)")

    fig.refresh()
    plt.legend = true

    plt.xlim = (-4.0, 4.0)

    plt.yaxis
    plt.yaxis.setTickUnit(new NumberTickUnit(0.1))

    plt.plot
    plt.plot.addDomainMarker(new ValueMarker(0.0))
    plt.plot.addRangeMarker(new ValueMarker(1.0))

    plt.xlabel = "x"
    plt.ylabel = "f(x)"
  }

}
