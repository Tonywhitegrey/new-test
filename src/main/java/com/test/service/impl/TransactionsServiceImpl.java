package com.test.service.impl;

import com.test.entity.Transactions;
import com.test.mapper.TransactionsMapper;
import com.test.service.TransactionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Transaction Information Service Implementation
 */
@Service
public class TransactionsServiceImpl implements TransactionsService {
    /**
     * Inject Transaction Mapper
     */
    @Resource
    private TransactionsMapper transactionsMapper;

    /**
     * Query all transaction information
     *
     * @return List<Transactions>
     */
    @Override
    public List<Transactions> selectAll() {
        return transactionsMapper.selectAll();
    }
}
