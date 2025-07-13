package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    @Test
    void testSimpleBlockingQueue() throws InterruptedException {
        String message = "Hello World";
        AtomicReference<String> received = new AtomicReference<>();
        var queue = new SimpleBlockingQueue<String>(1);
        var producer = new Thread(() -> {
            try {
                queue.offer(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        var consumer = new Thread(() -> {
            try {
                received.set(queue.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
        assertThat(received.get()).isEqualTo(message);
    }
}
