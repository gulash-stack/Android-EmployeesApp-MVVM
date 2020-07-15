package com.example.android_test.java.model;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class ResponseObject {

    public enum ResponseType{
        JSON
    }

    private ResponseType responseType = ResponseType.JSON;
    private String responseBodyString;
    private Object responseBody;
    private int returnCode;
    private String message = "";

    public String getResponseBodyString() {
        return responseBodyString;
    }

    public void setResponseBodyString(String responseBodyString) {
        this.responseBodyString = responseBodyString;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * Sets the out type (XML / JSON)
     * Only relevant when input is Object(responseBody) and not responseBodyString
     * @param type XML or JSON
     */
    public void setType(ResponseType type) {
        this.responseType = type;
    }

    public String getType() {
        return responseType == ResponseType.JSON ? "application/json" : "application/xml";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getResponse() throws JSONException {
        if (responseBodyString != null && !responseBodyString.isEmpty()){
            return getResponseBodyString();
        }
        else if(responseBody != null)
        {
            String resultString = null;
            if (responseType == ResponseType.JSON) {
                Gson gson = new Gson();
                resultString = gson.toJson(responseBody);
            }
            return resultString;
        }
        else return null;
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open("json/power.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
