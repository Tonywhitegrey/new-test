package com.test.entity;

import lombok.Data;

/**
 * Transactions Entity Class
 */
@Data
public class Transactions {

    private String accountNumber;

    private String transactionDatetime;

    private Double transactionAmount;

    private String postDate;

    private String merchantNumber;

    private String merchantDescription;

    private String merchantCategoryCode;

    private String transactionNumber;
}
