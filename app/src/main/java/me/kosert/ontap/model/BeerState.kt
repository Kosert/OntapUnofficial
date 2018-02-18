package me.kosert.ontap.model

import com.google.gson.annotations.Expose

/**
 * Created by Kosert on 2018-02-18.
 */
class BeerState(
		@Expose val name: String,
		@Expose val brewery: String
)
