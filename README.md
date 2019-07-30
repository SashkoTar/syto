# Syto

Digital Signal Processing library for Scala

# Filter fundamentals

Simpy digital or analog filter can be drawn schematically like this. There is input sequence of values handled by linera time-invariant filter, which generates an output sequence. 

<img src="https://latex.codecogs.com/svg.latex?\Large&space;{x_n}\to\left\langle{filter}\right\rangle\to{y_n}"/>

Output sequence depends on filter's main characteristic which is called impulse response - h(n). Impulse response defines the reaction of LTI system in zero state on unit impulse. It is very important to know that having applied Laplace transformation to impulse response function we'll recieve transfer function of filter. If system is discrete than Z-transformation should be used instead of Laplace transformation. Below is depicted transfer function for digital filter after Z-transformation.  

<img src="https://latex.codecogs.com/svg.latex?\Large&space;H(z)=\frac{b_{0}+b_{1}z^{-1}+b_{m}z^{-m}}{a_{0}+a_{1}z^{-1}+a_{n}z^{-n}}"/>

It seen that a transfer function is also a rational function. As it turns out the process of filter design converges to finding two vectors - B = [b0...bn] and A = [a0...an] which contain coeficients of polynomials at numenator and denumenator respectivelly. There are several approximations
to solve this task

At its current stage Syto implements IIR filters only. However, as Syto is young and growing library
its API will be enriched to design other families of filters soon. But for a while please keep in mind that all info below is about IIR filter only.  

The great advancements in the filter design theory have given a birth to several techniques of generating the transfer functions with expected characterisitcs. Each of them has its own pros and cons but in the majority of cases the classical approach is used. Syto exploits this method as well. It consists of three fundamental steps:

1) Find a transfer function of normalized analog filter by applying one of known approximation
2) By means of frequency transformation, obtain the transfer function of the desired filter: lowpass, highpass, bandpass or bandstop
3) Apply bilinear transformation to convert the analog transfer function to digital one  

Normalized analog filter is the filter in which the cutoff frequency is equal to 1 rad/sec

## Types of approximation
The most popular approximation methods are as follows:
- Butterworth
- Chebyshev
- Inverse-Chebyshev
- Elliptic
- Bessel-Thomson

and all these five types had been implemented in Syto

## Frequency transformations
After normalized lowpass filter H(s) is designed according to the one of approximation methods mentioned above, the frequency transformation is applied to obtain the transfer function for required filter. There are four transformations that map the magnitude response of analog normalized filter to that of specified _lowpass_, _highpass_, _bandpass_ or _bandstop_ filters

## Filtering

There are different schemas to implement filtering itself. Syto uses _direct form II structure_

# Getting started with Syto

Let's get started with Syto

## Building Syto
```bash
mvn clean package
```

## Syto as Maven dependency

```bash
to be defined
```

## Usage guide

Filtering the data with Syto is pretty simple procedure and contains three steps only:
1) Specification of filter characterisitcs (order, ripples, cutoff frequencies). It requires some efforts of a customer.
2) Building the transfer function satisfying specified parameter and extraction it's polynomials coefficients 
3) Filtering the data itself, passing the polynomials coefficients and input sequence to lfilter/filtfilt method

## Example

Having done some calculation user comes to conclusion that he needs digital third order low-pass Butterworth filter with cutOff frequency 3.5 Hz, sampled at rate 30 Hz. Therefore to get cooficients for transfer function polynomials user has to call such chain 

```scala 
   val (b, a) = new TransferFunctionBuilder()
      .butterworthApproximation(3)  // The order of Butterworth filter
      .digitalize(30)  // digital filter with sampling rate at 30 Hz
      .transformToLowPass(3.5) // Low-pass filter with cutoff frequency 3.5Hz
      .coefficients
```

Then user can start filtering itself using lfilter method
```scala 
  val y = lfilter(b, a, x) // x is sequence of input signals 
```
As alternative another method - filtfilt - can be used which applies linear filter twice 
