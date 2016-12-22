# coding=utf-8
import numpy as np

def distance(xi,xj,p):
    return pow(np.sum(pow(np.abs(np.subtract(xi,xj)),p)),1.0/p)

class X:
    def __init__(self,parent,data,left,right):
        self.parent=parent
        self.data=data
        self.left=left
        self.right=right

def getDataSet():
    inputVecs=[(2,3),(5,4),(9,6),(4,7),(8,1),(7,2)]

    return inputVecs


def sort():
    inputVecs=getDataSet()
    inputVecs.sort(key=lambda x:x[0])
    return inputVecs

def createTree():
    inputVecs=getDataSet()
    root = X(None,inputVecs[0], None, None)
    for i in range(len(inputVecs)):
        if (i+1<len(inputVecs)):
            leaf = X(None, inputVecs[i+1], None, None)
            createLeaf(root,leaf)
    return root

def createLeaf(root,leaf):
    if root.data[0]>leaf.data[0]:
        if root.left!=None:
            createLeaf(root.left,leaf)
        else:
            root.left=leaf
    elif root.data[0]<leaf.data[0]:
        if root.right!=None:
            createLeaf(root.right,leaf)
        else:
            root.right=leaf
    leaf.parent=root


def printMidTree():
    printMidTree1(createTree())

def printMidTree1(root):
    if root.left!=None:
        printMidTree1(root.left)
    if root!=None:
        print root.data
    if root.right!=None:
        printMidTree1(root.right)

def printFrontTree():
    printFrontTree1(createTree())

def printFrontTree1(root):
    if root!=None:
        print root.data
    if root.left!=None:
        printMidTree1(root.left)
    if root.right!=None:
        printMidTree1(root.right)

def getLeaf(root, data):
    if root.left==None and root.right==None:
        return root
    elif root.data[0]>data[0]:
        if root.left!=None:
            return getLeaf(root.left, data)
        else:
            return getLeaf(root.right, data)
    else:
        if root.right!=None:
            return getLeaf(root.right,data)
        else:
            return getLeaf(root.left,data)

printFrontTree()
# print getLeaf(createTree(),(1,3)).data