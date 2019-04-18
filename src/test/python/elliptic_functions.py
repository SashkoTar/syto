from scipy.special import ellipj,ellipk,ellipkinc
import math

pi = 3.14159265359

def checkTables():
    #0.17364817766693034885171662676931
    k = math.sin(45 * pi/180)
    print(k)
    print(ellipk(k*k))
    #print(ellipk(0.93))
    #print(ellipk(0.93))


if __name__ == "__main__":
    checkTables()
    #print(ellipkinc(pi/6, 0.5))
    #print(ellipk(0.93))
    #print(ellipj(0.93, 0.6))



