package ru.basejava;

public class MainDeadLock {
    private static final String LOCK_OBJECT1 = "Lock_object1";
    private static final String LOCK_OBJECT2 = "Lock_object2";
    public static void getMonitor(Object lockObject1, Object lockObject2) {
        synchronized (lockObject1) {
            System.out.println("thread " + Thread.currentThread().getName() + " hold " + lockObject1 + " and wait for " + lockObject2);
            synchronized (lockObject2) {
                System.out.println("thread " + Thread.currentThread().getName() + " hold " + lockObject2);
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> getMonitor(LOCK_OBJECT1, LOCK_OBJECT2));
        Thread thread2 = new Thread(() -> getMonitor(LOCK_OBJECT2, LOCK_OBJECT1));

        thread1.start();
        thread2.start();
    }
}

