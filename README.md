# Syto

Digital Signal Processing library for Scala

# Filter fundamentals

Simpy digital or analog filter can be drawn schematically like this. There is input sequence of values handled by linera time-invariant filter, which generates an output sequence. 

<img src="https://latex.codecogs.com/svg.latex?\Large&space;{x_n}\to\left\langle{filter}\right\rangle\to{y_n}"/>

Output sequence depends on filter's main characteristic which is called impulse response - h(n). Impulse response defines the reaction of LTI system in zero state on unit impulse. It is very important to know that having applied Laplace transformation to impulse response function we'll recieve transfer function of filter. If system is discrete than Z-transformation should be used instead of Laplace transformation. Below is depicted transfer function for digital filter after Z-transformation.  

<img src="https://latex.codecogs.com/svg.latex?\Large&space;H(z)=\frac{b_{0}+b_{1}z^{-1}+b_{m}z^{-m}}{a_{0}+a_{1}z^{-1}+a_{n}z^{-n}}"/>

It seen that a transfer function is also a rational function. As it turns out the process of filter design converges to finding two vectors - B and A which contain coeficients of polynomials at numenator and denumenator respectivelly. There are several approximations
to solve this task
## Types of approximation

## Types of filters

## Filtering

# Getting started with Syto

## Building Syto

## Syto as Maven dependency

## Usage

```scala 
   val (b, a) = new TransferFunctionBuilder()
      .butterworthApproximation(order)
      .digitalize(sampleFreq)
      .transformToLowPass(cufOffFreq)
      .coefficients
```


