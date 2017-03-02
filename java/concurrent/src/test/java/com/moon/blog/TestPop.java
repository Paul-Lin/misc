package com.moon.blog;

/**
 * Created by Paul on 2017/2/28.
 */
public class TestPop {
    private final ShareResource resource;

    public TestPop(ShareResource resource) {
        this.resource = resource;
    }

    public void test(){
        for(int i=0;i<3;i++){
            new Thread(()->{
                for(int j=0;j<5;j++){
                    resource.pop();
                }
            });
        }
    }
}
