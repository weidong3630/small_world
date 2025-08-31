package com.fnw.train;

import com.fnw.model.NeuralNetwork;
import com.fnw.model.Neuron;
import com.fnw.model.Edge;

import java.util.Map;
import java.util.Random;

/**
 * 基础训练器实现
 * 提供训练神经网络的基本功能
 */
public class BaseTrainer implements Trainer {
    
    // 随机数生成器
    protected Random random;
    
    // 学习率
    protected double learningRate;
    
    public BaseTrainer() {
        this.random = new Random();
        this.learningRate = 0.01;
    }
    
    public BaseTrainer(double learningRate) {
        this.random = new Random();
        this.learningRate = learningRate;
    }
    
    @Override
    public void train(NeuralNetwork network, int epochs) {
        for (int i = 0; i < epochs; i++) {
            trainEpoch(network);
            
            // 每100轮输出一次训练信息
            if (i % 100 == 0) {
                double score = evaluate(network);
                System.out.println("Epoch: " + i + ", Score: " + score);
            }
        }
    }
    
    @Override
    public void train(NeuralNetwork network, Dataset dataset, int epochs) {
        for (int i = 0; i < epochs; i++) {
            trainEpoch(network, dataset);
            
            // 每100轮输出一次训练信息
            if (i % 100 == 0) {
                double score = evaluate(network);
                System.out.println("Epoch: " + i + ", Score: " + score);
            }
        }
    }
    
    @Override
    public void trainEpoch(NeuralNetwork network) {
        // 在基础实现中，我们随机激活一些神经元
        // 在更复杂的实现中，这里会包含实际的训练逻辑
        for (Neuron neuron : network.getNeurons()) {
            // 以一定的概率激活神经元
            if (random.nextDouble() < 0.1) {
                neuron.setActivated(true);
            } else {
                neuron.setActivated(false);
            }
        }
        
        // 调整边的权重（在基础实现中，我们只是随机调整）
        for (Edge edge : network.getEdges()) {
            // 这里可以添加调整边权重的逻辑
            // 在实际实现中，会根据神经元的激活状态和学习规则来调整
        }
    }
    
    @Override
    public void trainEpoch(NeuralNetwork network, Dataset dataset) {
        // 如果数据集为空，使用随机训练
        if (dataset == null || dataset.size() == 0) {
            trainEpoch(network);
            return;
        }
        
        // 遍历数据集中的所有样本
        for (int i = 0; i < dataset.size(); i++) {
            Map<String, Boolean> sample = dataset.getSample(i);
            
            // 根据样本数据设置神经元的激活状态
            for (Map.Entry<String, Boolean> entry : sample.entrySet()) {
                Neuron neuron = network.getNeuron(entry.getKey());
                if (neuron != null) {
                    neuron.setActivated(entry.getValue());
                }
            }
            
            // 应用训练逻辑（在基础实现中，我们只是随机调整）
            for (Edge edge : network.getEdges()) {
                // 这里可以添加调整边权重的逻辑
                // 在实际实现中，会根据神经元的激活状态和学习规则来调整
            }
        }
    }
    
    @Override
    public double evaluate(NeuralNetwork network) {
        // 基础评估函数，计算激活神经元的比例
        int totalNeurons = network.getNeurons().size();
        if (totalNeurons == 0) {
            return 0.0;
        }
        
        int activatedNeurons = 0;
        for (Neuron neuron : network.getNeurons()) {
            if (neuron.isActivated()) {
                activatedNeurons++;
            }
        }
        
        return (double) activatedNeurons / totalNeurons;
    }
    
    // Getter and Setter methods
    public double getLearningRate() {
        return learningRate;
    }
    
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}
