from my_butter import buttap
from numpy import (atleast_1d, poly, polyval, roots, real)
from scipy.signal import butter, lfilter

if __name__ == "__main__":
    #a = poly([0.7143 + 0.33*1j,   0.6,   0.7143 -0.33*1j])
    #print a
    b, a = butter(2, normal_cutoff, btype='low', analog=False)

