package com.moon.misc;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * Created by Paul on 2017/2/7.
 */
public class BubbleSortTest {
    @Test
    public void testSort() {
        int a[] = {4, 31, 32, 43, 6, 3, 5};
        for(int i=0;i<a.length;i++)
            for(int j=a.length-1;j>i;j--){
                if(a[j]<a[j-1]){
                    int temp=a[j];
                    a[j]=a[j-1];
                    a[j-1]=temp;
                }
            }
        for(int i=0;i<a.length;i++)
            for(int j=i+1;j<a.length;j++)
                if(a[i]>a[j]){
                    int temp=a[i];
                    a[i]=a[j];
                    a[j]=temp;
                }

        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" , ");
    }


}
