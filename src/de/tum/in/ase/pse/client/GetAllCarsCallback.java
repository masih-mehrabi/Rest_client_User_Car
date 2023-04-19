package de.tum.in.ase.pse.client;


import de.tum.in.ase.pse.model.Car;
import kong.unirest.*;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetAllCarsCallback implements Callback<JsonNode> {

    private final RESTClientDelegate delegate;

    /**
     * constructor -> binds the delegate who
     * will fulfill the request to this class
     */
    public GetAllCarsCallback(RESTClientDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void completed(HttpResponse<JsonNode> response) {
        List<Car> cars = new ArrayList<>();
        
        for (int i = 0; i < getResponseBody(response).getArray().length(); i++) {
            Car car = new Car(getCarJson(response.getBody(), i));
            cars.add(car);
        }
        delegate.getAllCarsFinished(cars, getResponseStatus(response));
    }

    @Override
    public void failed(UnirestException e) {
        System.out.println("GET request has failed: " + e.getMessage());
    }

    @Override
    public void cancelled() {
        System.out.println("GET request was cancelled");
    }


    /**
     * returns the status of a response to a REST request
     */
    private int getResponseStatus(HttpResponse<JsonNode> response) {
        int status = response.getStatus();
        System.out.println("GET cars request has completed with status " + status);
        return status;
    }

    /**
     * returns the body of a response to a REST request as a JsonNode
     */
    private JsonNode getResponseBody(HttpResponse<JsonNode> response) {
        return response.getBody();
    }

    /**
     * get the car as a JSONObject -> return a single car from an Array of cars contained
     * in the body of a response
     */
    private JSONObject getCarJson(JsonNode body, int i) {
        return body.getArray().getJSONObject(i);
    }
}
