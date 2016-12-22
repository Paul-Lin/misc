# coding=utf-8
import numpy as np

def loadDataSet():
    postingList=[['my', 'dog', 'has', 'flea', 'problems', 'help', 'please'],
                 ['maybe', 'not', 'take', 'him', 'to', 'dog', 'park', 'stupid'],
                 ['my', 'dalmation', 'is', 'so', 'cute', 'I', 'love', 'him'],
                 ['stop', 'posting', 'stupid', 'worthless', 'garbage'],
                 ['mr', 'licks', 'ate', 'my', 'steak', 'how', 'to', 'stop', 'him'],
                 ['quit', 'buying', 'worthless', 'dog', 'food', 'stupid']]
    classVec = [0,1,0,1,0,1]    #1 is abusive, 0 not
    return postingList,classVec

def createVocabList(dataSet):
    vocabSet=set([])
    for doc in dataSet:
        vocabSet=vocabSet|set(doc)
    return list(vocabSet)

def setOfWordVec(inputVect,vocabList):
    vec=[0]*len(vocabList)
    for word in inputVect:
        if word in vocabList:
            vec[vocabList.index(word)]=1
        else:
            print '%s is not in my vocab'%word
    return vec

def trainNB0(trainMat,trainCate):
    numOfDoc=len(trainMat)
    pAbuse=sum(trainCate)/float(numOfDoc)
    numOfWord=len(trainMat[0])
    p0Num=np.ones(numOfWord)
    p0Denom=float(2)
    p1Num=np.ones(numOfWord)
    p1Denom=float(2)
    for i in range(len(trainCate)):
        if trainCate[i]==1:
            p1Num+=trainMat[i]
            p1Denom+=sum(trainMat[i])
        else:
            p0Num+=trainMat[i]
            p0Denom+=sum(trainMat[i])
    p0Vect=np.log(p0Num/p0Denom)
    p1Vect=np.log(p1Num/p1Denom)
    return p0Vect,p1Vect,pAbuse

def classifyNB0(vec2Classify,p0Vect,p1Vect,pAbuse):
    p1=sum(vec2Classify*p1Vect)+np.log(pAbuse)
    p0=sum(vec2Classify*p0Vect)+np.log(1.0-pAbuse)
    if p1>p0:
        return 1
    else:
        return 0

def testingNB():
    postingList,trainCate=loadDataSet()
    vocabList=createVocabList(postingList)
    trainMat=[]
    for doc in postingList:
        trainMat.append(setOfWordVec(doc,vocabList))
    p0Vect,p1Vect,pAbuse=trainNB0(trainMat,trainCate)
    testEntry=['love','my','dalmation']
    thisDoc=np.array(setOfWordVec(testEntry,vocabList))
    print testEntry,'classified as: ',classifyNB0(thisDoc,p0Vect,p1Vect,pAbuse)
    testEntry=['stupid','garbage']
    thisDoc=np.array(setOfWordVec(testEntry,vocabList))
    print testEntry,'classified as: ',classifyNB0(thisDoc,p0Vect,p1Vect,pAbuse)

testingNB()