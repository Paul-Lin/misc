# coding = utf-8
import numpy as np

def distance(xi,xj,p):
    return pow(np.sum(pow(np.abs(np.subtract(xi,xj)),p)),1.0/p)


print distance((1,1),(4,4),3)