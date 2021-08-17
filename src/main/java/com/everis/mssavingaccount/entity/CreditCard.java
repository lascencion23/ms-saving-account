package com.everis.mssavingaccount.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    private String id;

    private String cardNumber;

    private Customer customer;

    private Double limitCredit;

    private LocalDate expirationDate;

    private LocalDateTime date;
}
