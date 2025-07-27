package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import ru.job4j.email.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParallelGetIndexTest {
    @Test
    void whenIntegerTypeThenGetIndex() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        ParallelGetIndex<Integer> parallelGetIndex = new ParallelGetIndex<>(array, 0, array.length, 15);
        int index = parallelGetIndex.getIndex();
        assertThat(index).isEqualTo(14);
    }

    @Test
    void whenCustomTypeThenGetIndex() {
        User[] array = {
                new User("Pavel", "pavel@ya.ru"),
                new User("Serg", "serg@ya.ru"),
                new User("Ivan", "ivan@ya.ru"),
                new User("Petr", "petr@ya.ru"),
                new User("Oleg", "oleg@ya.ru")
        };
        ParallelGetIndex<User> parallelGetIndex = new ParallelGetIndex<>(array, 0, array.length, new User("Pavel", "pavel@ya.ru"));
        int index = parallelGetIndex.getIndex();
        assertThat(index).isEqualTo(0);
    }

    @Test
    void whenCustomTypeThenGetIndexNotFound() {
        User[] array = {
                new User("Pavel", "pavel@ya.ru"),
                new User("Serg", "serg@ya.ru"),
                new User("Ivan", "ivan@ya.ru"),
                new User("Petr", "petr@ya.ru"),
                new User("Oleg", "oleg@ya.ru")
        };
        ParallelGetIndex<User> parallelGetIndex = new ParallelGetIndex<>(array, 0, array.length, new User("Pavel", "pavel1@ya.ru"));
        int index = parallelGetIndex.getIndex();
        assertThat(index).isEqualTo(-1);
    }
}