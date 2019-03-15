import math

import matplotlib.pyplot as plt
import numpy as np

import thinkdsp
from thinkdsp import Sinusoid, Chirp

PI2 = math.pi * 2

class CustomSawtoothSignal(Sinusoid):
    def evaluate(self, ts):
        """Evaluates the signal at the given times.
        ts: float array of times
        returns: float wave array
        """
        ts = np.asarray(ts)
        cycles = self.freq * ts + self.offset / PI2
        frac, _ = np.modf(cycles)
        #ys = thinkdsp.normalize(thinkdsp.unbias(frac), self.amp)
        #print frac.mean()
        #ys = thinkdsp.unbias(frac)
        ys = frac
        return ys


class TromboneGliss(Chirp, object):
    """Represents a sawtooth signal."""
    def evaluate(self, ts):
        return super(TromboneGliss, self).evaluate(ts)

def plotTromboneGliss():
    signal = TromboneGliss(start=10, end=20)
    wave = signal.make_wave(duration=1, start=0, framerate=100).make_spectrum()
    fig, ax = plt.subplots()
    ax.plot(wave.fs, wave.amps)
    plt.show()



def plotChirp():
    signal = thinkdsp.Chirp(start=10, end=20)
    wave = signal.make_wave(duration=1, start=0, framerate=100).make_spectrum()
    fig, ax = plt.subplots()
    ax.plot(wave.fs, wave.amps)
    plt.show()


if __name__ == "__main__":
    plotTromboneGliss()