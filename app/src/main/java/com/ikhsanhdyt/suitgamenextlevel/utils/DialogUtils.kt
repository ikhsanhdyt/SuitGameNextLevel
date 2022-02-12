package com.ikhsanhdyt.suitgamenextlevel.utils
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ikhsanhdyt.suitgamenextlevel.databinding.DialogWinnerBinding

object DialogUtils {
    fun showInputNameDialog(
        context: Context,
        winner: String,
        onButtonClick: (AlertDialog?,String) -> Unit
    ) {
        var dialog: AlertDialog? = null

        val dialogBinding =
            DialogWinnerBinding.inflate((context as AppCompatActivity).layoutInflater).apply {
                tvWinner.text = winner
                btnPlayAgain.setOnClickListener {
                    onButtonClick(dialog,"play_again")
                }
                btnMenu.setOnClickListener {
                    onButtonClick(dialog,"menu")
                }
            }

        dialog = AlertDialog.Builder(context)
            .setView(dialogBinding.root)
            .create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.show()

    }
}