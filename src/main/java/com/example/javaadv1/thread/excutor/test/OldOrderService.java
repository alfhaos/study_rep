package com.example.javaadv1.thread.excutor.test;

import com.example.javaadv1.thread.excutor.future.CallableMainV2;

import java.util.concurrent.*;

import static com.example.util.MyLogger.log;
import static com.example.util.ThreadUtils.sleep;

public class OldOrderService {

    public void order(String orderNo) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(3);

        Future<Boolean> inventoryWorkFuture = es.submit(new InventoryWork(orderNo));
        Future<Boolean> shippingWorkFuture = es.submit(new ShippingWork(orderNo));
        Future<Boolean> accountingWorkFuture = es.submit(new AccountingWork(orderNo));
        // 작업 요청
        Boolean inventoryResult = inventoryWorkFuture.get();
        Boolean shippingResult = shippingWorkFuture.get();
        Boolean accountingResult = accountingWorkFuture.get();

        // 결과 확인
        if (inventoryResult && shippingResult && accountingResult) {
            log("모든 주문 처리가 성공적으로 완료되었습니다.");
        } else {
            log("일부 작업이 실패했습니다.");
        }
    }

    static class InventoryWork implements Callable<Boolean> {

        private final String orderNo;

        public InventoryWork(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public Boolean call() {
            log("재고 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class ShippingWork implements Callable<Boolean> {

        private final String orderNo;

        public ShippingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean call() {
            log("배송 시스템 알림: " + orderNo);
            sleep(1000);
            return true;
        }
    }

    static class AccountingWork implements Callable<Boolean> {

        private final String orderNo;

        public AccountingWork(String orderNo) {
            this.orderNo = orderNo;
        }

        public Boolean call() {
            log("회계 시스템 업데이트: " + orderNo);
            sleep(1000);
            return true;
        }
    }

}