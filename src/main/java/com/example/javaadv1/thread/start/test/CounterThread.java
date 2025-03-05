package com.example.javaadv1.thread.start.test;

import com.example.javaadv1.util.MyLogger;

import static com.example.javaadv1.util.MyLogger.*;

public class CounterThread {

    public static void main(String[] args) {

//        Thread thread = new Thread(() -> count());
        Thread thread = new Thread(new CounterRunnable());

        thread.start();
    }


    static void count() {

        for(int i = 1 ; i <= 5 ; i++) {
            log("value : " + i);
        }
    }

    static class CounterRunnable implements Runnable {

        @Override
        public void run() {
            for(int i = 1 ; i <= 5 ; i++) {
            log("value : " + i);
            }
        }
    }
}
