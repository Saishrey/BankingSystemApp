package UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.basicbankingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import data.User;
import database.UserHelper;

import database.UserContract.UserEntry;

public class UserInfoActivity extends AppCompatActivity {
    ImageView profileImage;
    TextView name, email, gender, accountNo, desc_account, balance, ifscCode, contact;
    FloatingActionButton fab;

    Intent intent;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email_id);
        desc_account = findViewById(R.id.desc_account_no);
        accountNo = findViewById(R.id.account_no);
        balance = findViewById(R.id.avail_balance);
        ifscCode = findViewById(R.id.ifsc_code);
        contact = findViewById(R.id.phone_no);
        gender = findViewById(R.id.gender);
        profileImage = findViewById(R.id.profile_image_id);

        // Getting the intent
        intent = getIntent();
        extras = intent.getExtras();

        String currentAccountNo = extras.getString("ACCOUNT_NO");
        Cursor cursor = new UserHelper(this).readParticularData(currentAccountNo);
        cursor.moveToNext();
        int phoneNoColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_CONTACT);
        int emailColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_EMAIL);
        int ifscCodeColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_IFSC_CODE);
        int nameColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
        int accountBalanceColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_USER_ACCOUNT_BALANCE);
        int genderColumnIndex = cursor.getColumnIndex(UserEntry.COLUMN_GENDER);

        String currentName = cursor.getString(nameColumnIndex);
        String currentEmail = cursor.getString(emailColumnIndex);
        String currentPhoneNumber = cursor.getString(phoneNoColumnIndex);
        String currentIfscCode = cursor.getString(ifscCodeColumnIndex);
        int currentAccountBalance = cursor.getInt(accountBalanceColumnIndex);
        int currentGender = cursor.getInt(genderColumnIndex);


        setTitle(currentName);

        // Extracting the data
        if (extras != null){
            name.setText(currentName.toUpperCase(Locale.ROOT));

            if(currentGender == 0) {
                gender.setText("F");
                profileImage.setImageResource(R.drawable.female);
            } else {
                gender.setText("M");
                profileImage.setImageResource(R.drawable.male);

            }

            accountNo.setText(currentAccountNo);
            desc_account.setText(currentAccountNo);

            email.setText(currentEmail);
            contact.setText(currentPhoneNumber);
            ifscCode.setText(currentIfscCode);
            String availableBalance = "₹ " + currentAccountBalance;
            balance.setText(availableBalance);

        }
        else {
            Log.d("UserInfoActivity", "Empty Intent");
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), SelectUserActivity.class);

                intent.putExtra("ACCOUNT_NO", currentAccountNo);
                intent.putExtra("NAME", currentName);
                intent.putExtra("BALANCE", currentAccountBalance);
                intent.putExtra("GENDER", currentGender);
                view.getContext().startActivity(intent);

            }
        });

    }

}
