package com.fnw.train;

import com.fnw.model.Neuron;
import java.util.List;
import java.util.Map;

/**
 * 数据集接口
 * 用于表示训练神经网络的外部数据
 */
public interface Dataset {
    
    /**
     * 获取数据集中的样本数量
     * @return 样本数量
     */
    int size();
    
    /**
     * 获取指定索引的样本
     * @param index 样本索引
     * @return 样本数据，键为神经元ID，值为神经元是否应该被激活
     */
    Map<String, Boolean> getSample(int index);
    
    /**
     * 获取所有样本
     * @return 所有样本的列表
     */
    List<Map<String, Boolean>> getAllSamples();
    
    /**
     * 随机打乱数据集
     */
    void shuffle();
}
