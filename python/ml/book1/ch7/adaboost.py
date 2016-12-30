# coding:utf8
import numpy as np
import adaboost

'''
    AdaBoost
    优点:泛化错误率低，易编码，可以应用在大部分分类器上，无参数调整
    缺点：对离群点敏感
    使用数据类型:数值型和标称型数据

    AdaBoost的一般流程
    1.收集数据：可以使用任意方法

    2.准备数据：依赖于所使用的弱分类器类型，此处使用的是单层决策树，
    这种分类器可以处理任何数据类型。当然也可以使用任意分类器作为弱分类器。

    3.分析数据：可以使用任意方法

    4.训练算法：AdaBoost的大部分时间都用在训练上，
    分类器将多次在同一数据集上训练弱分类器。

    5.测试算法：计算分类的错误率

    6.使用算法：同SVM一样，AdaBoost预测两个类别中的一个。
    如果想把它应用到多个计算类别的场合，那么就要像多类SVM中的做法一样对AdaBoost进行修改。
'''


def loadSimpData():
    datMat = np.matrix([[1.0, 2.1],
                        [2.0, 1.1],
                        [1.3, 1.0],
                        [1.0, 1.0],
                        [2.0, 1.0]])
    classLabels = [1.0, 1.0, -1.0, -1.0, 1.0]
    return datMat, classLabels


def stumpClassify(dataMatrix, dimen, threshVal, threshIneq):
    '''
    用于测试是否有某个值小于或者大于我们正在测试的阀值。
    通过阀值比较对数据进行分类的。
    所有在阀值一边的数据会分类别-1，而在另外一边的数据类别+1。
    该函数可以通过数组过滤来实现，
    首先将返回数组的全部元素设置为1，
    然后将所有不满足不等式要求的元素设置为-1。
    可以基于数据集中的任一元素进行比较，同时也可以将不等号在大于、小于之间切换。
    :param dataMatrix:
    :param dimen:
    :param threshVal:
    :param threshIneq:
    :return:
    '''
    retArray = np.ones((np.shape(dataMatrix)[0], 1))
    if threshIneq == 'lt':
        retArray[dataMatrix[:, dimen] <= threshVal] = -1.0
    else:
        retArray[dataMatrix[:, dimen] > threshVal] = -1.0
    return retArray

def buildStump(dataArr,classLabels,D):
    '''
    对加权数据集集中循环，并找到具有最低错误率的单层决策树。
    将最小错误率minError设为正无穷
    对数据集中的每一个特征（第一层循环）：
        对每个步长（第二层循环）：
            对每个不等号（第三层循环）：
                建立一颗单层决策树并利用加权数据集对它进行测试
                如果错误率低于minError,则将当前单层决策树设为最佳单层决策树
    返回最佳单层决策树
    :param dataArr:
    :param classLabels:
    :param D:
    :return:
    '''
    dataMatrix=np.mat(dataArr)
    labelMat=np.mat(classLabels).T
    m,n=np.shape(dataMatrix)
    numSteps=10.0
    bestStump={}
    bestClassEst=np.mat(np.zeros((m,1)))
    minError=np.inf
    for i in range(n):
        rangeMin=dataMatrix[:,i].min()
        rangeMax=dataMatrix[:,i].max()
        stepSize=(rangeMax-rangeMin)/numSteps
        for j in range(-1,int(numSteps)+1):
            for inequal in ['lt','gt']:
                threshVal=(rangeMin+float(j)*stepSize)
                predictedVals=stumpClassify(dataMatrix,i,threshVal,inequal)
                errArr=np.mat(np.ones(m,1))
                errArr[predictedVals==labelMat]=0
                weightedError=D.T*errArr
                if weightedError<minError:
                    minError=weightedError
                    bestClassEst=predictedVals.copy()
                    bestStump['dim']=i
                    bestStump['thresh']=threshVal
                    bestStump['ineq']=inequal
    return bestStump,minError,bestClassEst
