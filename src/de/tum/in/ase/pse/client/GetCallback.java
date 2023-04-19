package de.tum.in.ase.pse.client;


import de.tum.in.ase.pse.model.Car;
import kong.unirest.*;
import kong.unirest.json.JSONObject;

public class GetCallback implements Callback<JsonNode> {

    private final RESTClientDelegate delegate;
    
    private static final int SUCCESS_RESPONSE = 200;

    /**
     * constructor -> binds the delegate who
     * will fulfill the request to this class
     */
    public GetCallback(RESTClientDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void completed(HttpResponse<JsonNode> response) {
        /*TODO:implement the GET-response.*/
        if (getResponseStatus(response) == SUCCESS_RESPONSE) {
            Car car1 =  new Car(getBodyObject(response.getBody()));
            System.out.println(getResponseBody(response));
            delegate.getCarFinished(car1, getResponseStatus(response));
        }
    }

    //you can leave it as it is
    @Override
    public void failed(UnirestException e) {
        System.out.println("GET request has failed: " + e.getMessage());

    }

    //you can leave it as it is
    @Override
    public void cancelled() {
        System.out.println("GET request was cancelled");
    }

    //AUXILIARY METHODS

    /**
     * returns the status of a response to a REST request
     */
    private int getResponseStatus(HttpResponse<JsonNode> response) {
        int status = response.getStatus();
        System.out.println("GET car(id) request has completed with status " + status);
        return status;
    }

    /**
     * returns the body of a response to a REST request as a JsonNode
     */
    private JsonNode getResponseBody(HttpResponse<JsonNode> response) {
        return response.getBody();
    }

    /**
     * returns the JSON object from the body of a response to a REST request
     */
    private JSONObject getBodyObject(JsonNode body) {
        return body.getObject();
    }
}
