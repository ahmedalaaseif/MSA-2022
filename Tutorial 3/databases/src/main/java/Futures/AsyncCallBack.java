package Futures;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class AsyncCallBack {


    public static long operation(long x) throws Exception {
        if (x < 2) {
            throw new Exception("Negative Range Not Allowed");
        } else {
            return LongStream.range(2, x).map(i -> i * i).reduce((i, j) -> i + j).getAsLong();
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        long x = 100000000000L;
//        long x = 1;

        Supplier s = new Supplier() {
            @Override
            public Object get() {
                long result = 0;
                try {
                    result = operation(x);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        };

        Consumer c = new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println("Result: " + o);
            }
        };

        try {
          CompletableFuture cf = CompletableFuture.supplyAsync(s)
                    .whenCompleteAsync((r, e) -> {
                        if (r.getClass() == Long.class) {
                            c.accept(r);
                        } else {
                            System.out.println(r.getClass().toString());
                            ((Exception) r).printStackTrace();
                        }
                    });
          long i =0;
          while (!cf.isDone()){
            if(i % 100000000 ==0){
                System.out.println("Loops wasted " + i);
            }
            i++;
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
