package com.moon.threadlocal;

import org.junit.Test;

/**
 * Created by Paul on 2017/2/12.
 */
public class SequenceTest {
    @Test
    public void testSequeceA(){
        Sequence sequence=new SequenceA();
        ClientThread thread1=new ClientThread(sequence);
        ClientThread thread2=new ClientThread(sequence);
        ClientThread thread3=new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();
    }

    @Test
    public void testSequenceB(){
        Sequence sequence=new SequenceB();
        ClientThread thread1=new ClientThread(sequence);
        ClientThread thread2=new ClientThread(sequence);
        ClientThread thread3=new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
