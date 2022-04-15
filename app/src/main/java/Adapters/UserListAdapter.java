package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.basicbankingapp.R;

import java.util.ArrayList;

import data.User;

public class UserListAdapter extends ArrayAdapter<User> {

    public UserListAdapter(Activity context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_list_item, parent, false);
        }

        // Get the {@link User} object located at this position in the list
        User currentUser = getItem(position);

        // Find the TextView in the user_list_item.xml layout with the ID user_name_text_view
        TextView userNameTextView = (TextView) listItemView.findViewById(R.id.user_name_text_view);
        userNameTextView.setText(currentUser.getName());

        // Find the TextView in the user_list_item.xml layout with the ID balance_text_view
        TextView balanceTextView = (TextView) listItemView.findViewById(R.id.balance_text_view);
        String availableBalance = "Balance: \u20B9 " + Integer.toString(currentUser.getAvailableBalance());
        balanceTextView.setText(availableBalance);

        // Find the ImageView in the user_list_item.xml layout with the ID user_icon_image_view
        ImageView imageview = (ImageView) listItemView.findViewById(R.id.user_icon_image_view);

        if(currentUser.getGender() == 0) { // Female
            imageview.setImageResource(R.drawable.female);
        }
        else { // Male
            imageview.setImageResource(R.drawable.male);
        }


        return listItemView;
    }


}
