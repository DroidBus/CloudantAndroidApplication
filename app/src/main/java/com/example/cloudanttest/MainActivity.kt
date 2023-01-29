package com.example.cloudanttest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloudant.sync.event.Subscribe
import com.cloudant.sync.event.notifications.DocumentUpdated
import com.cloudant.sync.event.notifications.ReplicationCompleted
import com.cloudant.sync.event.notifications.ReplicationErrored
import com.example.cloudanttest.model.TasksModel
import com.example.cloudanttest.services.ReplicationFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Set up our tasks DocumentStore within its own folder in the applications
        // data directory.


        GlobalScope.launch {
            doReplication()
        }

    }


     fun doReplication(){

        val path: File = this.getApplicationContext().getDir(
            "db_task_local",
            MODE_PRIVATE
        )
        val replicationFactory= ReplicationFactory()
        val pullReplicator = replicationFactory.replicate("db_task", "db_task_local",path)

         pullReplicator.eventBus.register(this)
    }


    //
    // REPLICATIONLISTENER IMPLEMENTATION
    //
    @Subscribe
    fun complete(rc: ReplicationCompleted?) {
        runOnUiThread{
            Toast.makeText(this,"Replication Completed",Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Calls the TodoActivity's replicationComplete method on the main thread,
     * as the error() callback will probably come from a replicator worker
     * thread.
     */
    @Subscribe
    fun error(re: ReplicationErrored) {
        runOnUiThread{
            Toast.makeText(this,"Replication Error",Toast.LENGTH_SHORT).show()
        }
    }

    @Subscribe
    fun documentUpdated(documentUpdated: DocumentUpdated) {
        runOnUiThread{
            Toast.makeText(this,"Document updated"+ documentUpdated.newDocument.revision,Toast.LENGTH_SHORT).show()
        }

    }


}