package com.fnw.train;

/**
 * 训练器工厂类
 * 用于创建不同类型的训练器
 */
public class TrainerFactory {
    
    /**
     * 训练器类型枚举
     */
    public enum TrainerType {
        BASE,      // 基础训练器
        HEBBIAN    // Hebb学习规则训练器
    }
    
    /**
     * 创建训练器
     * @param type 训练器类型
     * @return 训练器实例
     */
    public static Trainer createTrainer(TrainerType type) {
        switch (type) {
            case BASE:
                return new BaseTrainer();
            case HEBBIAN:
                return new HebbianTrainer();
            default:
                throw new IllegalArgumentException("未知的训练器类型: " + type);
        }
    }
    
    /**
     * 创建训练器（带学习率参数）
     * @param type 训练器类型
     * @param learningRate 学习率
     * @return 训练器实例
     */
    public static Trainer createTrainer(TrainerType type, double learningRate) {
        switch (type) {
            case BASE:
                return new BaseTrainer(learningRate);
            case HEBBIAN:
                return new HebbianTrainer(learningRate);
            default:
                throw new IllegalArgumentException("未知的训练器类型: " + type);
        }
    }
}
