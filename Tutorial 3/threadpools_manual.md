## Futures

----



Futures are an abstraction of concurrent execution. Typically to execute tasks concurrently threads need to be created and started by hand; futures abstract this process by scheduling to execute the task whenever the processor is free to compute.

#### Long Running Future Operation

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

public class Futures {
    public static long operation(long x) {
        return LongStream.range(2,x).map(i -> i*i).reduce(
          (i,j) -> i+j).getAsLong();
    }

    public static void main(String[] args) {
        long x = 100000000000L;

        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable task = new Runnable() {
            public void run() {
                operation(x);
            }
        };

        Callable<Long> callable = new Callable<Long>(){
            @Override
            public Long call() {
                return operation(x);
            }
        };

        Future<?> executorFuture = executor.submit(task);
        Future<?> executorCallable = executor.submit(callable);

        System.out.println("Not Done");

        while(!executorFuture.isDone() || !executorCallable.isDone()){

        }

        System.out.println("Done");

        executor.shutdown();
    }

}
```

From the code above there are two ways to specify future operations:
- Runnables
- Callables

Runnables do not return results while Callables do. An executor service needs to be defined to schedule the threads for the execution of the tasks.

#### CallBacks
CallBacks are a method for abstracting asynchronous operations as they all for method nesting. To implement callbacks neatly interfaces need to be created for specifying the sequence of callbacks.

```java
public interface CallBack {
    void fetchData(CallBackResult result);
}
```

Another interface will be created to handle the results of the callback.

```java
public interface CallBackResult {
    void onData(Long data);
    void onError(Throwable cause);
}
```

Implementation of both interfaces

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

public class CallBacks {
    public static long operation(long x) throws Exception {
        if (x < 2) {
            throw new Exception("Negative Range Not Allowed");
        } else {
            return LongStream.range(2, x).map(i -> i * i).reduce(
              (i, j) -> i + j).getAsLong();
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
```
The problem with callbacks is that they can produce spaghetti code rather quickly and yet remain locked to one thread. It is possible to implement each callback to run in a separate thread however, Java 8 abstracts this tedious task of handling threads manually.

#### Java 8 Completable Futures
*Completable Futures* are a new addition to the Java concurrent package, they abstract the creation of asynchronous tasks. A Completable Future consists of multiple *Completion Stages*, these can be thought of as pipelined callbacks.

```java
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class AsyncCallBack {
    public static long operation(long x) throws Exception {
        if (x < 2) {
            throw new Exception("Negative Range Not Allowed");
        } else {
            return LongStream.range(2, x).map(i -> i * i).reduce(
            (i, j) -> i + j).getAsLong();
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
```

The above code will call a *Supplier* asynchronously and then evaluate the result in a new stage defined by the *whenCompleteAsync* method. This method takes a *BiConsumer Lambda* that is, it takes two lambdas which in this case are *r* the result and *e* being the exception if any. Since the exception is previously handled in the *Supplier*, the result of the exception appears in the result lambda not the exception lambda.

More details on completable futures can be found in [here](http://www.deadcoderising.com/java8-writing-asynchronous-code-with-completablefuture) and [here](http://www.baeldung.com/java-completablefuture).