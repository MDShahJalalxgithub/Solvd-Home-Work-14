package org.solvd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        OwnThread ownThread = new OwnThread();
        ExecutorService executorService = Executors.newFixedThreadPool(7);
        for (int i = 0; i < 7; i++) {
            executorService.execute(ownThread);
        }
        executorService.shutdown();
    }
}