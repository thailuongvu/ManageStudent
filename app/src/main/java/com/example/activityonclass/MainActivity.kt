package com.example.activityonclass

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query

import java.io.*

class MainActivity : AppCompatActivity() {
    val REALM_NAME = "HD_REALM_NAME"
    lateinit var realm : Realm
    var autoCompleteTV: AutoCompleteTextView? = null
    val fileName1 = "student.json"
    val fileName="test01.txt"
    var simpleList:RecyclerView?=null
    val name= arrayOf("Hieu","Thai","Minh")
    val tClass= arrayOf("19CLC","20CLC","21CLC")
    val date= arrayOf("19/12/2001","19/12/2002","19/12/2003")
    val sex= arrayOf("Male","Female","Male")
    var layout = 0
    var name123 = arrayListOf<String>()
    var adapter:MyListApdapter?=null


    private lateinit var studentList:ArrayList<Student>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm.copyFromRealm(filter)
        setContentView(R.layout.activity_main)
        openDatabase()
        writPerson()
        val btnAdd:Button = findViewById(R.id.button2)
        val btnChange:ImageButton=findViewById(R.id.addBtn)
        autoCompleteTV = findViewById(R.id.autoCompleteTextView)
        simpleList=findViewById(R.id.simpleListView)


        studentList=ArrayList()
    for(i in name.indices){
        name123.add(name[i])
        val s=Student(name[i], tClass[i],date[i],sex[i])
        studentList.add(s)
    }
        adapter=MyListApdapter(studentList)
        simpleList!!.adapter=adapter

        simpleList!!.layoutManager=LinearLayoutManager(this)


        adapter!!.onItemClick = { student:Student,pos:Int->
            val intent = Intent(this, SecondActivity::class.java)
            //intent.putExtra("key", "This is value")
            Toast.makeText (this, "Row clicekd: " + student.name,
                ("Row clicekd: " + student.name).length).show()

            intent.putExtra("key", student )

            intent.putExtra("keyIndex",pos)
            startActivityForResult(intent, 200)
        }

        if (layout == 0) {
            simpleList!!.layoutManager = LinearLayoutManager(this) //GridLayoutManager(this, 2)
        }
        else {
            simpleList!!.layoutManager = GridLayoutManager(this, 2)
        }
        btnChange.setOnClickListener() {
            if (layout == 0) {
                simpleList!!.layoutManager = GridLayoutManager(this, 2)
                layout = 1
            }
            else {
                simpleList!!.layoutManager = LinearLayoutManager(this)
                layout = 0
            }
            adapter!!.notifyDataSetChanged()
        }

        val searchAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, name123)
        autoCompleteTV!!.setAdapter(searchAdapter)
        autoCompleteTV!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var studentB:ArrayList<Student>
                studentB= ArrayList()
                for(i in 0..studentList.size-1){

                    if(studentList[i].name.contains(autoCompleteTV!!.text,ignoreCase = true)){
                        studentB.add(studentList[i])
                    }



                }
                val adapter1 =MyListApdapter(studentB)
                simpleList!!.adapter=adapter1
//                adapter1.onItemClick={
//                    student,pos->
//                    val intent = Intent(this, SecondActivity::class.java)
//                    //intent.putExtra("key", "This is value")
//
//
//                    intent.putExtra("key", student )
//
//                    intent.putExtra("keyIndex",pos)
//                    startActivityForResult(intent, 300)
//
//                }



            }
        })



        btnAdd.setOnClickListener {
            val intent = Intent(this, AddStudent::class.java)
            //intent.putExtra("key", "This is value")
            startActivityForResult(intent, 300)
        }
        if(ContextCompat.checkSelfPermission(
                applicationContext,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            val permissions= arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)

            ActivityCompat.requestPermissions(this,permissions,11111)


        }else{

            finish()
        }
    }
    fun openDatabase(){
        val configuration = RealmConfiguration.Builder(
            setOf(Person::class,Dog::class))
            .name(REALM_NAME).build()
// specify name so realm doesn't just use
// the "default.realm" file

        realm = Realm.open(configuration)
        Log.i("hdlog",realm.configuration.path)
///data/user/0/com.nlhdung.realmapp/files/HD_REALM_NAME
        writPerson()
    }
    fun writPerson(){
// plain old kotlin object
        val person = Person().apply {
            name = "Dung Nguyen"
            dog = Dog().apply { name = "Fido"; age = 2 }
        }

// persist it in a transaction
        realm.writeBlocking {
            val managedPerson = this.copyToRealm(person)
            Log.i("hdlog","Wrote " + managedPerson.name)
        }
    }
    fun readAllWithName(name: String){
        val all = realm.query<Person>().find()
        Log.i("hdlog","Read " + all.size + " elements")
// Persons named 'Dung'
//val personsByNameQuery = realm.query<Person>("name = $0", name)
        val personsByNameQuery = realm.query<Person>("name contains $0",
            name)
        val filteredPersons = personsByNameQuery.find()
        Log.i("hdlog","Read " + filteredPersons.toString())
    }
    override fun onPause() {
        super.onPause()
        createanWriteFile()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            11111->{
                if(grantResults.size > 0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                    Toast.makeText(this,"permission", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this,"permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
    fun createanWriteFile(){
        try {
            val path="mnt/sdcard/$fileName"
            val file= File(path)
            file.createNewFile()
            val outputStream= FileOutputStream(file)
            val writer= OutputStreamWriter(outputStream)

            writer.write(studentList.toString())
            writer.close()
            outputStream.close()
        }catch (t:Throwable

        ){

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 300) {
            if (resultCode === 700) {

                val people = data!!.getSerializableExtra("key3") as Student

                studentList.add(people)

                //simpleList!!.adapter=adapter
                adapter!!.notifyItemInserted(studentList.size-1)

            }
        }
        if(requestCode===200){
            if (resultCode === Activity.RESULT_OK) {
                val index=data!!.getIntExtra("indexB",0)

                val people = data!!.getSerializableExtra("key2") as Student

                studentList[index]=people

                //val adapter=MyListApdapter( studentList)
                //studentList[index]=people
                //simpleList!!.adapter=adapter
                adapter!!.notifyItemChanged(index)
            }else{
                val indexDel=data!!.getIntExtra("indexD",0)
                studentList.removeAt(indexDel)

                adapter!!.notifyItemRemoved(indexDel)
            }

        }
    }

}
