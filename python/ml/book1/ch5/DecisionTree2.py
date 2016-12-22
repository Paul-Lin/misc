# coding:utf-8
import numpy as np


def createDataSet():
    dataset = [
        [1, '青年', '否', '否', '一般', '否'],
        [2, '青年', '否', '否', '好', '否'],
        [3, '青年', '是', '否', '好', '是'],
        [4, '青年', '是', '是', '一般', '是'],
        [5, '青年', '否', '否', '一般', '否'],
        [6, '中年', '否', '否', '一般', '否'],
        [7, '中年', '否', '否', '好', '否'],
        [8, '中年', '是', '是', '好', '是'],
        [9, '中年', '否', '是', '非常好', '是'],
        [10, '中年', '否', '是', '非常好', '是'],
        [11, '老年', '否', '是', '非常好', '是'],
        [12, '老年', '否', '是', '好', '是'],
        [13, '老年', '是', '否', '好', '是'],
        [14, '老年', '是', '否', '非常好', '是'],
        [15, '老年', '否', '否', '一般', '否'],
    ]
    return dataset


def calcShannonEnt(dataSet):
    numOfEntries=len(dataSet)
    shannonEnt=float(0)
    labelCounts={}
    for fectVec in dataSet:
        currentLabel=fectVec[-1]
        if currentLabel not in labelCounts:
            labelCounts[currentLabel]=0
        labelCounts[currentLabel]+=1
    for key in labelCounts.keys():
        prob=labelCounts[key]/float(numOfEntries)
        shannonEnt-=prob*np.math.log(prob,2)
    return shannonEnt

def splitDataSet(dataSet, axis, value):
    retDataSet = []
    for vec in dataSet:
        if vec[axis] == value:
            reducedSet = vec[:axis]
            reducedSet.extend(vec[axis+1:])
            retDataSet.append(reducedSet)
    return retDataSet


def chooseBestFeature(dataSet):
    numOfFeature = len(dataSet[0]) - 1
    numOfDataVec = len(dataSet)
    bestInfoGain = float(0)
    bestFeature = -1
    baseEntropy = calcShannonEnt(dataSet)
    for i in range(numOfFeature):
        i += 1
        featList = [example[i] for example in dataSet]
        uniqueVals = set(featList)
        newEntropy = float(0)
        for value in uniqueVals:
            subDataSet = splitDataSet(dataSet, i, value)
            prob = len(subDataSet) / float(numOfDataVec)
            newEntropy += prob * calcShannonEnt(subDataSet)
        infoGain = baseEntropy - newEntropy
        if infoGain > bestInfoGain:
            bestInfoGain = infoGain
            bestFeature = i
    return bestFeature


print chooseBestFeature(createDataSet())
