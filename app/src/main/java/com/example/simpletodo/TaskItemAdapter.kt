package com.example.simpletodo
import android.hardware.biometrics.BiometricManager
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

/*
** Adapter is a bridge that tells the recycler view how to display the data we give it.
* The data here is the list of strings and it will be rendered item by item
 */
//created an adapter and passed the data that the adapter needs to display (aka listOfItems)
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener:OnLongClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    //define an interface for the long click
    interface OnLongClickListener{
        //need to pass in a specific position so that it knows which position to interact with
        fun onItemLongClicked(position:Int)
    }

    // Usually involves inflating a layout from XML and returning the holder
    //how to layout for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Get some context first.
        val context = parent.context
        //Then use the context to get the layout inflater.
        //The layout inflater is what we will use to inflate the simple layout
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        //the layout simple_list_item_1 is a basic layout that's provided by android
        //command+b to see the file for the simple layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    //Take whatever is in the list of items and use it to populate the list in view holder.
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Get the data model based on the position.
        //Our list of item is our data model.
        val item=listOfItems.get(position)

        //Setting the textView to be whatever the text for the specific task is.
        holder.textView.text= item.toString()
    }

    //the getter function that returns the size of the list
    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    //Grabbing references to the views we need so we can populate data into those views
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Store references to elements in our layout.
        //Since our layout (simple_list_item_1) has only one textview, that's the only thing we
        //need to reference to.

        //declare our textView
        val textView: TextView

        //Set our textView in the init block by using findViewById
        init{
            textView=itemView.findViewById(android.R.id.text1)

            //the itemView represents the single line in our To Do list
            itemView.setOnLongClickListener{
                //The adapterPosition will give us the position of where the action is taking place
                //Log.i("caren", "Long clicked on item: " + adapterPosition)
                //Once item is long clicked, it will look at the longClickListener that was passed in
                //and call the method onItemLongClicked while passing the adapter position
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }

    }
}