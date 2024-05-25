package Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.foodfireproject.R
import com.example.foodfireproject.YelpService
import com.example.foodfireproject.models.YelpSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import com.example.foodfireproject.models.YelpBusiness


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private lateinit var restaurantImageView: ImageView
    private lateinit var restaurantTextView: TextView
    private var currentIndex = 0
    private var restaurants: List<YelpSearchResult>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    fun fetchLocalRestaurants(location: String) {
        val call = YelpService.api.searchRestaurants(YelpService.getAuthHeader(), "restaurants", location)
        call.enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                if (response.isSuccessful)
                {
                    val restaurants = response.body()?.businesses
                    val firstRestaurant = restaurants?.get(currentIndex)
                    restaurantTextView.text = "Name: ${firstRestaurant?.name}, Rating: ${firstRestaurant?.rating}"

                    firstRestaurant?.let {
                        val imageUrl = it.image_url // Assuming there's an imageUrl property in your YelpSearchResult model
                        // You can use any image loading library or load the image asynchronously
                        // Here's a basic example using Picasso library
                        Glide.with(requireContext()).load(imageUrl).into(restaurantImageView)
                    }

                } else
                {
                    Log.e("Yelp", "Failed to get response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.e("Yelp", "Network request failed: ${t.message}")
            }
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_food, container, false)

        restaurantImageView = view.findViewById(R.id.restaurantImageView)
        restaurantTextView = view.findViewById(R.id.restaurantTextView)


        val submitCityButton = view.findViewById<Button>(R.id.foodSearch)

        submitCityButton.setOnClickListener{
            val location = view.findViewById<EditText>(R.id.foodCityET).text.toString()
            fetchLocalRestaurants(location)
        }

        val nextRestaurant = view.findViewById<Button>(R.id.showNextResturantButton)

        nextRestaurant.setOnClickListener{
            ++currentIndex
            val location = view.findViewById<EditText>(R.id.foodCityET).text.toString()
            fetchLocalRestaurants(location)
        }




        return view
    }


























    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}