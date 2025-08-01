package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    public SimpleBlockingQueue(final int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= capacity) {
                this.wait();
            }
            queue.add(value);
            this.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T item;
        synchronized (this) {
            while (isEmpty()) {
                this.wait();
            }
            item = queue.poll();
            this.notifyAll();
        }
        return item;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
