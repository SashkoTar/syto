# Syto

Digital Signal Processing library for Scala

# Filter fundamentals

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


