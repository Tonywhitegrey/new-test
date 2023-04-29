package com.test.utils;

import com.test.entity.Transactions;

import java.util.ArrayList;
import java.util.List;

/**
 * KNN Utils
 */
public class OutlierDetector {
    /**
     * 使用KNN方法检测离群点
     *
     * @param data       data
     * @param k          K-value, used to determine the number of neighbor data
     * @param threshold  Threshold, which represents that if the distance from the neighboring data to the target data exceeds this val
     * @return           Outlier List
     */
    public static List<Double> detectOutliersByKNN(Double[] data, int k, double threshold) {
        List<Double> outliers = new ArrayList<>();
        int n = data.length;
        for (int i = 0; i < n; i++) {
            int count = 0;
            double sumDistance = 0.0;
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                double distance = Math.abs(data[i] - data[j]);
                if (distance <= threshold) {
                    count++;
                    sumDistance += distance;
                }
                if (count >= k) {
                    break;
                }
            }
            if (count < k) {
                outliers.add(data[i]);
            }
        }
        return outliers;
    }
    /**
     * Using KNN method to detect outliers
     *
     * @param data       data
     * @param k          K-value, used to determine the number of neighbor data
     * @param threshold  Threshold, which represents that if the distance from the neighboring data to the target data exceeds this value, it is considered an outlier
     * @return           Outlier List
     */
    public static List<Transactions> detectOutliersByKNN(List<Transactions> data, int k, double threshold) {
        List<Transactions> outliers = new ArrayList<>();
        int n = data.size();
        for (int i = 0; i < n; i++) {
            int count = 0;
            double sumDistance = 0.0;
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                double distance = Math.abs(data.get(i).getTransactionAmount() - data.get(j).getTransactionAmount());
                if (distance <= threshold) {
                    count++;
                    sumDistance += distance;
                }
                if (count >= k) {
                    break;
                }
            }
            if (count < k) {
                outliers.add(data.get(i));
            }
        }
        return outliers;
    }
}
