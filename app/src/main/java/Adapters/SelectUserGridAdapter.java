package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.basicbankingapp.R;

import java.util.ArrayList;

import data.User;

public class SelectUserGridAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<User> usersList;

    public SelectUserGridAdapter(Activity context, ArrayList<User> users) {
        this.context = context;
        this.usersList = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = inflater.inflate(R.layout.user_grid_item,null);
        }


        User currentUser = usersList.get(position);

        // Find the TextView in the user_grid_item.xml layout with the ID user_name_text_view
        TextView userNameTextView = (TextView) convertView.findViewById(R.id.user_grid_name);
        userNameTextView.setText(currentUser.getName());


        // Find the ImageView in the user_grid_item.xml layout with the ID user_icon_image_view
        ImageView imageview = (ImageView) convertView.findViewById(R.id.user_grid_image);

        if(currentUser.getGender() == 0) { // Female
            imageview.setImageResource(R.drawable.female);
        }
        else { // Male
            imageview.setImageResource(R.drawable.male);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public User getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
