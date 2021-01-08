package com.codep25.codep25.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentTeamManagementListBinding
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.model.entity.TeamWithParticipants
import com.codep25.codep25.ui.adapter.TeamManagementRecyclerViewAdapter
import com.codep25.codep25.viewmodel.TeamManagementViewModel
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject


/**
 * A fragment representing a list of Items.
 */
class TeamManagementFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TeamManagementViewModel

    private lateinit var adapter: TeamManagementRecyclerViewAdapter

    private var _binding: FragmentTeamManagementListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TeamManagementViewModel::class.java)

        _binding = FragmentTeamManagementListBinding.inflate(inflater, container, false)

        viewModel.teams.observe(viewLifecycleOwner, Observer { onTeams(it) })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeamManagementRecyclerViewAdapter(viewModel.participantColors).apply {
            onTeamOrderChanged = {t -> viewModel.updateTeam(t)}
        }

        with(binding.list) {
            addItemDecoration(
                DividerItemDecoration(
                    context, DividerItemDecoration.VERTICAL
                )
            )

            layoutManager = LinearLayoutManager(context)
            adapter = this@TeamManagementFragment.adapter
        }

        binding.recreateFab.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setMessage(R.string.fragment_team_management_re_create_teams_ask)
                .setNegativeButton(R.string.no) {d, _ -> d.dismiss()}
                .setPositiveButton(R.string.yes) {d, _ ->
                    viewModel.createTeams()
                    d.dismiss()
                }.show()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchTeams()
    }

    private fun onTeams(teams: Resource<List<TeamWithParticipants>>) {
        when (teams.state) {
            Resource.State.SUCCESS -> teams.data?.let {
                adapter.setTeams(it)
                with(binding) {
                    progessBar.visibility = GONE
                    list.visibility = VISIBLE
                }
            }
            Resource.State.LOADING -> with(binding) {
                progessBar.visibility = VISIBLE
                list.visibility = GONE
            }
            Resource.State.ERROR -> teams.message?.let {
                when (teams.message) {
                    R.string.fragment_teams_changed_warn -> {
                        AlertDialog.Builder(requireContext())
                            .setMessage(teams.message)
                            .setPositiveButton(R.string.yes) { d, _ ->

                                viewModel.createTeams()
                                d.dismiss()
                            }.setNegativeButton(R.string.no) { d, _ ->
                                viewModel.fetchTeams(true)
                                d.dismiss()
                            }.show()
                    }
                    R.string.incorrect_participants_number -> {
                        AlertDialog.Builder(requireContext())
                            .setMessage(teams.message)
                            .setPositiveButton("Update participants") { d, _ ->
                                d.dismiss()
                                findNavController().navigate(R.id.nav_participants)
                            }.setCancelable(true).show()
                    }
                    else -> {
                        displayError(teams.message)
                    }
                }
            }
        }
    }

    private fun displayError(@StringRes resId: Int) {
        Toast
            .makeText(context, resId, Toast.LENGTH_SHORT)
            .show()
    }
}
