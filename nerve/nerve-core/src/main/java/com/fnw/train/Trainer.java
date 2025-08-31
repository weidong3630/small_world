package com.fnw.train;

import com.fnw.model.NeuralNetwork;

/**
 * 训练器接口
 * 定义训练神经网络的基本方法
 */
public interface Trainer {
    
    /**
     * 训练神经网络
     * @param network 要训练的神经网络
     * @param epochs 训练轮数
     */
    void train(NeuralNetwork network, int epochs);
    
    /**
     * 使用数据集训练神经网络
     * @param network 要训练的神经网络
     * @param dataset 训练数据集
     * @param epochs 训练轮数
     */
    void train(NeuralNetwork network, Dataset dataset, int epochs);
    
    /**
     * 在单个训练轮次中训练神经网络
     * @param network 要训练的神经网络
     */
    void trainEpoch(NeuralNetwork network);
    
    /**
     * 使用数据集在单个训练轮次中训练神经网络
     * @param network 要训练的神经网络
     * @param dataset 训练数据集
     */
    void trainEpoch(NeuralNetwork network, Dataset dataset);
    
    /**
     * 评估神经网络的性能
     * @param network 要评估的神经网络
     * @return 性能指标
     */
    double evaluate(NeuralNetwork network);
}
