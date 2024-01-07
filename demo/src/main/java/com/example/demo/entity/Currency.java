package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "currency")
public class Currency {
	
	@Id
	@Column(name = "currency_code")
	private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;
}
