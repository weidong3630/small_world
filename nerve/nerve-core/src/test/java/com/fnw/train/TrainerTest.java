package com.fnw.train;

import com.fnw.model.NeuralNetwork;
import com.fnw.model.Neuron;
import com.fnw.model.Edge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 训练框架测试类
 */
public class TrainerTest {
    
    @Test
    public void testTrainerFactory() {
        // 测试创建基础训练器
        Trainer baseTrainer = TrainerFactory.createTrainer(TrainerFactory.TrainerType.BASE);
        assertNotNull(baseTrainer);
        assertTrue(baseTrainer instanceof BaseTrainer);
        
        // 测试创建Hebb训练器
        Trainer hebbTrainer = TrainerFactory.createTrainer(TrainerFactory.TrainerType.HEBBIAN);
        assertNotNull(hebbTrainer);
        assertTrue(hebbTrainer instanceof HebbianTrainer);
        
        // 测试带学习率参数的训练器创建
        Trainer baseTrainerWithLR = TrainerFactory.createTrainer(TrainerFactory.TrainerType.BASE, 0.05);
        assertNotNull(baseTrainerWithLR);
        assertTrue(baseTrainerWithLR instanceof BaseTrainer);
        
        Trainer hebbTrainerWithLR = TrainerFactory.createTrainer(TrainerFactory.TrainerType.HEBBIAN, 0.05);
        assertNotNull(hebbTrainerWithLR);
        assertTrue(hebbTrainerWithLR instanceof HebbianTrainer);
    }
    
    @Test
    public void testBaseTrainer() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        
        // 添加神经元到网络
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        
        // 创建边并添加到网络
        Edge edge = new Edge(neuron1, neuron2, Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.SIMILARITY);
        network.addEdge(edge);
        
        // 创建基础训练器
        BaseTrainer trainer = new BaseTrainer(0.01);
        
        // 测试训练前评估
        double initialScore = trainer.evaluate(network);
        assertTrue(initialScore >= 0.0 && initialScore <= 1.0);
        
        // 测试单轮训练
        trainer.trainEpoch(network);
        
        // 测试训练后评估
        double afterScore = trainer.evaluate(network);
        assertTrue(afterScore >= 0.0 && afterScore <= 1.0);
        
