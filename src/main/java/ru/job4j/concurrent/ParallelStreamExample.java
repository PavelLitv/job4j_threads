package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> multiplication = list.stream()
                .reduce((left, right) -> left * right);
        System.out.println(multiplication.get());

        Optional<Integer> multi = list.parallelStream()
                .reduce((left, right) -> left * right);

        System.out.println(multi.get());

        list.stream().parallel().peek(System.out::println).toList();
        System.out.println();
        list.stream().parallel().forEach(System.out::println);
        System.out.println();
        list.stream().parallel().forEachOrdered(System.out::println);

    }
}
