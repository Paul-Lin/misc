# coding:utf8



def loadDataSet():
    '''
    创建一个用于测试的简单数据集
    :return:
    '''
    return [[1,3,4],[2,3,5],[1,2,3,5],[2,5]]

def createC1(dataSet):
    '''
    构建C1（大小为1的所有候选项集的集合）
    然后扫面数据集来判断这些只有一个元素的项集的集合
    :param dataSet:
    :return:
    '''
    C1=[]
    for transaction in dataSet:
        for item in transaction:
            if not [item] in C1:
                C1.append([item])
    C1.sort()
    return map(frozenset,C1)

def scanD(D,Ck,minSupport):
    '''
    对数据集中的每条交易记录tran
    对每个候选项集can:
        检查一下can是否是tran的子集:
        如果是，则增加can的计数值
    对每个候选项集:
        如果其支持度不低于最小值，则保留该项集
        返回所有频繁项集列表
    :param D: 包含候选集的列表
    :param Ck: 数据集Ck
    :param minSupport: 感兴趣项集的最小支持度
    :return:
    '''
    ssCnt={}
    for tid in D:
        for can in Ck:
            if can.issubset(tid):
                if not ssCnt.has_key(can):
                    ssCnt[can]=1
                else:
                    ssCnt[can]+=1
    numItems=float(len(D))
    retList=[]
    supportData={}
    for key in ssCnt:
        support=ssCnt[key]/numItems
        if support >= minSupport:
            retList.insert(0,key)
        supportData[key]=support
    return retList,supportData

def aprioriGen(Lk,k):
    retList=[]
    lenLk=len(Lk)

dataSet=loadDataSet()
C1=createC1(dataSet)
D=map(set,dataSet)
L1,supportData0=scanD(D,C1,0.5)
print L1