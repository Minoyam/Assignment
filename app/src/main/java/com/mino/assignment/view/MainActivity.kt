package com.mino.assignment.view

import android.os.Bundle
import com.mino.assignment.R
import com.mino.assignment.basic.BaseActivity
import com.mino.assignment.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main,R.id.fl_container) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startFragment(BookListFragment.newInstance(),false)
    }
}