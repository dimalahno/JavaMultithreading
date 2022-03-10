package demo6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

    private final Random random = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void stageOne() {

        synchronized (lock1){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }

    public void stageTwo() {

        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    public void process () {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    public void main() {
        System.out.println("Starting...");

        var start = System.currentTimeMillis();

        var t1 = new Thread(this::process);
        var t2 = new Thread(this::process);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        var end = System.currentTimeMillis();

        System.out.println("Time take: " + (end - start));
        System.out.println("List1: " + list1.size());
        System.out.println("List2: " + list2.size());
    }
}
