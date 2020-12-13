package com.ismin.android

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class CreateCardFragment : Fragment() {

    private lateinit var listener: CardCreator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_create_card, container, false)

        rootView.findViewById<View>(R.id.f_create_card_view_clicker).setOnClickListener {
            listener.closeCardCreation()
        }

        rootView.findViewById<Button>(R.id.f_create_card_btn_save).setOnClickListener {
            val name =
                rootView.findViewById<EditText>(R.id.f_create_card_edt_name).text.toString();
            val job =
                rootView.findViewById<EditText>(R.id.f_create_card_edt_job).text.toString();
            val description = rootView.findViewById<EditText>(R.id.f_create_card_edt_description).text.toString();
            val card = Card(name, job, description)
            listener.onCardCreated(card)
        }


        ObjectAnimator.ofFloat(
            rootView.findViewById(R.id.constraintLayout),
            "translationY",
            300f,
            0f
        )
            .apply {
                interpolator = BounceInterpolator()
                duration = 5000
                start()
            }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CardCreator) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CardCreator")
        }
    }

}

interface CardCreator {
    fun onCardCreated(card: Card)
    fun closeCardCreation()
}