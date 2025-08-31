package com.fnw.model;

/**
 * 边模型
 * 神经元之间的连接，暂且称之为边。首先边上可以存储一定量的信息，
 * 然后边有如下性质。
 * 1. 方向性，包括单向、双向、无向等几种情况
 * 2. 关系类型，边表示一种关系，这种关系有几个类型，包括等价关系、因果关系、相似关系、关联关系等。
 */
public class Edge {
    // 边存储的信息
    private Object information;
    
    // 起始神经元
    private Neuron fromNeuron;
    
    // 目标神经元
    private Neuron toNeuron;
    
    // 边的方向性
    private Direction direction;
    
    // 关系类型
    private RelationshipType relationshipType;
    
    public Edge() {
    }
    
    public Edge(Neuron fromNeuron, Neuron toNeuron, Direction direction, RelationshipType relationshipType) {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.direction = direction;
        this.relationshipType = relationshipType;
    }
    
    public Edge(Neuron fromNeuron, Neuron toNeuron, Direction direction, RelationshipType relationshipType, Object information) {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.direction = direction;
        this.relationshipType = relationshipType;
        this.information = information;
    }
    
    // Getter and Setter methods
    public Object getInformation() {
        return information;
    }
    
    public void setInformation(Object information) {
        this.information = information;
    }
    
    public Neuron getFromNeuron() {
        return fromNeuron;
    }
    
    public void setFromNeuron(Neuron fromNeuron) {
        this.fromNeuron = fromNeuron;
    }
    
    public Neuron getToNeuron() {
        return toNeuron;
    }
    
    public void setToNeuron(Neuron toNeuron) {
        this.toNeuron = toNeuron;
    }
    
    public Direction getDirection() {
        return direction;
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public RelationshipType getRelationshipType() {
        return relationshipType;
    }
    
    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }
    
    @Override
    public String toString() {
        return "Edge{" +
                "information=" + information +
                ", fromNeuron=" + fromNeuron +
                ", toNeuron=" + toNeuron +
                ", direction=" + direction +
                ", relationshipType=" + relationshipType +
                '}';
    }
    
    /**
     * 边的方向性枚举
     */
    public enum Direction {
        UNIDIRECTIONAL,  // 单向
        BIDIRECTIONAL,   // 双向
        UNDIRECTED       // 无向
    }
    
    /**
     * 关系类型枚举
     */
    public enum RelationshipType {
        EQUIVALENCE,  // 等价关系
        CAUSALITY,    // 因果关系
        SIMILARITY,   // 相似关系
        ASSOCIATION   // 关联关系
    }
}
