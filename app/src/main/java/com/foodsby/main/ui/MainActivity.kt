package com.foodsby.main.ui

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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

    private lateinit var deliveriesAdapter: DeliveriesAdapter

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

            mainRecyclerView.adapter?.notifyItemRangeRemoved(0, mainRecyclerView.adapter.itemCount)

            val data = mainViewModel.deliveryResponse.value?.dropoffs
            data?.let {
                for(dropOff in it) {
                    if(mainViewModel.idFromDay(mainViewModel.currentDayFromDropOff(dropOff)) == id) {
                        if(mainRecyclerView.adapter == null) {
                            setAdapter(dropOff.deliveries)
                        } else {
                            updateAdapter(dropOff.deliveries)
                        }
                    }
                }
            }
        }
    }

    /**
     * Calls the necessary method inside our adapter to diff the contents of what's currently there
     * and the parameter
     *
     * @param deliveries - the list of [Delivery] to update our adapter with
     */
    private fun updateAdapter(deliveries: List<Delivery>?) {
        deliveries?.let {
            deliveriesAdapter.update(it)
        }
    }

    /**
     * Sets the adapter with the list of [Delivery]
     *
     * @param deliveries - the list to update our adapter with
     */
    private fun setAdapter(deliveries: List<Delivery>?) {
        deliveries?.let {
            // Creates a vertical Layout Manager
            mainRecyclerView.layoutManager = LinearLayoutManager(this)
            deliveriesAdapter = DeliveriesAdapter(context = this, deliveries = it)
            mainRecyclerView.adapter = deliveriesAdapter
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

    /**
     * Given the view representing which day is selected, checks to make sure that view is visible.
     *
     * If it is not, this method is also responsible for scrolling our HSV to verfiy it is
     *
     * @param view - the view to check
     */
    private fun verifySelectedDayIsVisible(view: View?) {
        view?.let {
            mainScrollView.post {
                if(!mainScrollView.isViewVisible(it, Rect())) {
                    mainScrollView.fullScroll(mainScrollView.directionFromViewOffset(it, Rect()))
                }
            }
        }
    }

    /*
     * Parse the response from out data layer and display
     */
    private fun parseResponse(response: DeliveryResponse) {
        response.dropoffs?.let {
            var selectedRadioButton: RadioButton? = null
            for(dropOff in it) {
                if(dropOff.deliveries?.isNotEmpty() == true) {

                    val radioButton = mainViewModel.createRadioButtonFromDropOff(dropOff, this)
                    mainSegmentedGroup.addView(radioButton)

                    if(radioButton.text == getString(R.string.today_text)) {
                        selectedRadioButton = radioButton
                        mainSegmentedGroup.check(mainViewModel.idFromDay(mainViewModel.currentDayFromDropOff(dropOff)))
                    }
                }
            }

            verifySelectedDayIsVisible(selectedRadioButton)

            mainSegmentedGroup.updateBackground()
        }
    }
}


