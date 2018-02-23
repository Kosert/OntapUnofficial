package me.kosert.ontap.model

/**
 * Created by Kosert on 2018-02-22.
 */
enum class SyncPeriod(val enumValue: Int, val minTime: Int, val maxTime: Int)
{
	SYNC_5(0, 5, 10),
	SYNC_15(1, 15, 20),
	SYNC_30(2, 30, 40),
	SYNC_60(3, 60, 75);

	companion object {

		fun fromInt(value: Int) : SyncPeriod?
		{
			return when(value)
			{
				0 -> SYNC_5
				1 -> SYNC_15
				2 -> SYNC_30
				3 -> SYNC_60
				else -> null
			}
		}

	}
}