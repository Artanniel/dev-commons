package com.artantech.dev_commons.project2.service;

import com.artantech.dev_commons.project2.entity.vo.Seller;
import com.artantech.dev_commons.service.core.AbstractValidationService;
import com.artantech.dev_commons.service.core.CrudService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellerService extends AbstractValidationService<Seller, Long> implements CrudService<Seller, Long> {
    private final Map<Long, Seller> sellers = new HashMap<>();
    private Long nextId = 4L; // Starting after the hardcoded wallets
    {
        sellers.put(1L, new Seller(1L, "Jhon", 2000.0, 1));
        sellers.put(2L, new Seller(2L, "Doe", 100000, 2));
        sellers.put(3L, new Seller(3L, "Rachel", 30000.00, 5));
    }

    private Long getNextId() {
        return nextId++;
    }

    @Override
    public List<Seller> findAll() {
        return new ArrayList<>(sellers.values());
    }

    @Override
    public Seller findById(Long id) {
        Seller seller = sellers.get(id);
        if (seller == null) {
            throw new RuntimeException("Wallet not found with id: " + id);
        }
        return seller;
    }

    @Override
    public Seller save(Seller seller) {
        if (seller.getId() > 0){
            sellers.put(seller.getId(), seller);
        }else{
            Long newId = getNextId();
            seller.setId(newId);
            sellers.put(newId, seller);
        }
        return seller;
    }

    @Override
    public Seller update(Long id, Seller seller) {
        if (!sellers.containsKey(id)) {
            throw new RuntimeException("Wallet not found with id: " + id);
        }
        sellers.put(id, seller);
        return seller;
    }

    @Override
    public void delete(Long id) {
        if (!sellers.containsKey(id)) {
            throw new RuntimeException("Wallet not found with id: " + id);
        }
        sellers.remove(id);
    }
}
