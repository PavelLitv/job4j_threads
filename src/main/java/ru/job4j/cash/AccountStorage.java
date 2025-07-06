package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        boolean result = false;
        synchronized (accounts) {
            if (!accounts.containsKey(account.id())) {
                accounts.put(account.id(), account);
                result = true;
            }
        }
        return result;
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            return accounts.replace(account.id(), account) != null;
        }
    }

    public void delete(int id) {
        synchronized (accounts) {
            accounts.remove(id);
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        synchronized (accounts) {
            Account payer = accounts.get(fromId);
            Account receiver = accounts.get(toId);
            if (payer != null && receiver != null && payer.amount() >= amount) {
                payer = new Account(payer.id(), payer.amount() - amount);
                receiver = new Account(receiver.id(), receiver.amount() + amount);
                update(payer);
                update(receiver);
                result = true;
            }
        }
        return result;
    }
}
