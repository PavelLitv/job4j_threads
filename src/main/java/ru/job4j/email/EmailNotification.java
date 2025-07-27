package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final MessageComposer composer;

    public EmailNotification(MessageComposer composer) {
        this.composer = composer;
    }

    public void emailTo(User user) {
        pool.submit(() -> {
            Message message = composer.compose(user);
            send(message);
        });
    }

    public void close() {
        pool.shutdown();
    }

    public void send(Message message) {
        System.out.println(message);
    }
}
