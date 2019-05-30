from scipy import signal
import matplotlib.pyplot as plt
import numpy as np




if __name__ == "__main__":
    t = np.linspace(1, 20, 20)
    #x = (np.sin(2*np.pi*0.75*t*(1-t) + 2.1) + 0.1*np.sin(2*np.pi*1.25*t + 1) + 0.18*np.cos(2*np.pi*3.85*t))
    #xn = x + np.random.randn(len(t)) * 0.08
    b, a = signal.butter(3, 0.3)
  #  zi = signal.lfilter_zi(b, a)
  #  print(zi)
    print(b)
    print(a)
    res = signal.lfilter(b, a, t)
    print(res)




