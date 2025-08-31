package com.example.nerve.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * NerveCore类的单元测试
 */
public class NerveCoreTest {
    
    @Test
    public void testGetModuleName() {
        NerveCore nerveCore = new NerveCore();
        assertEquals("nerve-core", nerveCore.getModuleName());
    }
    
    @Test
    public void testInitialize() {
        NerveCore nerveCore = new NerveCore();
        // 这里只是简单测试，实际测试中可能需要捕获输出
        assertDoesNotThrow(nerveCore::initialize);
    }
}
