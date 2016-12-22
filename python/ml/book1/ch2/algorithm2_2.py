# coding=utf-8
import numpy as np

def getTrainDataSet():
    inputVecs=[[3,3],[4,3],[1,1]]
    labels=[1,1,-1]
    return inputVecs,labels

def createGram(inputVecs):
    tmp=[]
    for row in range(len(inputVecs)):
        line=[]
        for col in range(len(inputVecs)):
          line.append(sum(np.multiply(inputVecs[row],inputVecs[col])))
        tmp.append(line)
    return tmp

def train(inputVecs,labels,a=np.zeros(3,dtype=np.int8),b=0,n=1):
    gram=createGram(inputVecs)
    isNotFound=True
    while isNotFound:
        tmp=0
        for i in range(len(labels)):
            for j in range(len(labels)):
                tmp=tmp+a[j]*gram[i][j]*labels[j]
            tmp=labels[i]*(tmp+b)
            if tmp<=0:
                a[i]=a[i]+n
                b=b+n*labels[i]
                isNotFound=True
                break
            else:
                isNotFound=False
                continue
    a.shape=(3,1)
    tmp=np.array(labels)
    tmp.shape=(3,1)
    w=sum(np.multiply(a,np.multiply(inputVecs,tmp)))
    return w,b


inputVecs,labels=getTrainDataSet()

print train(inputVecs,labels)
