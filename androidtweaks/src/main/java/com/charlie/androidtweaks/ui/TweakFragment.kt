package com.charlie.androidtweaks.ui


import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.data.Tweak

private const val KEY_TWEAKS = "tweakfragment"

class TweakFragment : TweakBaseFragment() {

    private lateinit var tweaks: ArrayList<Tweak>
    //collections
    private var heads: MutableSet<String>
    //tweaks of each head
    private var headsTweaks: MutableMap<String, ArrayList<Tweak>>
    private lateinit var categorys: ArrayList<String>

    companion object {


        fun newInstance(tweaks: ArrayList<Tweak>) =
            TweakFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_TWEAKS, tweaks)
                }
            }
    }

    init {
        heads = mutableSetOf()
        headsTweaks = mutableMapOf()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        tweaks = arguments?.getSerializable(KEY_TWEAKS) as ArrayList<Tweak>


        for (i in tweaks) {
            heads.add(i.collection)
        }

        for (i in heads) {
            val preference = Preference(context)
            preference.title = i
            preference.setOnPreferenceClickListener {
                val fragment = TweakChildFragment.newInstance(i, headsTweaks.get(i) as ArrayList<Tweak>)
                requireFragmentManager().beginTransaction()
                    .replace(R.id.fl_tweak, fragment)
                    .addToBackStack("tweak_child")
                    .commit()
                false
            }
            screen.addPreference(preference)
        }

        preferenceScreen = screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (i in heads) {
            var list = mutableListOf<Tweak>()
            for (j in tweaks) {
                if (i.equals(j.collection)) {
                    list.add(j)
                }
            }
            headsTweaks.put(i, list as ArrayList<Tweak>)
        }
    }
}