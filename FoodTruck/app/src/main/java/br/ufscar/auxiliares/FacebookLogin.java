package br.ufscar.auxiliares;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

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

                        try
                        {
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            //String picture = object.getString("picture");
                            //profile.getProfilePictureUri(200,200).toString()

                            //new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);


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
        /*Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
        */
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

class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImage(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

