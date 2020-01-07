package com.example.test1.mpchar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.test1.R;
import com.example.test1.call.CallMgr;
import com.example.test1.call.bean.Contact;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends Activity{
    private BarChart mBarChart;
    private BarData mBarData;
    private CallMgr mCallMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        mBarChart = findViewById(R.id.barChart);
        mCallMgr = new CallMgr(this);
        mCallMgr.setmListener(new CallMgr.ILoadCallogFinishListener() {
            @Override
            public void onFinish() {
                //加载数据后初始化界面
                initData();
                initBarChart();
            }
        });
        mCallMgr.init();
    }

    private void initData() {
        // y 数据
        ArrayList<BarEntry> yValues = new ArrayList<>();
        // y2 数据
        ArrayList<BarEntry> yValues2 = new ArrayList<>();

        for (int x = 0; x < mCallMgr.mContacts.size(); x++) {
            Contact contact = mCallMgr.mContacts.get(x);
            yValues.add(new BarEntry(x,contact.mIncomingNum+contact.mOutGoingNum));
        }

        // y 轴数据集
        BarDataSet barDataSet = new BarDataSet(yValues, "通话次数");
        barDataSet.setColor(Color.YELLOW);

        mBarData = new BarData(barDataSet);
    }

    private void initBarChart() {
// 设置 条形图 简介
        // 设置 柱子的宽度
         mBarData.setBarWidth(0.2f);

        // 获取 x 轴
        XAxis xAxis = mBarChart.getXAxis();
        // 设置 x 轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 取消 垂直 网格线
        xAxis.setDrawGridLines(false);
        // 设置 x 轴 坐标旋转角度
        xAxis.setLabelRotationAngle(10f);
        // 设置 x 轴 坐标字体大小
        xAxis.setTextSize(15f);
        // 设置 x 坐标轴 颜色
        xAxis.setAxisLineColor(Color.RED);
        // 设置 x 坐标轴 宽度
        xAxis.setAxisLineWidth(0.5f);
        // 设置 x轴 的刻度数量
        xAxis.setLabelCount(mCallMgr.mContacts.size());

        xAxis.setValueFormatter(new ContactValueFormat());

        // 获取 右边 y 轴
        YAxis mRAxis = mBarChart.getAxisRight();
        // 隐藏 右边 Y 轴
        mRAxis.setEnabled(false);
        // 获取 左边 Y轴
        YAxis mLAxis = mBarChart.getAxisLeft();
        // 取消 左边 Y轴 坐标线
        mLAxis.setDrawAxisLine(false);
        // 取消 横向 网格线
        mLAxis.setDrawGridLines(false);
        // 设置 Y轴 的刻度数量
        mLAxis.setLabelCount(10);
        mLAxis.setAxisMinimum(0);

        mBarChart.setData(mBarData);

    }

    //替换x轴坐标为联系人名字
    public class ContactValueFormat extends ValueFormatter{
        @Override
        public String getFormattedValue(float value) {
            int index = ((int) value % mCallMgr.mContacts.size());
            String name = mCallMgr.mContacts.get(index).mName;
            return name;
        }
    }
}
