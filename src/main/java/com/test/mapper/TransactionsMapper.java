package com.test.mapper;


import com.test.entity.Transactions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsMapper {
    List<Transactions> selectAll();
}
