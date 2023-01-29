package com.example.cloudanttest.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cloudant.sync.documentstore.DocumentStore;
import com.cloudant.sync.documentstore.DocumentStoreNotOpenedException;
import com.cloudant.sync.replication.Replicator;
import com.cloudant.sync.replication.ReplicatorBuilder;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public  class ReplicationFactory {

    //local db which is created ex: manual, masters
    private DocumentStore mDocumentStore;
    private Replicator mPullReplicator;



    //to box
    //to cloud
    public Replicator replicate(String source, String destination, File path){

        URI uri;
        try {
             uri = new URI("https://apikey-v2-2q25sze5zjw9xclsq2z9oajki8fh94v74wf76fdkj79:8cec861877018bfa8e4a5c1fb0f204fc@ec1df7de-1f54-4eaa-b3cd-74abb6c7f849-bluemix.cloudantnosqldb.appdomain.cloud/"+source);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


        try {
            this.mDocumentStore = DocumentStore.getInstance(new File(path, destination));
        } catch (DocumentStoreNotOpenedException e) {
            Log.e("Exception", "Unable to open DocumentStore", e);
        }

        mPullReplicator = ReplicatorBuilder.pull().to(mDocumentStore).from(uri).build();
        mPullReplicator.start();
        return mPullReplicator;

    }

  /*  private URI createServerURI()
            throws URISyntaxException {
        // We store this in plain text for the purposes of simple demonstration,
        // you might want to use something more secure.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        String username = sharedPref.getString(TodoActivity.SETTINGS_CLOUDANT_USER, "");
        String dbName = sharedPref.getString(TodoActivity.SETTINGS_CLOUDANT_DB, "");
        String apiKey = sharedPref.getString(TodoActivity.SETTINGS_CLOUDANT_API_KEY, "");
        String apiSecret = sharedPref.getString(TodoActivity.SETTINGS_CLOUDANT_API_SECRET, "");
        String host = username + ".cloudant.com";

        // We recommend always using HTTPS to talk to Cloudant.
        return new URI("https", apiKey + ":" + apiSecret, host, 443, "/" + dbName, null, null);
    }*/
}
