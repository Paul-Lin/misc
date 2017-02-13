# coding:utf-8

from numpy import *


def loadDataSet(fileName):
    '''
    打开文件并对其逐行解析，
    从而得到每行的类标签和整个数据矩阵
    :param fileName:
    :return:
    '''
    dataMat = []
    labelMat = []
    fr = open(fileName)
    for line in fr.readlines():
        lineArr = line.strip().split('\t')
        dataMat.append([float(lineArr[0]), float(lineArr[1])])
        labelMat.append(float(lineArr[2]))
    return dataMat, labelMat


def selectJrand(i, m):
    '''
    i是第一个alpha的下标,
    m是所有的alpha的数目。
    只要函数值不等于输入值i，函数就会随机选择
    :param i:
    :param m:
    :return:
    '''
    j = i
    while (j == i):
        j = int(random.uniform(0, m))
    return j


def clipAlpha(aj, H, L):
    '''
    用于调整大于H或小于L的alpha值
    :param aj:
    :param H:
    :param L:
    :return:
    '''
    if aj > H:
        aj = H
    if L > aj:
        aj = L
    return aj

def smoSimple(dataMatiIn,classLabels,C,toler,maxIter):
    dataMatrix=mat(dataMatiIn)
    labelMat=mat(classLabels).transpose()
    b=0
    m,n=shape(dataMatrix)
    alphas=mat(zeros(m,1))
    iter=0
    while(iter<maxIter):
        alphaPairsChanged=0
        for i in range(m):
            fXi=float(multiply(alphas,labelMat)).T*(dataMatrix*dataMatrix[i,:].T)+b
            Ei=fXi-float(labelMat[i])
            if((labelMat[i]*Ei<-toler) and (alphas[i]<C)) or ((labelMat[i]*Ei>toler)and (alphas[i]>0)):
                j=selectJrand(i,m)
                fXj=float(multiply(alphas,labelMat).T*(dataMatrix*dataMatrix[j,:].T))+b
                Ej=fXj-float(labelMat[j])
                alphaIold=alphas[i].copy()
                alphaHold=alphas[j].copy()
                if(labelMat[i]!=labelMat[j]):
                    L=max(0,alphas[j]-alphas[i])
                    H=min(C,C+alphas[j]-alphas[i])
                else:
                    L=max(0,alphas[j]+alphas[i]-C)
                    H=min(C,alphas[j]+alphas[i])
                if L==H:
                    print "L==H"
                    continue
                eta=2.0*dataMatrix[i,:]*dataMatrix[j,:]
                if eta>=0:
                    print "eta>=0"
                    continue
                alphas[j]-=labelMat[j]*(Ei-Ej)/eta
                alphas[j]=clipAlpha(alphas[j],H,L)
                if(abs(alphas[j]-alphajold))



dataArr,labelArr=loadDataSet('testSet.txt')
print dataArr
print labelArr