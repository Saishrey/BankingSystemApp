package UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.basicbankingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import database.TransactionContract.TransactionEntry;
import database.TransactionHelper;
import database.UserHelper;

public class AmountInput extends AppCompatActivity {

    private String toAccountNo;
    private String fromAccountNo;
    private int transferAmount = 0;

    private String date_and_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_input);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        fromAccountNo = extras.getString("FROM_ACCOUNT_NO");
        toAccountNo = extras.getString("TO_ACCOUNT_NO");

        ImageView toImage = (ImageView) findViewById(R.id.paying_image_view);

        if(extras.getInt("TO_GENDER") == 0) {
            toImage.setImageResource(R.drawable.female);
        }
        else {
            toImage.setImageResource(R.drawable.male);
        }

        TextView toName = (TextView) findViewById(R.id.paying_name_text_view);
        String payingName = "Paying " + extras.getString("TO_NAME");
        toName.setText(payingName);

        TextView toContact = (TextView) findViewById(R.id.paying_contact_text_view);
        String payingContact = "+91 " + extras.getString("TO_CONTACT");
        toContact.setText(payingContact);

        Button closeButton = (Button) findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        EditText inputAmount = (EditText) findViewById(R.id.input_amount);

        FloatingActionButton fabSend = (FloatingActionButton) findViewById(R.id.fab_send);

        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputAmount.getText().toString().isEmpty()) {
                    Toast.makeText(AmountInput.this, "Payment must be at least ₹1", Toast.LENGTH_LONG).show();
                }
                else {
                    int amount = Integer.parseInt(inputAmount.getText().toString());
                    if(amount <= 0) {
                        Toast.makeText(AmountInput.this, "Payment must be at least ₹1", Toast.LENGTH_LONG).show();
                    }
                    else if(amount > extras.getInt("FROM_BALANCE")) {
                        Toast.makeText(AmountInput.this, "Your account balance is ₹" + extras.getInt("FROM_BALANCE"), Toast.LENGTH_LONG).show();
                    }
                    else if(amount >= 100000) {
                        Toast.makeText(AmountInput.this, "Payment must be at most ₹1,00,000", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Calendar calendar = Calendar.getInstance();
                        String dateString = "dd-MMM-yyyy, hh:mm a";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateString);
                        date_and_time = simpleDateFormat.format(calendar.getTime());

                        transferAmount = amount;
                        int fromBalance = extras.getInt("FROM_BALANCE");
                        int toBalance = extras.getInt("TO_BALANCE");

                        insertTransaction(fromBalance, toBalance);

                        Toast.makeText(AmountInput.this, "Transaction Successful", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(AmountInput.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void insertTransaction(int fromBalance, int toBalance) {
        fromBalance -= transferAmount;
        toBalance += transferAmount;

        new UserHelper(this).updateAmount(fromAccountNo, fromBalance);
        new UserHelper(this).updateAmount(toAccountNo, toBalance);

        new TransactionHelper(this).insertTransferData(fromAccountNo, toAccountNo, transferAmount, 1, date_and_time);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builderExitButton = new AlertDialog.Builder(AmountInput.this);
        builderExitButton.setTitle("Are you sure you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Calendar calendar = Calendar.getInstance();
                        String dateString = "dd-MMM-yyyy, hh:mm a";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateString);
                        date_and_time = simpleDateFormat.format(calendar.getTime());

                        // Transactions Cancelled
                        TransactionHelper dbHelper = new TransactionHelper(AmountInput.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.put(TransactionEntry.COLUMN_FROM_ACCOUNT_NO, fromAccountNo);
                        values.put(TransactionEntry.COLUMN_TO_ACCOUNT_NO, toAccountNo);
                        values.put(TransactionEntry.COLUMN_STATUS, 0);
                        values.put(TransactionEntry.COLUMN_AMOUNT, 0);
                        values.put(TransactionEntry.COLUMN_DATE_TIME, date_and_time);

                        db.insert(TransactionEntry.TABLE_NAME, null, values);

                        Toast.makeText(AmountInput.this, "Transaction Cancelled", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AmountInput.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertExit = builderExitButton.create();
        alertExit.show();
    }
}