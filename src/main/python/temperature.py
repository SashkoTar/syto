import csv
from datetime import datetime

import numpy as np

startdate = '20111118'
enddate   = '20121125'


r = csv.DictReader("C:\Projects\sonia\src\main\resources\LOBO0010-20180922103241.tsv", delimiter='\t')
#data.seek(0)

# Break the file into two lists
date, temp = [],[]
date, temp = zip(*[(datetime.strptime(x['date[AST]'], "%Y-%m-%d %H:%M:%S"), x['temperature[C]']) for x in r if x['temperature[C]'] is not None])

# temp needs to be converted from a "list" into a numpy array...
temp = np.array(temp)
temp = temp.astype(np.float) #...of floats


import scipy.signal as signal
import matplotlib.pyplot as plt

# First, design the Buterworth filter
N  = 2    # Filter order
Wn = 0.01 # Cutoff frequency
B, A = signal.butter(N, Wn, output='ba')

# Second, apply the filter
tempf = signal.filtfilt(B,A, temp)

# Make plots
fig = plt.figure()
ax1 = fig.add_subplot(211)
plt.plot(date,temp, 'b-')
plt.plot(date,tempf, 'r-',linewidth=2)
plt.ylabel("Temperature (oC)")
plt.legend(['Original','Filtered'])
plt.title("Temperature from LOBO (Halifax, Canada)")
ax1.axes.get_xaxis().set_visible(False)

ax1 = fig.add_subplot(212)
plt.plot(date,temp-tempf, 'b-')
plt.ylabel("Temperature (oC)")
plt.xlabel("Date")
plt.legend(['Residuals'])