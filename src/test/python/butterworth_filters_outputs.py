import numpy as np
import matplotlib.pyplot as plt
from scipy.signal import butter, freqz

def print_assert_equals(coefficients, type):
    for idx, val in enumerate(coefficients):
        print('assertEquals({0}({1}), {2}, 0.001)'.format(type, idx, val))

def generateTestLowPassButterworthFilter(order, cutoff_fc, sample_fs):
    nyq = 0.5 * sample_fs
    normal_cutoff = cutoff_fc/nyq
    b, a = butter(order, normal_cutoff, btype='low', analog=False)
    print('val (b, a) = DigitalButterworthLowPassFilter({0}, {1}f, {2}).calculateCoefficients()'.format(order, cutoff_fc, sample_fs))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestHighPassButterworthFilter(order, cutoff_fc, sample_fs):
    nyq = 0.5 * sample_fs
    normal_cutoff = cutoff_fc/nyq
    b, a = butter(order, normal_cutoff, btype='high', analog=False)
    print('val (b, a) = DigitalButterworthHighPassFilter({0}, {1}f, {2}).calculateCoefficients()'.format(order, cutoff_fc, sample_fs))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestBandPassButterworthFilter(order, lowcut_fc, highcut_fc, sample_fs):
    nyq = 0.5 * sample_fs
    low = lowcut_fc / nyq
    high = highcut_fc / nyq
    b, a = butter(order, [low, high], btype='band', analog=False)


    print('val (b, a) = DigitalButterworthBandPassFilter({0}, {1}f, {2}f, {3}).calculateCoefficients()'.format(order, lowcut_fc, highcut_fc, sample_fs))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")


def normalize_filter():
    # Plot the frequency response.
    a = [1, -1.1360854939070557, 1.972302360606315, -0.9497603087997856, 0.7008967811884024]
    b = [1, 0, -2, 0, 1]
    w, h = freqz(b, a, worN=100)
    plt.subplot(2, 1, 1)
    plt.plot(w, np.abs(h), 'b')
    plt.title("Lowpass Filter Frequency Response")
    plt.xlabel('Frequency [Hz]')
    plt.grid()
    plt.show()

if __name__ == "__main__":
    #generateTestLowPassButterworthFilter(2, 3.667, 30)
    #generateTestLowPassButterworthFilter(3, 3.667, 30)
    #generateTestBandPassButterworthFilter(2, 18, 22, 100)
    #generateTestBandPassButterworthFilter(3, 40, 170, 1000)
    #normalize_filter()
    #generateTestHighPassButterworthFilter(4, 13, 33)
    generateTestLowPassButterworthFilter(3, 10, 40)


