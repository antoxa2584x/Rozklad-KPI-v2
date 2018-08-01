package com.goldenpiedevs.schedule.app.core.api.group

import com.goldenpiedevs.schedule.app.core.api.GroupService

class GroupManager(var groupService: GroupService) {
    fun autocomplete(query: String) =
            groupService.searchGroupList(mutableMapOf(GroupService.QUERY to query))

    fun groupDetails(id: Int) =
            groupService.getGroup(id)


}