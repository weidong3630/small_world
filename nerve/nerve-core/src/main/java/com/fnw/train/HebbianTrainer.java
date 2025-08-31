package com.fnw.train;

import com.fnw.model.NeuralNetwork;
import com.fnw.model.Neuron;
import com.fnw.model.Edge;

import java.util.List;
import java.util.Map;

/**
 * Hebb学习规则训练器
 * 基于Hebb学习规则来训练神经网络
 * Hebb规则："一起激活的神经元会连接在一起"
 */
public class HebbianTrainer extends BaseTrainer {
    
    public HebbianTrainer() {
        super();
    }
    
    public HebbianTrainer(double learningRate) {
        super(learningRate);
    }
    
    @Override
    public void trainEpoch(NeuralNetwork network) {
        // 应用Hebb学习规则
        // 对于每条边，如果连接的两个神经元都被激活，则增强边的权重
        // 如果只有一个神经元被激活，则减弱边的权重
        
        for (Edge edge : network.getEdges()) {
            Neuron fromNeuron = edge.getFromNeuron();
            Neuron toNeuron = edge.getToNeuron();
            
            // 根据Hebb学习规则更新边的权重
            updateEdgeWeight(edge, fromNeuron, toNeuron);
        }
        
        // 可以在这里添加其他训练逻辑
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
            
            // 应用Hebb学习规则
            for (Edge edge : network.getEdges()) {
                Neuron fromNeuron = edge.getFromNeuron();
                Neuron toNeuron = edge.getToNeuron();
                
                // 根据Hebb学习规则更新边的权重
                updateEdgeWeight(edge, fromNeuron, toNeuron);
            }
        }
    }
    
    /**
     * 根据Hebb学习规则更新边的权重
     * @param edge 要更新的边
     * @param fromNeuron 起始神经元
     * @param toNeuron 目标神经元
     */
    private void updateEdgeWeight(Edge edge, Neuron fromNeuron, Neuron toNeuron) {
        // 获取当前边的信息（在实际实现中，这可能是一个权重值）
        Object currentInfo = edge.getInformation();
        double weight = 0.0;
        
        // 如果当前信息是数字类型，将其转换为权重
        if (currentInfo instanceof Number) {
            weight = ((Number) currentInfo).doubleValue();
        }
        
        // 应用Hebb学习规则
        if (fromNeuron.isActivated() && toNeuron.isActivated()) {
            // 两个神经元都被激活，增强连接
            weight += learningRate;
        } else if (fromNeuron.isActivated() || toNeuron.isActivated()) {
            // 只有一个神经元被激活，减弱连接
            weight -= learningRate * 0.5;
        } else {
            // 两个神经元都没有被激活，轻微减弱连接
            weight -= learningRate * 0.1;
        }
        
        // 确保权重在合理范围内
        weight = Math.max(-1.0, Math.min(1.0, weight));
        
        // 更新边的信息
        edge.setInformation(weight);
    }
    
    @Override
    public double evaluate(NeuralNetwork network) {
        // 计算所有边的平均权重作为评估指标
        List<Edge> edges = network.getEdges();
        if (edges.isEmpty()) {
            return 0.0;
        }
        
        double totalWeight = 0.0;
        int validEdges = 0;
        
        for (Edge edge : edges) {
            Object info = edge.getInformation();
            if (info instanceof Number) {
                totalWeight += ((Number) info).doubleValue();
                validEdges++;
            }
        }
        
        return validEdges > 0 ? totalWeight / validEdges : 0.0;
    }
}
