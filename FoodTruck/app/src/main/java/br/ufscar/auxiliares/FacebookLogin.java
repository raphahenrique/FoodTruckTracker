package br.ufscar.auxiliares;

import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLogin implements FacebookCallback<LoginResult>{
    @Override
    public void onSuccess(LoginResult loginResult) {
        System.out.println("onSuccess");

        GraphRequest request = GraphRequest.newMeRequest
                (loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        Log.v("LoginActivity", response.toString());
                        //System.out.println("Check: " + response.toString());
                        try
                        {
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            //String picture = object.getString("picture");
                            System.out.println(id + ", " + name + ", " + email  );

                            /* Ver ONDE ele salva info sobre user entre seções e como recuperar




                            Usar sharedPreferences para salvar e saber que ele ja logou**
                            // Save your info
                            SharedPreferences settings = getSharedPreferences("my_file_name", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("username", username.getText().toString());
                            editor.putString("password", password.getText().toString());
                            editor.commit();

                            // Obtain your info
                            SharedPreferences settings = getSharedPreferences("my_file_name", 0);
                            String username = settings.getString("username", "");
                            String password = settings.getString("password", "");
                             */


                            Log.i("ID: ", id);
                            Log.i("name: ", name);
                            Log.i("email: ", email);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {
        System.out.println("onCancel");
    }

    @Override
    public void onError(FacebookException error) {
        System.out.println("onError");
        Log.v("LoginActivity", error.getCause().toString());
    }
}
