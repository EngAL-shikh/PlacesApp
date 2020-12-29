package com.amroz.placesapp


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment


import java.text.SimpleDateFormat
import java.util.*


class DialogAdd: DialogFragment() {

    val sdf = SimpleDateFormat("EEE MMM d HH mm ss z yyyy")

    private lateinit var places: Places


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

      //  places= Places(0,"","", Date())
        val v=activity?.layoutInflater?.inflate(R.layout.costumdialog,null)

        val title=v?.findViewById(R.id.ed_title) as EditText
        val det= v.findViewById(R.id.ed_det) as EditText
        val lang= v.findViewById(R.id.ed_lang) as EditText
        val lat= v.findViewById(R.id.ed_lat) as EditText






        return  AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(v)
            .setPositiveButton("ADD"){dialog,_->
                val data=Places(0,"adf","adf","adf","asdf" ,"")
                targetFragment.let {fragment ->
                    (fragment as Callbacks).addnewPlace(data)
                }
            }.setNegativeButton("cancel"){dialog,_->
                dialog.cancel()
            }.create()




    }






    interface Callbacks{

        fun addnewPlace(places: Places)

    }


}