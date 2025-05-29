package com.artantech.dev_commons.project2.controller;

import com.artantech.dev_commons.project2.entity.Marketplace;
import com.artantech.dev_commons.project2.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/marketplace")
public class MarketplaceController {

    @Autowired
    private MarketplaceService marketplaceService;

    @GetMapping("")
    public ResponseEntity<List<Marketplace>> getMarketplaces() {
        List<Marketplace> marketplaces = marketplaceService.listMarketplace();
        return new ResponseEntity<>(marketplaces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marketplace> getMarketplaceById(@PathVariable Long id) {
        Marketplace marketplace = marketplaceService.getMarketplaceById(id);
        return new ResponseEntity<>(marketplace, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Marketplace> createMarketplace(@RequestBody Marketplace marketplace) {
        Marketplace createdMarketplace = marketplaceService.saveMarketplace(marketplace);
        return new ResponseEntity<>(createdMarketplace, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marketplace> updateMarketplace(@PathVariable Long id, @RequestBody Marketplace marketplace) {
        Marketplace updatedMarketplace = marketplaceService.updateMarketplace(id, marketplace);
        return new ResponseEntity<>(updatedMarketplace, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarketplace(@PathVariable Long id) {
        marketplaceService.deleteMarketplace(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
