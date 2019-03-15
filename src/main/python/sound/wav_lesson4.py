import math

import matplotlib.pyplot as plt

import thinkdsp

PI2 = math.pi * 2

def plotUncorrelatedUniformNoise():
    signal = thinkdsp.UncorrelatedUniformNoise()
    wave = signal.make_wave(duration=0.1, framerate=11025)
    fig, ax = plt.subplots()
    ax.plot(wave.ts, wave.ys)
    plt.show()

def plotUncorrelatedUniformNoiseSpectrum():
    signal = thinkdsp.UncorrelatedUniformNoise()
    wave = signal.make_wave(duration=0.1, framerate=11025).make_spectrum()
    wave.plot_power(linewidth=0.5)
    fig, ax = plt.subplots()
    ax.plot(wave.fs, wave.amps)
    plt.show()


def executeBartlett():
    tri_sig = thinkdsp.SinSignal(200) + thinkdsp.SinSignal(300)
    wave = tri_sig.make_wave(duration=1.)
    plotSignalAndSpectrum(wave)



def plotSignalAndSpectrum(wave):
    spectrum = wave.make_spectrum()
    plt.subplot(2, 1, 1)
    plt.plot(spectrum.fs, spectrum.amps)
    #plt.axvline(20, color='k')
    #plt.xlim(0, 200)
    #plt.title("Lowpass Filter Frequency Response")
    plt.xlabel('Frequency [Hz]')
    plt.grid()
    plt.subplot(2, 1, 2)
    plt.plot(wave.ts, wave.ys, 'b-', label='data')
    plt.xlabel('Time [sec]')
    plt.grid()
    plt.legend()

    #plt.subplots_adjust(hspace=0.35)
    plt.show()



if __name__ == "__main__":
    executeBartlett()
    #plotUncorrelatedUniformNoiseSpectrum()