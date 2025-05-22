// 使用importmap定义的模块标识符
import { DrawUtil } from 'DrawUtil';

document.addEventListener('DOMContentLoaded', function() {
    const app = document.getElementById('app');
    if (app) {
        app.textContent = 'hello,world, 1!';
    }
    
    // 创建DrawUtil实例并调用绘制方法
    const drawUtil = new DrawUtil('myCanvas');
    if (drawUtil.ctx) {
        drawUtil.drawPoint(100, 100, 5, 'red');
        drawUtil.drawLine(0, 0, 200, 200, 'blue', 2);
    }
});