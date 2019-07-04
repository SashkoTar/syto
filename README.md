# Syto

Digital Signal Processing library for Scala

# Filter fundamentals



<img src="https://latex.codecogs.com/svg.latex?\Large&space;x_n\to\left\langlefilter\right\rangle\toy_n"/>

<img src="https://latex.codecogs.com/svg.latex?\Large&space;H(z)=\frac{b_{0}+b_{1}z^{-1}+b_{m}z^{-m}}{a_{0}+a_{1}z^{-1}+a_{n}z^{-n}}"/>

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


