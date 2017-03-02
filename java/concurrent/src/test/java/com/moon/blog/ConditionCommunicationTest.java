package com.moon.blog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Paul on 2017/2/28.
 */
public class ConditionCommunicationTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Lock lock=new ReentrantLock();
        final Condition subCondition=lock.newCondition();
        final Condition addCondition=lock.newCondition();
        final AtomicBoolean isSub=new AtomicBoolean(true);
        System.out.println("................");
        service.submit(new SubThread(isSub,lock,subCondition,addCondition));
        service.submit(new AddThread(isSub,lock,subCondition,addCondition));

    }

}

class SubThread extends Thread {
    private final AtomicBoolean isSub;
    private final Lock lock;
    private final Condition subCondition;
    private final Condition addCondition;

    public SubThread(AtomicBoolean isSub, Lock lock, Condition subCondition, Condition addCondition) {
        this.isSub = isSub;
        this.lock = lock;
        this.subCondition = subCondition;
        this.addCondition = addCondition;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            while (isSub.get()) {
                addCondition.await();
                System.out.println(Thread.currentThread().getName()+" subing ... ");
            }
            isSub.set(false);
            addCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class AddThread extends Thread{
    private final AtomicBoolean isSub;
    private final Lock lock;
    private final Condition subCondition;
    private final Condition addCondition;

    public AddThread(AtomicBoolean isSub, Lock lock, Condition subCondition, Condition addCondition) {
        this.isSub = isSub;
        this.lock = lock;
        this.subCondition = subCondition;
        this.addCondition = addCondition;
    }

    @Override
    public void run() {
        lock.lock();
        try{
            while(!isSub.get()){
                subCondition.await();
                System.out.println(Thread.currentThread().getName()+" adding... ");
            }
            isSub.set(true);
            addCondition.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

class Business {
    Lock lock = new ReentrantLock();
    Condition subCondition = lock.newCondition();
    Condition addCondition = lock.newCondition();
    private boolean isSub = true;

    public void sub(int i) {
        lock.lock();
        try {
            while (isSub) {
                addCondition.await();
            }
            for (int j = 1; j <= 3; j++) {
                System.out.println("sub thread sequence of " + j + ", loop of " + i);
            }
            TimeUnit.SECONDS.sleep(1);
            isSub = false;
            addCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void add(int i) {
        lock.lock();
        try {
            while (!isSub) {
                subCondition.await();
            }
            for (int j = 1; j <= 3; j++) {
                System.out.println("Add thread sequece of " + j + ", loop of " + i);
            }
            TimeUnit.SECONDS.sleep(1);
            isSub = true;
            subCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}