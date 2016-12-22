# coding=utf-8
import numpy as np

def bagOfWord2VecMN(vocabList,words):
    returnVec=[0]*len(vocabList)
    for word in words:
        if word in vocabList:
            returnVec[vocabList.index(word)]+=1
    return returnVec


def textParse(bigString):
    import re
    listOfTokens=re.split(r'\W*',bigString)
    return [tok.lower() for tok in listOfTokens if len(tok)>2]