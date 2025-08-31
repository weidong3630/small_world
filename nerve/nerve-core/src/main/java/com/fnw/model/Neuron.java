package com.fnw.model;

/**
 * 神经元模型
 * 单个神经元主要会存储一定量的信息，每个神经元表示一个物理对象的抽象。
 */
public class Neuron {
    // 神经元存储的信息
    private Object information;
    
    // 神经元的唯一标识
    private String id;
    
    // 神经元的激活状态
    private boolean activated;
    
    public Neuron() {
        this.activated = false;
    }
    
    public Neuron(String id, Object information) {
        this.id = id;
        this.information = information;
        this.activated = false;
    }
    
    // Getter and Setter methods
    public Object getInformation() {
        return information;
    }
    
    public void setInformation(Object information) {
        this.information = information;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public boolean isActivated() {
        return activated;
    }
    
    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    
    @Override
    public String toString() {
        return "Neuron{" +
                "id='" + id + '\'' +
                ", information=" + information +
                ", activated=" + activated +
                '}';
    }
}
