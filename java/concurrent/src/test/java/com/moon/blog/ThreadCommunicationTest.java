package com.moon.blog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Paul on 2017/2/28.
 */
public class ThreadCommunicationTest {

    private static char currentThread='A';

    public static void main(String[] args) {
        final Lock lock=new ReentrantLock();
        final Condition conditionA=lock.newCondition();
        final Condition conditionB=lock.newCondition();
        ThreadCommunicationTest test=new ThreadCommunicationTest();
        ExecutorService serivce= Executors.newCachedThreadPool();
        serivce.execute(()->{
            for(int i=1;i<=52;i++){

                try{
                    lock.lock();
                    while(currentThread!='A'){
                        System.out.println("B got: "+i);

                        conditionA.await();
                    }
                    if(i%2==0){
                        currentThread='B';
                        conditionB.signalAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        });
        serivce.execute(()->{
            for(int i=1;i<=52;i++){
                try{
                    lock.lock();
                    while(currentThread!='B'){
                        System.out.println("A got: "+i);

                        conditionB.await();
                    }
                    if(i%2==1){
                        currentThread='A';
                        conditionA.signalAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        });
    }

}
