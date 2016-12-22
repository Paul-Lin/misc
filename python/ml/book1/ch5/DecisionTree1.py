# coding:utf-8
import numpy as np

def calcShannonEnt(dataSet):
    numEntries=len(dataSet)
    labelCounts={}
    for fectureVec in dataSet:
        currentLabel=fectureVec[-1]
        if currentLabel not in labelCounts.keys():
            labelCounts[currentLabel]=0
        labelCounts[currentLabel]+=1
    shannonEnt=float(0)
    for key in labelCounts.keys():
        prob=float(labelCounts[key])/numEntries
        shannonEnt-= prob * np.math.log(prob, 2)
    return shannonEnt

def createDataSet():
    dataset=[
        [1,'青年','否','否','一般','否'],
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

def splitDataSet(dataSet,axis,value):
    retDataSet=[]
    for featVec in dataSet:
        if featVec[axis]==value:
            reducedVec=featVec[:axis]
            reducedVec.extend(featVec[axis+1:])
            retDataSet.append(reducedVec)
    return retDataSet

def chooseBestFeatureToSplit(dataSet):
    numFeatures=len(dataSet[0])-1
    baseEntropy=calcShannonEnt(dataSet)
    bestInfoGain=float(0)
    bestFeature=-1
    for i in range(numFeatures):
        i+=1
        featList=[example[i] for example in dataSet]
        uniqueVals=set(featList)
        newEntropy=0.0
        for value in uniqueVals:
            subDataSet=splitDataSet(dataSet,i,value)
            prob=len(subDataSet)/float(len(dataSet))
            newEntropy+=prob*calcShannonEnt(subDataSet)
        infoGain=baseEntropy-newEntropy
        if infoGain>bestInfoGain:
            bestInfoGain=infoGain
            bestFeature=i
    return bestFeature

def majorityCnt(classList):
    classCount={}
    for vote in classList:
        if vote not in classCount.keys():
            classCount[vote]=0
        classCount[vote]+=1
    import operator
    sortedClassCount=sorted(classCount.iteritems(),
                            key=operator.itemgetter(1),
                            reverse=True)
    return sortedClassCo
def createTree(dataSet,labels):
    classList=[example[-1] for example in dataSet]
    if classList.count(classList[0]==len(classList)):
        return classList[0]
    if len(dataSet[0])==1:
        return majorityCnt(classList)
    bestFeat=chooseBestFeatureToSplit(dataSet)
    bestFeatLabel=labels[bestFeat]
    myTree={bestFeatLabel:{}}
    del(labels[bestFeat])
    featValues=[example[bestFeat] for example in dataSet]
    uniqueVals=set(featValues)
    for value in uniqueVals:
        subLabels=labels[:]
        myTree[bestFeatLabel][value]=createTree(splitDataSet(dataSet,bestFeat,value),subLabels)
    return myTree


print chooseBestFeatureToSplit(createDataSet())