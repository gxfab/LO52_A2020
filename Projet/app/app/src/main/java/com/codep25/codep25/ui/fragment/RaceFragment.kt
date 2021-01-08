package com.codep25.codep25.ui.fragment

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentRaceBinding
import com.codep25.codep25.model.entity.RacingTeam
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.ui.adapter.RaceAdapter
import com.codep25.codep25.viewmodel.RaceViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RaceFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RaceViewModel

    private var _binding: FragmentRaceBinding? = null
    private val binding get() = _binding!!

    private var chronometerStarted = false
    private lateinit var adapter: RaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RaceViewModel::class.java)

        viewModel.teams.observe(viewLifecycleOwner, Observer { onTeams(it) })
        viewModel.state.observe(viewLifecycleOwner, Observer { onState(it) })

        _binding = FragmentRaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RaceAdapter(viewModel.getParticipantsColors()).apply {
            onClick = { onTeamClick(it) }
        }

        with (binding) {
            chronometer.format = "%s"
            chronometer.setOnChronometerTickListener { chronometer ->
                adapter.onChronometer(SystemClock.elapsedRealtime() - chronometer.base)
            }

            chronoButton.setOnClickListener {
                if (chronometerStarted) {
                    AlertDialog.Builder(requireContext())
                        .setMessage(R.string.fragment_race_not_fished_warn)
                        .setNegativeButton(R.string.no) {d, _ -> d.dismiss() }
                        .setPositiveButton(R.string.yes) {d, _ ->
                            viewModel.stop()
                            d.dismiss()
                        }.show()
                } else {
                    viewModel.start()
                }
            }

            resultsButton.setOnClickListener {
                viewModel.showResults(requireContext())
            }
        }

        binding.teamRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER
            }

            adapter = this@RaceFragment.adapter
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchTeams()
    }

    private fun stopChronometer(base: Long) {
        with (binding) {
            chronometer.base = base
            chronometer.stop()
            chronoButton.setText(R.string.fragment_race_start_txt)
        }
    }

    private fun startChronometer(base: Long) {
        with (binding) {
            chronometer.base = base
            chronometer.start()
            chronoButton.setText(R.string.fragment_race_stop_txt)
        }
    }

    private fun onTeams(teams: Resource<List<RacingTeam>>) {
        when (teams.state) {
            Resource.State.SUCCESS ->
                if (teams.data?.isEmpty() != false) {
                    binding.teamRecyclerView.visibility = View.GONE
                    binding.noTeamLabel.visibility = View.VISIBLE
                } else {
                    binding.teamRecyclerView.visibility = View.VISIBLE
                    binding.noTeamLabel.visibility = View.GONE
                    adapter.setTeams(teams.data)
                }
            Resource.State.ERROR ->
                displayError(teams.message ?: R.string.unknown_error)
            Resource.State.LOADING -> {}
        }
    }

    private fun onState(state: RaceViewModel.State) {
        chronometerStarted = state.isStarted
        if (chronometerStarted && !state.isFinished)
            startChronometer(state.timeBase)
        else
            stopChronometer(state.timeBase)

        binding.resultsButton.visibility = if (state.isFinished) View.VISIBLE else View.GONE
    }

    private fun onTeamClick(team: RacingTeam) {
        viewModel.computeNextStep(team)
    }

    private fun displayError(@StringRes resId: Int) {
        Toast
            .makeText(context, resId, Toast.LENGTH_SHORT)
            .show()
    }
}