package com.fnw.train;

import java.util.*;

/**
 * 简单数据集实现
 * 用于存储和管理训练数据
 */
public class SimpleDataset implements Dataset {
    
    // 存储所有样本
    private List<Map<String, Boolean>> samples;
    
    // 随机数生成器
    private Random random;
    
    public SimpleDataset() {
        this.samples = new ArrayList<>();
        this.random = new Random();
    }
    
    public SimpleDataset(List<Map<String, Boolean>> samples) {
        this.samples = new ArrayList<>(samples);
        this.random = new Random();
    }
    
    @Override
    public int size() {
        return samples.size();
    }
    
    @Override
    public Map<String, Boolean> getSample(int index) {
        if (index < 0 || index >= samples.size()) {
            throw new IndexOutOfBoundsException("样本索引超出范围: " + index);
        }
        return samples.get(index);
    }
    
    @Override
    public List<Map<String, Boolean>> getAllSamples() {
        return new ArrayList<>(samples);
    }
    
    @Override
    public void shuffle() {
        Collections.shuffle(samples, random);
    }
    
    /**
     * 添加样本到数据集
     * @param sample 样本数据
     */
    public void addSample(Map<String, Boolean> sample) {
        samples.add(sample);
    }
    
    /**
     * 批量添加样本到数据集
     * @param samples 样本列表
     */
    public void addSamples(List<Map<String, Boolean>> samples) {
        this.samples.addAll(samples);
    }
    
    /**
     * 清空数据集
     */
    public void clear() {
        samples.clear();
    }
    
    /**
     * 创建一个简单的训练数据集示例
     * @return 示例数据集
     */
    public static SimpleDataset createExampleDataset() {
        SimpleDataset dataset = new SimpleDataset();
        
        // 创建一些示例数据
        // 模式1: 神经元1和2同时激活
        Map<String, Boolean> sample1 = new HashMap<>();
        sample1.put("1", true);
        sample1.put("2", true);
        sample1.put("3", false);
        dataset.addSample(sample1);
        
        // 模式2: 神经元2和3同时激活
        Map<String, Boolean> sample2 = new HashMap<>();
        sample2.put("1", false);
        sample2.put("2", true);
        sample2.put("3", true);
        dataset.addSample(sample2);
        
        // 模式3: 神经元1和3同时激活
        Map<String, Boolean> sample3 = new HashMap<>();
        sample3.put("1", true);
        sample3.put("2", false);
        sample3.put("3", true);
        dataset.addSample(sample3);
        
        // 模式4: 只有神经元1激活
        Map<String, Boolean> sample4 = new HashMap<>();
        sample4.put("1", true);
        sample4.put("2", false);
        sample4.put("3", false);
        dataset.addSample(sample4);
        
        return dataset;
    }
}
