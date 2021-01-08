package com.codep25.codep25.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentParticipantsBinding
import com.codep25.codep25.model.entity.ParticipantWithTeam
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.ui.adapter.ParticipantsAdapter
import com.codep25.codep25.ui.dialog.PlayerEditDialogFragment
import com.codep25.codep25.ui.view.NamedFab
import com.codep25.codep25.viewmodel.ParticipantsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ParticipantsFragment : DaggerFragment(), PlayerEditDialogFragment.EditActionListener {

    inner class CreateTextDocument: ActivityResultContracts.CreateDocument() {
        override fun createIntent(context: Context, input: String): Intent {
            return super.createIntent(context, input).apply {
                type = "text/plain"
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ParticipantsViewModel

    private lateinit var adapter: ParticipantsAdapter

    private var _binding: FragmentParticipantsBinding? = null
    private val binding get() = _binding!!

    private var nextFileAction: (() -> Unit)? = null

    private lateinit var fabRotateClockAnimation: Animation
    private lateinit var fabRotateAntiClockAnimation: Animation

    private val requestFileResult = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) {
        if (it != null)
            viewModel.fromFile(it)
    }

    private val requestFileWrite = registerForActivityResult(
        CreateTextDocument()
    ) {
        if (it != null)
            viewModel.toFile(it)
    }

    private val requestFilePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            nextFileAction?.invoke()
        }
    }


    private lateinit var subFabs: List<NamedFab>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ParticipantsViewModel::class.java)
        _binding = FragmentParticipantsBinding.inflate(inflater, container, false)

        viewModel.participants.observe(viewLifecycleOwner, Observer { onParticipants(it) })
        viewModel.parsedFile.observe(viewLifecycleOwner, Observer { onFileParsed(it) })
        viewModel.outFile.observe(viewLifecycleOwner, Observer { onFileWritten(it) })


        fabRotateAntiClockAnimation = AnimationUtils.loadAnimation(
            context, R.anim.fab_rotate_anticlock
        )

        fabRotateClockAnimation = AnimationUtils.loadAnimation(
            context, R.anim.fab_rotate_clock
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(context)
        subFabs = listOf(
            binding.createFab,
            binding.deleteFab,
            binding.exportFab,
            binding.importFab
        )

        adapter = ParticipantsAdapter(viewModel.getParticipantsColors())
        adapter.onItemClick = {onParticipantClick(it)}
        adapter.onSelectionModeChanged = {onParticipantSelection(it)}
        adapter.onSelected = {onParticipantSelectionChanged(it)}

        with(binding) {
            selectAllCheckbox.visibility = View.GONE
            selectAllCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (selectAllCheckbox.isPressed) {
                    if (isChecked)
                        adapter.selectAll()
                    else
                        adapter.deselectAll()
                }
            }

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = manager
                adapter = this@ParticipantsFragment.adapter
            }

            fabBackgroundView.setOnClickListener { hideAllFabs() }

            fab.apply {
                setOnClickListener {
                    if (binding.fabBackgroundView.visibility == View.GONE)
                        if (adapter.isSelecting)
                            removeAllParticipants(adapter.selectedParticipants)
                        else
                            createParticipant()
                    else
                        hideAllFabs()
                }

                setOnLongClickListener {
                    if (binding.fabBackgroundView.visibility == View.GONE)
                        showAllFabs()
                    else
                        hideAllFabs()
                    true
                }
            }

            createFab.setOnClickListener {
                createParticipant()
                hideAllFabs()
            }
            deleteFab.setOnClickListener { removeAllParticipant() }
            importFab.setOnClickListener { importParticipants() }
            exportFab.setOnClickListener { exportParticipants() }
        }

        hideSubFabs()
    }

    override fun onStart() {
        super.onStart()

        viewModel.fetchParticipants()
    }

    private fun showAllFabs() {
        binding.apply {
            fabBackgroundView.visibility = View.VISIBLE

            for (f in subFabs)
                f.show()

            fab.startAnimation(fabRotateClockAnimation)
        }
    }

    private fun hideAllFabs() {
        binding.apply {
            fabBackgroundView.visibility = View.GONE

            hideSubFabs()

            fab.startAnimation(fabRotateAntiClockAnimation)
        }
    }

    private fun hideSubFabs() {
        for (f in subFabs)
            f.hide()
    }

    private fun onFileParsed(res: Resource<Int>) {
        when(res.state) {
            Resource.State.SUCCESS ->
                Toast.makeText(
                    context,
                    getString(R.string.fragment_participants_player_added_from_file, res.data),
                    Toast.LENGTH_SHORT
                ).show()
            Resource.State.ERROR ->
                Toast.makeText(
                    context, R.string.error_failed_to_parse_file, Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun onFileWritten(res: Resource<Int>) {
        when(res.state) {
            Resource.State.SUCCESS ->
                Toast.makeText(
                    context,
                    getString(R.string.fragment_participants_player_added_to_file, res.data),
                    Toast.LENGTH_SHORT
                ).show()
            Resource.State.ERROR ->
                Toast.makeText(
                    context, R.string.error_failed_to_write_file, Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun onParticipants(participants: Resource<List<ParticipantWithTeam>>) {
        when(participants.state) {
            Resource.State.SUCCESS -> participants.data?.let {
                if (it.isNotEmpty()) {
                    adapter.setParticipants(it)
                    with(binding) {
                        participantsStateTxt.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                } else {
                    with(binding) {
                        participantsStateTxt.setText(R.string.fragment_participants_participants_state_no)
                        participantsStateTxt.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
            Resource.State.LOADING -> with(binding) {
                participantsStateTxt.setText(R.string.fragment_participants_participants_state_loading)
                participantsStateTxt.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
            Resource.State.ERROR -> participants.message?.let {
                displayError(it)
            }
        }
    }



    private fun onParticipantClick(p: ParticipantWithTeam) {
        PlayerEditDialogFragment.newInstanceEditPlayer(p, viewModel.getMaxLevel()).apply {
            onEditActionListener = this@ParticipantsFragment
        }.show(childFragmentManager, PlayerEditDialogFragment.TAG)

    }

    private fun onParticipantSelection(isSelecting: Boolean) {
        if (isSelecting) {
            binding.fab.setImageResource(R.drawable.ic_delete)
            binding.selectAllCheckbox.apply {
                visibility = View.VISIBLE
                isChecked = false
            }
        } else {
            binding.fab.setImageResource(R.drawable.ic_add)
            binding.selectAllCheckbox.visibility = View.GONE
        }
    }

    private fun onParticipantSelectionChanged(selectedIds: Set<Long>) {
        binding.selectAllCheckbox.isChecked = (selectedIds.size == adapter.itemCount)
    }

    private fun createParticipant() {
        PlayerEditDialogFragment.newInstanceCreatePlayer(viewModel.getMaxLevel()).apply {
            onEditActionListener = this@ParticipantsFragment
        }.show(childFragmentManager, PlayerEditDialogFragment.TAG)
    }

    private fun askUserConfirmation(action: (() -> Unit)) {
        AlertDialog.Builder(context)
            .setMessage(R.string.fragment_participants_remove_all_warn)
            .setNegativeButton(R.string.no) { _, _ -> }
            .setPositiveButton(R.string.yes) {_, _ -> action.invoke()}
            .create()
            .show()
    }

    private fun removeAllParticipant() {
        askUserConfirmation {viewModel.deleteAllParticipants()}
        hideAllFabs()
    }

    private fun removeAllParticipants(ids: Set<Long>) {
        askUserConfirmation {
            viewModel.deleteParticipants(*ids.toLongArray())
            adapter.isSelecting = false
        }
    }

    private fun importParticipants() {
        nextFileAction = {requestFileResult.launch(arrayOf("application/json", "text/plain"))}
        requestFilePermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        hideAllFabs()
    }

    private fun exportParticipants() {
        nextFileAction = {requestFileWrite.launch("codep25_participants")}
        requestFilePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        hideAllFabs()
    }

    private fun displayError(@StringRes resId: Int) {
        Toast
            .makeText(context, resId, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onRemove(id: Long) {
        viewModel.deleteParticipant(id)
    }

    override fun onCreate(name: String, level: Int) {
        viewModel.createParticipant(name, level)
    }

    override fun onEdit(id: Long, name: String, level: Int) {
        viewModel.updateParticipant(id, name, level)
    }
}