package com.mino.assignment.basic

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.mino.assignment.utils.transact

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes
    private val layoutRes: Int,
    private var containerId: Int = -1
) : AppCompatActivity() {

    private lateinit var binding: B

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