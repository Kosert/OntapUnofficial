package com.squareup.picasso

/**
 * Created by Kosert on 2018-02-18.
 */

class PicassoUtil
{
	companion object
	{
		fun clearCache(p : Picasso)
		{
			p.cache.clear()
		}
	}
}