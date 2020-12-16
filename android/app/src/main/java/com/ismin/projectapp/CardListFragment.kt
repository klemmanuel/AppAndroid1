package com.ismin.projectapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ismin.projectapp.databinding.FragmentCardListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CardListFragment : Fragment() {
    private lateinit var activity: Context
    private lateinit var binding: FragmentCardListBinding
    private lateinit var adapter: CardAdapter

    fun submitList(cards: List<Card>) {
        adapter.submitList(cards)
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onAttach(context: Context) {
        if (context is DescriptionActivityStarter && context is CanBeFavorite && context is CardsGetter) {
            activity = context
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate the layout for this fragment
        binding = FragmentCardListBinding.inflate(inflater, container, false)

        adapter = CardAdapter(
            onStartDescritionPushed = (activity as DescriptionActivityStarter)::startDescriptionActivity,
            onFavorite = (activity as CanBeFavorite)::toggleFavorite,
        )
        binding.fCardListRcvCards.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(context)
        binding.fCardListRcvCards.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        binding.fCardListRcvCards.addItemDecoration(dividerItemDecoration)

        refresh()

        return binding.root
    }

    fun refresh() {
        showLoading()
        (activity as CardsGetter).getAllCards().enqueue(object : Callback<List<Card>> {
            override fun onResponse(
                call: Call<List<Card>>,
                response: Response<List<Card>>
            ) {
                if (response.code() != 200) {
                    val error = HttpException(response)

                    displayErrorToast(error)
                    Log.e(DescriptionActivity.TAG, error.toString(), error)
                }
                response.body()?.let {
                    submitList(it)
                }
                hideLoading()
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                displayErrorToast(t)
                hideLoading()
            }
        })
    }

    private fun displayErrorToast(t: Throwable) {
        Toast.makeText(
            activity.applicationContext,
            "Network error ${t.localizedMessage}",
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CardListFragment()
    }
}

interface DescriptionActivityStarter {
    fun startDescriptionActivity(card: Card)
}

interface CanBeFavorite {
    fun toggleFavorite(card: Card)
}

interface CardsGetter {
    fun getAllCards(): Call<List<Card>>
}