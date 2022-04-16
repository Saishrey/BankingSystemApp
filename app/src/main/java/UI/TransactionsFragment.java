package UI;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.android.basicbankingapp.R;
import com.example.android.basicbankingapp.databinding.ActivitySelectUserBinding;
import com.example.android.basicbankingapp.databinding.FragmentTransactionsBinding;

import java.util.ArrayList;

import Adapters.TransactionListAdapter;
import data.Transaction;
import data.User;
import database.TransactionContract;
import database.TransactionContract.TransactionEntry;
import database.TransactionHelper;
import database.UserHelper;
import database.UserContract.UserEntry;

public class TransactionsFragment extends Fragment {

    FragmentTransactionsBinding fragmentTransactionsBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTransactionsBinding = FragmentTransactionsBinding.inflate(getLayoutInflater());

        View emptyView = fragmentTransactionsBinding.getRoot().findViewById(R.id.empty_view);

        ArrayList<Transaction> transactionArrayList = fetchTransactionList();

        if(!transactionArrayList.isEmpty()) {
            emptyView.setVisibility(View.GONE);
            emptyView.setVisibility(View.INVISIBLE);
            TransactionListAdapter transactionListAdapter = new TransactionListAdapter(TransactionsFragment.this, transactionArrayList);

            fragmentTransactionsBinding.transactionListView.setAdapter(transactionListAdapter);
        }

        return fragmentTransactionsBinding.getRoot();
    }


    private ArrayList<Transaction> fetchTransactionList() {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();

        // Database
        Cursor cursor = new TransactionHelper(this).readAllData();

        int fromAccountNoColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_FROM_ACCOUNT_NO);
        int toAccountColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_TO_ACCOUNT_NO);
        int amountColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_AMOUNT);
        int statusColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_STATUS);
        int datetimeColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_DATE_TIME);

        while (cursor.moveToNext()){
            String fromAccount = cursor.getString(fromAccountNoColumnIndex);
            String toAccount = cursor.getString(toAccountColumnIndex);
            int amount = cursor.getInt(amountColumnIndex);
            int status = cursor.getInt(statusColumnIndex);
            String datetime = cursor.getString(datetimeColumnIndex);

            Cursor fromCursor = new UserHelper(this).readParticularData(fromAccount);
            fromCursor.moveToNext();

            int fromNameColumnIndex = fromCursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
            int fromGenderColumnIndex = fromCursor.getColumnIndex(UserEntry.COLUMN_GENDER);

            String fromName = fromCursor.getString(fromNameColumnIndex);
            int fromGender = fromCursor.getInt(fromGenderColumnIndex);

            Cursor toCursor = new UserHelper(this).readParticularData(toAccount);
            toCursor.moveToNext();

            int toNameColumnIndex = toCursor.getColumnIndex(UserEntry.COLUMN_USER_NAME);
            int toGenderColumnIndex = toCursor.getColumnIndex(UserEntry.COLUMN_GENDER);

            String toName = toCursor.getString(toNameColumnIndex);
            int toGender = toCursor.getInt(toGenderColumnIndex);

            // Display the values from each column of the current row in the cursor in the TextView
            transactionArrayList.add(new Transaction(fromAccount, fromName, fromGender, toAccount, toName, toGender, amount, status, datetime));
        }

        return transactionArrayList;
    }

}