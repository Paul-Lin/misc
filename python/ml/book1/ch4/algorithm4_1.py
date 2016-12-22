# coding=utf-8
import numpy as np

def getDataSet():
    x1=[1,1,1,1,1,2,2,2,2,2,3,3,3,3,3]
    x2=['s','m','m','s','s','s','m','m','l','l','l','m','m','l','l']
    y=[-1,-1,1,1,-1,-1,-1,1,1,1,1,1,1,1,-1]
    return x1,x2,y

def getPosteriorProbability(ck):
    y=getDataSet()[2]
    yck=[x for x in y if x==ck]
    return float(len(yck))/len(y)

def getConditionalProbability(aj1,aj2,ck):
    x1,x2,y=getDataSet()
    paj1=0
    paj2=0
    pck=0
    for i in range(len(y)):
        if y[i]==ck:
            pck+=1
            if x1[i]==aj1:
                paj1+=1
            if x2[i]==aj2:
                paj2+=1
    return (float(paj1)/pck)*(float(paj2)/pck)

print getConditionalProbability(2,'s',1)*getPosteriorProbability(1)

print getConditionalProbability(2,'s',-1)*getPosteriorProbability(-1)