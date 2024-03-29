package deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    private final Account account1 = new Account();
    private final Account account2 = new Account();

    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void firstThread() throws InterruptedException{
        Random random = new Random();

        for (int i = 0; i < 10_000; i++) {
            acquireLocks(lock1, lock2);

            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException{
        Random random = new Random();

        for (int i = 0; i < 10_000; i++) {

            acquireLocks(lock2, lock1);

            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + account1.getBalance());
        System.out.println("Account 2 balance: " + account2.getBalance());
        System.out.println("Total balance: " + (account1.getBalance() + account2.getBalance()));
    }

    private void acquireLocks (final Lock firstLock, final Lock secondLock) throws InterruptedException {
        while (true) {
            // Acquire locks
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;

            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock) {
                    return;
                }

                if (gotFirstLock) {
                    firstLock.unlock();
                }

                if (gotSecondLock) {
                    secondLock.unlock();
                }

            }

            // Locks not acquired
            Thread.sleep(1);
        }
    }

}
