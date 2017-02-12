package com.moon.threadlocal;

import org.junit.Test;

/**
 * Created by Paul on 2017/2/12.
 */
public class SequenceA implements Sequence{
    private static int number=0;
    @Override
    public int getNumber(){
        number=number+1;
        return number;
    }

}
