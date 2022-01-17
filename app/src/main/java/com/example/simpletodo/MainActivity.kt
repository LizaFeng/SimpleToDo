package com.example.simpletodo
import android.hardware.biometrics.BiometricManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUriExposedException
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    //creating a variable that will hold the list of tasks
    //defining the list as an empty list
    var listOfTasks=mutableListOf<String>()

    //lateinit just means that we are going to initialize the variable something later on. This is to
    //avoid the error that tells us we did not initialize the variable
    lateinit var adapter:TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Since we added a parameter to the taskItemAdapter, we need to pass another paramter
        //when we call it. In our case, it is a longClick variable which will be held and implement
        //the interface that we just created for the longclick in adapter
        val OnLongClickListener=object:TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //defining what we want to happen
                //1. remove the item long clicked
                listOfTasks.removeAt(position)
                //2. notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()
                //3. save the changes in the file
                saveItems()

            }


        }

//        //1. let's detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            //code in here is going to be executed when the user clicks on a button
//
//        }
        //Example of how we can personally add to list of task.
        //listOfTasks.add("Do Laundry")

        loadItems()

        //Look up recyclerView in the layout and grab a reference to it.
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, OnLongClickListener)
        //Now we have our adapter and we passed in out listOfTasks that we want it to display in the
        //recyclerView.

        //Before we added this line, the adapter and the recyclerView exists independently from each other
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter= adapter

        //layout manager tells the recyclerView how to set up itself
        recyclerView.layoutManager= LinearLayoutManager(this)

        //created a variable for the findViewById so we dont have to make multiple calls
        val inputTextField=findViewById<EditText>(R.id.addTaskField)
        //set up the button and the input field so that the user can enter a task
        //1. get a reference for the button and setting a onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener{
                //1a. grab the text the user inputted
                val userInputtedTask=inputTextField.text.toString()
                //1b. add the string to the list of task
                listOfTasks.add(userInputtedTask)
                //1c. notify the data adapter that our data has been updated
                adapter.notifyItemInserted(listOfTasks.size-1)
                //1c. reset the text field so it is empty
                inputTextField.setText("")
                //save the changed data
                saveItems()

        }

    }
    //Save the data user inputted.
    //Save the data by writing and reading into a file.

    //create a method to get the data file we need
    fun getDataFile(): File{
        //Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    //Load the items by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks=org.apache.commons.io.FileUtils.readLines(getDataFile(),Charset.defaultCharset())
        }
        catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }

    //save items by writing them into our data file:
    //Going to take everything that is already in our list of task and write it to our file.
    fun saveItems() {
        //Using the try catch thing because if something goes wrong with the file
        //and we do not have this try catch, then the app will crash and we won't know whats wrong.
        try{
            //using the FileUtils from the commons to write a listOfTasks into the datafile and
                // surrounding it with a try catch block so that our app doesnt just crash when
                    // something bad happens
            org.apache.commons.io.FileUtils.writeLines(getDataFile(),listOfTasks)
        }
        //this catch is just in case
        catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}