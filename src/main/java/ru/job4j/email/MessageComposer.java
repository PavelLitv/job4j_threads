package ru.job4j.email;

@FunctionalInterface
public interface MessageComposer {
    Message compose(User user);
}
