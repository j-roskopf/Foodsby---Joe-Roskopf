package com.foodsby.main.ui

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.foodsby.R
import com.foodsby.android.extensions.directionFromViewOffset
import com.foodsby.android.extensions.isViewVisible
import com.foodsby.main.adapter.DeliveriesAdapter
import com.foodsby.model.network.Delivery
import com.foodsby.model.network.DeliveryResponse
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject



class MainActivity : AppCompatActivity() {

    @Inject lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup the observer so we can be notified when our live data gets a response from API / DB
        setupObserver()

        setupCheckChangeListener()

        //fetch the deliveries!
        mainViewModel.fetchDeliveries()
    }

    /**
     * Adds OnCheckChangedListener to our RadioGroup. This method is responsible
     * for creating the adapter based on the day selected
     */
    private fun setupCheckChangeListener() {
        mainSegmentedGroup.setOnCheckedChangeListener { group, id ->

            verifySelectedDayIsVisible(group.findViewById(id))

            Log.d("D","responseDebug - check changed! $id")
            val data = mainViewModel.deliveryResponse.value?.dropoffs
            data?.let {
                for(dropOff in it) {
                    if(mainViewModel.idFromDay(mainViewModel.currentDayFromDropOff(dropOff)) == id) {
                        setAdapter(dropOff.deliveries)
                    }
                }
            }
        }
    }

    private fun setAdapter(deliveries: List<Delivery>?) {
        deliveries?.let {
            // Creates a vertical Layout Manager
            mainRecyclerView.layoutManager = LinearLayoutManager(this)
            mainRecyclerView.adapter = DeliveriesAdapter(context = this, deliveries = it)
        }
    }

    /**
     * Setup the observer for our response from deliveries. This method is responsible
     * for calling the subsequent methods to parse the response and display UI accordingly
     */
    private fun setupObserver() {
        mainViewModel.deliveryResponse.observe(this as LifecycleOwner, Observer<DeliveryResponse> { response ->
            response?.let {
                parseResponse(it)
            }
        })
    }

    private fun verifySelectedDayIsVisible(view: View?) {
        view?.let {
            mainScrollView.post {
                Log.d("D","visibleDebug - is view visible? ${mainScrollView.isViewVisible(it)}")
                if(!mainScrollView.isViewVisible(it)) {
                    mainScrollView.fullScroll(mainScrollView.directionFromViewOffset(it))
                }
            }
        }
    }

    /*
     * Parse the response from out data layer!
     */
    private fun parseResponse(response: DeliveryResponse) {
        response.dropoffs?.let {
            var selectedRadioButton: RadioButton? = null
            for(dropOff in it) {
                val radioButton = mainViewModel.createRadioButtonFromDropOff(dropOff, this)
                mainSegmentedGroup.addView(radioButton)
                if(radioButton.text == "Today") {
                    selectedRadioButton = radioButton
                    Log.d("D","responseDebug - radio button text of today " + mainViewModel.currentDayFromDropOff(dropOff).hashCode())
                    mainSegmentedGroup.check(mainViewModel.idFromDay(mainViewModel.currentDayFromDropOff(dropOff)))
                }
            }

            verifySelectedDayIsVisible(selectedRadioButton)

            mainSegmentedGroup.updateBackground()
        }
    }
}


