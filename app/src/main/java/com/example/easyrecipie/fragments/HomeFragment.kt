package com.example.easyrecipie.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyrecipie.R
import com.example.easyrecipie.databinding.FragmentHomeBinding
import com.example.easyrecipie.models.Meal
import com.example.easyrecipie.viewModel.HomeViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var homeMvvm: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomMeal()

        observeRandomMeal()
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner,androidx.lifecycle.Observer{response->
            Glide.with(this@HomeFragment)
                .load(response.strMealThumb)
                .into(binding!!.imgRandomMeal)

        })

    }

}