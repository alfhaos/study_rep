package com.example.javaadv1.thread.start;

public class HelloThread extends Thread{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": run()");
    }
}
