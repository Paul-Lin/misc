# # coding=utf-8
#
# class Perceptron:
#     def __init__(self,input_num,activator):
#         self.activator=activator
#         self.weights=[0.0 for _ in range(input_num)]
#         self.bias=0.0
#
#     def train(self,input_vecs,labels,iteration,rate):
#         for i in range(iteration):
#             self._one_iteration(input_vecs,labels,rate)
#
#     def _one_iteration(self,input_vecs,labels,rate):
#         samples=zip(input_vecs,labels)
#         for (input_vec,label) in samples:
#             output=self.predict(input_vec,label)
#             self._update_weights=(input_vec,output,label,rate)
#
#     def _update_weights(self,input_vec,output,label,rate):
#
#
#     def predict(self,input_vec):
#         return self.activator(
#             reduce(lambda a,b:a+b,
#                    map(lambda (x,w):x*w,
#                        zip(input_vec,self.weights))
#                    ,0.0)+self.bias)
# def f(x):
#     return 1 if x>0 else 0
#
# def get_training_dataset():
#     input_vecs=[[1,1],[0,0],[1,0],[0,1]]
#     labels=[1,0,0,0]
#     return input_vecs,labels
#
# def train_and_perceptron():
#     p=Perceptron(2,f)
#     input_vecs,labels=get_training_dataset()
