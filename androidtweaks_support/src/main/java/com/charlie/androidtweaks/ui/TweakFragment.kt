package com.charlie.androidtweaks.ui


import android.os.Bundle
import android.support.v7.preference.Preference
import android.view.View
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.core.TweakManager
import com.charlie.androidtweaks.data.Tweak
import kotlinx.android.synthetic.main.tweaks_toolbar.*
import java.util.*

class TweakFragment : TweakBaseFragment() {
    private var tweaks: ArrayList<Tweak>? = null
    //collections
    private var heads: TreeSet<String> = TreeSet()
    //tweaks of each head
    private var headsTweaks: HashMap<String, ArrayList<Tweak>> = HashMap()
    //float window come back value
    internal var floatTweak: String? = ""

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        tweaks = TweakManager.getTweaks() ?: arrayListOf()

        for (tweak in tweaks!!) {
            heads.add(tweak.collection)
        }

        for (collection in heads) {
            val preference = Preference(context)
            preference.title = collection
            preference.setOnPreferenceClickListener {
                (activity as? TweakActivity)?.tweaks_toolbar?.title = collection
                val fragment = TweakChildFragment.newInstance(collection)
                floatTweak = collection
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
            for (j in tweaks!!) {
                if (i == j.collection) {
                    list.add(j)
                }
            }
            headsTweaks[i] = list as ArrayList<Tweak>
        }
    }
}