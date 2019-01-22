package com.lzf.page;

import android.animation.TypeEvaluator;

/**
 * @author: liuzhenfeng
 * @function:
 * @date: 2019/1/21  13:42
 */
public class ViewPathEvaluator implements TypeEvaluator<ViewPoint> {


    public ViewPathEvaluator() {
    }

    @Override
    public ViewPoint evaluate(float t, ViewPoint startValue, ViewPoint endValue) {

        float x, y;
        float startX, startY;

        //判断结束点的类型，根据后一个点类型，来计算开始点和结束点的变化
        if (endValue.operation == ViewPath.LINE) {
            startX = (startValue.operation == ViewPath.QUAD) ? startValue.x1 : startValue.x;
            startX = (startValue.operation == ViewPath.CURVE) ? startValue.x2 : startX;
            startY = (startValue.operation == ViewPath.QUAD) ? startValue.y1 : startValue.y;
            startY = (startValue.operation == ViewPath.CURVE) ? startValue.y2 : startY;

            x = startX + t * (endValue.x - startX);
            y = startY + t * (endValue.y - startY);
        } else if (endValue.operation == ViewPath.CURVE) {
            //判断开始点的类型，找到它真正的起始点，我就改了下这里，原来别人的代码的少判断了一种情况
            startX = (startValue.operation == ViewPath.QUAD) ? startValue.x1 : startValue.x;
            startY = (startValue.operation == ViewPath.QUAD) ? startValue.y1 : startValue.y;

            startX = (startValue.operation == ViewPath.CURVE) ? startValue.x2 : startX;
            startY = (startValue.operation == ViewPath.CURVE) ? startValue.y2 : startY;

            float oneMinusT = 1 - t;

            //三阶贝塞尔函数
            x = oneMinusT * oneMinusT * oneMinusT * startX +
                    3 * oneMinusT * oneMinusT * t * endValue.x +
                    3 * oneMinusT * t * t * endValue.x1 +
                    t * t * t * endValue.x2;

            y = oneMinusT * oneMinusT * oneMinusT * startY +
                    3 * oneMinusT * oneMinusT * t * endValue.y +
                    3 * oneMinusT * t * t * endValue.y1 +
                    t * t * t * endValue.y2;


        } else if (endValue.operation == ViewPath.MOVE) {
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