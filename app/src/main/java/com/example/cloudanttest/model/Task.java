package com.example.cloudanttest.model;

import com.cloudant.sync.documentstore.DocumentRevision;

import java.util.HashMap;
import java.util.Map;

public class Task {

    private Task() {}

    public Task(int desc) {
        this.setBedrooms(desc);
        this.setSquare_feet(squarefeet);
        this.setPrice(price);
    }

    // this is the revision in the database representing this task
    private DocumentRevision rev;
    public DocumentRevision getDocumentRevision() {
        return rev;
    }


    private int squarefeet;
    public int getSquare_feet() {
        return this.squarefeet;
    }
    public void setSquare_feet(int square_feets) {
        this.squarefeet = square_feets;
    }

    private int bedrooms;
    public int getBedrooms() {
        return this.bedrooms;
    }
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    private int price;
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{ bedrooms: " + getBedrooms() + ", squarefeets: " + getSquare_feet() + ", price: " + getPrice()+"}";
    }

    public static Task fromRevision(DocumentRevision rev) {
        Task t = new Task();
        t.rev = rev;
        // this could also be done by a fancy object mapper
        Map<String, Object> map = rev.getBody().asMap();

            t.setSquare_feet((int) map.get("square_feet"));
            t.setBedrooms((int) map.get("bedrooms"));
            t.setPrice((int) map.get("price"));
            return t;

    }

    public Map<String, Object> asMap() {
        // this could also be done by a fancy object mapper
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("square_feet", squarefeet);
        map.put("bedrooms", bedrooms);
        map.put("price", price);
        return map;
    }
}
