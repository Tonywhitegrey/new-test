package com.test.service.impl;

import com.test.entity.AccountInfo;
import com.test.entity.Transactions;
import com.test.entity.vo.Rule1VO;
import com.test.entity.vo.Rule2VO;
import com.test.mapper.AccountInfoMapper;
import com.test.mapper.TransactionsMapper;
import com.test.service.AccountInfoService;
import com.test.utils.OutlierDetector;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Account Information Service Implementation
 */
@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    /**
     * Injecting account information into the Mapper interface
     */
    @Resource
    private AccountInfoMapper accountInfoMapper;
    /**
     * Injecting Transaction Information Mapper Interface
     */
    @Resource
    private TransactionsMapper transactionsMapper;

    /**
     * Query all account info list
     *
     * @return List<AccountInfo>
     */
    @Override
    public List<AccountInfo> selectAll() {
        return accountInfoMapper.selectAll();
    }

    /**
     * 查询规则1过滤列表
     *
     * @return List<Rule1VO>
     */
    @Override
    public List<Rule1VO> rule1List() {
        // 实例化输出类
        List<Rule1VO> list = new ArrayList<>();
        // 查询所有账户信息
        List<AccountInfo> accountInfos = accountInfoMapper.selectAll();
        // 查询所有交易信息
        List<Transactions> transactions = transactionsMapper.selectAll();
        // 将交易信息通过账户中的accountNumber字段分组成map
        Map<String, List<Transactions>> collect = transactions.stream().collect(Collectors.groupingBy(Transactions::getAccountNumber));
        // 循环账户信息
        accountInfos.forEach(accountInfo -> {
            // 判断根据accountNumber字段分组成map中是否和账户信息匹配
            if (collect.containsKey(accountInfo.getAccountNumber())) {
                // 拿到账户下的所有交易记录
                List<Transactions> userTransactions = collect.get(accountInfo.getAccountNumber());
                // 将账户下的所有交易记录通过merchantDescription字段分组组成新map
                Map<String, List<Transactions>> collect1 = userTransactions.stream().collect(Collectors.groupingBy(Transactions::getMerchantDescription));
                // 循环分组的新map
                collect1.forEach((key, value) -> {
                    // 判断当前相同merchantDescription字段下是否存在2条以上数据
                    if (value.size() > 2) {
                        // 将存在的交易记录中的交易金额拿出来，设置成列表
                        List<Double> collect2 = value.stream().map(Transactions::getTransactionAmount).collect(Collectors.toList());
                        // 计算交易金额的平均数的绝对值，作为KNN的阈值
                        double average = collect2.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
                        // 调用KNN计算出异常列表数据
                        List<Transactions> outliers = OutlierDetector.detectOutliersByKNN(value, 1, Math.abs(average));
                        // 循环异常列表数据
                        outliers.forEach(outlier -> {
                            // 实例化规则1返回类，并组装数据
                            Rule1VO vo = new Rule1VO();
                            vo.setLastName(accountInfo.getLastName());
                            vo.setFirstName(accountInfo.getFirstName());
                            vo.setAccountNumber(accountInfo.getAccountNumber());
                            vo.setTransactionNumber(outlier.getTransactionNumber());
                            vo.setMerchantDescription(outlier.getMerchantDescription());
                            vo.setTransactionAmount(outlier.getTransactionAmount());
                            // 放入返回列表中
                            list.add(vo);
                        });
                    }
                });
            }
        });
        // 返回给前端
        return list;
    }

    /**
     * Query Rule 2 Filter List
     *
     * @return List<Rule2VO>
     */
    @Override
    public List<Rule2VO> rule2List() {
        // Instantiate output list class
        List<Rule2VO> list = new ArrayList<>();
        // Query all account information
        List<AccountInfo> accountInfos = accountInfoMapper.selectAll();
        // Query all transaction information
        List<Transactions> transactions = transactionsMapper.selectAll();
        // Divide transaction information into a map through the AccountNumber field in the account
        Map<String, List<Transactions>> collect = transactions.stream().collect(Collectors.groupingBy(Transactions::getAccountNumber));
        // Revolving account info
        accountInfos.forEach(accountInfo -> {
            // Determine whether the account number field matches the account information in the composition map
            if (collect.containsKey(accountInfo.getAccountNumber())) {
                // Obtain all transaction records under the account
                List<Transactions> userTransactions = collect.get(accountInfo.getAccountNumber());
                // Transaction records under revolving accounts
                userTransactions.forEach(t -> {
                    // Determine whether the region in the transaction record contains the state in the current account. If not, place it in the exception list
                    if (!t.getMerchantDescription().contains(accountInfo.getState())) {
                        // Instantiate Rule 2 to return the class and assemble the data
                        Rule2VO vo = new Rule2VO();
                        vo.setLastName(accountInfo.getLastName());
                        vo.setFirstName(accountInfo.getFirstName());
                        vo.setAccountNumber(accountInfo.getAccountNumber());
                        vo.setTransactionNumber(t.getTransactionNumber());
                        vo.setState(accountInfo.getState());
                        vo.setMerchantDescription(t.getMerchantDescription());
                        // Place Return List
                        list.add(vo);
                    }
                });
            }
        });
        // Return data list
        return list;
    }

    /**
     * Export Rule 1 Filter Information
     *
     * @return filePath
     */
    @SneakyThrows
    @Override
    public String rule1ExportList() {
        // Call Rule 1 List to Obtain Data
        List<Rule1VO> voList = rule1List();
        // Export file storage path and file name
        String exportPath = "rule1.txt";
        // Instantiating FileWriter
        FileWriter fw = new FileWriter(exportPath);
        // Instantiating BufferedWriter
        BufferedWriter bw = new BufferedWriter(fw);
        // Write the header file first and wrap it
        bw.write(voList.get(0).getHeader() + System.lineSeparator());
        // Loop List Write VO
        for (Rule1VO vo : voList) {
            // Convert each VO into a string and add a carriage return and line feed character
            bw.write(vo.toString() + System.lineSeparator());
        }
        bw.close();
        fw.close();
        // Return filePath
        return exportPath;
    }

    /**
     * Export Rule 2 Filter Information
     *
     * @return filePath
     */
    @SneakyThrows
    @Override
    public String rule2ExportList() {
        // 调用规则2列表拿到数据
        List<Rule2VO> voList = rule2List();
        // 导出文件存储路径和文件名
        String exportPath = "rule2.txt";
        // 实例化FileWriter
        FileWriter fw = new FileWriter(exportPath);
        // 实例化BufferedWriter
        BufferedWriter bw = new BufferedWriter(fw);
        // 先写入头文件，并换行
        bw.write(voList.get(0).getHeader() + System.lineSeparator());
        // 循环列表写入VO
        for (Rule2VO vo : voList) {
            // 将每个VO转换为字符串，并加上回车换行符
            bw.write(vo.toString() + System.lineSeparator());
        }
        bw.close();
        fw.close();
        // 返回文件地址
        return exportPath;
    }
}