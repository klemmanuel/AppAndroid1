package com.ismin.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_CARDS = "ARG_CARDS📚"

class CardListFragment : Fragment() {
    private lateinit var cards: ArrayList<Card>

    private lateinit var rcvCards: RecyclerView
    private lateinit var cardAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cards = arguments!!.getSerializable(ARG_CARDS) as ArrayList<Card>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_card_list, container, false)

        this.rcvCards = rootView.findViewById(R.id.f_card_list_rcv_cards)
        cardAdapter = CardAdapter(cards)
        this.rcvCards.adapter = cardAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        this.rcvCards.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        this.rcvCards.addItemDecoration(dividerItemDecoration)

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(cardsToDisplay: ArrayList<Card>): CardListFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARG_CARDS, cardsToDisplay)

            val cardListFragment = CardListFragment()
            cardListFragment.arguments = bundle

            return cardListFragment;
        }
    }
}