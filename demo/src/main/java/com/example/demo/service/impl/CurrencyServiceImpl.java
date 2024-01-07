package com.example.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Currency;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {
	
	@Resource
	CurrencyRepository currencyRepository;

	@Override
	public List<Currency> findAll() {
		return currencyRepository.findAll();
	}
	
	@Override
	public Currency findById(String code) {
		return currencyRepository.findById(code).orElse(null);
	}

	@Override
	public void save(Currency currency) {
		Currency temp = currencyRepository.findById(currency.getCurrencyCode()).orElse(null);
		if(temp != null) {
			throw new DuplicateKeyException("Currency with code '" + currency.getCurrencyCode() + "' already exists");
		}
		currencyRepository.save(currency);
	}
	
	@Override
	public void deleteById(String code) {
		currencyRepository.deleteById(code);
	}
	
	@Override
	public Currency update(Currency currency) {
		Currency temp = currencyRepository.findById(currency.getCurrencyCode()).orElse(null);
		if(temp == null) {
			throw new EntityNotFoundException("Currency with code '" + currency.getCurrencyCode() + "' not found");
		}
		return currencyRepository.save(currency);
	}
}
