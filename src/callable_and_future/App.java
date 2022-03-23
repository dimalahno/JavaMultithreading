package callable_and_future;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

//        executor.submit(() -> {
//
//            var random = new Random();
//            int duration = random.nextInt(4000);
//
//            System.out.println("Starting...");
//
//            try {
//                Thread.sleep(duration);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            System.out.println("Finished.");
//        });

        Future<Integer> future = executor.submit((Callable<Integer>) () -> {

            var random = new Random();
            int duration = random.nextInt(4000);

            if (duration > 2000) {
                throw new IOException("Sleeping for too long.");
            }

            System.out.println("Starting...");

            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Finished.");

            return duration;
        });

        executor.shutdown();

        try {
            System.out.println("Result is: " + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {

            IOException ex = (IOException) e.getCause();

            System.out.println(ex.getMessage());;
        }


    }

}
