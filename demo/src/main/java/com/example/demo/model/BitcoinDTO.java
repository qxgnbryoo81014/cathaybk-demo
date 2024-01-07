package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoinDTO {
	
	@JsonProperty(value = "time", access = JsonProperty.Access.WRITE_ONLY)
    private Map<String, String> time;
	
	@JsonProperty("bpi")
	Map<String, BpiData> bpiData;

	String updateDtm;
	
	public Map<String, String> getTime() {
		return time;
	}

	public void setTime(Map<String, String> time) {
		this.time = time;
	}

	public Map<String, BpiData> getBpiData() {
		return bpiData;
	}

	public void setBpiData(Map<String, BpiData> bpiData) {
		this.bpiData = bpiData;
	}

	public String getUpdateDtm() {
		if(time != null && time.get("updatedISO") != null) {
			DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			
			OffsetDateTime dateTime = OffsetDateTime.parse(time.get("updatedISO"), inputFormatter);
			LocalDateTime localDateTime = dateTime.toLocalDateTime();
			return localDateTime.format(outputFormatter);
		}else {
			return null;
		}
	}
	
	public void setUpdateDtm(String updateDtm) {
		this.updateDtm = updateDtm;
	}
}
