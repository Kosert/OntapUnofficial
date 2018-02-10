package me.kosert.ontap.ui.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Kosert on 2018-02-10.
 */

class CustomViewPager : ViewPager
{
	var isSwipingEnabled = true

	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

	override fun onTouchEvent(event: MotionEvent): Boolean
	{
		return this.isSwipingEnabled && super.onTouchEvent(event)
	}

	override fun onInterceptTouchEvent(event: MotionEvent): Boolean
	{
		return this.isSwipingEnabled && super.onInterceptTouchEvent(event)
	}
}