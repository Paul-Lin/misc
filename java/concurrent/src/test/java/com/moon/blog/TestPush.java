package com.moon.blog;

/**
 * Created by Paul on 2017/2/28.
 */
public class TestPush {
    private final ShareResource resource;

    public TestPush(ShareResource resource) {
        this.resource = resource;
    }

    public void test(){
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++)
                    resource.push(Thread.currentThread().getName(), "i");
            }).start();
        }
    }

    public static void main(String[] args) {
        final ShareResource resource = new ShareResource();
        new TestPop(resource).test();
        new TestPush(resource).test();

    }
}
