package com.codep25.codep25.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import com.codep25.codep25.R
import com.codep25.codep25.databinding.NamedFabBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NamedFab @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: NamedFabBinding =
        NamedFabBinding.inflate(LayoutInflater.from(context), this , true)

    private val fabOpenAnimation: Animation =
        AnimationUtils.loadAnimation(context, R.anim.fab_open)
    private val fabCloseAnimation: Animation =
        AnimationUtils.loadAnimation(context, R.anim.fab_close)

    init {

        attrs?.let {
            val styledAttributes =
                context.obtainStyledAttributes(it, R.styleable.NamedFab, 0, 0)

            val textValue = styledAttributes.getString(R.styleable.NamedFab_text)
            val textSize = styledAttributes.getDimensionPixelSize(R.styleable.NamedFab_textSize, -1)
            val textColor = styledAttributes.getColor(R.styleable.NamedFab_textColor, 4)
            val fabSize = styledAttributes.getInt(R.styleable.NamedFab_namedFabSize, FloatingActionButton.SIZE_AUTO)
            val fabIcon = styledAttributes.getResourceId(R.styleable.NamedFab_namedFabIcon, 0xFF000000.toInt())
            val backgroundTint = styledAttributes.getResourceId(R.styleable.NamedFab_backgroundTint, -1)

            with(binding.namedFabTextView) {
                if (textValue != null)
                    text = textValue

                if (textSize != -1)
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())

                setTextColor(textColor)
            }

            with(binding.namedFabFab) {
                size = fabSize

                if (backgroundTint != -1)
                    backgroundTintList = AppCompatResources.getColorStateList(context, backgroundTint)

                if (fabIcon != -1)
                    setImageResource(fabIcon)
            }

            styledAttributes.recycle()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.namedFabFab.setOnClickListener(l)
    }

    fun hide() {
        with(binding.root) {
            visibility = GONE
            startAnimation(fabCloseAnimation)
        }
    }

    fun show() {
        with(binding.root) {
            visibility = View.VISIBLE
            startAnimation(fabOpenAnimation)
        }
    }
}