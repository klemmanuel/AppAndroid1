package com.ismin.projectapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_CARDS = "ARG_CARDS"

class CardListFragment : Fragment() {
    private lateinit var activity: DescriptionActivityStarter
    private lateinit var cards: ArrayList<Card>
    private lateinit var rcvCards: RecyclerView

    override fun onAttach(context: Context) {
        if (context is DescriptionActivityStarter) {
            activity = context
        }
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            cards = it.getSerializable(ARG_CARDS) as ArrayList<Card>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_card_list, container, false)

        this.rcvCards = rootView.findViewById(R.id.f_card_list_rcv_cards)
        this.rcvCards.adapter = CardAdapter(cards, onStartDescritionPushed = {
            activity.startDescriptionActivity(it)
        })
        val linearLayoutManager = LinearLayoutManager(context)
        this.rcvCards.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        this.rcvCards.addItemDecoration(dividerItemDecoration)

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(cards: List<Card>) =
            CardListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CARDS, ArrayList(cards))
                }
            }
    }
}

interface DescriptionActivityStarter {
    fun startDescriptionActivity(card: Card)
}