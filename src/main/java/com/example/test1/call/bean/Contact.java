package com.example.test1.call.bean;

import android.telecom.Call;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    //姓名
    public String mName;
    //号码
    public String mNumber;
    //通话记录
    public List<Calllog> mCalllogs = new ArrayList<>();
    //打入次数
    public int mIncomingNum = 0;
    //打出次数
    public int mOutGoingNum = 0;
    //未接次数
    public int mMissNum = 0;
    //头像id
    private int mImageId;
    //最近是否通话
    public int mLevel = 0;// 0 未通话 1 最近两天 2 今天刚刚
    //是否触发提醒
    public boolean isNotify= false;
}
