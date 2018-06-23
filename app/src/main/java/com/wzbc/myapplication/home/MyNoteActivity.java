package com.wzbc.myapplication.home;

import android.support.v4.app.Fragment;


/**
 * Created by WZBC on 2018/4/11.
 */

public class MyNoteActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        long id =getIntent().getLongExtra("id",0);
        return  MyNoteFragment.NewInstance(id);
    }
}
