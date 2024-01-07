package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import com.example.demo.entity.Currency;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CoinDesk比特幣價格指數測試")
public class CurrencyTest {
	@Autowired
    private MockMvc mockMvc;
	
	@Resource
	CurrencyRepository currencyRepository;
	@Resource
	CurrencyService currencyService;

    @Test
    @DisplayName("coindeskAPI")
    void coindeskAPI() throws Exception {
    	MvcResult result = mockMvc.perform(get("/coindeskapi")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
	
	@Test
	@DisplayName("coindeskAPI-convert")
	void coindeskAPI_Convert() throws Exception {
		MvcResult result = mockMvc.perform(get("/coindeskapi/convert")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
	}
	
	@Test
	@DisplayName("currencyMapping-findAll")
	void currencyFindAll() throws Exception {
		MvcResult result = mockMvc.perform(get("/currencies")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
	}
	
	@Test
	@DisplayName("currencyMapping-create")
	void currencyCreate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(post("/currencies")
				.content(mapper.writeValueAsString(dummyCurrency()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
		assertNotNull(currencyService.findById("TEST"));
	}
	
	@Test
	@DisplayName("currencyMapping-update")
	void currencyUpdate() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Currency temp = new Currency();
    	temp.setCurrencyCode("USD");
    	temp.setCurrencyName("TEST-UPDATE-USD");
		
		mockMvc.perform(put("/currencies")
				.content(mapper.writeValueAsString(temp))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		assertEquals(temp.getCurrencyName(), currencyService.findById(temp.getCurrencyCode()).getCurrencyName());
	}
	
	@Test
	@DisplayName("currencyMapping-delete")
	void currencyDelete() throws Exception {
		String code = dummyCurrency().getCurrencyCode();
		mockMvc.perform(delete("/currencies/" + code)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		assertNull(currencyService.findById(code));
	}
	
	private Currency dummyCurrency() {
    	Currency dummyCurrency = new Currency();
    	dummyCurrency.setCurrencyCode("TEST");
    	dummyCurrency.setCurrencyName("測試");
        return dummyCurrency;
    }
}
