package com.fnw.train;

import java.util.*;

/**
 * 信息单位数据集实现
 * 用于存储和管理包含信息单位及其关系的训练数据
 */
public class InformationUnitDataset implements Dataset {
    
    // 存储所有样本
    private List<Sample> samples;
    
    // 随机数生成器
    private Random random;
    
    public InformationUnitDataset() {
        this.samples = new ArrayList<>();
        this.random = new Random();
    }
    
    public InformationUnitDataset(List<Sample> samples) {
        this.samples = new ArrayList<>(samples);
        this.random = new Random();
    }
    
    @Override
    public int size() {
        return samples.size();
    }
    
    @Override
    public Map<String, Boolean> getSample(int index) {
        if (index < 0 || index >= samples.size()) {
            throw new IndexOutOfBoundsException("样本索引超出范围: " + index);
        }
        return samples.get(index).getNeuronActivations();
    }
    
    @Override
    public List<Map<String, Boolean>> getAllSamples() {
        List<Map<String, Boolean>> activationsList = new ArrayList<>();
        for (Sample sample : samples) {
            activationsList.add(sample.getNeuronActivations());
        }
        return activationsList;
    }
    
    @Override
    public void shuffle() {
        Collections.shuffle(samples, random);
    }
    
    /**
     * 添加样本到数据集
     * @param sample 样本数据
     */
    public void addSample(Sample sample) {
        samples.add(sample);
    }
    
    /**
     * 批量添加样本到数据集
     * @param samples 样本列表
     */
    public void addSamples(List<Sample> samples) {
        this.samples.addAll(samples);
    }
    
    /**
     * 获取指定索引的样本
     * @param index 样本索引
     * @return 样本数据
     */
    public Sample getInformationSample(int index) {
        if (index < 0 || index >= samples.size()) {
            throw new IndexOutOfBoundsException("样本索引超出范围: " + index);
        }
        return samples.get(index);
    }
    
    /**
     * 获取所有样本
     * @return 所有样本的列表
     */
    public List<Sample> getAllInformationSamples() {
        return new ArrayList<>(samples);
    }
    
    /**
     * 清空数据集
     */
    public void clear() {
        samples.clear();
    }
    
    /**
     * 样本类，包含信息单位及其关系
     */
    public static class Sample {
        // 信息单位，键为神经元ID，值为信息内容
        private Map<String, String> informationUnits;
        
        // 信息单位之间的关系，包含起始神经元ID、目标神经元ID和关系描述
        private List<Relation> relations;
        
        // 神经元激活状态，用于训练
        private Map<String, Boolean> neuronActivations;
        
        public Sample() {
            this.informationUnits = new HashMap<>();
            this.relations = new ArrayList<>();
            this.neuronActivations = new HashMap<>();
        }
        
        public Sample(Map<String, String> informationUnits, List<Relation> relations, Map<String, Boolean> neuronActivations) {
            this.informationUnits = informationUnits;
            this.relations = relations;
            this.neuronActivations = neuronActivations;
        }
        
        /**
         * 添加信息单位
         * @param neuronId 神经元ID
         * @param information 信息内容
         */
        public void addInformationUnit(String neuronId, String information) {
            informationUnits.put(neuronId, information);
        }
        
        /**
         * 添加关系
         * @param fromNeuronId 起始神经元ID
         * @param toNeuronId 目标神经元ID
         * @param relationDescription 关系描述
         */
        public void addRelation(String fromNeuronId, String toNeuronId, String relationDescription) {
            relations.add(new Relation(fromNeuronId, toNeuronId, relationDescription));
        }
        
        /**
         * 设置神经元激活状态
         * @param neuronId 神经元ID
         * @param activated 是否激活
         */
        public void setNeuronActivation(String neuronId, boolean activated) {
            neuronActivations.put(neuronId, activated);
        }
        
        // Getter methods
        public Map<String, String> getInformationUnits() {
            return informationUnits;
        }
        
        public List<Relation> getRelations() {
            return relations;
        }
        
        public Map<String, Boolean> getNeuronActivations() {
            return neuronActivations;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Sample{\n");
            sb.append("  信息单位: \n");
            for (Map.Entry<String, String> entry : informationUnits.entrySet()) {
                sb.append("    神经元 ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            sb.append("  关系: \n");
            for (Relation relation : relations) {
                sb.append("    ").append(relation.toString()).append("\n");
            }
            sb.append("  神经元激活状态: \n");
            for (Map.Entry<String, Boolean> entry : neuronActivations.entrySet()) {
                sb.append("    神经元 ").append(entry.getKey()).append(": ").append(entry.getValue() ? "激活" : "未激活").append("\n");
            }
            sb.append("}");
            return sb.toString();
        }
    }
    
    /**
     * 关系类，表示信息单位之间的关系
     */
    public static class Relation {
        private String fromNeuronId;
        private String toNeuronId;
        private String description;
        
        public Relation(String fromNeuronId, String toNeuronId, String description) {
            this.fromNeuronId = fromNeuronId;
            this.toNeuronId = toNeuronId;
            this.description = description;
        }
        
        // Getter methods
        public String getFromNeuronId() {
            return fromNeuronId;
        }
        
        public String getToNeuronId() {
            return toNeuronId;
        }
        
        public String getDescription() {
            return description;
        }
        
        @Override
        public String toString() {
            return "神经元 " + fromNeuronId + " -> 神经元 " + toNeuronId + " (" + description + ")";
        }
    }
    
    /**
     * 创建一个示例数据集
     * @return 示例数据集
     */
    public static InformationUnitDataset createExampleDataset() {
        InformationUnitDataset dataset = new InformationUnitDataset();
        
        // 样本1: 动物相关的信息
        Sample sample1 = new Sample();
        sample1.addInformationUnit("1", "狗");
        sample1.addInformationUnit("2", "哺乳动物");
        sample1.addInformationUnit("3", "宠物");
        sample1.addRelation("1", "2", "是一种");
        sample1.addRelation("1", "3", "是");
        sample1.setNeuronActivation("1", true);
        sample1.setNeuronActivation("2", true);
        sample1.setNeuronActivation("3", true);
        dataset.addSample(sample1);
        
        // 样本2: 植物相关的信息
        Sample sample2 = new Sample();
        sample2.addInformationUnit("4", "玫瑰");
        sample2.addInformationUnit("5", "花");
        sample2.addInformationUnit("6", "植物");
        sample2.addRelation("4", "5", "是一种");
        sample2.addRelation("4", "6", "是一种");
        sample2.setNeuronActivation("4", true);
        sample2.setNeuronActivation("5", true);
        sample2.setNeuronActivation("6", true);
        dataset.addSample(sample2);
        
        // 样本3: 食物相关的信息
        Sample sample3 = new Sample();
        sample3.addInformationUnit("7", "苹果");
        sample3.addInformationUnit("8", "水果");
        sample3.addInformationUnit("9", "红色");
        sample3.addRelation("7", "8", "是一种");
        sample3.addRelation("7", "9", "具有颜色");
        sample3.setNeuronActivation("7", true);
        sample3.setNeuronActivation("8", true);
        sample3.setNeuronActivation("9", true);
        dataset.addSample(sample3);
        
        return dataset;
    }
}
