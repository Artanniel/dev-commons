package com.artantech.dev_commons.project2.repository;

import com.artantech.dev_commons.project2.entity.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {

}
