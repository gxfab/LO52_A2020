package com.tps.appf1.LayoutManagers

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.room.Room
import com.tps.appf1.R
import com.tps.appf1.databases.race.teams.TeamDatabase

/* class TeamListViewer, AdapterView.OnItemClickListener {

    //Test code to display the list of teams, runners....
    var teamsDB = Room.databaseBuilder(this.requireContext(), TeamDatabase::class.java, "teams-db").allowMainThreadQueries().build()

    var teamList = arrayOf(teamsDB.teamDatabaseDao().getTeamsList())
    var listView: ListView = view.findViewById<ListView>(R.id.listViewTeam)
    var arrayAdapter: ArrayAdapter<List<Int>> = ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_single_choice, teamList)

    listView.adapter = arrayAdapter
    listView.choiceMode = ListView.CHOICE_MODE_SINGLE
    listView.onItemClickListener = this
}

override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    TODO("Not yet implemented")
} */