package com.roygf.grintest.activities

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    protected fun showMessageDialog(message : String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }

    protected fun showMessageDialog(messageInt : Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(messageInt)
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }
}