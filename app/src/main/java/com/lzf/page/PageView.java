package com.lzf.page;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: liuzhenfeng
 * @function: 啥是佩奇
 * @date: 2019/1/21  09:38
 */
public class PageView extends View {

    private Paint paintPink = new Paint();
    private Paint paintRed = new Paint();
    private Paint paintBlack = new Paint();
    private Paint paintWhite = new Paint();

    private Path mPath = new Path();
    private Path mPathEar1 = new Path();
    private Path mPathEar2 = new Path();
    private Path mPathBody = new Path();
    private Path mPathArmRight = new Path();
    private Path mPathHandRight = new Path();
    private Path mPathArmLeft = new Path();
    private Path mPathHandLeft = new Path();
    private Path mPathTail = new Path();

    // 常规百分百绘制
    private ValueAnimator animNose;
    private ValueAnimator animEyes;
    private ValueAnimator animFace;
    private ValueAnimator animMouth;
    private ValueAnimator animLegs;
    private ValueAnimator animFoots;
    private int progressNose = 0;
    private int progressEyes = 0;
    private int progressFace = 0;
    private int progressMouth = 0;
    private int progressLegs = 0;
    private int progressFoots = 0;

    // 贝塞尔曲线绘制
    private ValueAnimator animHead;
    private ValueAnimator animEar1;
    private ValueAnimator animEar2;
    private ValueAnimator animBody;
    private ValueAnimator animArmRight;
    private ValueAnimator animHandRight;
    private ValueAnimator animArmLeft;
    private ValueAnimator animHandLeft;
    private ValueAnimator animTail;

    private ViewPoint pointHead = new ViewPoint();
    private ViewPoint pointEar1 = new ViewPoint();
    private ViewPoint pointEar2 = new ViewPoint();
    private ViewPoint pointBody = new ViewPoint();
    private ViewPoint pointArmRight = new ViewPoint();
    private ViewPoint pointHandRight = new ViewPoint();
    private ViewPoint pointArmLeft = new ViewPoint();
    private ViewPoint pointHandLeft = new ViewPoint();
    private ViewPoint pointTail = new ViewPoint();


    boolean isInitPath = false;
    private AnimatorSet animatorSet;

    public PageView(Context context) {
        super(context);
        init();
    }

    public PageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paintPink.setColor(Color.rgb(255, 155, 192));
        paintPink.setStyle(Paint.Style.STROKE);
        paintPink.setStrokeWidth(3f);
        paintPink.setAntiAlias(true);

//        paintRed.setColor(Color.RED);
        paintRed.setColor(Color.rgb(255, 99, 71));
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(3f);
        paintRed.setAntiAlias(true);

        paintBlack.setColor(Color.BLACK);
        paintBlack.setStyle(Paint.Style.STROKE);
        paintBlack.setStrokeWidth(3f);
        paintBlack.setAntiAlias(true);

