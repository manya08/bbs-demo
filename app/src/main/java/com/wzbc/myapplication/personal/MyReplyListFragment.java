package com.wzbc.myapplication.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wzbc.myapplication.R;
import com.wzbc.myapplication.home.GetData;
import com.wzbc.myapplication.home.MyNoteActivity;


/**
 * Created by WZBC on 2018/6/7.
 */

public class MyReplyListFragment extends ListFragment {
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.myreply_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intent intent =new Intent();
        int postid =intent.getIntExtra("postid",0);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), GetData.getDatareply(postid),R.layout.reply_list_item,
                new String[]{"username","reply_context","date"},
                new int[]{R.id.reply_username,R.id.reply_text,R.id.reply_time}
        );
        listView = (ListView)view.findViewById(R.id.myreply_list);
        listView.setAdapter(adapter);

        //点击列表数据跳转的具体数据
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),MyNoteActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

    }

}
