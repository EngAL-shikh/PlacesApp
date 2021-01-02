
package com.amroz.placesapp
import androidx.lifecycle.ViewModel


class ViewModel : ViewModel() {

    private  val placesRepository= PlacesRepository.get()
   val placesListLiveData=placesRepository.getPlacess()
   val HospitalsListLiveData=placesRepository.getHospitals()
   val SchoolsListLiveData=placesRepository.getSchools()
   val RestaurantsListLiveData=placesRepository.getRestaurants()
   val CafesListLiveData=placesRepository.getCafes()
   val SuperMarketListLiveData=placesRepository.getSuperMarket()





    // add to place

    fun addtask(places: Places){
        placesRepository.addnewPlaces(places)
    }

    // update place

    fun update(places: Places){
        placesRepository.updatePlaces(places)
    }

    fun deletPlaces(places: Places){
        placesRepository.deletePlaces(places)
    }





}