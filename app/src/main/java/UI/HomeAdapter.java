package UI;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.android.basicbankingapp.R;

public class HomeAdapter extends FragmentPagerAdapter {

    /** Context of the app */
    private Context mContext;

    public HomeAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new UsersFragment();
        }
        else {
            return new TransactionsFragment();
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.users);
        } else {
            return mContext.getString(R.string.transactions);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}