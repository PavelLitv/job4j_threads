package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        boolean replaced = false;
        var current = memory.get(model.id());
        if (model.version() == current.version()) {
            replaced = memory.replace(model.id(), current, new Base(model.id(), model.name(), model.version() + 1));
        }
        if (!replaced) {
            throw new OptimisticException("Versions are not equal");
        }
        return replaced;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
