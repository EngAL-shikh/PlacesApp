package com.amroz.placesapp

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amroz.placesapp.DialogAdd.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_contact.*


class contactFragment() : Fragment(), Callbacks {


    private var adapter: TaskAdapter? = TaskAdapter(emptyList())
    lateinit var rec:RecyclerView
    lateinit var add1:FloatingActionButton
    lateinit var et_title:EditText
    lateinit var et_det:EditText
    lateinit var et_lat:EditText
    lateinit var et_lang:EditText
     lateinit var save:Button
    private val ViewModel by lazy {
        ViewModelProviders.of(this).get(ViewModel::class.java)
    }


    private  lateinit var requst: TextView
    companion object{
        fun newInstance(data:String):contactFragment{
            val args=Bundle().apply {
                putSerializable("name",data)
            }
            return  contactFragment().apply {
                arguments=args
            }
        }
    }


    var contact:String=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact=arguments?.getSerializable("name")as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.fragment_contact, container, false)

          var rec=view.findViewById(R.id.rec) as RecyclerView
          var add1=view.findViewById(R.id.add) as FloatingActionButton
         et_title=view.findViewById(R.id.ed_title) as EditText
        et_det=view.findViewById(R.id.ed_det) as EditText
        et_lat=view.findViewById(R.id.ed_latitude) as EditText
        et_lang=view.findViewById(R.id.ed_longitude) as EditText
        save=view.findViewById(R.id.ed_save) as Button


        add1.setOnClickListener {


            card_add_place.visibility=View.VISIBLE





        }


      save.setOnClickListener {
          var data=Places(0,et_title.text.toString(),
              et_det.text.toString(),
              et_lat.text.toString().toFloat(),
              et_lang.text.toString().toFloat(),
              "")
            ViewModel.addtask(data)
            card_add_place.visibility=View.GONE
        }

        rec.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (contact=="profile"){

            Log.d("adding","done")
            ViewModel.placesListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {

                        Log.d("amroz",places.size.toString())
                        updateUI(places)

                    }
                })
        }

    }

    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.title)
        val Describe: TextView = itemView.findViewById(R.id.Describe)
        val latitude: TextView = itemView.findViewById(R.id.latitude)
         val longitude: TextView = itemView.findViewById(R.id.longitude)
        // val add1: FloatingActionButton = itemView.findViewById(R.id.add)





        private lateinit var places: Places
        fun bind(item: Places) {

            this.places = item

            title.text = this.places.title
            Describe.text = this.places.Describe
            latitude.text = this.places.latitude.toString()
            longitude.text = this.places.longitude.toString()










        }

        init {




//

//            delete.setOnClickListener {
//
//
//
//                val builder= AlertDialog.Builder(requireContext())
//                builder.setPositiveButton("yes"){_,_->
//
//                    ViewModel.deletPlaces(places)
//                }
//
//                builder.setNegativeButton("no"){_,_->
//
//
//                }
//                builder.setTitle("Are sure you want to delete ${places.title}")
//                builder.setMessage("Are you sure")
//                builder.create().show()
//            }




        }







    }


    private inner class TaskAdapter(var places: List<Places>) :
        RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.place_list, parent, false)
            return TaskHolder(view)
        }



        override fun getItemCount(): Int {



            return places.size

        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val places = places[position]
            holder.bind(places)









        }

    }
    private fun updateUI(places: List<Places>) {
        var rec=view?.findViewById(R.id.rec) as RecyclerView
        adapter = TaskAdapter(places)
        rec.adapter = adapter
    }


    override fun addnewPlace(places: Places) {
       ViewModel.addtask(places)
    }

}