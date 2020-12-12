package com.ismin.projectapp


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ismin.projectapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.element_liste.*
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private var image : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            //val urlImg = URL("https://www.parisinfo.com/var/otcp/sites/images/media/1.-photos/02.-sites-culturels-630-x-405/cimetiere-du-pere-lachaise-automne-630x405-c-thinkstock/37716-1-fre-FR/Cimetiere-du-Pere-Lachaise-automne-630x405-C-Thinkstock.jpg")
            //image = BitmapFactory.decodeStream(urlImg.openConnection().getInputStream())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView =inflater.inflate(R.layout.fragment_info, container, false)

        //val urlImg = URL("https://www.parisinfo.com/var/otcp/sites/images/media/1.-photos/02.-sites-culturels-630-x-405/cimetiere-du-pere-lachaise-automne-630x405-c-thinkstock/37716-1-fre-FR/Cimetiere-du-Pere-Lachaise-automne-630x405-C-Thinkstock.jpg")
        //val image : Bitmap = BitmapFactory.decodeStream(urlImg.openConnection().getInputStream())
        val viewImg=rootView.findViewById<ImageView>(R.id.imagetest)
        //viewImg.setImageBitmap(image)
        Picasso.get().load("https://www.parisinfo.com/var/otcp/sites/images/media/1.-photos/02.-sites-culturels-630-x-405/cimetiere-du-pere-lachaise-automne-630x405-c-thinkstock/37716-1-fre-FR/Cimetiere-du-Pere-Lachaise-automne-630x405-C-Thinkstock.jpg").into(viewImg)
        // Inflate the layout for this fragment
        return rootView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
