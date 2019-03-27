package com.goldenpiedevs.schedule.app.core.api.group

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class GroupManager(private var groupService: GroupService) {
    fun autocomplete(query: String) =
            groupService.searchGroupList(mutableMapOf(GroupService.QUERY to query))

    fun groupDetails(id: Int) =
            groupService.getGroup(id)

    fun getGroupInfoAsync(id: String) = GlobalScope.async {
        val group = groupService.getGroupInfo(id).await().body()?.data
        group?.save()
        return@async group
    }

}