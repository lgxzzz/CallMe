package com.example.test1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.test1.adapter.ContactsAdapter;
import com.example.test1.call.CallMgr;
import com.example.test1.mpchar.BarChartActivity;
import com.example.test1.service.NotifyService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

//    private Peoples[] peoples={new Peoples("妈妈",R.drawable.mother),
//            new Peoples("妹妹",R.drawable.sister),new Peoples("朋友",R.drawable.friends),
//            new Peoples("同学",R.drawable.classmates),new Peoples("妹妹",R.drawable.sister)};
//    private List<Peoples> peoplesList =new ArrayList<>();
//    private PeoplesAdapter adapter_people;
//    private Notes[] notes={new Notes("妈妈","最近交流的很密切哦~"),
//            new Notes("妹妹","小懒鬼，好久没有来找我了。"),new Notes("朋友","好久没联系了..."),
//            new Notes("同学","最近交流的很密切哦~"),new Notes("妹妹","好久没联系了...")};
//    private List<Notes> notesList=new ArrayList<>();
//    private NotesAdapter adapter_notes;

    private ContactsAdapter mAapter;
    private CallMgr mCallMgr;
    private RecyclerView recyclerView_peoples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView=(NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_remind);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.nav_statistics:
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, BarChartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.startActivity(intent);
                        break;
                }
                return true;
            }
        });


        mCallMgr = CallMgr.getInstance();
        mCallMgr.setmListener(new CallMgr.ILoadCallogFinishListener() {
            @Override
            public void onFinish() {
                //加载数据后初始化界面
                initView();
                registerBroadcast();
                startNotifyService();
            }
        });
        mCallMgr.init();
        //授权
        getPersimmionInfo();
    }


    public void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.TEST_NOTIFY);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Constant.TEST_NOTIFY)){
                    mAapter.notifyDataSetChanged();
                }
            }
        },filter);
    }


    public void initView() {

        mAapter = new ContactsAdapter(mCallMgr.mContacts);
        recyclerView_peoples= findViewById(R.id.recycler_contacts);
        LinearLayoutManager leftllm = new LinearLayoutManager(this);
        leftllm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView_peoples.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView_peoples.setLayoutManager(leftllm);
        recyclerView_peoples.setAdapter(mAapter);



//        initPeoples();
//        initsNotes();
//        final RecyclerView recyclerView_peoples= findViewById(R.id.recycler_people);
//        final RecyclerView recyclerView_notes=(RecyclerView)findViewById(R.id.recycler_notes);
//
//        adapter_people=new PeoplesAdapter(peoplesList);
//        LinearLayoutManager leftllm = new LinearLayoutManager(this);
//        leftllm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView_peoples.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        recyclerView_peoples.setLayoutManager(leftllm);
//        recyclerView_peoples.setAdapter(adapter_people);
//        recyclerView_peoples.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (recyclerView.getScrollState() !=RecyclerView.SCROLL_STATE_IDLE) {
//                    recyclerView_notes.scrollBy(280+dx,dy);
//                }
//            }
//        });
//
//        adapter_notes=new NotesAdapter(notesList);
//        LinearLayoutManager rightllm = new LinearLayoutManager(this);
//        rightllm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView_notes.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        recyclerView_notes.setLayoutManager(rightllm);
//        recyclerView_notes.setAdapter(adapter_notes);
//        recyclerView_notes.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (recyclerView.getScrollState() !=RecyclerView.SCROLL_STATE_IDLE) {
//                    recyclerView_peoples.scrollBy(dx-280, dy);
//                }
//            }
//        });
    }

//    private void initPeoples(){
//        for(int i=0;i<peoples.length;i++){
//            peoplesList.add(peoples[i]);
//        }
//    }
//
//    private void initsNotes(){
//        for (int j=0;j<notes.length;j++){
//            notesList.add(notes[j]);
//        }
//    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rank:
                Toast.makeText(this,"You click Rank",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"you click delete",Toast.LENGTH_SHORT).show();
                break;
                default:
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1005){
            mCallMgr.init();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCallMgr.init();
    }

    //授权信息
    private void getPersimmionInfo() {
        if (Build.VERSION.SDK_INT >= 23) {
            //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                //手机为Android6.0的版本,未授权则动态请求授权
                //2. 申请请求授权权限
                //1. Activity
                // 2. 申请的权限名称
                // 3. 申请权限的 请求码
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.READ_CALL_LOG//通话记录
                        ,Manifest.permission.READ_CONTACTS//联系人记录
                                ,Manifest.permission.CALL_PHONE
                        }, 1005);
            } else {//手机为Android6.0的版本,权限已授权可以使用
                // 执行下一步
//                initContacts();
                mCallMgr.init();
            }
        } else {//手机为Android6.0以前的版本，可以使用
//            initContacts();
            mCallMgr.init();
        }

    }

    //启动通知服务
    public void startNotifyService(){
          Intent intent = new Intent();
          intent.setClass(this, NotifyService.class);
          startService(intent);
    }
}
