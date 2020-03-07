package com.mateuszcholyn

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mateuszcholyn.wallet.R

class TextFragment : Fragment() {

    private var customText: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.text_fragment, container, false)
        val textView = view.findViewById<TextView>(R.id.textFragmentId)
        textView.text = customText
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (customText == null) {
            customText = arguments?.getString("customText")
        }
    }

    override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        if (context != null && attrs != null && customText == null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.MyFragment_MembersInjector)
            if (ta.hasValue(R.styleable.MyFragment_MembersInjector_customText)) {
                customText = ta.getString(R.styleable.MyFragment_MembersInjector_customText)
            }
            ta.recycle()
        }
    }
}