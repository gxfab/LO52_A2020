package com.codep25.codep25.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentRaceBinding
import com.codep25.codep25.databinding.FragmentRacesResultsBinding
import com.codep25.codep25.model.entity.Race
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.ui.adapter.RaceResultAdapter
import com.codep25.codep25.viewmodel.RaceViewModel
import com.codep25.codep25.viewmodel.RacesResultsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RacesResultsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RacesResultsViewModel

    private var _binding: FragmentRacesResultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: RaceResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RacesResultsViewModel::class.java)

        viewModel.races.observe(viewLifecycleOwner, Observer { onRaces(it) })

        _binding = FragmentRacesResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RaceResultAdapter()
        adapter.onClick = { onRaceClick(it) }

        binding.racesRecyclerView.apply {
            adapter = this@RacesResultsFragment.adapter
            layoutManager= LinearLayoutManager(context)
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchRaces()
    }

    private fun onRaces(races: Resource<List<Race>>) {
        when (races.state) {
            Resource.State.SUCCESS -> {
                val list = races.data ?: listOf()

                if (list.isEmpty()) {
                    binding.noRacesTextView.visibility = View.VISIBLE
                    binding.racesRecyclerView.visibility = View.GONE
                } else {
                    binding.noRacesTextView.visibility = View.GONE
                    binding.racesRecyclerView.visibility = View.VISIBLE
                    adapter.setRaces(list)
                }
            }
            Resource.State.LOADING -> {}
            Resource.State.ERROR -> displayError(races.message ?: R.string.unknown_error)
        }
    }

    private fun onRaceClick(race: Race) {
        viewModel.openRace(race, requireContext())
    }

    private fun displayError(@StringRes resId: Int) {
        Toast
            .makeText(context, resId, Toast.LENGTH_SHORT)
            .show()
    }
}