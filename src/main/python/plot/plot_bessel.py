"""Plot some Bessel functions of integer order, using Scipy and pylab"""

import matplotlib.pyplot as plt
import numpy as np
from scipy import special


def jn_asym(n,x):
    """Asymptotic form of jn(x) for x>>n"""

    #@ The asymptotic formula is:
    #@ j_n(x) ~ sqrt(2.0/pi/x)*cos(x-(n*pi/2.0+pi/4.0))

    return np.sqrt(2.0/np.pi/x)*np.cos(x-(n*np.pi/2.0+np.pi/4.0)) #@

# build a range of values to plot in
x = np.linspace(0,30,400)

# Start by plotting the well-known j0 and j1
plt.figure()
plt.plot(x,special.j0(x),label='$J_0$')  #@
plt.plot(x,special.j1(x),label='$J_1$')  #@

# Show a higher-order Bessel function
n = 5
plt.plot(x,special.jn(n,x),label='$J_%s$' % n)

# and compute its asymptotic form (valid for x>>n, where n is the order).  We
# must first find the valid range of x where at least x>n.
#@ Find where x>n and evaluate the asymptotic relation only there
x_asym = x[x>n] #@
plt.plot(x_asym,jn_asym(n,x_asym),label='$J_%s$ (asymptotic)' % n) #@

# Finish off the plot
plt.legend()
plt.title('Bessel Functions')
# horizontal line at 0 to show x-axis, but after the legend
plt.axhline(0)

# Extra credit: redo the above, for the asymptotic range 0<x<<n.  The
# asymptotic form in this regime is:
#
# J(n,x) = (1/gamma(n+1))(x/2)^n

# Now, let's verify numerically the recursion relation
# J(n+1,x) = (2n/x)J(n,x)-J(n-1,x)
jn = special.jn  # just a shorthand

# Be careful to check only for x!=0, to avoid divisions by zero
#@ xp contains the positive values of x
xp = x[x>0.0] #@

# construct both sides of the recursion relation, these should be equal
#@ Define j_np1  to hold j_(n+1) evaluated at the points xp
j_np1 = jn(n+1,xp)
#@ Define j_np1_rec to express j_(n+1) via a recursion relation, at points xp
j_np1_rec = (2.0*n/xp)*jn(n,xp)-jn(n-1,xp)

# Now make a nice error plot of the difference, in a new figure
plt.figure()

#@ We now plot the difference between the two formulas above.  Note that to
#@ properly display the errors, we want to use a logarithmic y scale.  Search
#@ the matplotlib docs for the proper calls.
plt.semilogy(xp,abs(j_np1-j_np1_rec),'r+-') #@

plt.title('Error in recursion for $J_%s$' % n)
plt.grid()

# Don't forget a show() call at the end of the script
plt.show()