package UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.basicbankingapp.R;

import java.util.ArrayList;

import data.User;
import database.UserHelper;
import database.UserContract.UserEntry;
import Adapters.UserListAdapter;


public class UsersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);


        // Create an array of users
        final ArrayList<User> users = fetchUserList();

        UserListAdapter adapter = new UserListAdapter(getActivity(), users);

        ListView listView = (ListView) rootView.findViewById(R.id.user_list);

        listView.setAdapter(adapter);

        // Set a click listener to open User Info Activity when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Get the {@link User} object at the given position the user clicked on
                User user = users.get(position);

                Intent intent = new Intent(view.getContext(), UserInfoActivity.class);

//                ImageView imageView = (ImageView) listView.findViewById(R.id.user_icon_image_view);

//                Pair<View, String> pair = new Pair<>(imageView, "dp_transition");

//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair);

                intent.putExtra("ACCOUNT_NO", user.getAccountNumber());
                view.getContext().startActivity(intent);
//                view.getContext().startActivity(intent, options.toBundle());
            }
        });

        return rootView;
    }

    private ArrayList<User> fetchUserList() {
        ArrayList<User> userArrayList = new ArrayList<>();

        // Database
        UserHelper dbHelper;

        Cursor cursor = new UserHelper(this).readAllData();

        int phoneNoColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_CONTACT);
        int emailColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL);
        int ifscCodeColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_IFSC_CODE);
        int accountNumberColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_NUMBER);
        int nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
        int accountBalanceColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_BALANCE);
        int genderColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_GENDER);

        while (cursor.moveToNext()){
            String currentName = cursor.getString(nameColumnIndex);
            String accountNumber = cursor.getString(accountNumberColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String phoneNumber = cursor.getString(phoneNoColumnIndex);
            String ifscCode = cursor.getString(ifscCodeColumnIndex);
            int accountBalance = cursor.getInt(accountBalanceColumnIndex);
            int gender = cursor.getInt(genderColumnIndex);

            // Display the values from each column of the current row in the cursor in the TextView
            userArrayList.add(new User(currentName, accountNumber, phoneNumber, ifscCode, gender, accountBalance, email));
        }

        return userArrayList;
    }
}