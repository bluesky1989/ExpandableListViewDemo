package com.dyq.expandablelistviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * author:duyongqiang
 * mail: duyongqiang09@126.com
 * date:2019/7/18
 * Description:自定义BaseExpandableListAdapter
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> parentList;
    private List<List<String>> childList;



    public interface OnClickCollapse{
        void onCollapse(int groupPosition);
    }

    private OnClickCollapse onClickCollapse;

    public void setOnClickCollapse(OnClickCollapse onClickCollapse){
        this.onClickCollapse=onClickCollapse;
    }

    public MyExpandableListViewAdapter(Context context, List<String> parentList, List<List<String>> childList) {
        this.context = context;
        this.parentList = parentList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {//分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.parent_item,parent,false);
            groupViewHolder=new GroupViewHolder();
            groupViewHolder.tv_group=convertView.findViewById(R.id.tv_group);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder= (GroupViewHolder) convertView.getTag();
        }

        //设置GropItem显示内容
        groupViewHolder.tv_group.setText(parentList.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;

        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.child_item,parent,false);
            childViewHolder=new ChildViewHolder();
            childViewHolder.tv_child=convertView.findViewById(R.id.tv_child);
            childViewHolder.tv_collapse=convertView.findViewById(R.id.tv_collapse);
            convertView.setTag(childViewHolder);

        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }



        //设置ChildItem显示内容
        childViewHolder.tv_child.setText(childList.get(groupPosition).get(childPosition));


        if (isLastChild){
            childViewHolder.tv_collapse.setVisibility(View.VISIBLE);
        }else {
            childViewHolder.tv_collapse.setVisibility(View.GONE);
        }





        //点我收缩
        childViewHolder.tv_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCollapse.onCollapse(groupPosition);
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {//指定位置上的子元素是否可选中
        return true;
    }

    class GroupViewHolder{
        TextView tv_group;
    }

    class ChildViewHolder{
        TextView tv_child;
        TextView tv_collapse;
    }

}
