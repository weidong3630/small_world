package com.fnw.model;

import java.util.*;

/**
 * 神经网络模型
 * 用于管理神经元和边的集合
 */
public class NeuralNetwork {
    // 神经元集合
    private Map<String, Neuron> neurons;
    
    // 边集合
    private List<Edge> edges;
    
    public NeuralNetwork() {
        this.neurons = new HashMap<>();
        this.edges = new ArrayList<>();
    }
    
    /**
     * 添加神经元
     * @param neuron 神经元
     */
    public void addNeuron(Neuron neuron) {
        neurons.put(neuron.getId(), neuron);
    }
    
    /**
     * 根据ID获取神经元
     * @param id 神经元ID
     * @return 神经元
     */
    public Neuron getNeuron(String id) {
        return neurons.get(id);
    }
    
    /**
     * 添加边
     * @param edge 边
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }
    
    /**
     * 获取所有神经元
     * @return 神经元集合
     */
    public Collection<Neuron> getNeurons() {
        return neurons.values();
    }
    
    /**
     * 获取所有边
     * @return 边集合
     */
    public List<Edge> getEdges() {
        return edges;
    }
    
    /**
     * 根据神经元获取相关的边
     * @param neuron 神经元
     * @return 与该神经元相关的边
     */
    public List<Edge> getEdgesForNeuron(Neuron neuron) {
        List<Edge> relatedEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFromNeuron().equals(neuron) || edge.getToNeuron().equals(neuron)) {
                relatedEdges.add(edge);
            }
        }
        return relatedEdges;
    }
    
    /**
     * 激活神经元
     * @param neuronId 神经元ID
     */
    public void activateNeuron(String neuronId) {
        Neuron neuron = neurons.get(neuronId);
        if (neuron != null) {
            neuron.setActivated(true);
        }
    }
    
    /**
     * 取消激活神经元
     * @param neuronId 神经元ID
     */
    public void deactivateNeuron(String neuronId) {
        Neuron neuron = neurons.get(neuronId);
        if (neuron != null) {
            neuron.setActivated(false);
        }
    }
    
    /**
     * 信息录入，将信息存入神经元
     * @param neuronId 神经元ID
     * @param information 要存储的信息
     * @return 是否成功录入信息
     */
    public boolean storeInformation(String neuronId, Object information) {
        Neuron neuron = neurons.get(neuronId);
        if (neuron != null) {
            neuron.setInformation(information);
            return true;
        }
        return false;
    }
    
    /**
     * 创建连接
     * @param fromNeuronId 起始神经元ID
     * @param toNeuronId 目标神经元ID
     * @param direction 连接方向
     * @param relationshipType 关系类型
     * @param information 连接上的信息
     * @return 是否成功创建连接
     */
    public boolean createConnection(String fromNeuronId, String toNeuronId, 
                                   Edge.Direction direction, Edge.RelationshipType relationshipType, 
                                   Object information) {
        Neuron fromNeuron = neurons.get(fromNeuronId);
        Neuron toNeuron = neurons.get(toNeuronId);
        
        if (fromNeuron != null && toNeuron != null) {
            Edge edge = new Edge(fromNeuron, toNeuron, direction, relationshipType, information);
            edges.add(edge);
            return true;
        }
        return false;
    }
    
    /**
     * 创建连接（不带信息）
     * @param fromNeuronId 起始神经元ID
     * @param toNeuronId 目标神经元ID
     * @param direction 连接方向
     * @param relationshipType 关系类型
     * @return 是否成功创建连接
     */
    public boolean createConnection(String fromNeuronId, String toNeuronId, 
                                   Edge.Direction direction, Edge.RelationshipType relationshipType) {
        return createConnection(fromNeuronId, toNeuronId, direction, relationshipType, null);
    }
    
    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "neurons=" + neurons.size() +
                ", edges=" + edges.size() +
                '}';
    }
}
