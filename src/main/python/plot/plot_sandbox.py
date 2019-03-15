
import matplotlib.pyplot as plt
import numpy as np
from mpl_toolkits.mplot3d import Axes3D


def sinus():
    x = np.arange(0, 10, 0.2)
    y = np.sin(x)
    fig, ax = plt.subplots()
    ax.plot(x, y)
    plt.show()

def two_plots():
#Creates two subplots and unpacks the output array immediately
    t = np.arange(0.0, 2.0, 0.01)
    s1 = np.sin(2*np.pi*t)
    s2 = np.sin(4*np.pi*t)

    plt.figure(1)
    plt.subplot(211)
    plt.plot(t, s1)
    plt.subplot(212)
    plt.plot(t, 2*s1)

    plt.figure(2)
    plt.plot(t, s2)

    # now switch back to figure 1 and make some changes
    plt.figure(1)
    plt.subplot(211)
    plt.plot(t, s2, 's')
    ax = plt.gca()
    ax.set_xticklabels([])

    plt.show()

def plot3d():
    fig = plt.figure()
    ax = Axes3D(fig)
    X = np.arange(-4, 4, 0.25)
    Y = np.arange(-4, 4, 0.25)
    X, Y = np.meshgrid(X, Y)
    R = np.sqrt(X**2 + Y**2)
    Z = np.sin(R)

    ax.plot_surface(X, Y, Z, rstride=1, cstride=1, cmap=plt.cm.hot)
    ax.contourf(X, Y, Z, zdir='z', offset=-2, cmap=plt.cm.hot)
    ax.set_zlim(-2,2)

    # savefig('../figures/plot3d_ex.png',dpi=48)
    plt.show()

def xTowerAcc():
    import pandas
    colnames = ['accX', 'accY']
    data = pandas.read_csv('C:\\Projects\\HFDPrules\\src\\main\\resources\\acceleration.csv', names=colnames)
    y = data.accX.tolist()[0:1000]
    x = np.arange(0, 100, 0.1)
    plt.plot(x, y, color="green", linewidth=0.3, linestyle="-")
    ax = plt.gca()
    ax.spines['right'].set_color('red')
    ax.spines['top'].set_color('blue')
    ax.xaxis.set_ticks_position('bottom')
    ax.spines['bottom'].set_position(('data',0))
    ax.yaxis.set_ticks_position('left')
    ax.spines['left'].set_position(('data',0))

    plt.show()



if __name__ == "__main__":
    two_plots()