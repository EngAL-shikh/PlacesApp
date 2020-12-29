
package com.amroz.placesapp
import androidx.lifecycle.ViewModel


class ViewModel : ViewModel() {

    private  val placesRepository= PlacesRepository.get()
   val placesListLiveData=placesRepository.getPlacess()





    fun addtask(places: Places){
        placesRepository.addnewPlaces(places)
    }

    fun deletPlaces(places: Places){
        placesRepository.deletePlaces(places)
    }





}