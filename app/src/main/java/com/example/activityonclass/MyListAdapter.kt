package com.example.activityonclass

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyListApdapter(private val students : List<Student>) :
    RecyclerView.Adapter<MyListApdapter.ViewHolder>() {
    var onItemClick: ((Student,Int) -> Unit)? = null
    var index:Int?=null
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

        val imageView:ImageView= listItemView.findViewById(R.id.imageView)
       val name:TextView=listItemView.findViewById(R.id.textView2)
       val tClass:TextView=listItemView.findViewById(R.id.textView3)
      val date:TextView=listItemView.findViewById(R.id.textView4)
      val sex:TextView=listItemView.findViewById(R.id.textView5)
        init {
            listItemView.setOnClickListener { onItemClick?.invoke(students[adapterPosition],adapterPosition) }
//            val messageBtnTemp = listItemView.findViewById<Button>(R.id.messageBtn)
//            messageBtnTemp.setOnClickListener {
//                onButtonClick?.invoke(students[adapterPosition])
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):

            MyListApdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
// Inflate the custom layout
        val contactView = inflater.inflate(R.layout.list_view, parent, false)
// Return a new holder instance
        return ViewHolder(contactView)
    }
    override fun getItemCount(): Int {
        return students.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val student: Student = students.get(position)
        index=position
        // Set item views based on your views and data model
        val tvFullName = holder.name
        tvFullName.setText(student.name)
        val tvClass = holder.tClass
        tvClass.setText(student.tClass)
        val tvBirthday = holder.date
        tvBirthday.setText(student.date)
        val tvSex=holder.sex
        tvSex.setText(student.sex)

    }

}
//class MyListAdapter(private val context: Activity, private val maintitles: ArrayList<Student>,
//                    ):
//    ArrayAdapter<Student>(context, R.layout.list_view, maintitles){
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater=context.layoutInflater
//        val rowView:View =inflater.inflate(R.layout.list_view,null,true)
//        val imageView:ImageView= rowView.findViewById(R.id.imageView)
//        val name:TextView=rowView.findViewById(R.id.textView2)
//        val tClass:TextView=rowView.findViewById(R.id.textView3)
//        val date:TextView=rowView.findViewById(R.id.textView4)
//        val sex:TextView=rowView.findViewById(R.id.textView5)
//        name.text=maintitles[position].name
//        tClass.text=maintitles[position].tClass
//        date.text=maintitles[position].date
//        sex.text=maintitles[position].sex
//
//        return rowView
//    }
//    }

