import math

import matplotlib.pyplot as plt
import numpy as np

from thinkdsp import Sinusoid

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


def plotSawtoothSignal():
    sig = CustomSawtoothSignal(10)
    duration = sig.period*5
    wave = sig.make_wave(duration, 100)
    fig, ax = plt.subplots()
    ax.plot(wave.ts, wave.ys)
    plt.show()




if __name__ == "__main__":
    plotSawtoothSignal()