package com.fnw.train;

import com.fnw.model.NeuralNetwork;
import com.fnw.model.Neuron;
import com.fnw.model.Edge;

/**
 * 信息单位数据集训练示例
 * 演示如何使用InformationUnitDataset进行训练，并生成新的数据集
 */
public class InformationUnitTrainingExample {
    
    public static void main(String[] args) {
        System.out.println("=== 信息单位数据集训练示例 ===");
        
        // 步骤1: 创建神经网络
        NeuralNetwork network = createNeuralNetwork();
        
        // 步骤2: 创建训练数据集
        InformationUnitDataset trainingDataset = createTrainingDataset();
        
        // 步骤3: 显示训练前的网络状态
        System.out.println("\n--- 训练前的网络状态 ---");
        displayNetworkState(network);
        
        // 步骤4: 使用数据集训练神经网络
        System.out.println("\n--- 开始训练 ---");
        trainNetwork(network, trainingDataset);
        
        // 步骤5: 显示训练后的网络状态
        System.out.println("\n--- 训练后的网络状态 ---");
        displayNetworkState(network);
        
        // 步骤6: 基于训练后的网络生成新的数据集
        System.out.println("\n--- 生成新的数据集 ---");
        InformationUnitDataset generatedDataset = generateDatasetFromTrainedNetwork(network, 5);
        
        // 步骤7: 显示生成的数据集
        System.out.println("\n--- 生成的数据集内容 ---");
        displayDataset(generatedDataset);
        
        System.out.println("\n=== 训练和生成完成 ===");
    }
    
    /**
     * 创建神经网络
     * @return 创建的神经网络
     */
    private static NeuralNetwork createNeuralNetwork() {
        System.out.println("创建神经网络...");
        
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元（ID 1-9）
        for (int i = 1; i <= 9; i++) {
            network.addNeuron(new Neuron(String.valueOf(i), null));
        }
        
        // 录入信息到神经元
        network.storeInformation("1", "狗");
        network.storeInformation("2", "哺乳动物");
        network.storeInformation("3", "宠物");
        network.storeInformation("4", "玫瑰");
        network.storeInformation("5", "花");
        network.storeInformation("6", "植物");
        network.storeInformation("7", "苹果");
        network.storeInformation("8", "水果");
        network.storeInformation("9", "红色");
        
        // 创建连接
        network.createConnection("1", "2", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("1", "3", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是");
        network.createConnection("4", "5", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("4", "6", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("7", "8", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "是一种");
        network.createConnection("7", "9", Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "具有颜色");
        
        System.out.println("神经网络创建完成，包含 " + network.getNeurons().size() + " 个神经元和 " + network.getEdges().size() + " 个连接");
        return network;
    }
    
    /**
     * 创建训练数据集
     * @return 创建的训练数据集
     */
    private static InformationUnitDataset createTrainingDataset() {
        System.out.println("创建训练数据集...");
        
        // 使用现有的示例数据集
        InformationUnitDataset dataset = InformationUnitDataset.createExampleDataset();
        
        System.out.println("训练数据集创建完成，包含 " + dataset.size() + " 个样本");
        return dataset;
    }
    
    /**
     * 训练神经网络
     * @param network 要训练的神经网络
     * @param dataset 训练数据集
     */
    private static void trainNetwork(NeuralNetwork network, InformationUnitDataset dataset) {
        // 创建Hebb训练器
        HebbianTrainer trainer = new HebbianTrainer(0.05);
        
        // 显示训练前评估
        double initialScore = trainer.evaluate(network);
        System.out.println("训练前评估: " + initialScore);
        
        // 训练网络
        System.out.println("开始训练，共 30 轮...");
        trainer.train(network, dataset, 30);
        
        // 显示训练后评估
        double finalScore = trainer.evaluate(network);
        System.out.println("训练后评估: " + finalScore);
        System.out.println("评估值变化: " + (finalScore - initialScore));
    }
    
    /**
     * 显示神经网络状态
     * @param network 要显示的神经网络
     */
    private static void displayNetworkState(NeuralNetwork network) {
        System.out.println("神经元信息:");
        for (Neuron neuron : network.getNeurons()) {
            if (neuron.getInformation() != null) {
                System.out.println("  神经元 " + neuron.getId() + ": " + neuron.getInformation());
            }
        }
        
        System.out.println("连接信息:");
        for (Edge edge : network.getEdges()) {
            Object weight = edge.getInformation();
            double weightValue = 0.0;
            if (weight instanceof Number) {
                weightValue = ((Number) weight).doubleValue();
            }
            System.out.println("  " + edge.getFromNeuron().getId() + " -> " + edge.getToNeuron().getId() + 
                             " (" + edge.getInformation() + ") 权重: " + weightValue);
        }
    }
    
    /**
     * 基于训练后的网络生成新的数据集
     * @param network 训练后的神经网络
     * @param sampleCount 要生成的样本数量
     * @return 生成的数据集
     */
    private static InformationUnitDataset generateDatasetFromTrainedNetwork(NeuralNetwork network, int sampleCount) {
        System.out.println("基于训练后的网络生成 " + sampleCount + " 个新样本...");
        
        InformationUnitDataset generatedDataset = new InformationUnitDataset();
        
        // 获取所有神经元
        java.util.Collection<Neuron> neurons = network.getNeurons();
        java.util.List<Neuron> neuronList = new java.util.ArrayList<>(neurons);
        
        // 获取所有边
        java.util.List<Edge> edges = network.getEdges();
        
        // 生成指定数量的样本
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < sampleCount; i++) {
            InformationUnitDataset.Sample sample = new InformationUnitDataset.Sample();
            
            // 随机选择一些神经元作为信息单位
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
        
        System.out.println("新数据集生成完成");
        return generatedDataset;
    }
    
    /**
     * 显示数据集内容
     * @param dataset 要显示的数据集
     */
    private static void displayDataset(InformationUnitDataset dataset) {
        System.out.println("数据集大小: " + dataset.size());
        
        for (int i = 0; i < dataset.size(); i++) {
            InformationUnitDataset.Sample sample = dataset.getInformationSample(i);
            System.out.println("\n样本 " + (i + 1) + ":");
            System.out.println(sample.toString());
        }
    }
}
