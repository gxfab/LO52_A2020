package com.codep25.codep25.ui.dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.codep25.codep25.R
import com.codep25.codep25.databinding.FragmentPlayerEditBinding
import com.codep25.codep25.model.entity.ParticipantWithTeam
import kotlinx.android.synthetic.main.fragment_player_edit.*


class PlayerEditDialogFragment(val maxLevel: Int) : DialogFragment() {

    interface EditActionListener {
        fun onRemove(id: Long)
        fun onCreate(name: String, level: Int)
        fun onEdit(id: Long, name: String, level: Int)
    }

    private var _binding: FragmentPlayerEditBinding? = null
    private val binding get() = _binding!!

    var onEditActionListener: EditActionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            if (arguments != null) {
                val name = arguments?.getString(KEY_NAME) ?: ""
                val level = arguments?.getInt(KEY_LEVEL) ?: 0

                playerNameEditText.setText(name)
                seekBar.progress = level
                setProgress(level)

                operationTitle.setText(R.string.fragment_player_edit_edit_player)
                addPlayerBtn.setText(R.string.fragment_player_edit_edit_player)
            } else {
                operationTitle.setText(R.string.fragment_player_edit_new_player)
                removeButton.visibility = View.GONE
                addPlayerBtn.setText(R.string.fragment_player_edit_add_player)
                setProgress(0)
            }

            seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    setProgress(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

            playerNameEditText.addTextChangedListener (object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    addPlayerBtn.isEnabled = !s.isNullOrBlank()
                }

            })

            addPlayerBtn.isEnabled = !playerNameEditText.text.isNullOrBlank()
            seekBar.max = maxLevel

            removeButton.setOnClickListener { onRemove() }
            addPlayerBtn.setOnClickListener { onCreateOrUpdate() }
            cancelButton.setOnClickListener { dialog?.dismiss() }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun onRemove() {
        dialog?.dismiss()

        val id = arguments?.getLong(KEY_ID, -1) ?: -1L
        if (id != -1L)
            onEditActionListener?.onRemove(id)
    }

    private fun onCreateOrUpdate() {
        dialog?.dismiss()

        val id = arguments?.getLong(KEY_ID, -1) ?: -1L
        if (id != -1L) {
            onEditActionListener?.onEdit(
                id, binding.playerNameEditText.text.toString(), seekBar.progress
            )
        } else {
            if (!binding.playerNameEditText.text.isNullOrBlank())
                onEditActionListener?.onCreate(
                    binding.playerNameEditText.text.toString(), seekBar.progress
                )
        }
    }

    private fun setProgress(progress: Int) {
        binding.playerLevelText.text =
            context?.getString(R.string.fragment_player_edit_player_level, progress)
    }

    companion object {
        const val TAG = "player_dialog_fragment"
        private const val KEY_ID = "player_id"
        private const val KEY_NAME = "player_name"
        private const val KEY_LEVEL = "player_level"

        fun newInstanceCreatePlayer(maxLevel: Int): PlayerEditDialogFragment {
            return PlayerEditDialogFragment(maxLevel)
        }

        fun newInstanceEditPlayer(p: ParticipantWithTeam, maxLevel: Int): PlayerEditDialogFragment {
            return PlayerEditDialogFragment(maxLevel).apply {
                val b = Bundle()
                b.putLong(KEY_ID, p.id)
                b.putString(KEY_NAME, p.name)
                b.putInt(KEY_LEVEL, p.level)

                arguments = b
            }
        }
    }
}