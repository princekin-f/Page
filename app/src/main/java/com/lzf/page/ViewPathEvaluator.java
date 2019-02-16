package com.lzf.page;

import android.animation.TypeEvaluator;

/**
 * @author: liuzhenfeng
 * @function: 贝塞尔曲线的当前坐标估值器
 * @date: 2019/1/21  13:42
 */
public class ViewPathEvaluator implements TypeEvaluator<ViewPoint> {

    public ViewPathEvaluator() {
    }

    @Override
    public ViewPoint evaluate(float t, ViewPoint startValue, ViewPoint endValue) {

        float x, y;
        float startX, startY;

        // 判断结束点的类型，根据后一个点类型，来计算开始点和结束点的变化
        if (endValue.operation == ViewPath.LINE) {
            // line：画直线，当前值 = 起始点 + t * (结束点-起始点）

            // 判断开始点的类型，找到它真正的起始点
            // 如果上一步操作（startValue）是二阶，取二阶的结束点x1 为新起点
            startX = (startValue.operation == ViewPath.QUAD) ? startValue.x1 : startValue.x;
            // 如果上一步操作（startValue）是三阶，取三阶的结束点x2 为新起点
            startX = (startValue.operation == ViewPath.CURVE) ? startValue.x2 : startX;
            // 以上两步：如果既不是二阶，也不是三阶，直接取startValue.x（一阶）

            // Y 取值方式与 X 类似
            startY = (startValue.operation == ViewPath.QUAD) ? startValue.y1 : startValue.y;
            startY = (startValue.operation == ViewPath.CURVE) ? startValue.y2 : startY;

            x = startX + t * (endValue.x - startX);
            y = startY + t * (endValue.y - startY);

        } else if (endValue.operation == ViewPath.CURVE) {
            // curve：三阶，同上：先求真实起始点，然后套公式求值
            startX = (startValue.operation == ViewPath.QUAD) ? startValue.x1 : startValue.x;
            startY = (startValue.operation == ViewPath.QUAD) ? startValue.y1 : startValue.y;

            startX = (startValue.operation == ViewPath.CURVE) ? startValue.x2 : startX;
            startY = (startValue.operation == ViewPath.CURVE) ? startValue.y2 : startY;

            float oneMinusT = 1 - t;

            //三阶贝塞尔函数（套公式）
            x = oneMinusT * oneMinusT * oneMinusT * startX +
                    3 * oneMinusT * oneMinusT * t * endValue.x +
                    3 * oneMinusT * t * t * endValue.x1 +
                    t * t * t * endValue.x2;

            y = oneMinusT * oneMinusT * oneMinusT * startY +
                    3 * oneMinusT * oneMinusT * t * endValue.y +
                    3 * oneMinusT * t * t * endValue.y1 +
                    t * t * t * endValue.y2;

        } else if (endValue.operation == ViewPath.MOVE) {
            // move：重新设置起点
            x = endValue.x;
            y = endValue.y;

        } else if (endValue.operation == ViewPath.QUAD) {
            startX = (startValue.operation == ViewPath.CURVE) ? startValue.x2 : startValue.x;
            startY = (startValue.operation == ViewPath.CURVE) ? startValue.y2 : startValue.y;

            startX = (startValue.operation == ViewPath.QUAD) ? startValue.x1 : startX;
            startY = (startValue.operation == ViewPath.QUAD) ? startValue.y1 : startY;

            //二阶贝塞尔函数
            float oneMinusT = 1 - t;
            x = oneMinusT * oneMinusT * startX +
                    2 * oneMinusT * t * endValue.x +
                    t * t * endValue.x1;

            y = oneMinusT * oneMinusT * startY +
                    2 * oneMinusT * t * endValue.y +
                    t * t * endValue.y1;

        } else {
            x = endValue.x;
            y = endValue.y;
        }
        return new ViewPoint(x, y);
    }
}