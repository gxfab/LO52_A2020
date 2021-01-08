package com.codep25.codep25.router

import android.content.Context
import android.content.Intent
import com.codep25.codep25.ui.activity.MainActivity

class MainRouter {
    companion object {
        fun openMain(ctx : Context) {
            val intent = Intent(ctx, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            ctx.startActivity(intent)
        }
    }
}
