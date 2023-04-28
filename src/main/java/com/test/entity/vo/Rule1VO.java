package com.test.entity.vo;

import lombok.Data;

/**
 * Rule 1 Output VO
 */
@Data
public class Rule1VO {

    private String lastName;

    private String firstName;

    private String accountNumber;

    private String transactionNumber;

    private String merchantDescription;

    private Double transactionAmount;

    /**
     * 设置导出时的数据标题 Set the data title during export
     *
     * @return 通过制表符分割的头名称 Header names separated by tabs
     */
    public String getHeader() {
        return "lastName\tfirstName\taccountNumber\ttransactionNumber\tmerchantDescription\ttransactionAmount";
    }

    /**
     * 重新toString方法 Rewrite ToString method
     *
     * @return String
     */
    @Override
    public String toString() {
        return lastName + "\t" + firstName + "\t" + accountNumber + "\t" + transactionNumber + "\t" + merchantDescription + "\t" + transactionAmount;
    }
}
