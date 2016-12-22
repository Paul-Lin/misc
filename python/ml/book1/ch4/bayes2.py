# coding=utf-8
import numpy as np

def textParse(bigString):
    import re
    listOfTokens=re.split(r'\W*',bigString)
    return [tok.lower() for tok in listOfTokens if len(tok)>2]

def createVocabList(docList):
    vocabList=set([])
    for doc in docList:
        vocabList=vocabList|set(doc)
    return list(vocabList)

def createSetOfWord2Vec(vocabList,wordList):
    returnVec=[0]*len(vocabList)
    for word in wordList:
        if word in vocabList:
            returnVec[vocabList.index(word)]=1
    return returnVec

def createBagOfWord2Vec(vocabList,wordList):
    returnVec=[0]*len(vocabList)
    for word in wordList:
        if word in vocabList:
            returnVec[vocabList.index(word)]+=1
    return returnVec

def trainNB0(trainMat,trainCate):
    numOfWord=len(trainMat[0])
    numOfDoc=len(trainMat)
    p0Num=np.ones(numOfWord)
    p0Denom=float(2)
    p1Num=np.ones(numOfWord)
    p1Denom=float(2)
    pAbuse=sum(trainCate)/float(numOfDoc)
    for i in range(numOfDoc):
        if trainCate[i]==0:
            p0Num += trainMat[i]
            p0Denom += sum(trainMat[i])
        else:
            p1Num+=trainMat[i]
            p1Denom+=sum(trainMat[i])
    p0Vec = np.log(p0Num / p0Denom)
    p1Vec=np.log(p1Num/p1Denom)
    return p0Vec,p1Vec,pAbuse

def classifyNB0(wordVec,p0Vec,p1Vec,pAbuse):
    p0=sum(wordVec*p0Vec)+np.log(float(1)-pAbuse)
    p1=sum(wordVec*p1Vec)+np.log(pAbuse)
    if p0>p1:
        return 0
    else:
        return 1

def spamTest():
    docList=[]
    classList=[]
    for i in range(1,26):
        wordList=textParse(open('email/spam/%d.txt'%i).read())
        docList.append(wordList)
        classList.append(1)
        wordList=textParse(open('email/ham/%d.txt'%i).read())
        docList.append(wordList)
        classList.append(0)
    vocabList=createVocabList(docList)
    trainingSet=range(50)
    testSet=[]
    for i in range(10):
        randIndex=int(np.random.uniform(0,len(trainingSet)))
        testSet.append(trainingSet[randIndex])
        del(trainingSet[randIndex])
    trainMat=[]
    trainClasses=[]
    for docIndex in trainingSet:
        trainMat.append(createBagOfWord2Vec(vocabList,docList[docIndex]))
        trainClasses.append(classList[docIndex])
    p0Vec,p1Vec,pSpam=trainNB0(np.array(trainMat),np.array(trainClasses))
    errorCount=0
    for docIndex in testSet:
        wordVect=createBagOfWord2Vec(vocabList,docList[docIndex])
        if classList[docIndex]!=classifyNB0(np.array(wordVect),p0Vec,p1Vec,pSpam):
            errorCount+=1

    errorRate=float(errorCount)/len(testSet)
    print "Error Rate: ",errorRate

spamTest()