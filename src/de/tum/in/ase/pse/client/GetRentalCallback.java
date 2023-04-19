package de.tum.in.ase.pse.client;


import de.tum.in.ase.pse.model.Rental;
import kong.unirest.*;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetRentalCallback implements Callback<JsonNode> {

    private final RESTClientDelegate delegate;

    /**
     * constructor -> binds the delegate who
     * will fulfill the request to this class
     */
    public GetRentalCallback(RESTClientDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void completed(HttpResponse<JsonNode> response) {
        /*TODO:implement the GET-response.*/
        List<Rental> rentals = new ArrayList<>();
        
        for (int i = 0; i < getResponseBody(response).getArray().length(); i++) {
            Rental rental = new Rental(getRentalJson(getResponseBody(response), i));
            rentals.add(rental);
        }
         delegate.getAllUserRentalsFinished(rentals, getResponseStatus(response));
        System.out.println(rentals);
        
    }

    //you can leave it as it is
    @Override
    public void failed(UnirestException e) {
        System.out.println("Get request has failed: " + e.getMessage());
    }

    //you can leave it as it is
    @Override
    public void cancelled() {
        System.out.println("Get request was cancelled");
    }

    //AUXILIARY METHODS

    /**
     * returns the status of a response to a REST request
     */
    private int getResponseStatus(HttpResponse<JsonNode> response) {
        int status = response.getStatus();
        System.out.println("GET request to rentals has completed with status " + status);
        return status;
    }

    /**
     * returns the body of a response to a REST request as a JsonNode
     */
    private JsonNode getResponseBody(HttpResponse<JsonNode> response) {
        return response.getBody();
    }

    /**
     * get the rental as a JSONObject -> return a single rental from an Array of rentals contained
     * in the body of a response
     */
    private JSONObject getRentalJson(JsonNode body, int i) {
        return body.getArray().getJSONObject(i);
    }
}
