package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelGetIndex<T> extends RecursiveTask<Integer> {
    static final int SIZE_MIN = 10;
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParallelGetIndex(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (to - from < SIZE_MIN) {
            int result = -1;
            for (int i = from; i <= to; i++) {
                if (Objects.equals(array[i], value)) {
                    result = i;
                    break;
                }
            }
            return result;
        }
        int middle = (from + to) / 2;
        ParallelGetIndex<T> left = new ParallelGetIndex<>(array, from, middle, value);
        ParallelGetIndex<T> right = new ParallelGetIndex<>(array, middle + 1, to, value);
        left.fork();
        right.fork();
        int leftIndex = left.join();
        int rightIndex = right.join();
        return leftIndex != -1 ? leftIndex : rightIndex;
    }

    public int getIndex() {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(this);
    }
}
