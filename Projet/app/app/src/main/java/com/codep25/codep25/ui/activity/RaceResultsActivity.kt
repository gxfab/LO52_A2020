package com.codep25.codep25.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codep25.codep25.R
import com.codep25.codep25.databinding.ActivityRaceResultsBinding
import com.codep25.codep25.model.entity.*
import com.codep25.codep25.ui.adapter.RaceTeamResultAdapter
import com.codep25.codep25.ui.adapter.SimpleTimeAdapter
import com.codep25.codep25.viewmodel.RaceResultsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class RaceResultsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RaceResultsViewModel

    private var _binding: ActivityRaceResultsBinding? = null
    private val binding get() = _binding!!

    private var raceId = -1L
    private lateinit var adapter: RaceTeamResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RaceResultsViewModel::class.java)

        viewModel.race.observe(this, Observer { onRace(it) })

        _binding = ActivityRaceResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        raceId = intent.getLongExtra(RACE_ID_KEY, -1)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        adapter = RaceTeamResultAdapter()
        listOf(
            binding.topPitStopRecyclerView,
            binding.topTimesRecyclerView,
            binding.topSprintsRecyclerView,
            binding.topHurdlesRecyclerView,
            binding.overallRecycler
        ).forEach {
            it.layoutManager = LinearLayoutManager(this@RaceResultsActivity)
            it.isNestedScrollingEnabled = true
        }

        binding.overallRecycler.apply {
            adapter = this@RaceResultsActivity.adapter
        }

        setLoading(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchRaceById(raceId)
    }

    private fun onRace(raceResource: Resource<RaceWithTeams>) {
        when(raceResource.state) {
            Resource.State.SUCCESS -> {
                if (raceResource.data != null) {
                    val times = raceResource.data.allTimes()
                    binding.apply {
                        topTimesRecyclerView.adapter = SimpleTimeAdapter(
                            getTopTimes(raceResource.data)
                        )

                        topSprintsRecyclerView.adapter = SimpleTimeAdapter(
                            getTopSprintTimes(times)
                        )

                        topHurdlesRecyclerView.adapter = SimpleTimeAdapter(
                            getTopHurdleTimes(times)
                        )

                        topPitStopRecyclerView.adapter = SimpleTimeAdapter(
                            getTopPitStopTimes(times)
                        )

                    }
                    adapter.setRace(raceResource.data)
                }

                setLoading(false)
            }
            Resource.State.LOADING -> setLoading(true)
            Resource.State.ERROR -> displayError(raceResource.message ?: R.string.unknown_error)
        }
    }

    private fun getTopTimes(race: RaceWithTeams): List<SimpleTime> {
        return race.teams
            .sortedBy { it.totalRaceTime }
            .subList(0, min(3, race.teams.size))
            .map { SimpleTime(it.team.name, it.totalRaceTime) }
    }

    private fun getTopSprintTimes(times: List<RaceTime>): List<SimpleTime> {
        return times
            .filter { it.state == RacingState.SPRINT }
            .sortedBy { it.timeMs }
            .subList(0, min(3, times.size))
            .map { SimpleTime(it.participant.name, it.timeMs) }
    }

    private fun getTopHurdleTimes(times: List<RaceTime>): List<SimpleTime> {
        return times
            .filter { it.state == RacingState.HURDLE }
            .sortedBy { it.timeMs }
            .subList(0, min(3, times.size))
            .map { SimpleTime(it.participant.name, it.timeMs) }
    }

    private fun getTopPitStopTimes(times: List<RaceTime>): List<SimpleTime> {
        return times
            .filter { it.state == RacingState.PIT_STOP }
            .sortedBy { it.timeMs }
            .subList(0, min(3, times.size))
            .map { SimpleTime(it.participant.name, it.timeMs) }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            resultsLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun displayError(@StringRes resId: Int) {
        Toast
            .makeText(this, resId, Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        const val RACE_ID_KEY = "race_id_key"
    }
}