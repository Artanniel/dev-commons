package com.artantech.dev_commons.project1.controller;

import com.artantech.dev_commons.project1.entity.vo.Wallet;
import com.artantech.dev_commons.project1.service.WalletService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    WalletService walletService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("")
    public ResponseEntity<?> getAllWallets(
            @RequestHeader("Authorization") String authHeader) {
        final String serviceName = "getAllWallets";
        walletService.validateAuthHeader(serviceName, authHeader);
        JsonNode wallets = walletService.getResponsePayload(serviceName);
        return new ResponseEntity<>(wallets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWalletById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "params", required = false) Map<String, String> requestParams,
            @RequestParam(value = "query", required = false) Map<String, String> requestQuery) {
        final String serviceName = "getWalletById";
        if (requestParams != null) {
            walletService.validateParams(serviceName, requestParams);
        }
        if (requestQuery != null) {
            walletService.validateQuery(serviceName, requestQuery);
        }
        walletService.validateAuthHeader(serviceName, authHeader);
        Wallet wallet = walletService.findById(id);
        return new ResponseEntity<>(wallet, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createWallet(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) JsonNode requestBody) {
        final String serviceName = "createWallet";
        try {
            walletService.validateAuthHeader(serviceName, authHeader);
            walletService.validateRequestPayload(serviceName, requestBody);
            Wallet wallet = objectMapper.treeToValue(requestBody, Wallet.class);
            Wallet createdWallet = walletService.save(wallet);
            return new ResponseEntity<>(createdWallet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWallet(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) JsonNode requestBody,
            @RequestParam(value = "params", required = false) Map<String, String> requestParams,
            @RequestParam(value = "query", required = false) Map<String, String> requestQuery) {

        final String serviceName = "updateWallet";
        walletService.validateAuthHeader(serviceName, authHeader);
        walletService.validateRequestPayload(serviceName, requestBody);
        walletService.validateParams(serviceName, requestParams);
        walletService.validateQuery(serviceName, requestQuery);

        try {
            Wallet wallet = objectMapper.treeToValue(requestBody, Wallet.class);
            Wallet updatedWallet = walletService.update(id, wallet);
            return new ResponseEntity<>(updatedWallet, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(),
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWallet(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(value = "params", required = false) Map<String, String> requestParams,
            @RequestParam(value = "query", required = false) Map<String, String> requestQuery) {
        final String serviceName = "deleteWallet";
        try {
            walletService.validateAuthHeader(serviceName, authHeader);
            walletService.validateParams(serviceName, requestParams);
            walletService.validateQuery(serviceName, requestQuery);
            walletService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error processing request: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}