package br.ufscar.foodtruck;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Review> implements View.OnClickListener{

    /* pegar img do usuario
     ImageView user_picture;
     userpicture=(ImageView)findViewById(R.id.userpicture);
     URL img_value = null;
     img_value = new URL("http://graph.facebook.com/"+id+"/picture?type=large");
     Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
     userpicture.setImageBitmap(mIcon1);
     */

    private ArrayList<Review> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        RatingBar listRating;
        TextView txtComentarios;
        ImageView imgUser;
    }

    public CustomAdapter(Context context,ArrayList<Review> data) {
        super(context, R.layout.review_layout_listview, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Review dataReview=(Review) object;

        /*switch (v.getId())
        {
            case R.id.:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
        */
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Review dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.review_layout_listview, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.listview_txtNome);
            viewHolder.listRating = (RatingBar) convertView.findViewById(R.id.listview_ratingBar);
            viewHolder.imgUser = (ImageView)convertView.findViewById(R.id.listview_img_profile);
            viewHolder.txtComentarios = (TextView) convertView.findViewById(R.id.listview_txtComentario);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ?
                R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);

        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtComentarios.setText(dataModel.getComment());
        viewHolder.listRating.setRating(dataModel.getRating());
        if(dataModel.getPicture()!=null){
            new DownloadImage(viewHolder.imgUser).execute(dataModel.getPicture());
        }

        //Profile profile = Profile.getCurrentProfile();
        //if(profile!=null){
          //  new DownloadImage(viewHolder.imgUser).execute();
        //}

        //viewHolder.info.setOnClickListener(this);
        //viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
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
