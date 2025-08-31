package com.fnw.train;

import com.fnw.model.NeuralNetwork;
import com.fnw.model.Neuron;
import com.fnw.model.Edge;

/**
 * 训练框架使用示例
 * 演示如何使用训练框架来训练神经网络
 */
public class TrainingExample {
    
    public static void main(String[] args) {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 使用新方法创建神经元并录入信息
        Neuron neuron1 = new Neuron("1", null);
        Neuron neuron2 = new Neuron("2", null);
        Neuron neuron3 = new Neuron("3", null);
        
        // 添加神经元到网络
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        network.addNeuron(neuron3);
        
        // 使用新方法录入信息
        network.storeInformation("1", "输入神经元");
        network.storeInformation("2", "处理神经元");
        network.storeInformation("3", "输出神经元");
        
        // 使用新方法创建连接
        network.createConnection("1", "2", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, 0.5);
        network.createConnection("2", "3", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, 0.5);
        network.createConnection("1", "3", Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.SIMILARITY, 0.3);
        
        System.out.println("=== 神经网络创建完成 ===");
        System.out.println("神经元数量: " + network.getNeurons().size());
        System.out.println("连接数量: " + network.getEdges().size());
        
        // 显示神经元信息
        System.out.println("\n神经元信息:");
        for (Neuron neuron : network.getNeurons()) {
            System.out.println("神经元 " + neuron.getId() + ": " + neuron.getInformation());
        }
        
        // 显示连接信息
        System.out.println("\n连接信息:");
        for (Edge edge : network.getEdges()) {
            System.out.println("连接 " + edge.getFromNeuron().getId() + "->" + edge.getToNeuron().getId() + 
                             " 类型: " + edge.getRelationshipType() + 
                             " 方向: " + edge.getDirection() + 
                             " 信息: " + edge.getInformation());
        }
        
        // 使用工厂创建Hebb训练器
        Trainer trainer = TrainerFactory.createTrainer(TrainerFactory.TrainerType.HEBBIAN, 0.05);
        
        System.out.println("\n=== 使用随机数据训练 ===");
        System.out.println("开始训练神经网络...");
        System.out.println("训练前评估: " + trainer.evaluate(network));
        
        // 模拟一些训练数据
        for (int epoch = 0; epoch < 5; epoch++) {
            // 模拟输入数据，激活相应的神经元
            if (epoch % 2 == 0) {
                neuron1.setActivated(true);
                neuron2.setActivated(true);
                neuron3.setActivated(false);
            } else {
                neuron1.setActivated(false);
                neuron2.setActivated(false);
                neuron3.setActivated(true);
            }
            
            // 训练一轮
            trainer.trainEpoch(network);
            
            System.out.println("第 " + (epoch + 1) + " 轮训练后评估: " + trainer.evaluate(network));
        }
        
        // 长时间训练
        System.out.println("\n开始长时间训练...");
        trainer.train(network, 1000);
        
        System.out.println("训练完成!");
        System.out.println("最终评估: " + trainer.evaluate(network));
        
        // 输出最终的边权重
        System.out.println("\n最终边权重:");
        for (Edge edge : network.getEdges()) {
            Object weight = edge.getInformation();
            if (weight instanceof Number) {
                System.out.println("边 " + edge.getFromNeuron().getId() + "->" + edge.getToNeuron().getId() + 
                                 " 权重: " + ((Number) weight).doubleValue());
            }
        }
        
        // 创建新的神经网络以演示使用外部数据训练
        NeuralNetwork network2 = new NeuralNetwork();
        
        // 使用新方法创建神经元并录入信息
        Neuron neuron4 = new Neuron("4", null);
        Neuron neuron5 = new Neuron("5", null);
        network2.addNeuron(neuron4);
        network2.addNeuron(neuron5);
        
        // 使用新方法录入信息
        network2.storeInformation("4", "输入神经元2");
        network2.storeInformation("5", "输出神经元2");
        
        // 使用新方法创建连接
        network2.createConnection("4", "5", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, 0.5);
        
        System.out.println("\n=== 使用外部数据训练 ===");
        
        // 创建训练数据集
        Dataset dataset = SimpleDataset.createExampleDataset();
        
        System.out.println("数据集大小: " + dataset.size());
        System.out.println("训练前评估: " + trainer.evaluate(network2));
        
        // 使用数据集进行训练
        trainer.train(network2, dataset, 100);
        
        System.out.println("使用外部数据训练完成!");
        System.out.println("最终评估: " + trainer.evaluate(network2));
        
        // 输出最终的边权重
        System.out.println("\n最终边权重:");
        for (Edge edge : network2.getEdges()) {
            Object weight = edge.getInformation();
            if (weight instanceof Number) {
                System.out.println("边 " + edge.getFromNeuron().getId() + "->" + edge.getToNeuron().getId() + 
                                 " 权重: " + ((Number) weight).doubleValue());
            }
        }
    }
}
