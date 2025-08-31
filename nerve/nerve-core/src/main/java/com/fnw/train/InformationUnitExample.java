package com.fnw.train;

import com.fnw.model.NeuralNetwork;
import com.fnw.model.Neuron;
import com.fnw.model.Edge;

/**
 * 信息单位数据集使用示例
 * 演示如何使用包含信息单位及其关系的数据集
 */
public class InformationUnitExample {
    
    public static void main(String[] args) {
        System.out.println("=== 信息单位数据集示例 ===");
        
        // 创建示例数据集
        InformationUnitDataset dataset = InformationUnitDataset.createExampleDataset();
        
        System.out.println("数据集大小: " + dataset.size());
        
        // 显示所有样本
        for (int i = 0; i < dataset.size(); i++) {
            InformationUnitDataset.Sample sample = dataset.getInformationSample(i);
            System.out.println("\n样本 " + (i + 1) + ":");
            System.out.println(sample.toString());
        }
        
        // 演示如何创建自定义样本
        System.out.println("\n=== 创建自定义样本 ===");
        InformationUnitDataset.Sample customSample = new InformationUnitDataset.Sample();
        
        // 添加信息单位
        customSample.addInformationUnit("10", "汽车");
        customSample.addInformationUnit("11", "交通工具");
        customSample.addInformationUnit("12", "轮子");
        
        // 添加关系
        customSample.addRelation("10", "11", "是一种");
        customSample.addRelation("10", "12", "具有");
        
        // 设置神经元激活状态
        customSample.setNeuronActivation("10", true);
        customSample.setNeuronActivation("11", true);
        customSample.setNeuronActivation("12", false);
        
        System.out.println("自定义样本:");
        System.out.println(customSample.toString());
        
        // 将自定义样本添加到数据集
        dataset.addSample(customSample);
        System.out.println("\n添加自定义样本后的数据集大小: " + dataset.size());
        
        // 演示如何使用数据集训练神经网络
        System.out.println("\n=== 使用信息单位数据集训练神经网络 ===");
        
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元（基于数据集中的信息单位）
        for (int i = 1; i <= 12; i++) {
            network.addNeuron(new Neuron(String.valueOf(i), null));
        }
        
        // 录入信息到神经元
        InformationUnitDataset.Sample sample1 = dataset.getInformationSample(0);
        for (String neuronId : sample1.getInformationUnits().keySet()) {
            network.storeInformation(neuronId, sample1.getInformationUnits().get(neuronId));
        }
        
        // 显示神经元信息
        System.out.println("神经元信息:");
        for (String neuronId : sample1.getInformationUnits().keySet()) {
            Neuron neuron = network.getNeuron(neuronId);
            if (neuron != null) {
                System.out.println("  神经元 " + neuronId + ": " + neuron.getInformation());
            }
        }
        
        // 创建连接（基于数据集中的关系）
        for (InformationUnitDataset.Relation relation : sample1.getRelations()) {
            boolean result = network.createConnection(
                relation.getFromNeuronId(), 
                relation.getToNeuronId(), 
                Edge.Direction.UNIDIRECTIONAL, 
                Edge.RelationshipType.CAUSALITY, 
                relation.getDescription()
            );
            if (result) {
                System.out.println("  创建连接: " + relation.toString());
            }
        }
        
        // 显示连接信息
        System.out.println("\n连接信息:");
        for (Edge edge : network.getEdges()) {
            System.out.println("  " + edge.getFromNeuron().getId() + " -> " + edge.getToNeuron().getId() + 
                             " (" + edge.getInformation() + ")");
        }
        
        // 使用Hebb训练器训练
        Trainer trainer = TrainerFactory.createTrainer(TrainerFactory.TrainerType.HEBBIAN, 0.1);
        
        System.out.println("\n训练前评估: " + trainer.evaluate(network));
        
        // 使用数据集训练
        trainer.train(network, dataset, 100);
        
        System.out.println("训练后评估: " + trainer.evaluate(network));
        
        // 显示训练后的连接权重
        System.out.println("\n训练后的连接权重:");
        for (Edge edge : network.getEdges()) {
            Object weight = edge.getInformation();
            if (weight instanceof Number) {
                System.out.println("  " + edge.getFromNeuron().getId() + " -> " + edge.getToNeuron().getId() + 
                                 " 权重: " + ((Number) weight).doubleValue());
            }
        }
    }
}
