package com.example.android_test.java.model;

import com.example.android_test.java.EmployeeApp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FakeInterceptor implements Interceptor {

    private static Map<String, ResponseObject> sInterceptionMap = new HashMap<>();

    public static void addTest(String regex, ResponseObject object) {
        sInterceptionMap.put(regex,object);
    }

    public static void clearAllTests(){
        sInterceptionMap.clear();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        if(EmployeeApp.useFakeResponse) {
            String responseString;
            final HttpUrl uri = chain.request().url();
            String url = uri.toString();
            ResponseObject responseObject = null;
            for (String key : sInterceptionMap.keySet()) {
                if (url.contains(key)) {
                    responseObject = sInterceptionMap.get(key);
                    break;
                }
            }

            if (responseObject != null) {
                try {
                    responseString = responseObject.getResponse();
                }
                catch(Exception e){
                    e.printStackTrace();
                    responseString = null;
                }

                if (responseString != null) {
                    response = new Response.Builder()
                            .code(responseObject.getReturnCode())
                            .message(responseObject.getMessage())
                            .request(chain.request())
                            .protocol(Protocol.HTTP_1_0)
                            .body(ResponseBody.create(MediaType.parse(responseObject.getType()), responseString.getBytes()))
                            .addHeader("content-type", responseObject.getType())
                            .build();

                } else {
                    response = chain.proceed(chain.request());
                }
            } else {
                response = chain.proceed(chain.request());
            }
        } else{
            response = chain.proceed(chain.request());
        }
        return response;
    }
}
