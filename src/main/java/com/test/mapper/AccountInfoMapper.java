package com.test.mapper;


import com.test.entity.AccountInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountInfoMapper {
    List<AccountInfo> selectAll();
}
