import math

import matplotlib.pyplot as plt
import numpy as np

import thinkdsp
from thinkdsp import Sinusoid

PI2 = math.pi * 2


class CustomSawtoothSignal(Sinusoid):
    """Represents a square signal."""

    def evaluate(self, ts):
        """Evaluates the signal at the given times.

        ts: float array of times

        returns: float wave array
        """
        ts = np.asarray(ts)
        cycles = self.freq * ts + self.offset / PI2
        frac, _ = np.modf(cycles)
        ys = self.amp * np.sign(thinkdsp.unbias(frac))
        return ys

def plotTriangle():
    tri_sig = thinkdsp.TriangleSignal(200)
    wave = tri_sig.make_wave().make_spectrum()
    fig, ax = plt.subplots()
    ax.plot(wave.fs, wave.amps)
    plt.show()

def plotTriangleAliasing():
    tri_sig = thinkdsp.TriangleSignal(1100)
    wave = tri_sig.make_wave(framerate=10000).make_spectrum()
    fig, ax = plt.subplots()
    ax.plot(wave.fs, wave.amps)
    plt.show()


def plotSinCosAliasing():
    framerate = 10000
    signal = thinkdsp.CosSignal(4500)
    duration = signal.period*5
    segmentSig = signal.make_wave(duration, framerate=framerate)
    signal = thinkdsp.CosSignal(5500)
    segmentCos = signal.make_wave(duration, framerate=framerate)
    plt.subplot(2, 1, 1)
    plt.plot(segmentSig.ts, segmentSig.ys, 'o-')
    plt.title('A tale of 2 subplots')
    plt.ylabel('Sinus')

    plt.subplot(2, 1, 2)
    plt.plot(segmentCos.ts, segmentCos.ys, '.-')
    plt.xlabel('time (s)')
    plt.ylabel('Cosinus')
    plt.show()

def plotSquare():
    sq_sig = thinkdsp.SquareSignal(100)
    wave = sq_sig.make_wave().make_spectrum()
    fig, ax = plt.subplots()
    ax.plot(wave.fs, wave.amps)
    plt.show()


if __name__ == "__main__":
    plotSinCosAliasing()