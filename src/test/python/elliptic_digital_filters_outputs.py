from scipy.signal import ellipap, ellip


def print_assert_equals(coefficients, type):
    for idx, val in enumerate(coefficients):
        print('assertEquals({0}({1}), {2}, 0.001)'.format(type, idx, val))

def generateTestLowPassFilter(order, cutoff_fc, rs, rp):
    b, a = ellip(order, rs, rp, cutoff_fc, btype='low', analog=True)
    print('val (b, a) = calculateCoefficients({0}, {1}f, {2}f, {3}f)'.format(order, cutoff_fc, rs, rp))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestHighPassFilter(order, cutoff_fc, rs, rp):
    b, a = ellip(order, rs, rp, cutoff_fc, btype='high', analog=True)
    print('val (b, a) = calculateCoefficients({0}, {1}f, {2}f, {3}f)'.format(order, cutoff_fc, rs, rp))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")


def generateTestBandPassFilter(order, low_freq, high_freq, rs, rp):
    b, a = ellip(order, rs, rp, [low_freq, high_freq], btype='bandpass', analog=True)
    print('val (b, a) = calculateCoefficients({0}, {1}f, {2}f)'.format(order, low_freq, high_freq))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestBandStopFilter(order, low_freq, high_freq, rs, rp, sample_fs):
    nyq = 0.5 * sample_fs
    low = low_freq / nyq
    high = high_freq / nyq
    b, a = ellip(order, rs, rp, [low, high], btype='bandstop', analog=False)
    print('val (b, a) = calculateCoefficients({0}, {1}f, {2}f, {3}f)'.format(order, low_freq, high_freq, sample_fs))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")



def generatePrototypeRoots(order, rp, rs):
    z, p, k = ellipap(order, rp, rs)
    print('val (z, p, k) = calculateRoots({0})'.format(order))
    print('assertEquals(k, {0}, 0.001)'.format(k))
    print_assert_equals(z, "z")
    print_assert_equals(p, "p")




if __name__ == "__main__":
    #generatePrototypeRoots(2, 5, 40)
    #generateTestLowPassFilter(2, 10, 5, 40)
    #generateTestHighPassFilter(3, 10, 5, 40)
    #generateTestBandPassFilter(2, 10, 20, 5, 40)
    generateTestBandStopFilter(3, 10, 20, 5, 40, 50)


