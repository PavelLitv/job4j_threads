package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    public void whenMultipleConsumersThenAllItemsAreConsumedOnce() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10_000);
        Thread producer = new Thread(
                () -> IntStream.range(0, 10_000).forEach(item -> {
                            try {
                                queue.offer(item);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                )
        );
        producer.start();
        Thread consumerSecond = getConsumer(queue, buffer);
        Thread consumerFirst = getConsumer(queue, buffer);
        producer.join();
        consumerFirst.interrupt();
        consumerFirst.join();
        consumerSecond.interrupt();
        consumerSecond.join();
        assertThat(buffer).hasSize(10_000);
        assertThat(buffer).containsExactlyInAnyOrderElementsOf(
                IntStream.range(0, 10_000)
                        .boxed()
                        .collect(Collectors.toList())
        );
    }

    private static Thread getConsumer(SimpleBlockingQueue<Integer> queue, CopyOnWriteArrayList<Integer> buffer) {
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        return consumer;
    }
}
