import numpy as np
import matplotlib.pyplot as plt


def xTowerAcc():
    from scipy.io import wavfile
    rate, audio = wavfile.read('C:\\Projects\\sonia\\src\\main\\resources\\nightingale.wav')
    audio = np.mean(audio, axis=1)
    N = audio.shape[0]
    L = N / rate
    #print(f'Audio length: {L:.2f} seconds')
    f, ax = plt.subplots()
    ax.plot(np.arange(N), audio)
    ax.set_xlabel('Time [s]')
    ax.set_ylabel('Amplitude [unknown]')
    plt.show()


if __name__ == "__main__":
    xTowerAcc()