package portfolio.kartik.com;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class FragmentBase extends Fragment  {

    public FragmentBase() {
        // Required empty public constructor
    }

    //PS somehow getting resources as instance variable is creating memory issues,and app to crash
    AlertDialog ad;
    ProgressDialog loading;


//    public void showLoader(){
//        GifImageView gif=(GifImageView) getActivity().findViewById(R.id.Loading);
//        gif.setVisibility(View.VISIBLE);
//    }
//    public void hideLoader(){
//        GifImageView gif=(GifImageView) getActivity().findViewById(R.id.Loading);
//        gif.setVisibility(View.GONE);
//    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager)this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null || activeNetworkInfo.getType()== ConnectivityManager.TYPE_WIFI)
            return activeNetworkInfo.isConnected();
        else
            return false;
    }

    public void hideSoftInput(IBinder binder) {
        //myEditText.getWindowToken()
        InputMethodManager mgrs = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgrs.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void showSoftInput(View view) {
        InputMethodManager mgrs = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgrs.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public AlertDialog showCustomMessage(String message) {

        if (loading != null)
            loading.dismiss();
        AlertDialog.Builder adb = new AlertDialog.Builder(this.getActivity());
        adb.setTitle(this.getResources().getString(R.string.app_name));
        adb.setMessage(message);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = adb.create();
        ad.show();
        return ad;
    }

    public void showToast( String message) {
        Toast toast = Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public boolean isTablet() {

        boolean xlarge = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public boolean isPackagePresent(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = this.getActivity().getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    public int resolveAttribute(int attr){
        TypedValue typedvalueattr = new TypedValue();
        this.getActivity().getTheme().resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.resourceId;
    }

    public int resolveAttributeColor(int attr){
        TypedValue typedvalueattr = new TypedValue();
        this.getActivity().getTheme().resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.data;
    }


    public void showEndPop() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this.getActivity());
        adb.setTitle(this.getResources().getString(R.string.app_name));
        adb.setMessage("Are you sure you want to quit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
                getActivity().finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ad.dismiss();
            }
        });
        ad = adb.create();
        ad.show();
    }


}
