package com.moon.misc;

import org.junit.Test;

/**
 * Created by Paul on 2017/2/7.
 */
public class SelectSortTest {
    @Test
    public void test(){
        int a[]={4,9,5,3,7};
        int min;
        for(int i=0;i<a.length;i++){
            min=i;
            for(int j=i+1;j<a.length;j++){
                if(a[min]>a[j]){
                    min=j;
                }
            }
            int temp=a[i];
            a[i]=a[min];
            a[min]=temp;
        }
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" , ");
    }
}
