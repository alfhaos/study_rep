package com.example.javaadv1.thread.excutor.poolsize;

import com.example.javaadv1.thread.excutor.RunnableTask;

import java.util.concurrent.*;

import static com.example.javaadv1.thread.excutor.ExecutorUtils.printState;
import static com.example.util.MyLogger.log;
import static com.example.util.ThreadUtils.sleep;



public class PoolSizeMainV4 {

    static final int TASK_SIZE = 1100; // 1. 일반
    //static final int TASK_SIZE = 1200; // 2. 긴급
    //static final int TASK_SIZE = 1201; // 3. 거절

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(100, 200,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        printState(es);

        long startMs = System.currentTimeMillis();
        for (int i = 1; i <= TASK_SIZE; i++) {
            String taskName = "task" + i;
            try {
                es.execute(new RunnableTask(taskName));
                printState(es, taskName);
            } catch (RejectedExecutionException e) {
                log(taskName + " -> " + e);
            }
        }


        es.close();
        long endMs = System.currentTimeMillis();
        log("time: " + (endMs - startMs));

    }
}