        // 测试多轮训练
        trainer.train(network, 10);
    }
    
    @Test
    public void testHebbianTrainer() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        
        // 添加神经元到网络
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        
        // 创建边并添加到网络（初始权重为0.5）
        Edge edge = new Edge(neuron1, neuron2, Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.SIMILARITY, 0.5);
        network.addEdge(edge);
        
        // 创建Hebb训练器
        HebbianTrainer trainer = new HebbianTrainer(0.1);
        
        // 测试训练前评估
        double initialScore = trainer.evaluate(network);
        assertEquals(0.5, initialScore, 0.001);
        
        // 激活两个神经元
        neuron1.setActivated(true);
        neuron2.setActivated(true);
        
        // 训练一轮
        trainer.trainEpoch(network);
        
        // 验证权重是否增加
        Object newWeight = edge.getInformation();
        assertTrue(newWeight instanceof Number);
        assertTrue(((Number) newWeight).doubleValue() > 0.5);
        
        // 测试评估
        double afterScore = trainer.evaluate(network);
        assertTrue(afterScore > 0.5);
    }
    
    @Test
    public void testHebbianTrainerWeightUpdate() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        
        // 添加神经元到网络
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        
        // 创建边并添加到网络（初始权重为0.0）
        Edge edge = new Edge(neuron1, neuron2, Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.SIMILARITY, 0.0);
        network.addEdge(edge);
        
        // 创建Hebb训练器
        HebbianTrainer trainer = new HebbianTrainer(0.1);
        
        // 测试情况1：两个神经元都被激活
        neuron1.setActivated(true);
        neuron2.setActivated(true);
        trainer.trainEpoch(network);
        
        double weight1 = ((Number) edge.getInformation()).doubleValue();
        assertEquals(0.1, weight1, 0.001);
        
        // 测试情况2：只有起始神经元被激活
        neuron1.setActivated(true);
        neuron2.setActivated(false);
        trainer.trainEpoch(network);
        
        double weight2 = ((Number) edge.getInformation()).doubleValue();
        assertEquals(0.05, weight2, 0.001); // 0.1 - 0.1 * 0.5
        
        // 测试情况3：两个神经元都没有被激活
        neuron1.setActivated(false);
        neuron2.setActivated(false);
        trainer.trainEpoch(network);
        
        double weight3 = ((Number) edge.getInformation()).doubleValue();
        assertEquals(0.04, weight3, 0.001); // 0.05 - 0.1 * 0.1
    }
    
    @Test
    public void testTrainingWithDataset() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        
        // 添加神经元到网络
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        
        // 创建边并添加到网络（初始权重为0.0）
        Edge edge = new Edge(neuron1, neuron2, Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.SIMILARITY, 0.0);
        network.addEdge(edge);
        
        // 创建训练数据集
        Dataset dataset = SimpleDataset.createExampleDataset();
        
        // 创建Hebb训练器
        HebbianTrainer trainer = new HebbianTrainer(0.1);
        
        // 测试训练前评估
        double initialScore = trainer.evaluate(network);
        assertEquals(0.0, initialScore, 0.001);
        
        // 使用数据集训练
        trainer.train(network, dataset, 10);
        
        // 测试训练后评估
        double afterScore = trainer.evaluate(network);
        assertNotEquals(0.0, afterScore);
    }
    
    @Test
    public void testTrainingWithInformationUnitDataset() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        for (int i = 1; i <= 3; i++) {
            network.addNeuron(new Neuron(String.valueOf(i), "神经元" + i));
        }
        
        // 创建边并添加到网络（初始权重为0.0）
        Edge edge1 = new Edge(network.getNeuron("1"), network.getNeuron("2"), 
                             Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, 0.0);
        Edge edge2 = new Edge(network.getNeuron("1"), network.getNeuron("3"), 
                             Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.SIMILARITY, 0.0);
        network.addEdge(edge1);
        network.addEdge(edge2);
        
        // 创建信息单位数据集
        InformationUnitDataset dataset = InformationUnitDataset.createExampleDataset();
        
        // 创建Hebb训练器
        HebbianTrainer trainer = new HebbianTrainer(0.1);
        
        // 测试训练前评估
        double initialScore = trainer.evaluate(network);
        assertEquals(0.0, initialScore, 0.001);
        
        // 使用数据集训练
        trainer.train(network, dataset, 10);
        
        // 测试训练后评估
        double afterScore = trainer.evaluate(network);
        // 训练后的评估值应该与训练前不同
        assertNotEquals(initialScore, afterScore, 0.001);
    }
    
    @Test
    public void testSimpleDataset() {
        // 创建数据集
        SimpleDataset dataset = new SimpleDataset();
        
        // 创建样本数据
        new java.util.HashMap<String, Boolean>() {{
            put("1", true);
            put("2", false);
        }};
        
        // 测试数据集大小
        assertEquals(0, dataset.size());
        
        // 添加样本
        dataset.addSample(new java.util.HashMap<String, Boolean>() {{
            put("1", true);
            put("2", false);
        }});
        
        // 测试数据集大小
        assertEquals(1, dataset.size());
        
        // 测试获取样本
        java.util.Map<String, Boolean> sample = dataset.getSample(0);
        assertNotNull(sample);
        assertTrue(sample.get("1"));
        assertFalse(sample.get("2"));
        
        // 测试创建示例数据集
        SimpleDataset exampleDataset = SimpleDataset.createExampleDataset();
        assertTrue(exampleDataset.size() > 0);
    }
}
