package me.kosert.ontap.ui.activities.main.fragments.favorites

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.favorites_fragment.*
import me.kosert.ontap.R
import me.kosert.ontap.ui.activities.main.fragments.AbstractMainController
import me.kosert.ontap.ui.activities.main.fragments.MainAbstractFragment

/**
 * Created by Kosert on 2018-02-10.
 */

class FavoritesFragment : MainAbstractFragment()
{
	override val recyclerView: RecyclerView
		get() = favs_recycler
	override val controller: AbstractMainController
		get() = TODO("not implemented")

	companion object
	{
		fun newInstance() : FavoritesFragment
		{
			val fragment = FavoritesFragment()
			val args = Bundle()

			fragment.arguments = args
			return fragment
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		return inflater.inflate(R.layout.favorites_fragment, container, false)
	}

}