package ru.job4j.cash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountStorageTest {
    @Test
    void whenAdd() {
        var storage = new AccountStorage();
        boolean result = storage.add(new Account(1, 100));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
        assertThat(result).isTrue();
    }

    @Test
    void whenAccountIsExistAddThenNotUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        boolean result = storage.add(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(100);
        assertThat(result).isFalse();
    }

    @Test
    void whenUpdate() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        boolean result = storage.update(new Account(1, 200));
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(200);
        assertThat(result).isTrue();
    }

    @Test
    void whenNoAccountForUpdateThenNotAdd() {
        var storage = new AccountStorage();
        boolean result = storage.update(new Account(1, 100));
        assertThat(storage.getById(1)).isEmpty();
        assertThat(result).isFalse();
    }

    @Test
    void whenDelete() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.delete(1);
        assertThat(storage.getById(1)).isEmpty();
    }

    @Test
    void whenTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 100));
        storage.add(new Account(2, 100));
        boolean result = storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(firstAccount.amount()).isEqualTo(0);
        assertThat(secondAccount.amount()).isEqualTo(200);
        assertThat(result).isTrue();
    }

    @Test
    void whenNotEnoughAmountForTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 50));
        storage.add(new Account(2, 100));
        boolean result = storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(firstAccount.amount()).isEqualTo(50);
        assertThat(secondAccount.amount()).isEqualTo(100);
        assertThat(result).isFalse();
    }

    @Test
    void whenPayerIsNotExistTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(2, 100));
        boolean result = storage.transfer(1, 2, 100);
        var secondAccount = storage.getById(2)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 2"));
        assertThat(secondAccount.amount()).isEqualTo(100);
        assertThat(result).isFalse();
    }

    @Test
    void whenReceiverIsNotExistTransfer() {
        var storage = new AccountStorage();
        storage.add(new Account(1, 50));
        boolean result = storage.transfer(1, 2, 100);
        var firstAccount = storage.getById(1)
                .orElseThrow(() -> new IllegalStateException("Not found account by id = 1"));
        assertThat(firstAccount.amount()).isEqualTo(50);
        assertThat(result).isFalse();
    }
}
