package com.artantech.dev_commons.project2.service;

import com.artantech.dev_commons.project2.model.Marketplace;
import com.artantech.dev_commons.project2.repository.MarketplaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketplaceService {

    private final MarketplaceRepository marketplaceRepository;
    
    public MarketplaceService(MarketplaceRepository marketplaceRepository) {
        this.marketplaceRepository = marketplaceRepository;
    }
    
    public List<Marketplace> listMarketplace() {
        return marketplaceRepository.findAll();
    }

    public Marketplace saveMarketplace(Marketplace product) {
        return marketplaceRepository.save(product);
    }
}
