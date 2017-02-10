package com.moon.misc;

import org.junit.Test;

/**
 * Created by Paul on 2017/2/7.
 */
public class InsertSortTest {
    @Test
    public void test() {
        int a[] = {4, 9, 5, 3, 7};
        for (int i = 1; i < a.length; i++) {
            int j = i;
            if(a[j]>a[j-1]){
                int temp = a[j];
                for ( ; j > 0&&temp > a[j - 1]; ) {
                        a[j] = a[j - 1];
                        j--;
                }
                a[j]=temp;
            }
        }

        for(int i=0;i<a.length;i++){
            int j=i;
            int temp=a[i];
            for(;j>0&&temp>a[j-1];){
                a[j]=a[j-1];
                j--;
            }
            a[j]=temp;
        }
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+" , ");
    }
}
