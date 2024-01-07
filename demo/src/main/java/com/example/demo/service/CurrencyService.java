package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Currency;

public interface CurrencyService {
	
	List<Currency> findAll();
	Currency findById(String code);
	void save(Currency currency);
	void deleteById(String code);
	Currency update(Currency currency);
}
