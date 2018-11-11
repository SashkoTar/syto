import numpy as np
import matplotlib.pyplot as plt
from scipy.signal import butter, freqz, cheby1

def print_assert_equals(coefficients, type):
    for idx, val in enumerate(coefficients):
        print('assertEquals({0}({1}), {2}, 0.001)'.format(type, idx, val))

def generateTestLowPassChebyshevFirstTypeFilter(order, rp, cutoff_fc, sample_fs):
    nyq = 0.5 * sample_fs
    normal_cutoff = cutoff_fc/nyq
    b, a = cheby1(order, rp, normal_cutoff, btype='low', analog=False)
    print('val (b, a) = DigitalChebyshevFirstTypeLowPassFilter({0}, {1}f, {2}f, {3}).calculateCoefficients()'.format(order, rp, cutoff_fc, sample_fs))
    print_assert_equals(a, "a")
    print_assert_equals(b, "b")

def generateTestHighPassChebyshevFilter(order, rp, cutoff_fc, sample_fs):
    nyq = 0.5 * sample_fs
    normal_cutoff = cutoff_fc/nyq
    b, a = cheby1(order, rp, normal_cutoff, btype='high', analog=False)
    print('val (b, a) = DigitalChebyshevFirstTypeHighPassFilter({0}, {1}f, {2}f, {3}).calculateCoefficients()'.format(order, rp, cutoff_fc, sample_fs))
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


def plot_chebyshev1_filter(order, rp, cutoff_fc, sample_fs):
    import numpy as np
    from scipy.signal import cheby1, freqz
    import matplotlib.pyplot as plt
    # Sampling parameters
    #fs = 32  # Hz
    # Desired filter parameters
    #fcut = 0.25    # Hz
    # Normalized frequency argument for cheby1
    wn = cutoff_fc / (0.5*sample_fs)
    b, a = cheby1(order, rp, wn, btype='high')
    w, h = freqz(b, a, worN=8000)
    plt.figure(1)
    plt.plot(0.5*sample_fs*w/np.pi, 20*np.log10(np.abs(h)))
    plt.axvline(cutoff_fc, color='r', alpha=0.2)
    plt.plot([0, cutoff_fc], [-rp, -rp], color='r', alpha=0.2)
    plt.xlim(0, 0.3)
    plt.xlabel('Frequency (Hz)')
    plt.ylim(-5*rp, rp)
    plt.ylabel('Gain (dB)')
    plt.grid()
    plt.title("Chebyshev Type I Lowpass Filter")
    plt.tight_layout()

    plt.show()

if __name__ == "__main__":
    #plot_chebyshev1_filter(5, 0.001, 0.25, 32)
    #generateTestLowPassChebyshevFirstTypeFilter(5, 0.001, 0.25, 32)
    generateTestHighPassChebyshevFilter(5, 0.001, 0.25, 32)
    #generateTestLowPassButterworthFilter(3, 3.667, 30)
    #generateTestBandPassButterworthFilter(2, 18, 22, 100)
    #generateTestBandPassButterworthFilter(3, 40, 170, 1000)
    #normalize_filter()
    #generateTestHighPassButterworthFilter(4, 13, 33)


