package com.dyq.expandablelistviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private ExpandableListView expandable_list_view;
    private MyExpandableListViewAdapter adapter;
    private List<String> groupList;
    private List<List<String>> childList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();

        //初始化数据
        initData();

        //设置adapter
        setAdapter();

        //设置点击事件
        setListener();

    }

    private void initView() {
        expandable_list_view=findViewById(R.id.expandable_list_view);
    }

    private void initData() {
        groupList=new ArrayList<>();
        childList=new ArrayList<>();

        groupList.add("水果");
        groupList.add("蔬菜");
        groupList.add("肉类");

        List<String> childData = null;
        for (int i=0;i<groupList.size();i++){
            if (i==0){
                childData=new ArrayList<>();
                childData.add("苹果");
                childData.add("香蕉");
                childData.add("桔子");
            }else if (i==1){
                childData=new ArrayList<>();
                childData.add("冬瓜");
                childData.add("青椒");

            } else if (i==2) {
                childData=new ArrayList<>();
                childData.add("牛肉");
                childData.add("羊肉");
                childData.add("猪肉");
            }

            childList.add(childData);
        }
    }


    private void setAdapter() {
        adapter=new MyExpandableListViewAdapter(this,groupList,childList);
        expandable_list_view.setAdapter(adapter);
    }

    private void setListener() {
        //设置GropItem被点击事件
        expandable_list_view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(MainActivity.this,"点击父内容项："+groupList.get(groupPosition),Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        //设置ChildItem被点击事件
        expandable_list_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(MainActivity.this,"点击子内容项："+childList.get(groupPosition).get(childPosition),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //设置只打开一个组,其他组默认收缩
        expandable_list_view.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i=0;i<groupList.size();i++){
                    if (i!=groupPosition){
                        expandable_list_view.collapseGroup(i);
                    }
                }
            }
        });


        //设置手动点击收缩事件
        adapter.setOnClickCollapse(new MyExpandableListViewAdapter.OnClickCollapse() {
            @Override
            public void onCollapse(int groupPosition) {
                expandable_list_view.collapseGroup(groupPosition);
            }
        });

    }

}
