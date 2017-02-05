# coding:utf8
import random


def splitData(data,M,k,seed):
    test=[]
    train=[]
    random.seed(seed)
    for user,item in data:
        if random.randint(0,M)==k:
            test.append([user,item])
        else:
            train.append([user,item])
    return train,test

