package Futures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPool {
    private static ExecutorService threadPool;


    //Singleton Design Pattern
    public static ExecutorService getThreadPool(int threads) {
        if (threadPool.equals(null)){
            threadPool = Executors.newFixedThreadPool(threads);
        }
        return threadPool;
    }

    public static void main(String[] args) {
        //Core logic of application goes here

    }

}
