package com.ikhsanhdyt.suitgamenextlevel.sharedPreferences;

import android.content.Context
import android.content.SharedPreferences

class PlayerSharedPref(context: Context) {
    private var preference: SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        private const val NAME = "playerSharedPref" //app name or else
        private const val MODE = Context.MODE_PRIVATE
        private val PREF_PLAYER_NAME = Pair("PREF_PLAYER_NAME", null)
    }

    var playerName: String?
        get() = preference.getString(PREF_PLAYER_NAME.first, PREF_PLAYER_NAME.second)
        set(value) = preference.edit {
            it.putString(PREF_PLAYER_NAME.first, value)
        }

}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

private fun SharedPreferences.delete() {
    edit().clear().apply()
}