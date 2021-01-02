package com.amroz.placesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_contact.*


class contactFragment() : Fragment() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var adapter: PlacekAdapter? = PlacekAdapter(emptyList())
    lateinit var rec:RecyclerView
    lateinit var add1:FloatingActionButton

     var list:List<Places> = emptyList()

    var listPlace= listOf<Places>()
    private val ViewModel by lazy {
        ViewModelProviders.of(this).get(ViewModel::class.java)
    }


    private  lateinit var requst: TextView
    companion object{
        fun newInstance(data: String):contactFragment{
            val args=Bundle().apply {
                putSerializable("name", data)
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
          var map_show=view.findViewById(R.id.map_show) as TextView
          var add1=view.findViewById(R.id.add) as FloatingActionButton




        add1.setOnClickListener {
            var intent= Intent(context, AddNewsLocation::class.java)
            startActivity(intent)
        }
        rec.layoutManager = LinearLayoutManager(context)






          var  mIth = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: ViewHolder, target: ViewHolder
                    ): Boolean {
                        val fromPos = viewHolder.adapterPosition
                        val toPos = target.adapterPosition

                        // move item in `fromPos` to `toPos` in adapter.
                        return true // true if moved, false otherwise
                    }

                    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {

                      // ViewModel.deletPlaces()
                        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show()

                    }
                })

           .attachToRecyclerView(rec)



        // Inflate the layout for this fragment
        return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (contact=="Hospitals"){
            Log.d("adding", "done")
            ViewModel.HospitalsListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {
                        Log.d("amroz", places.size.toString())
                        updateUI(places)
                        map_show.setOnClickListener {
                            var list = CatList(places)
                            toMap(list)
                        }



                    }
                })
        }else if (contact=="Schools"){
            ViewModel.SchoolsListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {
                        Log.d("amroz", places.size.toString())
                        updateUI(places)
                        map_show.setOnClickListener {
                            var list = CatList(places)
                            toMap(list)
                        }

                    }
                })

        }else if (contact=="Restaurants"){
            ViewModel.RestaurantsListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {
                        Log.d("amroz", places.size.toString())
                        updateUI(places)
                        map_show.setOnClickListener {
                            var list = CatList(places)
                            toMap(list)
                        }

                    }
                })

        }else if (contact=="Cafes"){
            ViewModel.CafesListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {
                        Log.d("amroz", places.size.toString())
                        updateUI(places)
                        map_show.setOnClickListener {
                            var list = CatList(places)
                            toMap(list)
                        }
                    }
                })
        }else{
            ViewModel.SuperMarketListLiveData.observe(
                viewLifecycleOwner,
                Observer { places ->
                    places?.let {
                        Log.d("amroz", places.size.toString())
                        updateUI(places)
                        map_show.setOnClickListener {
                            var list = CatList(places)
                            toMap(list)
                        }
                    }
                })
        }
    }

    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.title)
        val Describe: TextView = itemView.findViewById(R.id.Describe)
        val type: TextView = itemView.findViewById(R.id.type)
        val ic_type: TextView = itemView.findViewById(R.id.ic_type)
         val card: CardView = itemView.findViewById(R.id.places_card)






        private lateinit var places: Places
        fun bind(item: Places) {
            this.places = item
            title.text = this.places.title
            Describe.text = this.places.Describe
            type.text = this.places.type.toString()









            card.setOnClickListener {

                var intent=Intent(context, UpdatePlaceActivity::class.java)
                intent.putExtra("id",places.id)
                startActivity(intent)
            }





            ic_type.setOnClickListener {


               var intent=Intent(context, MapsActivity::class.java)
                  intent.putExtra("singleplace", places)
                  intent.putExtra("Activity", "1")

                startActivity(intent)


            }
        }



    }




    private inner class PlacekAdapter(var places: List<Places>) :
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


        adapter = PlacekAdapter(places)
        rec.adapter = adapter


    }




    fun toMap(list: CatList){
        var intent=Intent(context, MapsActivity::class.java)
        intent.putExtra("places", list)
        intent.putExtra("Activity", "0")
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

                        var data =Places(
                            0, title.toString(),
                            det.toString(),
                            lat.toString().toFloat(),
                            lang.toString().toFloat(), type.toString()
                        )


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