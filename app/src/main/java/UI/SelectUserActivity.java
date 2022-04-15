package UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.android.basicbankingapp.R;
import com.example.android.basicbankingapp.databinding.ActivityMainBinding;
import com.example.android.basicbankingapp.databinding.ActivitySelectUserBinding;

import java.util.ArrayList;

import Adapters.SelectUserGridAdapter;
import data.User;
import database.UserHelper;
import database.UserContract.UserEntry;

public class SelectUserActivity extends AppCompatActivity {

    ActivitySelectUserBinding binding;
    Intent intent;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Getting the intent
        intent = getIntent();
        extras = intent.getExtras();

        setTitle(extras.getString("NAME"));

        ArrayList<User> users;

        if(extras != null) {
            users = fetchUserListExcept(extras.getString("ACCOUNT_NO"));
            SelectUserGridAdapter gridAdapter = new SelectUserGridAdapter(SelectUserActivity.this, users);
            binding.gridView.setAdapter(gridAdapter);

            binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    User toUser = users.get(position);
                    Intent newIntent = new Intent(view.getContext(), AmountInput.class);
                    newIntent.putExtra("TO_ACCOUNT_NO", toUser.getAccountNumber());
                    newIntent.putExtra("TO_NAME", toUser.getName());
                    newIntent.putExtra("TO_CONTACT", toUser.getContact());
                    newIntent.putExtra("TO_GENDER", toUser.getGender());
                    newIntent.putExtra("TO_BALANCE", toUser.getAvailableBalance());
                    newIntent.putExtra("FROM_BALANCE", extras.getInt("BALANCE"));
                    newIntent.putExtra("FROM_ACCOUNT_NO", extras.getString("ACCOUNT_NO"));
                    newIntent.putExtra("FROM_NAME", extras.getString("NAME"));
                    view.getContext().startActivity(newIntent);
                }
            });
        }
        else {
            Log.d("SelectUserActivity", "Empty Intent");
        }



    }

    private ArrayList<User> fetchUserListExcept(String accountNo) {
        ArrayList<User> userArrayList = new ArrayList<>();

        // Database
        UserHelper dbHelper;

        Cursor cursor = new UserHelper(this).readAllDataExcept(accountNo);

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