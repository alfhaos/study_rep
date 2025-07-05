package com.example.javaadv1.thread.excutor;

import java.util.concurrent.*;

import static com.example.javaadv1.thread.excutor.ExecutorUtils.printState;

public class PrestartPoolMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1000);
        printState(es);
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) es;
        poolExecutor.prestartAllCoreThreads();
        printState(es);
    }
}
