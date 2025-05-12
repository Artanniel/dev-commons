package com.interall.dev_commons.project2.controller;

import com.interall.dev_commons.project2.model.Marketplace;
import com.interall.dev_commons.project2.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marketplace")
public class MarketplaceController {

    @Autowired
    private MarketplaceService marketplaceService;

    @GetMapping("")
    public List<Marketplace> getMarketplaces() {
        return marketplaceService.listMarketplace();
    }

    @PostMapping
    public Marketplace createMarketplace(@RequestBody Marketplace marketplace) {
        return marketplaceService.saveMarketplace(marketplace);
    }
}
