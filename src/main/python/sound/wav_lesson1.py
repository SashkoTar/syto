import matplotlib.pyplot as plt

import thinkdsp


def plotPianoG():
    wave = thinkdsp.read_wave('C:\\Projects\\syto\\src\\main\\resources\\68448__pinkyfinger__piano-g.wav')
    fig, ax = plt.subplots()
    ax.plot(wave.ts[0:1000], wave.ys[0:1000])
    plt.show()



if __name__ == "__main__":
    plotPianoG()