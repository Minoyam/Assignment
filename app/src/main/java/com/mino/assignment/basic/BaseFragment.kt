package com.mino.assignment.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.mino.assignment.utils.transact
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    Fragment() {
    protected lateinit var binding: B
    private val containerId by lazy { (activity as BaseActivity<*>).getContainerId().toInt() }

    abstract fun init()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    fun onBackPressed() {
        if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
        } else {
            activity?.finish()
        }
    }

    protected fun startFragment(
        fragment: Fragment,
        isBackStack: Boolean = true
    ) {
        parentFragmentManager.transact {
            if (fragment.isAdded) {
                show(fragment)
            } else {
                replace(containerId, fragment, fragment.javaClass.simpleName)
                    .apply {
                        if (isBackStack) {
                            addToBackStack(fragment.javaClass.simpleName)
                        }
                    }
            }
        }
    }

}