package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Currency;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.BitcoinDTO;
import com.example.demo.model.BpiData;
import com.example.demo.service.CurrencyService;
import com.example.demo.utils.ApiCommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/coindeskapi")
public class CoinDeskController {
	private static final String COINDESK_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
	public static Map<String, String> currencyMap = new HashMap<String, String>();

	@Resource
	ApiCommonUtil apiCommonUtil;
	@Resource
	CurrencyService currencyService;
	
    @GetMapping
    public ResponseEntity<String> getCurrentPrice() {
    	RestTemplate restTemplate = apiCommonUtil.defaulRestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(COINDESK_URL, String.class);
    	return new ResponseEntity<>(response.getBody(), HttpStatus.resolve(response.getStatusCodeValue()));
    }
    
    @GetMapping("/convert")
    public ResponseEntity<ApiResponse> getCurrentPrice_convert() throws JsonMappingException, JsonProcessingException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	RestTemplate restTemplate = apiCommonUtil.defaulRestTemplate();
    	ResponseEntity<String> response = restTemplate.getForEntity(COINDESK_URL, String.class);
    	
    	/** 初始化幣別對應表 */
    	if(currencyMap.isEmpty()) {
    		currencyMap = currencyService.findAll().stream()
    	            .collect(Collectors.toMap(Currency::getCurrencyCode, Currency::getCurrencyName));
    	}
    	
    	ApiResponse apiResponse = null; 
    	BitcoinDTO bitcoinDTO = null;
		bitcoinDTO = objectMapper.readValue(response.getBody(), BitcoinDTO.class);
		for(Map.Entry<String, BpiData> entry : bitcoinDTO.getBpiData().entrySet()) {
			/** 取得幣別中文名稱 */
			entry.getValue().setCodeName(currencyMap.get(entry.getKey()));
		}
		apiResponse = new ApiResponse(bitcoinDTO);
		apiResponse.setStatusCode(response.getStatusCodeValue());
    	return new ResponseEntity<>(apiResponse, HttpStatus.resolve(apiResponse.getStatusCode()));
    }
}
