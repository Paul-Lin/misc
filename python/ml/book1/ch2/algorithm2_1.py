# coding=utf-8
import numpy as np

def getTrainingDataset():
    inputVecs=[[3,3],[4,3],[1,1]]
    labels=[1,1,-1]
    return inputVecs,labels



def train(inputVecs,labels,w,b):
    isNotTraininWell=True
    while isNotTraininWell:
        for (vec,label) in zip(inputVecs,labels):
            result=sum(np.multiply(w,vec)) + b
            if(label>0):
                if  result> 0:
                    isNotTraininWell=False
                else:
                    isNotTraininWell=True
                    w=np.add(w,np.multiply(label,vec))
                    b=b+label
                    break
            else:
                if result < 0:
                    isNotTraininWell = False
                else:
                    isNotTraininWell = True
                    w = np.add(w, np.multiply(label, vec))
                    b = b + label
                    break
    return w,b

(inputVecs,labels)=getTrainingDataset()
print train(inputVecs,labels,0,0)
