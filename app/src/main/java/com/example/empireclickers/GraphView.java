package com.example.empireclickers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import java.util.ArrayList;

public class GraphView extends View {
    private List<DataPoint> dataSet = new ArrayList<>();
    private long xMin = 0;
    private long xMax = 0;
    private long yMin = 0;
    private long yMax = 0;
    private Paint dataPointPaint;
    private Paint dataPointFillPaint;
    private Paint dataPointLinePaint;
    private Paint axisLinePaint;

    public GraphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        dataPointPaint = new Paint();
        dataPointPaint.setColor(Color.BLUE);
        dataPointPaint.setStrokeWidth(7f);
        dataPointPaint.setStyle(Paint.Style.STROKE);

        dataPointFillPaint = new Paint();
        dataPointFillPaint.setColor(Color.WHITE);

        dataPointLinePaint = new Paint();
        dataPointLinePaint.setColor(Color.BLUE);
        dataPointLinePaint.setStrokeWidth(7f);
        dataPointLinePaint.setAntiAlias(true);

        axisLinePaint = new Paint();
        axisLinePaint.setColor(Color.RED);
        axisLinePaint.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int index = 0; index < dataSet.size(); index++) {
            DataPoint currentDataPoint = dataSet.get(index);
            float realX = toRealX(currentDataPoint.xVal);
            float realY = toRealY(currentDataPoint.yVal);
            if (index < dataSet.size() - 1) {
                DataPoint nextDataPoint = dataSet.get(index + 1);
                float startX = toRealX(currentDataPoint.xVal);
                float startY = toRealY(currentDataPoint.yVal);
                float endX = toRealX(nextDataPoint.xVal);
                float endY = toRealY(nextDataPoint.yVal);
                canvas.drawLine(startX, startY, endX, endY, dataPointLinePaint);
            }
            canvas.drawCircle(realX, realY, 7f, dataPointFillPaint);
            canvas.drawCircle(realX, realY, 7f, dataPointPaint);
        }
        canvas.drawLine(0f, 0f, 0f, getHeight(), axisLinePaint);
        canvas.drawLine(0f, getHeight(), getWidth(), getHeight(), axisLinePaint);
    }

    public void setData(List<DataPoint> newDataSet) {

        xMin = newDataSet.stream().mapToLong(dataPoint -> dataPoint.xVal).min().orElse(0);
        xMax = newDataSet.stream().mapToLong(dataPoint -> dataPoint.xVal).max().orElse(0);

//        yMin = newDataSet.stream().mapToLong(dataPoint -> dataPoint.yVal).min().orElse(0);
        yMin = 0;
        yMax = newDataSet.stream().mapToLong(dataPoint -> dataPoint.yVal).max().orElse(0);
        dataSet.clear();
        dataSet.addAll(newDataSet);
        invalidate();
    }

    private float toRealX(long value) {
        return (float) value / xMax * getWidth();
    }

    private float toRealY(long value) {
        return (float) value / yMax * getHeight();
    }
}

class DataPoint {
    public long xVal;
    public long yVal;

    public DataPoint(long xVal, long yVal) {
        this.xVal = xVal;
        this.yVal = yVal;
    }

    public long getxVal() {
        return xVal;
    }

    public void setxVal(long xVal) {
        this.xVal = xVal;
    }

    public long getyVal() {
        return yVal;
    }

    public void setyVal(long yVal) {
        this.yVal = yVal;
    }
}


