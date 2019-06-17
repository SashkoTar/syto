import numpy as np
from scipy import signal

print(signal.convolve([1, -0.1, -0.06], [1, -0.4, -0.05]))