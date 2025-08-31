package com.fnw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 神经网络模型测试类
 */
public class NeuralNetworkTest {
    
    @Test
    public void testNeuronCreation() {
        // 创建神经元
        Neuron neuron = new Neuron("1", "测试信息");
        
        // 验证神经元属性
        assertEquals("1", neuron.getId());
        assertEquals("测试信息", neuron.getInformation());
        assertFalse(neuron.isActivated());
        
        // 验证激活功能
        neuron.setActivated(true);
        assertTrue(neuron.isActivated());
    }
    
    @Test
    public void testEdgeCreation() {
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        
        // 创建边
        Edge edge = new Edge(neuron1, neuron2, Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.ASSOCIATION, "连接信息");
        
        // 验证边属性
        assertEquals(neuron1, edge.getFromNeuron());
        assertEquals(neuron2, edge.getToNeuron());
        assertEquals(Edge.Direction.UNIDIRECTIONAL, edge.getDirection());
        assertEquals(Edge.RelationshipType.ASSOCIATION, edge.getRelationshipType());
        assertEquals("连接信息", edge.getInformation());
    }
    
    @Test
    public void testNeuralNetwork() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        
        // 添加神经元到网络
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        
        // 验证神经元添加
        assertEquals(2, network.getNeurons().size());
        assertEquals(neuron1, network.getNeuron("1"));
        assertEquals(neuron2, network.getNeuron("2"));
        
        // 创建边并添加到网络
        Edge edge = new Edge(neuron1, neuron2, Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.SIMILARITY);
        network.addEdge(edge);
        
        // 验证边添加
        assertEquals(1, network.getEdges().size());
        assertEquals(edge, network.getEdges().get(0));
        
        // 验证神经元激活功能
        assertFalse(neuron1.isActivated());
        network.activateNeuron("1");
        assertTrue(neuron1.isActivated());
        network.deactivateNeuron("1");
        assertFalse(neuron1.isActivated());
    }
    
    @Test
    public void testGetEdgesForNeuron() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        Neuron neuron3 = new Neuron("3", "神经元3");
        
        // 添加神经元到网络
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        network.addNeuron(neuron3);
        
        // 创建边
        Edge edge1 = new Edge(neuron1, neuron2, Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY);
        Edge edge2 = new Edge(neuron2, neuron3, Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.ASSOCIATION);
        Edge edge3 = new Edge(neuron1, neuron3, Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.EQUIVALENCE);
        
        // 添加边到网络
        network.addEdge(edge1);
        network.addEdge(edge2);
        network.addEdge(edge3);
        
        // 验证与neuron1相关的边
        assertEquals(2, network.getEdgesForNeuron(neuron1).size());
        
        // 验证与neuron2相关的边
        assertEquals(2, network.getEdgesForNeuron(neuron2).size());
        
        // 验证与neuron3相关的边
        assertEquals(2, network.getEdgesForNeuron(neuron3).size());
    }
    
    @Test
    public void testStoreInformation() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron = new Neuron("1", "原始信息");
        network.addNeuron(neuron);
        
        // 验证原始信息
        assertEquals("原始信息", neuron.getInformation());
        
        // 存入新信息
        boolean result = network.storeInformation("1", "新信息");
        
        // 验证信息录入结果
        assertTrue(result);
        assertEquals("新信息", neuron.getInformation());
        
        // 尝试向不存在的神经元存入信息
        boolean result2 = network.storeInformation("2", "其他信息");
        assertFalse(result2);
    }
    
    @Test
    public void testCreateConnection() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        
        // 创建连接
        boolean result = network.createConnection("1", "2", 
                Edge.Direction.UNIDIRECTIONAL, Edge.RelationshipType.CAUSALITY, "连接信息");
        
        // 验证连接创建结果
        assertTrue(result);
        assertEquals(1, network.getEdges().size());
        
        // 验证边的属性
        Edge edge = network.getEdges().get(0);
        assertEquals(neuron1, edge.getFromNeuron());
        assertEquals(neuron2, edge.getToNeuron());
        assertEquals(Edge.Direction.UNIDIRECTIONAL, edge.getDirection());
        assertEquals(Edge.RelationshipType.CAUSALITY, edge.getRelationshipType());
        assertEquals("连接信息", edge.getInformation());
        
        // 尝试创建与不存在神经元的连接
        boolean result2 = network.createConnection("1", "3", 
                Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.SIMILARITY);
        assertFalse(result2);
    }
    
    @Test
    public void testCreateConnectionWithoutInformation() {
        // 创建神经网络
        NeuralNetwork network = new NeuralNetwork();
        
        // 创建神经元
        Neuron neuron1 = new Neuron("1", "神经元1");
        Neuron neuron2 = new Neuron("2", "神经元2");
        network.addNeuron(neuron1);
        network.addNeuron(neuron2);
        
        // 创建连接（不带信息）
        boolean result = network.createConnection("1", "2", 
                Edge.Direction.BIDIRECTIONAL, Edge.RelationshipType.EQUIVALENCE);
        
        // 验证连接创建结果
        assertTrue(result);
        assertEquals(1, network.getEdges().size());
        
        // 验证边的属性
        Edge edge = network.getEdges().get(0);
        assertEquals(neuron1, edge.getFromNeuron());
        assertEquals(neuron2, edge.getToNeuron());
        assertEquals(Edge.Direction.BIDIRECTIONAL, edge.getDirection());
        assertEquals(Edge.RelationshipType.EQUIVALENCE, edge.getRelationshipType());
        assertNull(edge.getInformation());
    }
}
