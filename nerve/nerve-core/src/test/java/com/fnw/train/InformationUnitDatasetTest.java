package com.fnw.train;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 信息单位数据集测试类
 */
public class InformationUnitDatasetTest {
    
    @Test
    public void testDatasetCreation() {
        // 创建数据集
        InformationUnitDataset dataset = new InformationUnitDataset();
        
        // 验证初始状态
        assertEquals(0, dataset.size());
    }
    
    @Test
    public void testAddSample() {
        // 创建数据集
        InformationUnitDataset dataset = new InformationUnitDataset();
        
        // 创建样本
        InformationUnitDataset.Sample sample = new InformationUnitDataset.Sample();
        sample.addInformationUnit("1", "信息1");
        sample.addRelation("1", "2", "关系描述");
        sample.setNeuronActivation("1", true);
        
        // 添加样本
        dataset.addSample(sample);
        
        // 验证样本添加
        assertEquals(1, dataset.size());
    }
    
    @Test
    public void testGetSample() {
        // 创建数据集
        InformationUnitDataset dataset = new InformationUnitDataset();
        
        // 创建样本
        InformationUnitDataset.Sample sample = new InformationUnitDataset.Sample();
        sample.addInformationUnit("1", "信息1");
        sample.addRelation("1", "2", "关系描述");
        sample.setNeuronActivation("1", true);
        
        // 添加样本
        dataset.addSample(sample);
        
        // 获取样本
        InformationUnitDataset.Sample retrievedSample = dataset.getInformationSample(0);
        
        // 验证样本内容
        assertEquals(1, retrievedSample.getInformationUnits().size());
        assertEquals("信息1", retrievedSample.getInformationUnits().get("1"));
        assertEquals(1, retrievedSample.getRelations().size());
        assertEquals("关系描述", retrievedSample.getRelations().get(0).getDescription());
        assertTrue(retrievedSample.getNeuronActivations().get("1"));
    }
    
    @Test
    public void testGetSampleAsMap() {
        // 创建数据集
        InformationUnitDataset dataset = new InformationUnitDataset();
        
        // 创建样本
        InformationUnitDataset.Sample sample = new InformationUnitDataset.Sample();
        sample.addInformationUnit("1", "信息1");
        sample.setNeuronActivation("1", true);
        sample.setNeuronActivation("2", false);
        
        // 添加样本
        dataset.addSample(sample);
        
        // 获取样本作为Map
        java.util.Map<String, Boolean> sampleMap = dataset.getSample(0);
        
        // 验证Map内容
        assertEquals(2, sampleMap.size());
        assertTrue(sampleMap.get("1"));
        assertFalse(sampleMap.get("2"));
    }
    
    @Test
    public void testCreateExampleDataset() {
        // 创建示例数据集
        InformationUnitDataset dataset = InformationUnitDataset.createExampleDataset();
        
        // 验证数据集大小
        assertEquals(3, dataset.size());
        
        // 验证第一个样本
        InformationUnitDataset.Sample sample1 = dataset.getInformationSample(0);
        assertEquals(3, sample1.getInformationUnits().size());
        assertEquals(2, sample1.getRelations().size());
        assertEquals(3, sample1.getNeuronActivations().size());
        
        // 验证信息单位
        assertEquals("狗", sample1.getInformationUnits().get("1"));
        assertEquals("哺乳动物", sample1.getInformationUnits().get("2"));
        assertEquals("宠物", sample1.getInformationUnits().get("3"));
        
        // 验证关系
        InformationUnitDataset.Relation relation1 = sample1.getRelations().get(0);
        assertEquals("1", relation1.getFromNeuronId());
        assertEquals("2", relation1.getToNeuronId());
        assertEquals("是一种", relation1.getDescription());
        
        InformationUnitDataset.Relation relation2 = sample1.getRelations().get(1);
        assertEquals("1", relation2.getFromNeuronId());
        assertEquals("3", relation2.getToNeuronId());
        assertEquals("是", relation2.getDescription());
        
        // 验证神经元激活状态
        assertTrue(sample1.getNeuronActivations().get("1"));
        assertTrue(sample1.getNeuronActivations().get("2"));
        assertTrue(sample1.getNeuronActivations().get("3"));
    }
    
    @Test
    public void testShuffle() {
        // 创建示例数据集
        InformationUnitDataset dataset = InformationUnitDataset.createExampleDataset();
        
        // 验证数据集大小
        assertEquals(3, dataset.size());
        
        // 打乱数据集
        dataset.shuffle();
        
        // 验证打乱后数据集大小不变
        assertEquals(3, dataset.size());
        
        // 验证仍然可以获取样本
        InformationUnitDataset.Sample sample = dataset.getInformationSample(0);
        assertNotNull(sample);
        assertFalse(sample.getInformationUnits().isEmpty());
    }
    
    @Test
    public void testToString() {
        // 创建样本
        InformationUnitDataset.Sample sample = new InformationUnitDataset.Sample();
        sample.addInformationUnit("1", "信息1");
        sample.addRelation("1", "2", "关系描述");
        sample.setNeuronActivation("1", true);
        
        // 验证toString方法
        String sampleString = sample.toString();
        assertTrue(sampleString.contains("信息单位"));
        assertTrue(sampleString.contains("关系"));
        assertTrue(sampleString.contains("神经元激活状态"));
        assertTrue(sampleString.contains("信息1"));
        assertTrue(sampleString.contains("关系描述"));
        assertTrue(sampleString.contains("激活"));
    }
}
