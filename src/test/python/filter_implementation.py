from scipy import signal
import matplotlib.pyplot as plt
import numpy as np


def lfilter_(b, a, y):
    return signal.lfilter(b, a, y)


def filtfilt_(b, a, y):
    return signal.filtfilt(b, a, y)


if __name__ == "__main__":
    end = 30
    t = np.linspace(1, end, end)
    #x = (np.sin(2*np.pi*0.75*t*(1-t) + 2.1) + 0.1*np.sin(2*np.pi*1.25*t + 1) + 0.18*np.cos(2*np.pi*3.85*t))
    #xn = x + np.random.randn(len(t)) * 0.08
    b, a = signal.butter(4, 0.3)
    #zi = signal.lfilter_zi(b, a)
    #print(zi)
    print(b)
    print(a)
    res = lfilter_(b, a, t)
    print("LFILT:")
    print(res)

    #res = filtfilt_(b, a, t)
    #print("FILTFILT")
    #print(res)




