import matplotlib.pyplot as plt
import numpy as np

def unitImpule(x):
    def func(x):
        if x == 0:
            return 1
        else:
            return 0
    return map(func, x)

def unitStep(x):
    def func(x):
        if x >= 0:
            return 1
        else:
            return 0
    return map(func, x)


def discreteCos():
    x = np.linspace(0.1, 2*np.pi, 10)
    markerline, stemlines, baseline = plt.stem(x, np.cos(x))
    #plt.setp(baseline, 'color', 'r', 'linewidth', 5)
    plt.show()

def plotUnitStep():
    x = np.linspace(-5, 5, 20)
    y = unitStep(x)
    markerline, stemlines, baseline = plt.stem(x, y)
    #plt.setp(baseline, 'color', 'r', 'linewidth', 5)
    plt.show()

def plotUnitImpulse():
    x = np.linspace(0, 10, 10)
    y = unitImpule(x)
    markerline, stemlines, baseline = plt.stem(x, y)
    #plt.setp(baseline, 'color', 'r', 'linewidth', 5)
    plt.show()

def plotBoth():
    plt.subplot(2, 1, 1)
    x = np.arange(-6, 20, 1)
    y = unitStep(x)
    markerline, stemlines, baseline = plt.stem(x, y)
    plt.xlabel('n')
    plt.ylabel('Unit Impulse')
    plt.grid(True)
    plt.subplot(2, 1, 2)
    y = unitImpule(x)
    markerline, stemlines, baseline = plt.stem(x, y)
    plt.xlabel('n')
    plt.ylabel('Unit Step')
    plt.grid(True)
    plt.show()

def findSameValue(x, y1, y2):
    #x = [-2, 4, 6, 11]
    #y1 = [22, 44, 55, 66]
    #y2 = [22, 424, 525, 66]
    same_x = []
    same_y = []
    for i in range(len(x)):
        if(y1[i] == y2[i]):
            same_x.append(x[i])
            same_y.append(y1[i])
    return same_x, same_y

def plotCosine():
    x = np.arange(0, 100, 1)
    #x = np.linspace(0.0, 20.)
    y1 = np.cos(2 * np.pi * x/8)
    y2 = np.cos(5 * np.pi * x/8)

    same_x, same_y = findSameValue(x, y1, y2)



    fig, ax = plt.subplots()
    line1, = ax.plot(x, y1, label='2*pi/8')
    line1.set_dashes([2, 2, 10, 2])  # 2pt line, 2pt break, 10pt line, 2pt break

    line2, = ax.plot(x, y2, dashes=[6, 2], label='5*pi/8')

    line3, = ax.plot(same_x, same_y, 'go')

    ax.legend()
    plt.grid(True)
    plt.show()





if __name__ == "__main__":
    plotCosine()