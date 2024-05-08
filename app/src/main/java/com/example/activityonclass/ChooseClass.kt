package com.example.activityonclass

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size

class MyListAdapter1
    (
    private val context: Activity, private val maintitles: List<String>,

    ) : ArrayAdapter<String>(context, R.layout.list_class, maintitles) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView: View = inflater.inflate(R.layout.list_class, null, true)
        val titleText = rowView.findViewById(R.id.textView3) as TextView

        titleText.text = maintitles[position]


        return rowView
    }
}
class ChooseClass : AppCompatActivity() {
    val tClass= arrayListOf<String>("19CLC","20CLC","21CLC")
    var customListView: ListView? = null
    var text:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_class2)
        customListView=findViewById(R.id.listView)
        customListView!!.setItemsCanFocus(true);
        val adapter = MyListAdapter1(this,tClass)
        customListView!!.adapter = adapter
        customListView!!.choiceMode=ListView.CHOICE_MODE_SINGLE
        customListView!!.setOnItemClickListener { adapterView, view, i, l ->
            text = tClass[i]
            customListView!!.deferNotifyDataSetChanged()
            try {
                for (ctr in 0..customListView!!.size) {
                    if (i === ctr) {
                        customListView!!.getChildAt(ctr).setBackgroundColor(Color.CYAN)
                    } else {
                        customListView!!.getChildAt(ctr).setBackgroundColor(Color.WHITE)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
        val btn:Button=findViewById(R.id.button)
        btn.setOnClickListener {
            val replyIntent = Intent()


            replyIntent.putExtra("key3", text)


            setResult(800, replyIntent)
            finish()
        }
    }
}