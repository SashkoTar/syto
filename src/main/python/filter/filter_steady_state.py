from scipy import signal
import matplotlib.pyplot as plt
import numpy as np


b, a = signal.butter(3, 0.05)
zi = signal.lfilter_zi(b, a)
#print(b)
#print(a)
#print(zi)

a = np.array([[2,1,1], [4,-6,0], [-2,7,2]])
b = np.array([5,-2,9])
x = np.linalg.solve(a, b)
print(x)
