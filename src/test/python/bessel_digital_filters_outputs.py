import numpy as np
import matplotlib.pyplot as plt
from scipy.signal import besselap, bessel, freqz

def print_assert_equals(coefficients, type):
    for idx, val in enumerate(coefficients):
        print('assertEquals({0}({1}), {2}, 0.001)'.format(type, idx, val))

def generateTestLowPassBesselFilter(order, cutoff_fc, sample_fs):
    nyq = 0.5 * sample_fs
    normal_cutoff = cutoff_fc/nyq
    b, a = bessel(order, normal_cutoff, btype='low', analog=False)
    print('val (b, a) = AnalogButterworthLowPassFilter({0}, {1}f, {2}f).calculateCoefficients()'.format(order, cutoff_fc, sample_fs))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestHignPassBesselFilter(order, cutoff_fc):
    b, a = bessel(order, cutoff_fc, btype='high', analog=True)
    print('val (b, a) = AnalogButterworthHighPassFilter({0}, {1}f).calculateCoefficients()'.format(order, cutoff_fc))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestBandPassBesselFilter(order, lowcut_fc, highcut_fc, sample_fs):
    nyq = 0.5 * sample_fs
    low = lowcut_fc / nyq
    high = highcut_fc / nyq
    b, a = bessel(order, [low, high], btype='bandpass', analog=False)

    print('val (b, a) = AnalogButterworthBandPassFilter({0}, {1}f, {2}f).calculateCoefficients()'.format(order, low, high))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestBandStopBesselFilter(order, low_freq, high_freq):
    b, a = bessel(order, [low_freq, high_freq], btype='bandstop', analog=True)
    print('val (b, a) = AnalogButterworthBandStopFilter({0}, {1}f, {2}f).calculateCoefficients()'.format(order, low_freq, high_freq))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestRootsBesselFilter(order):
    z, p, k = bessel(order, 1, btype='low', analog=True, output='zpk')
    print('val (z, p, k) = calculateRoots({0})'.format(order))
    print('assertEquals(k, {0}, 0.001)'.format(k))
    print_assert_equals(z, "z")
    print_assert_equals(p, "p")

def generateTestRootsBesselFilter2(order):
    z, p, k = besselap(order)
    print('val (z, p, k) = calculateRoots({0})'.format(order))
    print('assertEquals(k, {0}, 0.001)'.format(k))
    print_assert_equals(z, "z")
    print_assert_equals(p, "p")

def listZeroPoles():
    z, p, k = bessel(5, 1, btype='low', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))
    z, p, k = bessel(2, 1, btype='low', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))



if __name__ == "__main__":
    #plot_chebyshev1_filter(5, 0.001, 0.25, 32)
    #generateTestLowPassChebyshevSecondTypeFilter(4, 40, 100)
    #listZeroPoles()
    #generateTestHignPassButterworthFilter(7,  344)
    #listZeroPoles2()
    #generateTestBandStopButterworthFilter(3, 10, 20)
    #generateTestLowPassBesselFilter(2, 3, 40)
    #generateTestRootsBesselFilter2(3)
    #generateTestHignPassBesselFilter(3, 10)
    #generateTestBandPassBesselFilter(3, 10, 20)
    generateTestBandPassBesselFilter(2, 10, 20, 50)


