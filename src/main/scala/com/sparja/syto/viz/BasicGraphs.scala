package com.sparja.syto.viz

import breeze.linalg.linspace
import breeze.numerics.{cos, sin}
import breeze.plot.{Figure, plot}

object BasicGraphs {

  def main(args: Array[String]): Unit = {
    val f = Figure()
    val p = f.subplot(0)
    val x = linspace(0.0,2.0)
    val an = linspace(0, 2 * Math.PI, 100)
    p += plot(x, x :^ 2.0)
    p += plot(x, x :^ 3.0, '.')
    p += plot(cos(an), sin(an))

    f.height = 600
    f.width = 600

    p.xlabel = "x axis"
    p.ylabel = "y axis"
    f.saveas("lines.png")
  }


}
