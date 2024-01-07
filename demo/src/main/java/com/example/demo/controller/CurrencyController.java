package com.example.demo.controller;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Currency;
import com.example.demo.model.ApiResponse;
import com.example.demo.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
	@Resource
	CurrencyService currencyService;
	
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCurrencies() throws JsonProcessingException {
    	return new ResponseEntity<>(new ApiResponse(currencyService.findAll()), HttpStatus.OK);
    }

    // 根據幣別代碼取得單一幣別資料
    @GetMapping("/{currencyCode}")
    public ResponseEntity<ApiResponse> getCurrencyByCode(@PathVariable("currencyCode") String currencyCode) {
        Currency currency = currencyService.findById(currencyCode);
        if (currency != null) {
        	return new ResponseEntity<>(new ApiResponse(currency), HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), "No currency found for code: " + currencyCode, null), HttpStatus.NOT_FOUND);
        }
    }

    // 新增幣別資料
    @PostMapping
    public ResponseEntity<ApiResponse> addCurrency(@RequestBody Currency currency) {
    	try {
    		currencyService.save(currency);
    		return new ResponseEntity<>(new ApiResponse(null), HttpStatus.OK);
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<>(new ApiResponse(HttpStatus.CONFLICT.value(), e.getMessage(), null), HttpStatus.CONFLICT);
		}
    }

    // 更新幣別資料
    @PutMapping
    public ResponseEntity<ApiResponse> updateCurrency(@RequestBody Currency updatedCurrency) {
    	try {
    		return new ResponseEntity<>(new ApiResponse(currencyService.update(updatedCurrency)), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND .value(), e.getMessage(), null), HttpStatus.NOT_FOUND );
		}
    }

    // 刪除幣別資料
    @DeleteMapping("/{currencyCode}")
    public ResponseEntity<ApiResponse> deleteCurrency(@PathVariable("currencyCode") String currencyCode) {
    	try {
    		currencyService.deleteById(currencyCode);
    		return new ResponseEntity<>(new ApiResponse(null), HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND .value(), "Currency with code '" + currencyCode + "' not found.", null), HttpStatus.NOT_FOUND);
		}
    }
}
