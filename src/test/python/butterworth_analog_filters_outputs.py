import numpy as np
import matplotlib.pyplot as plt
from scipy.signal import butter, freqz, cheby2

def print_assert_equals(coefficients, type):
    for idx, val in enumerate(coefficients):
        print('assertEquals({0}({1}), {2}, 0.001)'.format(type, idx, val))

def generateTestLowPassButterworthFilter(order, cutoff_fc):
    b, a = butter(order, cutoff_fc, btype='low', analog=True)
    print('val (b, a) = AnalogButterworthLowPassFilter({0}, {1}f).calculateCoefficients()'.format(order, cutoff_fc))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestHignPassButterworthFilter(order, cutoff_fc):
    b, a = butter(order, cutoff_fc, btype='high', analog=True)
    print('val (b, a) = AnalogButterworthHighPassFilter({0}, {1}f).calculateCoefficients()'.format(order, cutoff_fc))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestBandPassButterworthFilter(order, low_freq, high_freq):
    b, a = butter(order, [low_freq, high_freq], btype='band', analog=True)
    print('val (b, a) = AnalogButterworthBandPassFilter({0}, {1}f, {2}f).calculateCoefficients()'.format(order, low_freq, high_freq))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def listZeroPoles():
    z, p, k = butter(5, 1, btype='low', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))
    z, p, k = butter(2, 1, btype='low', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))


def listZeroPoles2():
    z, p, k = butter(2, [10, 20], btype='band', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))
    z, p, k = butter(2, 1, btype='low', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))



if __name__ == "__main__":
    #plot_chebyshev1_filter(5, 0.001, 0.25, 32)
    #generateTestLowPassChebyshevSecondTypeFilter(4, 40, 100)
    #listZeroPoles()
    #generateTestHignPassButterworthFilter(7,  344)
    #generateTestBandPassButterworthFilter(2, 10, 20)
    listZeroPoles2()



