package com.test.service;

import com.test.entity.AccountInfo;
import com.test.entity.vo.Rule1VO;
import com.test.entity.vo.Rule2VO;

import java.util.List;

/**
 * Account Info Service Interface
 */
public interface AccountInfoService {
    /**
     * Query all account info list
     *
     * @return List<AccountInfo>
     */
    List<AccountInfo> selectAll();

    /**
     * Query Rule 1 Filter List
     *
     * @return List<Rule1VO>
     */
    List<Rule1VO> rule1List();

    /**
     * Query Rule 2 Filter List
     *
     * @return List<Rule2VO>
     */
    List<Rule2VO> rule2List();

    /**
     * Export Rule 1 Filter Information
     *
     * @return filePath
     */
    String rule1ExportList();

    /**
     * Export Rule 2 Filter Information
     *
     * @return filePath
     */
    String rule2ExportList();
}
