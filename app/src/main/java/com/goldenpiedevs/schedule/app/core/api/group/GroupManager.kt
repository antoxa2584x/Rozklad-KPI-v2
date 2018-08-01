package com.goldenpiedevs.schedule.app.core.api.group

class GroupManager(var groupService: GroupService) {
    fun autocomplete(query: String) =
            groupService.searchGroupList(mutableMapOf(GroupService.QUERY to query))

    fun groupDetails(id: Int) =
            groupService.getGroup(id)


}