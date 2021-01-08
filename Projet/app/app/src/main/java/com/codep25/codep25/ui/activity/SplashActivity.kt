package com.codep25.codep25.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codep25.codep25.R
import com.codep25.codep25.model.entity.Resource
import com.codep25.codep25.router.MainRouter
import com.codep25.codep25.viewmodel.SplashViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel
    private val observer = Observer<Resource<Boolean>> { onReady(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SplashViewModel::class.java)
        splashViewModel.isReady.observe(this, observer)
    }

    override fun onResume() {
        super.onResume()

        splashViewModel.runInternalChecks()
    }

    private fun onReady(result: Resource<Boolean>) {
        result.let {
            if (it.state == Resource.State.SUCCESS)
                MainRouter.openMain(this)

            if (it.state == Resource.State.ERROR) {
                val dialog = AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage(it.message ?: R.string.activity_splash_unknown_error)
                    .setPositiveButton(
                        R.string.activity_splash_error_dialog_positive_button
                    ) { _, _ ->
                        splashViewModel.isReady.removeObserver(observer)
                        finishAndRemoveTask()
                    }.create()

                dialog.show()

            }
        }
    }
}
