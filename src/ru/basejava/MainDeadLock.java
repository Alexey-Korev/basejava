package ru.basejava;

public class MainDeadLock {
    private static final Object LOCK_OBJECT1 = new Object();
    private static final Object LOCK_OBJECT2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (LOCK_OBJECT1) {
                System.out.println("поток 1 взял 1й монитор и ждет 2й монитор");
                synchronized (LOCK_OBJECT2) {
                    System.out.println("поток 1 взял 2й монитор");
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            synchronized (LOCK_OBJECT2) {
                System.out.println("поток 2 взял 2й монитор и ждет 1й монитор");
                synchronized (LOCK_OBJECT1) {
                    System.out.println("поток 2 взял 1й монитор");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}

