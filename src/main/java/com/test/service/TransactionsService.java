package com.test.service;

import com.test.entity.Transactions;

import java.util.List;

/**
 * Transaction Information Service Interface
 */
public interface TransactionsService {
    /**
     * Query all transaction information
     *
     * @return List<Transactions>
     */
    List<Transactions> selectAll();
}
