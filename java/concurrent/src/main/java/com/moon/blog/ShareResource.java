package com.moon.blog;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Paul on 2017/2/28.
 */
public class ShareResource {
    private String name;
    private String gender;
    private boolean isEmpty=true;
    private final Lock lock=new ReentrantLock();
    private final Condition condition=lock.newCondition();

    public void push(String name,String gender){
        lock.lock();
        try{
            if(!isEmpty){
                condition.await();
            }
            this.name=name;
            this.gender=gender;
            isEmpty=false;
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void pop(){
        lock.lock();
        try{
            if(isEmpty){
                condition.await();
            }
            Thread.sleep(1000);
            System.out.println(name+" : "+gender);
            isEmpty=true;
            condition.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

    }
}
