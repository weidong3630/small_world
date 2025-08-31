package com.fnw.train;

import com.fnw.model.NeuralNetwork;
import com.fnw.model.Neuron;
import com.fnw.model.Edge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 信息单位数据集训练测试类
 * 演示如何使用InformationUnitDataset进行训练，并生成新的数据集
 */
public class InformationUnitTrainingTest {
    
    @Test
    public void testTrainingWithInformationUnitDataset() {
        System.out.println("=== 信息单位数据集训练测试 ===");
        
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元（ID 1-9，对应示例数据集中的神经元）
        for (int i = 1; i <= 9; i++) {
            network.addNeuron(new Neuron(String.valueOf(i), null));
        }
        
        // 使用新方法录入信息到神经元
        network.storeInformation("1", "狗");
        network.storeInformation("2", "哺乳动物");
        network.storeInformation("3", "宠物");
        network.storeInformation("4", "玫瑰");
        network.storeInformation("5", "花");
        network.storeInformation("6", "植物");
        network.storeInformation("7", "苹果");
        network.storeInformation("8", "水果");
        network.storeInformation("9", "红色");
        
        // 使用新方法创建连接
        network.createConnection("1", "2", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("1", "3", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是");
        network.createConnection("4", "5", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("4", "6", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("7", "8", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("7", "9", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "具有颜色");
        
        // 创建信息单位数据集
        InformationUnitDataset dataset = InformationUnitDataset.createExampleDataset();
        
        System.out.println("数据集大小: " + dataset.size());
        for (int i = 0; i < dataset.size(); i++) {
            InformationUnitDataset.Sample sample = dataset.getInformationSample(i);
            System.out.println("样本 " + (i + 1) + " 信息单位数量: " + sample.getInformationUnits().size());
            System.out.println("样本 " + (i + 1) + " 关系数量: " + sample.getRelations().size());
        }
        
        // 创建Hebb训练器
        HebbianTrainer trainer = new HebbianTrainer(0.05);
        
        // 测试训练前评估
        double initialScore = trainer.evaluate(network);
        System.out.println("训练前评估: " + initialScore);
        
        // 显示训练前的边权重
        System.out.println("训练前边权重:");
        for (Edge edge : network.getEdges()) {
            Object weight = edge.getInformation();
            double weightValue = 0.0;
            if (weight instanceof Number) {
                weightValue = ((Number) weight).doubleValue();
            }
            System.out.println("  " + edge.getFromNeuron().getId() + "->" + edge.getToNeuron().getId() + 
                             " 权重: " + weightValue);
        }
        
        // 使用数据集训练
        trainer.train(network, dataset, 50);
        
        // 测试训练后评估
        double afterScore = trainer.evaluate(network);
        System.out.println("训练后评估: " + afterScore);
        
        // 显示训练后的边权重
        System.out.println("训练后边权重:");
        for (Edge edge : network.getEdges()) {
            Object weight = edge.getInformation();
            double weightValue = 0.0;
            if (weight instanceof Number) {
                weightValue = ((Number) weight).doubleValue();
            }
            System.out.println("  " + edge.getFromNeuron().getId() + "->" + edge.getToNeuron().getId() + 
                             " 权重: " + weightValue);
        }
        
        // 验证训练效果
        assertNotEquals(initialScore, afterScore, 0.001);
        
        // 验证边权重已更新
        for (Edge edge : network.getEdges()) {
            Object weight = edge.getInformation();
            assertTrue(weight instanceof Number);
            double weightValue = ((Number) weight).doubleValue();
            // 权重应该在合理范围内
            assertTrue(weightValue >= -1.0 && weightValue <= 1.0);
        }
    }
    
    @Test
    public void testGenerateNewInformationUnitDataset() {
        System.out.println("\n=== 生成新的信息单位数据集测试 ===");
        
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        for (int i = 1; i <= 6; i++) {
            network.addNeuron(new Neuron(String.valueOf(i), null));
        }
        
        // 录入信息
        network.storeInformation("1", "猫");
        network.storeInformation("2", "哺乳动物");
        network.storeInformation("3", "宠物");
        network.storeInformation("4", "鸟");
        network.storeInformation("5", "动物");
        network.storeInformation("6", "会飞");
        
        // 创建连接
        network.createConnection("1", "2", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("1", "3", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是");
        network.createConnection("4", "5", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("4", "6", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "具有特性");
        
        // 创建训练数据集
        InformationUnitDataset trainingDataset = new InformationUnitDataset();
        
        // 添加样本1: 猫的信息
        InformationUnitDataset.Sample sample1 = new InformationUnitDataset.Sample();
        sample1.addInformationUnit("1", "猫");
        sample1.addInformationUnit("2", "哺乳动物");
        sample1.addInformationUnit("3", "宠物");
        sample1.addRelation("1", "2", "是一种");
        sample1.addRelation("1", "3", "是");
        sample1.setNeuronActivation("1", true);
        sample1.setNeuronActivation("2", true);
        sample1.setNeuronActivation("3", true);
        trainingDataset.addSample(sample1);
        
        // 添加样本2: 鸟的信息
        InformationUnitDataset.Sample sample2 = new InformationUnitDataset.Sample();
        sample2.addInformationUnit("4", "鸟");
        sample2.addInformationUnit("5", "动物");
        sample2.addInformationUnit("6", "会飞");
        sample2.addRelation("4", "5", "是一种");
        sample2.addRelation("4", "6", "具有特性");
        sample2.setNeuronActivation("4", true);
        sample2.setNeuronActivation("5", true);
        sample2.setNeuronActivation("6", true);
        trainingDataset.addSample(sample2);
        
        System.out.println("训练数据集大小: " + trainingDataset.size());
        
        // 训练神经网络
        HebbianTrainer trainer = new HebbianTrainer(0.1);
        trainer.train(network, trainingDataset, 20);
        
        System.out.println("训练完成，开始生成新的数据集...");
        
        // 基于训练后的网络生成新的数据集
        InformationUnitDataset generatedDataset = generateDatasetFromNetwork(network, 3);
        
        System.out.println("生成的数据集大小: " + generatedDataset.size());
        
        // 验证生成的数据集
        assertEquals(3, generatedDataset.size());
        
        // 验证生成的样本
        for (int i = 0; i < generatedDataset.size(); i++) {
            InformationUnitDataset.Sample sample = generatedDataset.getInformationSample(i);
            assertNotNull(sample);
            assertFalse(sample.getInformationUnits().isEmpty());
            System.out.println("生成的样本 " + (i + 1) + ":");
            System.out.println(sample.toString());
        }
    }
    
    /**
     * 基于训练后的神经网络生成新的数据集
     * @param network 训练后的神经网络
     * @param sampleCount 要生成的样本数量
     * @return 生成的数据集
     */
    private InformationUnitDataset generateDatasetFromNetwork(NeuralNetwork network, int sampleCount) {
        InformationUnitDataset generatedDataset = new InformationUnitDataset();
        
        // 获取所有神经元
        java.util.Collection<Neuron> neurons = network.getNeurons();
        java.util.List<Neuron> neuronList = new java.util.ArrayList<>(neurons);
        
        // 获取所有边
        java.util.List<Edge> edges = network.getEdges();
        
        // 生成指定数量的样本
        for (int i = 0; i < sampleCount; i++) {
            InformationUnitDataset.Sample sample = new InformationUnitDataset.Sample();
            
            // 随机选择一些神经元作为信息单位
            java.util.Random random = new java.util.Random();
            int neuronCount = Math.min(3, neuronList.size()); // 每个样本最多3个信息单位
            java.util.Set<Integer> selectedIndices = new java.util.HashSet<>();
            
            for (int j = 0; j < neuronCount; j++) {
                int index;
                do {
                    index = random.nextInt(neuronList.size());
                } while (selectedIndices.contains(index));
                selectedIndices.add(index);
                
                Neuron neuron = neuronList.get(index);
                if (neuron.getInformation() != null) {
                    sample.addInformationUnit(neuron.getId(), neuron.getInformation().toString());
                }
            }
            
            // 基于边的关系添加关系
            int relationCount = Math.min(2, edges.size()); // 每个样本最多2个关系
            java.util.Set<Integer> selectedEdgeIndices = new java.util.HashSet<>();
            
            for (int j = 0; j < relationCount; j++) {
                int index;
                do {
                    index = random.nextInt(edges.size());
                } while (selectedEdgeIndices.contains(index));
                selectedEdgeIndices.add(index);
                
                Edge edge = edges.get(index);
                if (edge.getInformation() != null) {
                    sample.addRelation(
                        edge.getFromNeuron().getId(), 
                        edge.getToNeuron().getId(), 
                        edge.getInformation().toString()
                    );
                }
            }
            
            // 设置随机的神经元激活状态
            for (Neuron neuron : neuronList) {
                sample.setNeuronActivation(neuron.getId(), random.nextBoolean());
            }
            
            generatedDataset.addSample(sample);
        }
        
        return generatedDataset;
    }
}
