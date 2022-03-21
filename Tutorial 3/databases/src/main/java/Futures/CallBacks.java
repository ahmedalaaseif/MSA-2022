package Futures;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

public class CallBacks {


    public static long operation(long x) throws Exception {
        if (x < 2) {
            throw new Exception("Negative Range Not Allowed");
        } else {
            return LongStream.range(2, x).map(i -> i * i).reduce((i, j) -> i + j).getAsLong();
        }
    }



    public static void main(String[] args) {

        long x = 100000000000L;
//        long x = 10000L;

        CallBack fetcher = new CallBack() {
            @Override
            public void fetchData(CallBackResult result) {

                try {
                    result.onData(operation(x));
                } catch (Exception e) {
                    result.onError(e);
                }
            }
        };


        fetcher.fetchData(new CallBackResult() {
            @Override
            public void onData(Long data) {
                System.out.println("Computation complete, Result: " + data);
            }

            @Override
            public void onError(Throwable cause) {
                cause.printStackTrace();
            }
        });
    }
}
