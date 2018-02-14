package me.kosert.ontap.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

/**
 * Created by Kosert on 2018-02-14.
 */
abstract class AbstractActivity : AppCompatActivity()
{
	@CallSuper
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
	}
}