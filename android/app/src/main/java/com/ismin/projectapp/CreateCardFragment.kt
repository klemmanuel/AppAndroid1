package com.ismin.projectapp

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.transition.FadeThroughProvider

class CreateCardFragment : Fragment() {
    private var activity: CardCreator? = null;
    private lateinit var edtName: EditText
    private lateinit var edtJob: EditText
    private lateinit var edtDescription: EditText
    private lateinit var card: CardView
    private lateinit var rootLayout: ViewGroup
    private lateinit var blackView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_create_card, container, false)

        rootLayout = rootView.findViewById(R.id.f_create_card_root_layout)
        edtName = rootView.findViewById(R.id.f_create_card_edt_name)
        edtJob = rootView.findViewById(R.id.f_create_card_edt_job)
        edtDescription = rootView.findViewById(R.id.f_create_card_edt_description)
        card = rootView.findViewById(R.id.f_create_card_card)
        blackView = rootView.findViewById(R.id.f_create_card_black_view)

        rootView.setOnClickListener { activity?.closeCreateFragment() }
        rootView.findViewById<Button>(R.id.f_create_card_btn_save).setOnClickListener {
            saveCard()
        }

        ObjectAnimator.ofFloat(card, "translationY", 300f, 0f)
            .apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 300
                start()
            }

        FadeThroughProvider().createAppear(rootLayout, card)?.start()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()

        ObjectAnimator.ofFloat(card, "translationY", 0f, 300f)
            .apply {
                interpolator = AccelerateDecelerateInterpolator()
                duration = 100
                start()
            }

        FadeThroughProvider().createDisappear(rootLayout, card)?.start()
    }

    override fun onAttach(context: Context) {
        if (context is CardCreator) {
            activity = context
        }
        super.onAttach(context)
    }

    fun saveCard() {
        activity?.onCardCreated(
            Card(
                edtName.text.toString(),
                edtJob.text.toString(),
                edtDescription.text.toString()
            )
        )
    }
}

interface CardCreator {
    fun onCardCreated(card: Card)
    fun closeCreateFragment()
}