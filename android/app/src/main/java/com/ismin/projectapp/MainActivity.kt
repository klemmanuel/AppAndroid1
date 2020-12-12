package com.ismin.projectapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import org.json.JSONObject


class MainActivity : AppCompatActivity(), ListeFragment.OnFragmentInteractionListener {

    fun onFragmentInteraction(uri: Uri) { //pour plus tard avec la map
    }
    var listElement: ArrayList<Element> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listElement = arrayListOf()

        getBDD()
        val buttInfo = findViewById<Button>(R.id.info_button)
        buttInfo.setOnClickListener {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = InfoFragment()
            fragmentTransaction.replace(R.id.main_fragments_view, fragment)
            fragmentTransaction.commit()
        }

        //listener qui charge le fragment liste
        val listButton =  findViewById<Button>(R.id.liste_button)
        listButton.setOnClickListener {
            var fragment = ListeFragment()

            //data à transmettre
            val bundle = Bundle()
            bundle.putSerializable("element", listElement)
            fragment.arguments = bundle

            var fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_fragments_view, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun openDescription(position: Int) {
        val intent = Intent(this, DescriptionActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("Element", listElement[position])
        intent.putExtras(bundle)
        this.startActivity(intent)
    }

    //on utilise Fuel pour récuperer les données
    fun getBDD() {
        val myurl: String = "https://www.data.gouv.fr/s/resources/monuments-et-tombes-de-personnalites-du-cimetiere-du-pere-lachaise/20141103-215847/perelachaise_data.json"
        Fuel.get(myurl).responseString { request, response, result ->
            //do something with response
            result.fold({ d ->
                //do something with data
                val response = result.get()
                Toast.makeText(this, "It works, values: $result", Toast.LENGTH_SHORT).show()
                val jsonObject = JSONObject(response)
                val dataArray = jsonObject.getJSONArray("monuments")
                for (i in 0 until dataArray.length()) {
                    val dataobj = dataArray.getJSONObject(i)
                    //les liens ont changé du http au https ... on rajoute donc le s
                    val bonURL: String = dataobj.getJSONObject("image_principale").getString("url_original").substring(0, 4) + "s" + dataobj.getJSONObject("image_principale").getString("url_original").substring(4)

                    //on créer d'abord la table des personnes enterrées à cet endroit
                    var personnes: ArrayList<Personne> = arrayListOf()
                    val dataPersonneArray = dataobj.getJSONArray("personnalites")
                    for(i in 0 until dataPersonneArray.length()){
                        var dataobjPersonne = dataPersonneArray.getJSONObject(i)
                        var personne = Personne(dataobjPersonne.getString("nom"), dataobjPersonne.getString("activite"), dataobjPersonne.getString("date_naissance"), dataobjPersonne.getString("date_deces"), dataobjPersonne.getString("lien_wikipedia"))
                        personnes.add(personne)
                    }

                }

            }, { err ->
                //do something with error
            })
        }
    }

}
