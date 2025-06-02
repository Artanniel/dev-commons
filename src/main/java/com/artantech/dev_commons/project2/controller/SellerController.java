package com.artantech.dev_commons.project2.controller;

import com.artantech.dev_commons.project1.entity.vo.Wallet;
import com.artantech.dev_commons.project2.entity.Marketplace;
import com.artantech.dev_commons.project2.entity.vo.Seller;
import com.artantech.dev_commons.project2.service.SellerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("")
    public ResponseEntity<List<Seller>> getSellers(@RequestHeader("Authorization") String authHeader) {
        final String serviceName = "getSellers";
        sellerService.validateAuthHeader(serviceName, authHeader);
        List<Seller> sellers = sellerService.findAll();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id,
        @RequestHeader("Authorization") String authHeader,
        @RequestParam(value = "params", required = false) Map<String, String> requestParams,
        @RequestParam(value = "query", required = false) Map<String, String> requestQuery) {
        final String serviceName = "getSellerById";
        sellerService.validateParams(serviceName, requestParams);
        sellerService.validateQuery(serviceName, requestQuery);
        sellerService.validateAuthHeader(serviceName, authHeader);
        Seller seller = sellerService.findById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSeller(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) JsonNode requestBody) {
        final String serviceName = "createSeller";
        try {
            sellerService.validateAuthHeader(serviceName, authHeader);
            sellerService.validateRequestPayload(serviceName, requestBody);
            Seller seller = objectMapper.treeToValue(requestBody, Seller.class);
            Seller createdSeller = sellerService.save(seller);
            return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeller(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) JsonNode requestBody,
            @RequestParam(value = "params", required = false) Map<String, String> requestParams,
            @RequestParam(value = "query", required = false) Map<String, String> requestQuery
    ) {
        final String serviceName = "updateSeller";
        sellerService.validateAuthHeader(serviceName, authHeader);
        sellerService.validateRequestPayload(serviceName, requestBody);
        sellerService.validateParams(serviceName, requestParams);
        sellerService.validateQuery(serviceName, requestQuery);

        try {
            Seller seller = objectMapper.treeToValue(requestBody, Seller.class);
            Seller updatedSeller = sellerService.update(id, seller);
            return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeller(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "params", required = false) Map<String, String> requestParams,
            @RequestParam(value = "query", required = false) Map<String, String> requestQuery) {
        final String serviceName = "deleteSeller";
        try {
            sellerService.validateAuthHeader(serviceName, authHeader);
            sellerService.validateParams(serviceName, requestParams);
            sellerService.validateQuery(serviceName, requestQuery);
            sellerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
