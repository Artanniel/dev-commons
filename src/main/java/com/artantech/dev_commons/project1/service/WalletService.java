package com.artantech.dev_commons.project1.service;

import com.artantech.dev_commons.project1.entity.vo.Wallet;
import com.artantech.dev_commons.service.core.AbstractValidationService;
import com.artantech.dev_commons.service.core.CrudService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WalletService extends AbstractValidationService<Wallet, Long> implements CrudService<Wallet, Long> {

    private final Map<Long, Wallet> wallets = new HashMap<>();
    private Long nextId = 4L;
    {
        wallets.put(1L, new Wallet(1L, "Dollar", 2000.0));
        wallets.put(2L, new Wallet(2L, "BTC", 0.0001));
        wallets.put(3L, new Wallet(3L, "Real", 10000.0));
    }

    private Long getNextId() {
        return nextId++;
    }

    @Override
    public List<Wallet> findAll() {
        return new ArrayList<>(wallets.values());
    }

    @Override
    public Wallet findById(Long id) {
        Wallet wallet = wallets.get(id);
        if (wallet == null) {
            throw new RuntimeException("Wallet not found with id: " + id);
        }
        return wallet;
    }

    @Override
    public Wallet save(Wallet wallet) {
        wallets.put(getNextId(), wallet);
        return wallet;
    }

    @Override
    public Wallet update(Long id, Wallet wallet) {
        if (!wallets.containsKey(id)) {
            throw new RuntimeException("Wallet not found with id: " + id);
        }
        wallets.put(id, wallet);
        return wallet;
    }

    @Override
    public void delete(Long id) {
        if (!wallets.containsKey(id)) {
            throw new RuntimeException("Wallet not found with id: " + id);
        }
        wallets.remove(id);
    }
}
