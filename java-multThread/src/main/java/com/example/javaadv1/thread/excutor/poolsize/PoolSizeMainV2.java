package com.example.javaadv1.thread.excutor.poolsize;

import com.example.javaadv1.thread.excutor.RunnableTask;

import java.util.concurrent.*;

import static com.example.javaadv1.thread.excutor.ExecutorUtils.printState;
import static com.example.util.MyLogger.log;
import static com.example.util.ThreadUtils.sleep;



public class PoolSizeMainV2 {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        //ExecutorService es = new ThreadPoolExecutor(2, nThreads,
        //        0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        log("pool 생성");
        printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            printState(es, taskName);
        }
        es.close();
        log("== shutdown 완료 ==");

    }
}
