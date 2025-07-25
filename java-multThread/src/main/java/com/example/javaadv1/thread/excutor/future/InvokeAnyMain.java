package com.example.javaadv1.thread.excutor.future;

import com.example.javaadv1.thread.excutor.CallableTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.example.util.MyLogger.log;
import static com.example.util.ThreadUtils.sleep;

public class InvokeAnyMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask task1 = new CallableTask("task1", 1000);
        CallableTask task2 = new CallableTask("task2", 2000);
        CallableTask task3 = new CallableTask("task3", 3000);
        List<CallableTask> tasks = List.of(task1, task2, task3);

        Integer value = es.invokeAny(tasks);
        log("value = " + value);
        es.close();
    }
}
