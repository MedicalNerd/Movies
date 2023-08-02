package sg.edu.rp.c346.id22021421.movies;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movie> versionList;
    ImageView ivRate;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);
        parent_context= context;
        layout_id= resource;
        versionList = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);
        ImageView ivRate = rowView.findViewById(R.id.ivRate);
        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textView4);
        TextView tvyear= rowView.findViewById(R.id.textView5);
        TextView tvSinger = rowView.findViewById(R.id.textView7);

        // Obtain the Android Version information based on the position
        Movie currentVersion = versionList.get(position);
        Movie currentItem = versionList.get(position);
        // Set values to the TextView to display the corresponding information
        tvName.setText(currentVersion.getGenre());
        tvyear.setText(Integer.toString(currentVersion.getDate()));
        if (currentItem.getRatings().equals("PG") ) {
            String imageurl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/22/IMDA_Age_Rating_-_Parental_Guidance.png/288px-IMDA_Age_Rating_-_Parental_Guidance.png";
            Picasso.with(parent_context).load(imageurl).into(ivRate);
        } else if(currentItem.getRatings().equals("PG13")) {
            String imageurl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/IMDA_Age_Rating_-_Parental_Guidance_for_Under_13.png/288px-IMDA_Age_Rating_-_Parental_Guidance_for_Under_13.png";
            Picasso.with(parent_context).load(imageurl).into(ivRate);
        } else if(currentItem.getRatings().equals("NC16")){
            String imageurl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/IMDA_Age_Rating_-_No_Children_Under_16.png/288px-IMDA_Age_Rating_-_No_Children_Under_16.png";
            Picasso.with(parent_context).load(imageurl).into(ivRate);
        } else if(currentItem.getRatings().equals("M18")){
            String imageurl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/IMDA_Age_Rating_-_Mature_18.png/288px-IMDA_Age_Rating_-_Mature_18.png";
            Picasso.with(parent_context).load(imageurl).into(ivRate);
        }
        tvSinger.setText(currentVersion.getname());

        return rowView;
    } //generating a row if need to show 5 items this method is called 5 times.
    //Purpose of method is when needing to generate a row this method will be called



}

