package com.wzbc.myapplication.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wzbc.myapplication.R;
import com.wzbc.myapplication.home.GetData;
import com.wzbc.myapplication.home.MyNoteActivity;


/**
 * Created by WZBC on 2018/6/5.
 */

public class MyAttentionListFragment extends ListFragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.myguanzhu_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        SimpleAdapter adapter =new SimpleAdapter(getActivity(), GetData.getData(), R.layout.attention_list_item,
                new String[]{"title","username","date","watch_number","reply_number"},
                new int[]{R.id.title,R.id.username,R.id.date,R.id.watch,R.id.reply});
        listView = (ListView)view.findViewById(R.id.myguanzhu_list);
        listView.setAdapter(adapter);


    }


}
