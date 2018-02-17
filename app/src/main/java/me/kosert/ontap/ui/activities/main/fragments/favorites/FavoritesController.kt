package me.kosert.ontap.ui.activities.main.fragments.favorites

import android.content.Context
import me.kosert.ontap.data.StaticProvider
import me.kosert.ontap.model.Multitap
import me.kosert.ontap.ui.activities.main.fragments.ICallbacks
import me.kosert.ontap.ui.activities.main.fragments.MainAbstractController
import me.kosert.ontap.util.Logger
import kotlin.properties.Delegates

/**
 * Created by Kosert on 2018-02-16.
 */
class FavoritesController : MainAbstractController()
{
	override var callbacks by Delegates.notNull<ICallbacks>()

	override val logger = Logger("FavoritesController")

	override fun getDisplayedList(): List<Multitap>
	{
		return StaticProvider.Favorites.favoritesList
	}

	override fun onCreate(context: Context, callbacks: ICallbacks)
	{
		super.onCreate(context, callbacks)
		this.callbacks = callbacks
	}
}