package com.example.javaadv1.thread.start.test;

import static com.example.javaadv1.util.MyLogger.log;

public class Test4 {

    public static void main(String[] args) {

        Thread threadA = new Thread(new ThreadA("A", 1000));
        Thread threadB = new Thread(new ThreadA("B", 500));
        threadA.start();
        threadB.start();

    }
    static class ThreadA implements Runnable {

        private String content;
        private int sleepMs;

        public ThreadA(String content, int sleepMs) {
            this.content = content;
            this.sleepMs = sleepMs;
        }

        @Override
        public void run() {
            while(true) {
                log(content);
                try {
                    Thread.sleep(sleepMs);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
        }
    }
}
