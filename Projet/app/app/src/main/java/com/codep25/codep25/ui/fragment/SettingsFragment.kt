package com.codep25.codep25.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentSettingsBinding
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.ui.adapter.ParticipantColorsAdapter
import com.codep25.codep25.viewmodel.SettingsViewModel
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import dagger.android.support.DaggerFragment
import java.lang.NumberFormatException
import javax.inject.Inject


class SettingsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SettingsViewModel

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ParticipantColorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SettingsViewModel::class.java)

        viewModel.maxLevel.observe(viewLifecycleOwner, Observer { onMaxLevel(it) })
        viewModel.nbParticipantsPerTeam.observe(viewLifecycleOwner, Observer { onNbParticipantsPerTeam(it) })
        viewModel.participantColors.observe(viewLifecycleOwner, Observer { onParticipantColors(it) })
        viewModel.changesApplied.observe(viewLifecycleOwner, Observer { onChangesApplied(it) })

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ParticipantColorsAdapter()
        adapter.onClick = {pos ->
                ColorPickerDialog.Builder(context)
                .setTitle(R.string.fragment_settings_color_picker)
                .setPreferenceName("ColorPickerPref")
                .setPositiveButton(getString(R.string.confirm),
                    ColorEnvelopeListener { envelope, _ ->
                        adapter.setColorForPos(pos, envelope.color)
                    }
                ).setNegativeButton(
                    getString(R.string.cancel)
                ) { dialogInterface, _ -> dialogInterface.dismiss() }
                .attachAlphaSlideBar(false)
                .attachBrightnessSlideBar(false)
                .setBottomSpace(12)
                .show()
        }

        with (binding) {
            colorRecyclerview.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@SettingsFragment.adapter
                setHasFixedSize(true)
                addItemDecoration(
                    DividerItemDecoration(
                        context, DividerItemDecoration.VERTICAL
                    )
                )
            }

            nbParticipantsPerTeamInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    try {
                        if (!nbParticipantsPerTeamInput.hasFocus())
                            return

                        viewModel.resizeColors(
                            adapter.getColors(viewModel.colorFactory), s?.toString()?.toInt() ?: 0
                        )
                    } catch (e: NumberFormatException) {
                        // Silent
                    }

                }

            })

            applyButton.setOnClickListener {
                viewModel.applyChanges(
                    getMaxLevelFromInput(),
                    getNbPerTeamFromInput(),
                    adapter.getColors(viewModel.colorFactory)
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchMaxLevel()
        viewModel.fetchNbParticipantsPerTeam()
        viewModel.fetchParticipantColors()
    }

    private fun onMaxLevel(maxLevel: Resource<Int>) {
        when (maxLevel.state) {
            Resource.State.SUCCESS ->
                binding.maxLevelInput.setText(maxLevel.data?.toString())
        }
    }

    private fun onNbParticipantsPerTeam(nbParticipants: Resource<Int>) {
        when (nbParticipants.state) {
            Resource.State.SUCCESS ->
                binding.nbParticipantsPerTeamInput.setText(nbParticipants.data?.toString())
        }
    }

    private fun onParticipantColors(colors: Resource<ParticipantColors>) {
        when (colors.state) {
            Resource.State.SUCCESS ->
                if (colors.data != null)
                    adapter.setColors(colors.data)
        }
    }

    private fun onChangesApplied(applied: Resource<Boolean>) {
        when (applied.state) {
            Resource.State.SUCCESS ->
                Toast.makeText(context, R.string.fragment_settings_applied, Toast.LENGTH_SHORT)
                    .show()
            Resource.State.ERROR ->
                when (applied.message) {
                    R.string.fragment_settings_level_fit_warning ->
                        AlertDialog.Builder(context)
                            .setMessage(applied.message)
                            .setNegativeButton(R.string.no) {d, _ -> d.dismiss()}
                            .setPositiveButton(R.string.yes) {d, _ ->
                                viewModel.applyChanges(
                                    getMaxLevelFromInput(),
                                    getNbPerTeamFromInput(),
                                    adapter.getColors(viewModel.colorFactory),
                                    false)
                                d.dismiss()
                            }.show()
                    R.string.fragment_settings_invalid_params ->
                        Toast.makeText(context, applied.message, Toast.LENGTH_SHORT)
                            .show()
                }
        }
    }

    private fun getMaxLevelFromInput() =
        binding.maxLevelInput.text?.toString()?.toInt() ?: 0

    private fun getNbPerTeamFromInput() =
        binding.nbParticipantsPerTeamInput.text?.toString()?.toInt() ?: 0
}