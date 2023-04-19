package com.disruptivestudio.pruebadsapp.model

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.disruptivestudio.pruebadsapp.R

/**
 * Clase para guardar diferentes operaciones repetitivas
 */
object Utils {

    /**
     * Mostramos un mensaje al usuario
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun showAlertDialog(activity: Activity, content: String) {
        val builder = AlertDialog.Builder(activity).apply {
            setTitle(activity.getString(R.string.app_name))
            setIcon(
                activity.resources.getDrawable(
                    android.R.drawable.ic_dialog_alert,
                    activity.theme
                )
            )
            setPositiveButton(R.string.aceptar, { dialog, wicth ->
                dialog.cancel()
            })
            setMessage(content)
        }
        builder.show()
    }

}