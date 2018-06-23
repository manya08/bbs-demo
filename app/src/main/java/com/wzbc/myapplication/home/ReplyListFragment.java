package com.wzbc.myapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wzbc.myapplication.R;

import static com.wzbc.myapplication.home.MyNoteFragment.postid;

/**
 * Created by Administrator on 2018/6/11/011.
 */

public class ReplyListFragment extends ListFragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.reply_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        SimpleAdapter adapter = new SimpleAdapter(getActivity(),GetData.getDatareply(postid),R.layout.reply_list_item,
                new String[]{"ReplyAccount","ReplyContent","Replytime"},
                new int[]{R.id.reply_username,R.id.reply_text,R.id.reply_time}
                );
        listView = (ListView)view.findViewById(R.id.reply_list);
        listView.setAdapter(adapter);
    }

}
