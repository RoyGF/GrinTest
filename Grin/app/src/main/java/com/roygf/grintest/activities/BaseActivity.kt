package com.roygf.grintest.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

import com.roygf.grintest.R

open class BaseActivity : AppCompatActivity() {

    var mDialog : AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setView(R.layout.layout_progress_dialog)
        mDialog = mBuilder.create()
    }

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

    protected fun showProgressDialog(show : Boolean){

        if (show)
            mDialog?.show()
        else
            mDialog?.dismiss()
    }

}