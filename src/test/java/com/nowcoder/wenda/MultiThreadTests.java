package com.nowcoder.wenda;

/**
 * Created by ${ywj} on 2017/12/25.
 */

class MyThread extends Thread {
    private int tid;

    public MyThread(int tid) {
        this.tid = tid;
    }
    @Override
    public void run(){
        try {
            for (int i = 0; i < 10; ++i) {
                Thread.sleep(1000);
                System.out.println(String.format("%d:%d", tid, i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class MultiThreadTests {
    public static void testThread(){
        for (int i = 0; i < 10; ++i) {
           // new MyThread(i).start();
        }
        for (int i = 0; i < 10; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 10; ++i) {
                            Thread.sleep(1000);
                            System.out.println(String.format("T2 %d:",i));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        testThread();
    }
}
