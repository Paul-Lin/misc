package com.moon.threadlocal;

/**
 * Created by Paul on 2017/2/12.
 */
public class SequenceB implements Sequence{
    private static ThreadLocal<Integer> numberContainer=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    @Override
    public int getNumber() {
        numberContainer.set(numberContainer.get()+1);
        return numberContainer.get();
    }
}
