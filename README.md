# GooView粘性控件

- 了解几何图形工具的用法
- 掌握画不规则图形的方法

应用场景：未读提醒，效果图：

<img src="http://img.blog.csdn.net/20170218120957806?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYXhpMjk1MzA5MDY2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" width="250" /> <img src="http://img.blog.csdn.net/20170218121011415?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYXhpMjk1MzA5MDY2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" width="250" /> <img src="http://img.blog.csdn.net/20170218121024431?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYXhpMjk1MzA5MDY2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" width="250" />

# 绘制一帧的效果
画一帧粘性控件的步骤分析
1．画一个固定圆
2．画一个拖拽圆
3．画中间连接部分
将中间连接部分水平放置，四个角的坐标定为固定值，分别标记上点的编号，矩形中心的点为控件点，画曲线时用

![](http://img.blog.csdn.net/20170218121253768?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYXhpMjk1MzA5MDY2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

自定义一个GooView 继承View
```java
public class GooView extends View {
    private Paint paint;
    public GooView(Context context) {
        this(context,null);
    }
    public GooView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public GooView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画中间连接部分
        Path path = new Path();
        //跳到点1,默认为(0f,0f)
        path.moveTo(250f, 250f);
        //从点1->点2 画曲线
        path.quadTo(150f, 300f, 50f, 250f);
        //从点2->点3 画直线
        path.lineTo(50f, 350f);
        //从点3->点4 画曲线
        path.quadTo(150f, 300f, 250f, 350f);
        canvas.drawPath(path, paint);

        //画拖拽圆
        canvas.drawCircle(90f, 90f, 16f, paint);

        //画固定圆
        canvas.drawCircle(150f, 150f, 12f, paint);
    }
}
```
第20-30 行用Path 画中间曲线部分
第25 行quadTo(x1,y1,x2,y2)方法可以画当前所在点到x2,y2 间的一条曲线，x1,y1 是当前点与x2,y2 间的一个控件点，它的位置决定曲线弯曲的方向和弧度，将GooView 显示到MainActivity 中

```java
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(new GooView(this));
    }
}
```

# 替换变量

![](http://img.blog.csdn.net/20170218121413936?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYXhpMjk1MzA5MDY2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

分别给拖拽圆，固定圆的圆心，半径，两个附着点命名，修改GooView 的onDraw()方法

```java
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //固定圆的两个附着点
    PointF[] mStickPoints = new PointF[]{
            new PointF(250f, 250f),new PointF(250f, 350f)
    };

    //固定圆的两个附着点
    PointF[] mDragPoints = new PointF[]{
            new PointF(50f, 250f),new PointF(50f, 350f)
    };

    //控制点
    PointF mControlPoint = new PointF(150f, 300f);

    //画中间连接部分
    Path path = new Path();
    //跳到点1,默认为(0f,0f)
    path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
    //从点1->点2 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mDragPoints[0].x, mDragPoints[0].y);
    //从点2->点3 画直线
    path.lineTo( mDragPoints[1].x, mDragPoints[1].y);
    //从点3->点4 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mStickPoints[1].x, mStickPoints[1].y);
    canvas.drawPath(path, paint);

    //画拖拽圆
    //拖拽圆圆心
    PointF mDragCenter = new PointF(90f, 90f);
    //拖拽圆半径
    float mDragRadius = 16f;
    canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, paint);

    //画固定圆
    //固定圆圆心
    PointF mStickCenter = new PointF(150f, 150f);
    //固定圆半径
    float mStickRadius = 12f;
    canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickRadius, paint);
}
```

第3-14 行替换附着点及控制点
第30-40 行替换拖拽圆及固定圆的圆心及半径
将替换后的变量转换成GooView 的成员变量

```java
// 固定圆圆心
PointF mStickCenter = new PointF(150f, 150f);
// 固定圆半径
float mStickRadius = 12f;
// 拖拽圆圆心
PointF mDragCenter = new PointF(90f, 90f);
// 拖拽圆半径
float mDragRadius = 16f;
// 固定圆的两个附着点
PointF[] mStickPoints = new PointF[] { new PointF(250f, 250f),
        new PointF(250f, 350f) };
// 固定圆的两个附着点
PointF[] mDragPoints = new PointF[] { new PointF(50f, 250f),
        new PointF(50f, 350f) };

// 控制点
PointF mControlPoint = new PointF(150f, 300f);
@Override
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 画中间连接部分
    Path path = new Path();
    // 跳到点1,默认为(0f,0f)
    path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
    // 从点1->点2 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mDragPoints[0].x,
            mDragPoints[0].y);
    // 从点2->点3 画直线
    path.lineTo(mDragPoints[1].x, mDragPoints[1].y);
    // 从点3->点4 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mStickPoints[1].x,
            mStickPoints[1].y);
    canvas.drawPath(path, paint);
    // 画拖拽圆
    canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, paint);
    // 画固定圆
    canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickRadius, paint);
}
```

#  计算变量

![](http://img.blog.csdn.net/20170218121516113?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYXhpMjk1MzA5MDY2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

拖拽圆和固定圆的圆心和半径已知，角3 的正弦值为两圆心纵坐标之差比上横坐标之差，则角3 的角度可知，则角1 可知，AB,AC 的长度即可计算出来，mDragPoints[0]的坐标可以计算出来，同理其它三个附着点坐标也可知。mControlPoint 为两圆心连线的中点

几何图形工具

```java
/**
 * 几何图形工具
 */
public class GeometryUtil {

    /**
     * As meaning of method name.
     * 获得两点之间的距离
     * @param p0
     * @param p1
     * @return
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) +
                Math.pow(p0.x - p1.x, 2));
        return distance;
    }

    /**
     * Get middle point between p1 and p2.
     * 获得两点连线的中点
     * @param p1
     * @param p2
     * @return
     */
    public static PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

    /**
     * Get point between p1 and p2 by percent.
     * 根据百分比获取两点之间的某个点坐标
     * @param p1
     * @param p2
     * @param percent
     * @return
     */
    public static PointF getPointByPercent(PointF p1, PointF p2, float percent) {
        return new PointF(evaluateValue(percent, p1.x , p2.x),
                evaluateValue(percent, p1.y , p2.y));
    }

    /**
     * 根据分度值，计算从start 到end 中，fraction 位置的值。fraction 范围为0 -> 1
     * @param fraction
     * @param start
     * @param end
     * @return
     */
    public static float evaluateValue(float fraction, Number start, Number end){
        return start.floatValue() + (end.floatValue() - start.floatValue()) * fraction;
    }


    /**
     * Get the point of intersection between circle and line.
     * 获取通过指定圆心，斜率为lineK 的直线与圆的交点。
     *
     * @param pMiddle The circle center point.
     * @param radius The circle radius.
     * @param lineK The slope of line which cross the pMiddle.
     * @return
     */
    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, DoublelineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if(lineK != null){
            radian= (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        }else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

        return points;
    }
}
```

利用几何图形工具类计算四个附着点坐标及控件点坐标

```java
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float yOffset = mStickCenter.y - mDragCenter.y;
    float xOffset = mStickCenter.x - mDragCenter.x;

    Double lineK = null;
    if(xOffset != 0){
        //xOffset 分母不能为0
        lineK = (double) (yOffset/xOffset);
    }
    //计算四个附着点
    mDragPoints = GeometryUtil.getIntersectionPoints(mDragCenter,
            mDragRadius, lineK);
    mStickPoints = GeometryUtil.getIntersectionPoints(mStickCenter,
            mStickRadius, lineK);
    //一个控制点
    mControlPoint = GeometryUtil.getMiddlePoint(mDragCenter, mStickCenter);

    // 画中间连接部分
    Path path = new Path();
    // 跳到点1,默认为(0f,0f)
    path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
    // 从点1->点2 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mDragPoints[0].x,
            mDragPoints[0].y);
    // 从点2->点3 画直线
    path.lineTo(mDragPoints[1].x, mDragPoints[1].y);
    // 从点3->点4 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mStickPoints[1].x,
            mStickPoints[1].y);
    canvas.drawPath(path, paint);

    // 画拖拽圆

    canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, paint);

    // 画固定圆

    canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickRadius, paint);
}
```
第3-17 行计算四个附着点及控制点坐标

# 1.4 计算固定圆半径
GooView 重写onSizeChanged()方法，计算状态栏高度

```java
@Override
protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    //获取状态栏的高度，传入一个显示在屏幕上的view 即可
    statusBarHeight = Utils.getStatusBarHeight(this);
}
```

Utils.java

```java
public class Utils {
    public static Toast mToast;
    public static void showToast(Context mContext, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
    /**
     * 获取状态栏高度
     *
     * @param v
     * @return
     */
    public static int getStatusBarHeight(View v) {
        if (v == null) {
            return 0;
        }
        Rect frame = new Rect();
        v.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
}
```

修改onDraw()方法
 
```java
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float yOffset = mStickCenter.y - mDragCenter.y;
    float xOffset = mStickCenter.x - mDragCenter.x;

    Double lineK = null;
    if(xOffset != 0){
        //xOffset 分母不能为0
        lineK = (double) (yOffset/xOffset);
    }
    //计算四个附着点
    mDragPoints = GeometryUtil.getIntersectionPoints(mDragCenter,
            mDragRadius, lineK);
    mStickPoints = GeometryUtil.getIntersectionPoints(mStickCenter,
            mStickRadius, lineK);
    //一个控制点
    mControlPoint = GeometryUtil.getMiddlePoint(mDragCenter, mStickCenter);
    //移动画布
    canvas.save();
    canvas.translate(0, -statusBarHeight);
    // 画中间连接部分
    Path path = new Path();
    // 跳到点1,默认为(0f,0f)
    path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
    // 从点1->点2 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mDragPoints[0].x,
            mDragPoints[0].y);
    // 从点2->点3 画直线
    path.lineTo(mDragPoints[1].x, mDragPoints[1].y);
    // 从点3->点4 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mStickPoints[1].x,
            mStickPoints[1].y);
    canvas.drawPath(path, paint);

    // 画拖拽圆

    canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, paint);

    // 画固定圆

    canvas.drawCircle(mStickCenter.x, mStickCenter.y, mStickRadius, paint);
    canvas.restore();
}
```

第18-20 行把画布向上移动状态栏的高度，移动前需要保存一下当前状态，做完操作后需要恢复一下状态，由于在onTouchEvent()中用的是getRawX(),getRawY()获取的是相对屏幕的坐标，所以GooView画图操作时需要向上移到一个状态栏的高度才能刚好和手指重合拖拽圆跟随手指移动时，随着拖拽与固定圆的距离的变大，固定圆的半径越来越小

```java
//允许的最大距离
float farestDistance = 80f;
/**
 * 通过两圆圆心的距离，计算固定圆的半径
 * @return
 */
private float computeStickRadius() {
    //通过几何图形工具类可以计算出两圆圆心的距离，distance 是可以大于80f;
    float distance = GeometryUtil.getDistanceBetween2Points(mDragCenter, mStickCenter);

    //需要的是0.0f -> 1.0f 的值，所在大于80f 让distance 等于80f
    distance = Math.min(farestDistance, distance);

    float percent = distance/farestDistance;

    //需要固定圆心半径在12f -> 3f 间变化，可以利用类型估值器

    return evaluate(percent, mStickRadius, mStickRadius*0.25f);
}
//FloatEvaluator.java 中拷贝
public Float evaluate(float fraction, Number startValue, Number endValue) {
    float startFloat = startValue.floatValue();
    return startFloat + fraction * (endValue.floatValue() - startFloat);
}
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //通过两圆圆心的距离，计算固定圆的半径
    float tempStickRadius = computeStickRadius();

    float yOffset = mStickCenter.y - mDragCenter.y;
    float xOffset = mStickCenter.x - mDragCenter.x;

    Double lineK = null;
    if(xOffset != 0){
        lineK = (double) (yOffset/xOffset);
    }
    //计算四个附着点
    mDragPoints = GeometryUtil.getIntersectionPoints(mDragCenter, mDragRadius,lineK);
    mStickPoints = GeometryUtil.getIntersectionPoints(mStickCenter, tempStickRadius,lineK);
    //一个控制点
    mControlPoint = GeometryUtil.getMiddlePoint(mDragCenter, mStickCenter);

    //移动画布
    canvas.save();
    canvas.translate(0, -statusBarHeight);

    // 画中间连接部分
    Path path = new Path();
    // 跳到点1,默认为(0f,0f)
    path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
    // 从点1->点2 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mDragPoints[0].x,
            mDragPoints[0].y);
    // 从点2->点3 画直线
    path.lineTo(mDragPoints[1].x, mDragPoints[1].y);
    // 从点3->点4 画曲线
    path.quadTo(mControlPoint.x, mControlPoint.y, mStickPoints[1].x,
            mStickPoints[1].y);
    canvas.drawPath(path, paint);

    // 画拖拽圆

    canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, paint);

    // 画固定圆

    canvas.drawCircle(mStickCenter.x, mStickCenter.y, tempStickRadius, paint);
    canvas.restore();
}
```

第2 行定义最大的拖拽距离为80f
第7-24 行拖拽圆与固定圆的距离大于80f 时，取80f,通过两圆圆心的距离与80f 相对可以求出一个0.0f
到1.0f 的值，再通过估值器可以获得固定圆的半径在mStickRadius，mStickRadius*0.25f 间的变化值
第27-28 行通过两圆圆心的距离计算固定圆的半径tempStickRadius
第39,67 行将mStickRadius 替换成计算出来的半径tempStickRadius

# 事件处理
## 事件处理的分析
1．超出最大范围：拖拽圆与固定圆断开，松手后消失
2．超出最大范围：又放回去，恢复
3．没有超出最大范围：松手，回弹动画，恢复

## 事件处理的实现
修改onTouchEvent()方法

```java
//是否已经消失
private boolean isDisappear = false;
//是否超出范围
private boolean isOutOfRange = false;
public boolean onTouchEvent(MotionEvent event) {
    float x;
    float y;
    switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            //重置变量
            isDisappear = false;
            isOutOfRange = false;
            x = event.getRawX();
            y = event.getRawY();
            updateDragCenter(x, y);
            break;
        case MotionEvent.ACTION_MOVE:
            x = event.getRawX();
            y = event.getRawY();
            updateDragCenter(x, y);
            float d = GeometryUtil.getDistanceBetween2Points(mDragCenter,
                    mStickCenter);
            // 超出范围断开
            if (d > farestDistance) {
                isOutOfRange = true;
                invalidate();
            }

            break;
        case MotionEvent.ACTION_UP:
            if (isOutOfRange) {
                // 刚刚超出了范围
                float dis = GeometryUtil.getDistanceBetween2Points(mDragCenter,
                        mStickCenter);
                if (dis > farestDistance) {
                    // 超出范围，松手，断开，消失
                    isDisappear = true;
                    invalidate();
                } else {
                    // 超出范围，断开，又放回去了，恢复
                    updateDragCenter(mStickCenter.x, mStickCenter.y);
                }
            } else {
                // 没有超出范围，松手，回弹，恢复
                final PointF startP = new PointF(mDragCenter.x, mDragCenter.y);
                ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
                animator.setDuration(500);
                // 插值器，回弹效果
                animator.setInterpolator(new OvershootInterpolator(4));
                animator.addUpdateListener(new AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 生成0.0f ->1.0f 间的值
                        float percent = animation.getAnimatedFraction();
                        // 计算从开始点startP 到mStickCenter 间的所有值
                        PointF p = GeometryUtil.getPointByPercent(startP,
                                mStickCenter, percent);
                        updateDragCenter(p.x, p.y);
                    }
                });
                animator.start();
            }
            break;
        default:
            break;
    }
    return true;
}
```
第1-2 行创建两个布尔变量记录是否已经消失及是否超出范围
第11-12 行手指重新按下时，重置变量
第21-27 行拖拽过程中记录是否超出范围
第32-38 行超出范围，松手，消失，标记当前为消失状态
第39-41 行超出范围，又放回去了，需要恢复，直接更新拖拽圆圆心为固定圆心即可
第45-62 行没有超出范围，松手，需要回弹动画，恢复
修改onDraw()方法

```java
protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 通过两圆圆心的距离，计算固定圆的半径
    float tempStickRadius = computeStickRadius();

    float yOffset = mStickCenter.y - mDragCenter.y;
    float xOffset = mStickCenter.x - mDragCenter.x;

    Double lineK = null;
    if (xOffset != 0) {
        lineK = (double) (yOffset / xOffset);
    }
    // 计算四个附着点
    mDragPoints = GeometryUtil.getIntersectionPoints(mDragCenter,
            mDragRadius, lineK);
    mStickPoints = GeometryUtil.getIntersectionPoints(mStickCenter,
            tempStickRadius, lineK);
    // 一个控制点
    mControlPoint = GeometryUtil.getMiddlePoint(mDragCenter, mStickCenter);

    // 移动画布
    canvas.save();
    canvas.translate(0, -statusBarHeight);

    // 画出最大范围（参考）
    // 只画边线
    paint.setStyle(Style.STROKE);
    canvas.drawCircle(mStickCenter.x, mStickCenter.y, farestDistance, paint);
    // 填充
    paint.setStyle(Style.FILL);
    if(!isDisappear){
        //没有消失时，才绘制内容
        if (!isOutOfRange) {
            //没有超出范围时，才画连接部分和固定圆
            // 画中间连接部分
            Path path = new Path();
            // 跳到点1,默认为(0f,0f)
            path.moveTo(mStickPoints[0].x, mStickPoints[0].y);
            // 从点1->点2 画曲线
            path.quadTo(mControlPoint.x, mControlPoint.y, mDragPoints[0].x,
                    mDragPoints[0].y);
            // 从点2->点3 画直线
            path.lineTo(mDragPoints[1].x, mDragPoints[1].y);
            // 从点3->点4 画曲线
            path.quadTo(mControlPoint.x, mControlPoint.y,
                    mStickPoints[1].x, mStickPoints[1].y);
            canvas.drawPath(path, paint);

            // 画固定圆
            canvas.drawCircle(mStickCenter.x, mStickCenter.y,
                    tempStickRadius, paint);
        }
        // 画拖拽圆
        canvas.drawCircle(mDragCenter.x, mDragCenter.y, mDragRadius, paint);
    }
    canvas.restore();
}
```

第31-54 行没有消失时，才绘制内容，没有超出范围时，才绘制连接部分及固定圆

# 事件的监听回调
定义监听接口

```java
private OnUpdateListener onUpdateListener;
public OnUpdateListener getOnUpdateListener() {
    return onUpdateListener;
}
public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
    this.onUpdateListener = onUpdateListener;
}
public interface OnUpdateListener{
    //消失时回调
    public void onDisappear();
    //恢复时回调，分为超出范围恢复及没有超出范围恢复
    public void onReset(boolean isOutOfRange);
}
```

修改onTouchEvent()方法

```java
public boolean onTouchEvent(MotionEvent event) {
    float x;
    float y;
    switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            //重置变量
            isDisappear = false;
            isOutOfRange = false;
            x = event.getRawX();
            y = event.getRawY();
            updateDragCenter(x, y);
            break;
        case MotionEvent.ACTION_MOVE:
            x = event.getRawX();
            y = event.getRawY();
            updateDragCenter(x, y);
            float d = GeometryUtil.getDistanceBetween2Points(mDragCenter,
                    mStickCenter);
            // 超出范围断开
            if (d > farestDistance) {
                isOutOfRange = true;
                invalidate();
            }

            break;
        case MotionEvent.ACTION_UP:
            if (isOutOfRange) {
                // 刚刚超出了范围
                float dis = GeometryUtil.getDistanceBetween2Points(mDragCenter,
                        mStickCenter);
                if (dis > farestDistance) {
                    // 超出范围，松手，断开，消失
                    isDisappear = true;
                    invalidate();
                    if(onUpdateListener != null){
                        onUpdateListener.onDisappear();
                    }
                } else {
                    // 超出范围，断开，又放回去了，恢复
                    updateDragCenter(mStickCenter.x, mStickCenter.y);
                    if(onUpdateListener != null){
                        onUpdateListener.onReset(true);
                    }
                }
            } else {
                // 没有超出范围，松手，回弹，恢复
                final PointF startP = new PointF(mDragCenter.x, mDragCenter.y);
                ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
                animator.setDuration(500);
                // 插值器，回弹效果
                animator.setInterpolator(new OvershootInterpolator(4));
                animator.addUpdateListener(new AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 生成0.0f ->1.0f 间的值
                        float percent = animation.getAnimatedFraction();
                        // 计算从开始点startP 到mStickCenter 间的所有值
                        PointF p = GeometryUtil.getPointByPercent(startP,
                                mStickCenter, percent);
                        updateDragCenter(p.x, p.y);
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        //需要在动画结束时调用
                        if(onUpdateListener != null){
                            onUpdateListener.onReset(false);
                        }
                    }
                });
                animator.start();
            }
            break;

        default:
            break;
    }
    return true;
}
```
第35-37 行标记消失时，回调onDisappear()方法
第41-42 行恢复时回调onReset()方法，此时超出过范围，所以参数传入true
第64-73 行添加动画监听，在动画结束时回调onReset()方法，此时没有超出范围，所以参数传入false

修改MainActivity 测试监听回调

```java
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        GooView view = new GooView(this);
        setContentView(view);
        view.setOnUpdateListener(new OnUpdateListener() {
            @Override
            public void onReset(boolean isOutOfRange) {
                Utils.showToast(getApplicationContext(), "onReset:"+isOutOfRange);
            }

            @Override
            public void onDisappear() {
                Utils.showToast(getApplicationContext(), "onDisappear");
            }
        });
    }
}
```

# 关于我

- Email：<815712739@qq.com>
- CSDN博客：[Allen Iverson](http://blog.csdn.net/axi295309066)
- 新浪微博：[AndroidDeveloper](http://weibo.com/u/1848214604?topnav=1&amp;wvr=6&amp;topsug=1&amp;is_all=1)

# License

    Copyright 2015 AllenIverson

    Copyright 2012 Jake Wharton
    Copyright 2011 Patrik Åkerfeldt
    Copyright 2011 Francisco Figueiredo Jr.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
