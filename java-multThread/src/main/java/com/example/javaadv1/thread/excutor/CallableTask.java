package com.example.javaadv1.thread.excutor;

import java.util.concurrent.Callable;

import static com.example.util.MyLogger.log;
import static com.example.util.ThreadUtils.sleep;

public class CallableTask implements Callable<Integer> {

    private final String name;
    private int sleepMs = 1000;

    public CallableTask(String name) {
        this.name = name;
    }

    public CallableTask(String name, int sleepMs) {
        this.name = name;
        this.sleepMs = sleepMs;
    }

    @Override
    public Integer call() throws Exception {
        log(name + " 실행");
        sleep(sleepMs);
        log(name + " 완료");
        return sleepMs;
    }
}
