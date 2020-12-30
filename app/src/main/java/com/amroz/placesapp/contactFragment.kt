package com.amroz.placesapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_contact.*
import java.io.Serializable


class contactFragment() : Fragment() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())
    lateinit var rec:RecyclerView
    lateinit var add1:FloatingActionButton
     var list:List<Places> = emptyList()

    var listPlace= listOf<Places>()
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



        add1.setOnClickListener {

            var intent= Intent(context,AddNewsLocation::class.java)
            startActivity(intent)




        }


        rec.layoutManager = LinearLayoutManager(context)
        // Inflate the layout for this fragment
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (contact=="Hospitals"){

            Log.d("adding","done")
            ViewModel.HospitalsListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {

                        Log.d("amroz",places.size.toString())
                        updateUI(places)


                        ///list=Places
                        // toMap(list)
                       var list=CatList(places)

                        var intent=Intent(context,MapsActivity::class.java)
                        intent.putExtra("places",list)
                        startActivity(intent)





                    }
                })
        }else if (contact=="Schools"){
            ViewModel.SchoolsListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {

                        Log.d("amroz",places.size.toString())
                        updateUI(places)



                    }
                })

        }else if (contact=="Restaurants"){

            ViewModel.RestaurantsListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {

                        Log.d("amroz",places.size.toString())
                        updateUI(places)



                    }
                })

        }else if (contact=="Cafes"){

            ViewModel.CafesListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {

                        Log.d("amroz",places.size.toString())
                        updateUI(places)



                    }
                })
        }else{
            ViewModel.SuperMarketListLiveData.observe(
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
         val card: CardView = itemView.findViewById(R.id.places_card)
        // val add1: FloatingActionButton = itemView.findViewById(R.id.add)





        private lateinit var places: Places
        fun bind(item: Places) {

            this.places = item

            title.text = this.places.title
            Describe.text = this.places.Describe
            latitude.text = this.places.latitude.toString()
            longitude.text = this.places.longitude.toString()




            card.setOnClickListener {


                //var list=  Places(0,places.title,places.Describe,places.latitude,places.longitude,1)
              //  toMap(list)


            }











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




    fun toMap(places: Places){
        var intent=Intent(context,MapsActivity::class.java)
         intent.putExtra("places",places)
        startActivity(intent)

    }

    fun getPlaces() {
     var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Places")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newsList = mutableListOf<Places>();
                    for (places in task.result!!) {

                     var title=places.data.getValue("title")
                     var det=places.data.getValue("describe")
                     var lat=places.data.getValue("latitude")
                     var lang=places.data.getValue("longitude")
                     var type=places.data.getValue("type")

                        var data =Places(0,title.toString(),
                            det.toString(),
                            lat.toString().toFloat(),
                            lang.toString().toFloat()
                            ,type.toString())


                        ViewModel.addtask(data)


                    }



                } else {
                    Log.w("TAG", "Error getting documents.", task.exception)
                }
            }
    }

//    fun addToPlaces(){
//
//
//
//
//
//        db= FirebaseFirestore.getInstance()
//
////        var data=Places(0,et_title.text.toString(),
//////              et_det.text.toString(),
//////              et_lat.text.toString().toFloat(),
//////              et_lang.text.toString().toFloat(),
//////              1)
//
//
//        var place= Places(0,et_title.text.toString(),et_det.text.toString(),
//        et_lat.text.toString().toFloat(),
//        et_lang.text.toString().toFloat(),
//        "")
//        db.collection("Places").add(place).addOnCompleteListener{
//            if (it.isSuccessful){
//                Toast.makeText(context,"added", Toast.LENGTH_LONG).show()
//
//            }else{
//                Toast.makeText(context,"filde to add ${it.exception}", Toast.LENGTH_LONG).show()
//                Log.d("test",it.exception.toString())
//
//            }
//        }
//
//
//    }
}