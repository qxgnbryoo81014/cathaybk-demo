package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.CurrencyService;
import com.google.gson.Gson;


@DisplayName("CoinDesk比特幣價格指數測試")
@SpringBootTest
public class CurrencyTest {
	
	@Resource
	CurrencyRepository currencyRepository;
	@Resource
	CurrencyService currencyService;

    @Test
    @DisplayName("coindeskAPI")
    void coindeskAPI() {
		if(HttpStatus.OK == HttpStatus.resolve(200)) {
			System.out.println("test");
		}
    }
	
	@Test
	@DisplayName("coindeskAPI-convert")
	void coindeskAPI_Convert() {
		
	}
	
	@Test
	@DisplayName("currencyMapping-create")
	void currencyCreate() {
		
	}
	
	@Test
	@DisplayName("currencyMapping-select")
	void currencySelect() {
		
	}
	
	@Test
	@DisplayName("currencyMapping-update")
	void currencyUpdate() {
		
	}
	
	@Test
	@DisplayName("currencyMapping-delete")
	void currencyDelete() {
		
	}
}