        paintWhite.setColor(Color.WHITE);
        paintBlack.setStyle(Paint.Style.STROKE);
        paintBlack.setStrokeWidth(3f);
        paintBlack.setAntiAlias(true);
    }

    private void initIntAnim() {
        // 鼻子
        animNose = ValueAnimator.ofInt(1, 100);
        animNose.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressNose = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animNose.setDuration(1000);

        // 眼睛
        animEyes = ValueAnimator.ofInt(1, 100);
        animEyes.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressEyes = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animEyes.setDuration(800);

        // 腮红
        animFace = ValueAnimator.ofInt(1, 100);
        animFace.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressFace = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animFace.setDuration(800);

        // 嘴巴
        animMouth = ValueAnimator.ofInt(1, 100);
        animMouth.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressMouth = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animMouth.setDuration(500);

        // 腿
        animLegs = ValueAnimator.ofInt(1, 100);
        animLegs.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressLegs = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animLegs.setDuration(400);

        // 脚
        animFoots = ValueAnimator.ofInt(1, 100);
        animFoots.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progressFoots = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animFoots.setDuration(400);

    }

    public void initPath() {

        initIntAnim();

        // 千万不要觉得下面很复杂，就是找贝尔塞的控制点和结束点而已，很简单
        // 我们的ViewPath，其实可以绘制任何直线路径和贝塞尔曲线路径了，自己在调用lineTo传入点等就行了
        // 猪头 🐷
        ViewPath viewPath = new ViewPath();
        pointHead.x = dp2px(220);
        pointHead.y = dp2px(102);
        mPath.moveTo(pointHead.x, pointHead.y);
        viewPath.moveTo(pointHead.x, pointHead.y);
        viewPath.curveTo(dp2px(-100), dp2px(80), dp2px(130), dp2px(330), dp2px(170), dp2px(170));
        viewPath.quadTo(dp2px(210), dp2px(170), dp2px(240), dp2px(155));
        animHead = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPath.getPoints().toArray());
        animHead.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointHead = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animHead.setDuration(3000);

        // 右耳朵
        ViewPath viewPathEar1 = new ViewPath();
        pointEar1.x = dp2px(130);
        pointEar1.y = dp2px(105);
        mPathEar1.moveTo(pointEar1.x, pointEar1.y);
        viewPathEar1.moveTo(pointEar1.x, pointEar1.y);
        viewPathEar1.curveTo(dp2px(120), dp2px(50), dp2px(160), dp2px(50), dp2px(150), dp2px(102));
        animEar1 = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathEar1.getPoints().toArray());
        animEar1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointEar1 = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animEar1.setDuration(600);

        // 左耳朵
        ViewPath viewPathEar2 = new ViewPath();
        pointEar2.x = dp2px(100);
        pointEar2.y = dp2px(110);
        mPathEar2.moveTo(pointEar2.x, pointEar2.y);
        viewPathEar2.moveTo(pointEar2.x, pointEar2.y);
        viewPathEar2.curveTo(dp2px(80), dp2px(53), dp2px(120), dp2px(53), dp2px(120), dp2px(105));
        animEar2 = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathEar2.getPoints().toArray());
        animEar2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointEar2 = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animEar2.setDuration(600);

        // 身体：肚子
        ViewPath viewPathBody = new ViewPath();
        pointBody.x = dp2px(80);
        pointBody.y = dp2px(210);
        mPathBody.moveTo(pointBody.x, pointBody.y);
        viewPathBody.moveTo(pointBody.x, pointBody.y);
        viewPathBody.quadTo(dp2px(50), dp2px(270), dp2px(50), dp2px(320));
        viewPathBody.quadTo(dp2px(100), dp2px(320), dp2px(180), dp2px(320));
        viewPathBody.quadTo(dp2px(180), dp2px(270), dp2px(150), dp2px(210));
        animBody = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathBody.getPoints().toArray());
        animBody.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointBody = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animBody.setDuration(2000);

        // 右胳膊
        ViewPath viewPathArmRight = new ViewPath();
        pointArmRight.x = dp2px(160);
        pointArmRight.y = dp2px(233);
        mPathArmRight.moveTo(pointArmRight.x, pointArmRight.y);
        viewPathArmRight.moveTo(pointArmRight.x, pointArmRight.y);
        viewPathArmRight.quadTo(dp2px(170), dp2px(230), dp2px(210), dp2px(245));
        animArmRight = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathArmRight.getPoints().toArray());
        animArmRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointArmRight = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animArmRight.setDuration(500);

        // 右手
        ViewPath viewPathHandRight = new ViewPath();
        pointHandRight.x = dp2px(210);
        pointHandRight.y = dp2px(235);
        mPathHandRight.moveTo(pointHandRight.x, pointHandRight.y);
        viewPathHandRight.moveTo(pointHandRight.x, pointHandRight.y);
        viewPathHandRight.quadTo(dp2px(190), dp2px(242), dp2px(207), dp2px(255));
        animHandRight = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathHandRight.getPoints().toArray());
        animHandRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointHandRight = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animHandRight.setDuration(500);

        // 左胳膊
        ViewPath viewPathArmLeft = new ViewPath();
        pointArmLeft.x = dp2px(70);
        pointArmLeft.y = dp2px(233);
        mPathArmLeft.moveTo(pointArmLeft.x, pointArmLeft.y);
        viewPathArmLeft.moveTo(pointArmLeft.x, pointArmLeft.y);
        viewPathArmLeft.quadTo(dp2px(70), dp2px(230), dp2px(20), dp2px(245));
        animArmLeft = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathArmLeft.getPoints().toArray());
        animArmLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointArmLeft = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animArmLeft.setDuration(500);

        // 左手
        ViewPath viewPathHandLeft = new ViewPath();
        pointHandLeft.x = dp2px(20);
        pointHandLeft.y = dp2px(235);
        mPathHandLeft.moveTo(pointHandLeft.x, pointHandLeft.y);
        viewPathHandLeft.moveTo(pointHandLeft.x, pointHandLeft.y);
        viewPathHandLeft.quadTo(dp2px(40), dp2px(242), dp2px(22), dp2px(255));
        animHandLeft = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathHandLeft.getPoints().toArray());
        animHandLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointHandLeft = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animHandLeft.setDuration(500);

        // 尾巴
        ViewPath viewPathTail = new ViewPath();
        pointTail.x = dp2px(51);
        pointTail.y = dp2px(300);
        mPathTail.moveTo(pointTail.x, pointTail.y);
        viewPathTail.moveTo(pointTail.x, pointTail.y);
        viewPathTail.curveTo(dp2px(30), dp2px(330), dp2px(30), dp2px(280), dp2px(40), dp2px(300));
        viewPathTail.curveTo(dp2px(40), dp2px(320), dp2px(20), dp2px(300), dp2px(0), dp2px(310));
        animTail = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPathTail.getPoints().toArray());
        animTail.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointTail = (ViewPoint) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animTail.setDuration(1200);

        animatorSet = new AnimatorSet();
        // 设置动画集合，按顺序绘制
        animatorSet.playSequentially(animNose, animHead, animEar2, animEar1, animEyes, animMouth, animFace,
                animBody, animArmRight, animHandRight, animArmLeft, animHandLeft, animLegs, animFoots, animTail);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (widthMeasureSpec > 0) {
            if (!isInitPath) {
                isInitPath = true;
                initPath();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 鼻子：倾斜的椭圆
        RectF oval = new RectF(dp2px(200), dp2px(101), dp2px(250), dp2px(160));
        // 旋转画布，结束还需旋转回去
        canvas.rotate(-15, dp2px(getContext(), 225), dp2px(getContext(), 150));
        if (progressNose < 100) {
            paintPink.setStyle(Paint.Style.STROKE);
            paintRed.setStyle(Paint.Style.STROKE);
        } else {
            paintPink.setStyle(Paint.Style.FILL);
            paintRed.setStyle(Paint.Style.FILL);
        }
        canvas.drawArc(oval, 0, progressNose * 3.6f, false, paintPink);
        canvas.rotate(15, dp2px(getContext(), 225), dp2px(getContext(), 130));

        // 鼻孔
        oval.set(dp2px(213), dp2px(125), dp2px(223), dp2px(135));
        canvas.drawArc(oval, 0, progressNose * 3.6f, false, paintRed);
        oval.set(dp2px(230), dp2px(122), dp2px(240), dp2px(132));
        canvas.drawArc(oval, 0, progressNose * 3.6f, false, paintRed);

        paintPink.setStyle(Paint.Style.STROKE);
        paintRed.setStyle(Paint.Style.STROKE);

        // 眼睛：前两个眼眶，后两个眼球
        if (progressEyes < 100) {
            paintBlack.setStyle(Paint.Style.STROKE);
        } else {
            paintBlack.setStyle(Paint.Style.FILL);
        }
        oval.set(dp2px(110), dp2px(115), dp2px(140), dp2px(145));
        canvas.drawArc(oval, 0, progressEyes * 3.6f, false, paintPink);
        oval.set(dp2px(145), dp2px(105), dp2px(175), dp2px(135));
        canvas.drawArc(oval, 0, progressEyes * 3.6f, false, paintPink);
        oval.set(dp2px(123), dp2px(123), dp2px(133), dp2px(133));
        canvas.drawArc(oval, 0, progressEyes * 3.6f, false, paintBlack);
        oval.set(dp2px(158), dp2px(113), dp2px(168), dp2px(123));
        canvas.drawArc(oval, 0, progressEyes * 3.6f, false, paintBlack);

        paintBlack.setStyle(Paint.Style.STROKE);

        // 腮红
        if (progressFace < 100) {
            paintPink.setStyle(Paint.Style.STROKE);
        } else {
            paintPink.setStyle(Paint.Style.FILL);
        }
        oval.set(dp2px(70), dp2px(160), dp2px(95), dp2px(190));
        canvas.drawArc(oval, 0, progressFace * 3.6f, false, paintPink);

        // 嘴巴
        oval.set(dp2px(110), dp2px(175), dp2px(155), dp2px(200));
        canvas.drawArc(oval, 165, -progressMouth * 1.8f, false, paintRed);

        // 腿和脚，需要内容填充
        paintPink.setStyle(Paint.Style.FILL);
        paintBlack.setStyle(Paint.Style.FILL);

        // 腿
        canvas.drawRect(dp2px(95), dp2px(320), dp2px(98), dp2px(320 + 30 * progressLegs / 100f), paintPink);
        canvas.drawRect(dp2px(130), dp2px(320), dp2px(133), dp2px(320 + 30 * progressLegs / 100f), paintPink);

        // 小黑脚
        oval.set(dp2px(90), dp2px(350), dp2px(90 + 20 * progressFoots / 100f), dp2px(360));
        canvas.drawRoundRect(oval, dp2px(5), dp2px(5), paintBlack);
        oval.set(dp2px(125), dp2px(350), dp2px(125 + 20 * progressFoots / 100f), dp2px(360));
        canvas.drawRoundRect(oval, dp2px(5), dp2px(5), paintBlack);

        paintPink.setStyle(Paint.Style.STROKE);
        paintBlack.setStyle(Paint.Style.STROKE);

        // 身体轮廓：贝塞尔曲线
        // 头部
        mPath.lineTo(pointHead.x, pointHead.y);
        canvas.drawPath(mPath, paintPink);
        // 右耳朵
        mPathEar1.lineTo(pointEar1.x, pointEar1.y);
        canvas.drawPath(mPathEar1, paintPink);
        // 左耳朵
        mPathEar2.lineTo(pointEar2.x, pointEar2.y);
        canvas.drawPath(mPathEar2, paintPink);
        // 肚子
        mPathBody.lineTo(pointBody.x, pointBody.y);
        canvas.drawPath(mPathBody, paintRed);
        // 右胳膊
        mPathArmRight.lineTo(pointArmRight.x, pointArmRight.y);
        canvas.drawPath(mPathArmRight, paintPink);
        // 右手
        mPathHandRight.lineTo(pointHandRight.x, pointHandRight.y);
        canvas.drawPath(mPathHandRight, paintPink);
        // 左胳膊
        mPathArmLeft.lineTo(pointArmLeft.x, pointArmLeft.y);
        canvas.drawPath(mPathArmLeft, paintPink);
        // 左手
        mPathHandLeft.lineTo(pointHandLeft.x, pointHandLeft.y);
        canvas.drawPath(mPathHandLeft, paintPink);
        // 尾巴
        mPathTail.lineTo(pointTail.x, pointTail.y);
        canvas.drawPath(mPathTail, paintPink);

    }

    private int dp2px(float dpValue) {
        return dp2px(getContext(), dpValue);
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void startAnimation() {
        animatorSet.start();
    }


}
