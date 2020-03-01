package com.fabuleux.wuntu.tv_bucket.kotlin.utils

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener


class RecyclerOnClickListener(context: Context?,
                              recyclerView: RecyclerView,
                              private val mclickListener: OnItemClickListener?) : OnItemTouchListener {
    private val gestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onLongItemClick(view: View?, position: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && mclickListener != null && gestureDetector.onTouchEvent(e)) {
            mclickListener.onItemClick(child, rv.getChildPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    init {
        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && mclickListener != null) {
                    mclickListener.onLongItemClick(child, recyclerView.getChildPosition(child))
                }
            }
        })
    }
}
