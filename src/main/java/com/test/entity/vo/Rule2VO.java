package com.test.entity.vo;

import lombok.Data;

/**
 * Rule 2 Output VO
 */
@Data
public class Rule2VO {

    private String lastName;

    private String firstName;

    private String accountNumber;

    private String transactionNumber;

    private String merchantDescription;

    private String state;

    /**
     * 设置导出时的数据标题 Set the data title during export
     *
     * @return 通过制表符分割的头名称 Header names separated by tabs
     */
    public String getHeader() {
        return "lastName\tfirstName\taccountNumber\ttransactionNumber\tstate\tmerchantDescription";
    }

    /**
     * 重新toString方法 Rewrite ToString method
     *
     * @return String
     */
    @Override
    public String toString() {
        return lastName + "\t" + firstName + "\t" + accountNumber + "\t" + transactionNumber + "\t" + state + "\t" + merchantDescription;
    }
}
