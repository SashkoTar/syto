
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

from scipy.signal import butter, lfilter


def configure_butter_bandpass(lowcut, highcut, fs, order):
    nyq = 0.5 * fs
    low = lowcut / nyq
    high = highcut / nyq
    b, a = butter(order, [low, high], btype='band')
    return b, a

def configure_harmonic(module, frequency, x, phase=0.0):
    return module * np.cos(2 * np.pi * frequency * x + phase)

def filter_signal(data, lowcut, highcut, fs, order=5):
    b, a = configure_butter_bandpass(lowcut, highcut, fs, order=order)
    y = lfilter(b, a, data)
    return y


if __name__ == "__main__":
    import numpy as np
    import matplotlib.pyplot as plt
    from scipy.signal import freqz

    # Sample rate and desired cutoff frequencies (in Hz).
    fs = 5000.0
    lowcut = 200.0
    highcut = 500.0

    # Plot the frequency response for a few different orders.
    plt.figure(1)
    plt.clf()
    for order in [3, 6, 9]:
        b, a = configure_butter_bandpass(lowcut, highcut, fs, order=order)
        w, h = freqz(b, a, worN=2000)
        plt.plot((fs * 0.5 / np.pi) * w, abs(h), label="order = %d" % order)

    plt.plot([0, 0.5 * fs], [np.sqrt(0.5), np.sqrt(0.5)],
             '--', label='sqrt(0.5)')
    plt.xlabel('Frequency (Hz)')
    plt.ylabel('Gain')
    plt.grid(True)
    plt.legend(loc='best')

    # Filter a noisy signal.
    T = 0.05
    nsamples = T * fs
    t = np.linspace(0, T, nsamples, endpoint=False)
    a = 0.02
    f0 = 600.0
    #y = 0.1 * np.sin(2 * np.pi * 1.2 * np.sqrt(t))

    y = configure_harmonic(0.01, 312, t, 0.1)

    y += configure_harmonic(a, f0, t, 0.11)

   # y += configure_harmonic(0.02, 800, t, 0.2)

    y += configure_harmonic(0.03, 2000, t, 0.0)


    plt.figure(2)
    plt.clf()
    plt.plot(t, y, label='Noisy signal')

    y_filtered = filter_signal(y, lowcut, highcut, fs, order=2) + 0.2
    plt.plot(t, y_filtered, label='Filtered signal (%g Hz)' % f0)
    plt.xlabel('time (seconds)')
    plt.hlines([-a, a], 0, T, linestyles='--')
    plt.grid(True)
    plt.axis('tight')
    plt.legend(loc='upper left')

    plt.show()