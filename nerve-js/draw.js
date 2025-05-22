class DrawUtil {
    constructor(canvasId) {
        this.canvas = document.getElementById(canvasId);
        if (!this.canvas) {
            console.error(`Canvas element #${canvasId} not found`);
            return;
        }
        
        // 设置默认尺寸
        this.canvas.width = this.canvas.width || 400;
        this.canvas.height = this.canvas.height || 400;
        
        this.ctx = this.canvas.getContext('2d');
        if (!this.ctx) {
            console.error('Failed to get canvas context');
        }
    }

    drawPoint(x, y, radius = 3, color = 'black') {
        if (!this.ctx) return;
        this.ctx.beginPath();
        this.ctx.fillStyle = color;
        this.ctx.arc(x, y, radius, 0, Math.PI * 2);
        this.ctx.fill();
    }

    drawLine(x1, y1, x2, y2, color = 'black', lineWidth = 1) {
        if (!this.ctx) return;
        this.ctx.beginPath();
        this.ctx.strokeStyle = color;
        this.ctx.lineWidth = lineWidth;
        this.ctx.moveTo(x1, y1);
        this.ctx.lineTo(x2, y2);
        this.ctx.stroke();
    }
}