package com.goldenpiedevs.schedule.app.core.api.group

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.async

class GroupManager(private var groupService: GroupService) {
    fun autocomplete(query: String) =
            groupService.searchGroupList(mutableMapOf(GroupService.QUERY to query))

    fun groupDetails(id: Int) =
            groupService.getGroup(id)

    fun getGroupInfo(id: String) = GlobalScope.async {
        groupService.getGroupInfo(id).await().body()?.data
    }

}