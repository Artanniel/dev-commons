package com.artantech.dev_commons.project2.service;

import com.artantech.dev_commons.project2.entity.Marketplace;
import com.artantech.dev_commons.project2.repository.MarketplaceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class MarketplaceService {

    private final MarketplaceRepository marketplaceRepository;
    
    public MarketplaceService(MarketplaceRepository marketplaceRepository) {
        this.marketplaceRepository = marketplaceRepository;
    }
    
    public List<Marketplace> listMarketplace() {
        return marketplaceRepository.findAll();
    }

    public Marketplace getMarketplaceById(Long id) {
        return marketplaceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marketplace not found with ID: " + id));
    }

    public Marketplace saveMarketplace(Marketplace marketplace) {
        if (marketplace.getId() == null && marketplace.getCreatedAt() == null) {
            marketplace.setCreatedAt(LocalDateTime.now());
        }
        return marketplaceRepository.save(marketplace);
    }

    public Marketplace updateMarketplace(Long id, Marketplace marketplaceDetails) {
        Marketplace existingMarketplace = getMarketplaceById(id);
        existingMarketplace.setName(marketplaceDetails.getName());
        existingMarketplace.setDescription(marketplaceDetails.getDescription());
        return marketplaceRepository.save(existingMarketplace);
    }

    public void deleteMarketplace(Long id) {
        Marketplace marketplace = getMarketplaceById(id);
        marketplaceRepository.delete(marketplace);
    }
}
