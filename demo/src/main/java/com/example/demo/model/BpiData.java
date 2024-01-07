package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BpiData {
	
	// BitcoinDTO

//	@JsonProperty("orderID")
//	String updateDtm;
	@JsonProperty("code")
	String code;
	@JsonProperty("rate_float")
	float rateFloat;
	
	String codeName;
}
