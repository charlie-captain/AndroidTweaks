package com.charlie.androidtweaks.ui


import android.os.Bundle
import android.support.v7.preference.Preference
import android.view.View
import com.charlie.androidtweaks.R
import com.charlie.androidtweaks.data.Tweak
import kotlinx.android.synthetic.main.tweaks_toolbar.*

private const val KEY_TWEAKS = "tweakfragment"

class TweakFragment : TweakBaseFragment() {

    private var tweaks: ArrayList<Tweak>? = null
    //collections
    private var heads: HashSet<String> = HashSet()
    //tweaks of each head
    private var headsTweaks: HashMap<String, ArrayList<Tweak>> = HashMap()
    //float window come back value
    internal var floatTweak: String? = ""

    companion object {

        fun newInstance(tweaks: ArrayList<Tweak>) =
            TweakFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_TWEAKS, tweaks)
                }
            }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        tweaks = arguments?.getSerializable(KEY_TWEAKS) as? ArrayList<Tweak>

        for (i in tweaks!!) {
            heads.add(i.collection)
        }

        for (i in heads) {
            val preference = Preference(context)
            preference.title = i
            preference.setOnPreferenceClickListener {
                (activity as? TweakActivity)?.tweaks_toolbar?.title = i
                val fragment = TweakChildFragment.newInstance(headsTweaks[i] as ArrayList<Tweak>)
                floatTweak = i
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