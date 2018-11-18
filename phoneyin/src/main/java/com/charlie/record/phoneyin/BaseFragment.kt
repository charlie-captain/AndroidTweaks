package com.charlie.record.phoneyin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    protected var isVisiable = false

    protected var isLoaded = false

    protected var isInit = false

    protected lateinit var root: View

    protected abstract val resId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(resId, container)
        isInit = true
        if (isVisiable) {
            loadData()
        }
        return root
    }

    protected abstract fun realLoadData()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisiable = isVisibleToUser
        if (isVisiable && isInit) {
            loadData()
        }
    }

    private fun loadData() {
        if (!isLoaded) {
            realLoadData()
            isLoaded = true
        }
    }
}