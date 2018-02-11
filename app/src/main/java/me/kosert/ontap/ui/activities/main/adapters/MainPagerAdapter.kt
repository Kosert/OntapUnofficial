package me.kosert.ontap.ui.activities.main.adapters


import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import me.kosert.ontap.ui.activities.main.fragments.MainAbstractFragment
import me.kosert.ontap.ui.activities.main.fragments.MainFragmentType
import me.kosert.ontap.ui.activities.main.fragments.cities.CitiesFragment
import me.kosert.ontap.ui.activities.main.fragments.favorites.FavoritesFragment

/**
 * Created by Kosert on 2018-02-10.
 */

class MainPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm)
{
	private val NUM_ITEMS = 2
	private val fragments = SparseArray<MainAbstractFragment>()

	fun getFragment(position: Int): MainAbstractFragment?
	{
		return fragments.get(position)
	}

	override fun instantiateItem(container: ViewGroup?, position: Int): Any
	{
		val fragment = super.instantiateItem(container, position) as MainAbstractFragment
		fragments.put(position, fragment)
		return fragment
	}

	override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?)
	{
		fragments.remove(position)
		super.destroyItem(container, position, `object`)
	}

	override fun getItem(position: Int): Fragment?
	{

		return when(position)
		{
			0 -> {
				FavoritesFragment.newInstance()
			}
			1 -> {
				CitiesFragment.newInstance()
			}
			else ->{
				null
			}
		}

	}

	override fun getPageTitle(position: Int): CharSequence
	{
		return context.getString(MainFragmentType.values().get(position).titleId)
	}

	override fun getCount() = NUM_ITEMS

}