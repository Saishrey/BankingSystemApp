package Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.basicbankingapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import UI.TransactionsFragment;
import data.Transaction;
import data.User;
import database.TransactionHelper;

public class TransactionListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<Transaction> transactionArrayList;
    Context context;

    public TransactionListAdapter(TransactionsFragment context, ArrayList<Transaction> transactions) {
        this.context = context.getActivity();
        this.transactionArrayList = transactions;
    }

    @Override
    public int getCount() {
        return transactionArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = inflater.inflate(R.layout.transaction_list_item,null);
        }


        Transaction currentTransaction = transactionArrayList.get(position);

        ImageView fromUserImageView = (ImageView) convertView.findViewById(R.id.from_user_image_view);
        if(currentTransaction.getFromGender() == 0) {
            fromUserImageView.setImageResource(R.drawable.female);
        }
        else {
            fromUserImageView.setImageResource(R.drawable.male);
        }

        ImageView toUserImageView = (ImageView) convertView.findViewById(R.id.to_user_image_view);
        if(currentTransaction.getToGender() == 0) {
            toUserImageView.setImageResource(R.drawable.female);
        }
        else {
            toUserImageView.setImageResource(R.drawable.male);
        }

        TextView fromNameTextView = (TextView) convertView.findViewById(R.id.transaction_from_name_text_view);
        fromNameTextView.setText(currentTransaction.getFromName());

        TextView toNameTextView = (TextView) convertView.findViewById(R.id.transaction_to_name_text_view);
        toNameTextView.setText(currentTransaction.getToName());

        TextView amountTextView = (TextView) convertView.findViewById(R.id.transaction_amount_text_view);
        String amount = "₹" + currentTransaction.getAmountTransferred();
        amountTextView.setText(amount);

        ImageView statusImageView = (ImageView) convertView.findViewById(R.id.transaction_status);
        if(currentTransaction.getStatus() == 0) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

            statusImageView.setImageResource(R.drawable.ic_baseline_close_24);
            statusImageView.setColorFilter(Color.RED);
            convertView.getBackground().setColorFilter(filter);
            toUserImageView.setColorFilter(filter);
            fromUserImageView.setColorFilter(filter);
        }
        else {
            statusImageView.setImageResource(R.drawable.ic_baseline_check_24);
            statusImageView.setColorFilter(Color.GREEN);
        }

        Calendar calendar = Calendar.getInstance();
        String dateString = "dd-MMM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateString);
        String currentDate = simpleDateFormat.format(calendar.getTime());

        String datetime;
        if(currentTransaction.getDatetime().split(",")[0].equals(currentDate)) {
            datetime = currentTransaction.getDatetime().split(",")[1];
        }
        else {
            datetime = currentTransaction.getDatetime().split(",")[0];
        }

        TextView dateAndTimeTextView = (TextView) convertView.findViewById(R.id.date_and_time_text_view);
        dateAndTimeTextView.setText(datetime);

        return convertView;
    }


}
