import numpy as np
from scipy.signal import cheby1

def print_assert_equals(coefficients, type):
    for idx, val in enumerate(coefficients):
        print('assertEquals({0}({1}), {2}, 0.001)'.format(type, idx, val))

def generateTestLowPassChebyshevSecondTypeFilter(order, rp, cutoff_fc):
    b, a = cheby1(order, rp, cutoff_fc, btype='low', analog=True)
    print('val (b, a) = AnalogChebyshevSecondTypeLowPassFilter({0}, {1}f, {2}f).calculateCoefficients()'.format(order, rp, cutoff_fc))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestHighPassChebyshevFirstTypeFilter(order, rp, cutoff_fc):
    b, a = cheby1(order, rp, cutoff_fc, btype='high', analog=True)
    print('val (b, a) = AnalogChebyshevSecondTypeHighPassFilter({0}, {1}f, {2}f).calculateCoefficients()'.format(order, rp, cutoff_fc))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestBandPassChebyshevFirstTypeFilter(order, rp, lowFrew, highFreq):
    b, a = cheby1(order, rp, [lowFrew, highFreq], btype='band', analog=True)
    print('val (b, a) = AnalogChebyshevFirstTypeBandPassFilter({0}, {1}f, {2}f, {3}f).calculateCoefficients()'.format(order, rp, lowFrew, highFreq))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestBandStopChebyshevFirstTypeFilter(order, rp, lowFrew, highFreq):
    b, a = cheby1(order, rp, [lowFrew, highFreq], btype='stop', analog=True)
    print('val (b, a) = AnalogChebyshevFirstTypeBandStopFilter({0}, {1}f, {2}f, {3}f).calculateCoefficients()'.format(order, rp, lowFrew, highFreq))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def listZeroPoles():
    z, p, k = cheby1(5, 10, 1, btype='low', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))


def listZeroPolesHighPass():
    z, p, k = cheby1(2, 20, 1, btype='low', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))
    z, p, k = cheby1(2, 20, 1, btype='high', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))
    z, p, k = cheby1(2, 20, 5, btype='high', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))

def listZeroPolesBandPass():
    z, p, k = cheby1(2, 10, [10, 20], btype='band', analog=True, output='zpk')
    print("zeros: " + str(z))
    print("poles: " + str(p))
   # z, p, k = cheby2(2, 10, 1, btype='low', analog=True, output='zpk')
   # print("zeros: " + str(z))
   # print("poles: " + str(p))

def polyPrint():
    z = [-0.+3.53553391j,  0.-3.53553391j]
    print(np.poly(z))


if __name__ == "__main__":
    #plot_chebyshev1_filter(5, 0.001, 0.25, 32)
    #generateTestLowPassChebyshevSecondTypeFilter(4, 40, 100)
    #listZeroPoles()
    #generateTestLowPassChebyshevSecondTypeFilter(3, 10, 1)
    #generateTestHighPassChebyshevSecondTypeFilter(4, 20, 230)
    #listZeroPolesHighPass()
    #polyPrint()
    #generateTestBandPassChebyshevSecondTypeFilter(2, 10, 10, 20)
    #listZeroPolesBandPass()
    #generateTestBandStopChebyshevFirstTypeFilter(2, 10, 10, 30)
    generateTestHighPassChebyshevFirstTypeFilter(4, 40, 15)



