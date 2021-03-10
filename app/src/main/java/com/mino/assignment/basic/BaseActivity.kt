package com.mino.assignment.basic

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mino.assignment.transact

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes
    private val layoutRes: Int,
    private var containerId: Int = -1
) : AppCompatActivity() {

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
        if (containerId == -1) {
            containerId = binding.root.id
        }
    }
    protected fun startFragment(
        fragment: Fragment,
        isBackStack: Boolean
    ) {
        supportFragmentManager.transact {
            if (fragment.isAdded) {
                show(fragment)
            } else {
                add(containerId, fragment, fragment.javaClass.simpleName)
                    .apply {
                        if (isBackStack) {
                            addToBackStack(fragment.javaClass.simpleName)
                        }
                    }
            }
        }
    }
    fun getContainerId() = containerId.toString()
}