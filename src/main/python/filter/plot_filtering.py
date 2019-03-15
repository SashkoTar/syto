import matplotlib.pyplot as plt
import numpy as np
from scipy.signal import butter, lfilter

# building the test signal, a sum of two sines;
N = 32
x = np.sin(np.arange(N)/6. * 2 * np.pi)+ \
    np.sin(np.arange(N)/32. * 2 * np.pi)

# getting filter coefficients from scipy
b,a = butter(N=3, Wn=0.5)


# getting results of scipy lfilter to test my implementation against
y_lfilter = lfilter(b, a, x)


plt.plot(x, color='silver', label='Original')
#plt.plot(sig_ff, color='#3465a4', label='filtfilt')
plt.plot(y_lfilter, color='#cc0000', label='lfilter')
#plt.grid(True, which='both')
plt.legend(loc="best")
plt.show()



# printing the results
print "{}\t{}".format("x", 'lfilter')
for n in range(N):
    print "{}\t{}".format(x[n], y_lfilter[n])