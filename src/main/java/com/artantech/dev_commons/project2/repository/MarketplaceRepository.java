package com.artantech.dev_commons.project2.repository;

import com.artantech.dev_commons.project2.model.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {
}
