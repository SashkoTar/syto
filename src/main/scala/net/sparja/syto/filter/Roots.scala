package net.sparja.syto.filter

import breeze.math.Complex
import scala.collection.immutable.Seq

case class Roots(val zeros: Seq[Complex], val poles: Seq[Complex], val scale: Double)
