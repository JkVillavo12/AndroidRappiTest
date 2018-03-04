package jkvillavo.co.com.rappiandroidtest.commons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import jkvillavo.co.com.rappiandroidtest.R;
import jkvillavo.co.com.rappiandroidtest.rest.model.moviedetail.Genre;
import jkvillavo.co.com.rappiandroidtest.rest.model.moviedetail.ProductionCompany;

/**
 * Created by JkVillavo12Col on 2/03/18.
 */

public class Utilidades {

    /**
     * Checking Internet Connectivity both WIFI and Moblie
     */
    public static boolean haveNetworkConnection(Context mContext) {

        if (mContext == null) return false;
        else {
            boolean haveConnectedWifi = false;
            boolean haveConnectedMobile = false;

            final ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected()) {
                    haveConnectedWifi = true;
                }
                if (ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected()) {
                    haveConnectedMobile = true;
                }
            }
            return haveConnectedWifi || haveConnectedMobile;
        }

    }

    public static void showAlertDialog(final Context context, String message) {

        if (context != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
            builder.setMessage(message);
            builder.setCancelable(false);
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

    }

    public static void showSnackBarForPrimary(CoordinatorLayout coordinatorLayout, final Context context) {

        try {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, context.getString(R.string.msg_checknet), Snackbar.LENGTH_LONG)
                    .setAction("CONFIGURAR", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });

            View sbView = snackbar.getView();
            sbView.setBackgroundColor(context.getResources().getColor(R.color.accent));

            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(context.getResources().getColor(R.color.primary_text));

            TextView textViewAction = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_action);
            textViewAction.setTextColor(context.getResources().getColor(R.color.primary_text));
            TextViewCompat.setTextAppearance(textViewAction, R.style.TextAppearance_AppCompat_Body2);

            snackbar.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

    public static String getStringFromListGenres(List<Genre> genres) {

        StringBuffer stringBuffer = new StringBuffer();
        if (genres != null) {
            if (!genres.isEmpty()) {
                stringBuffer = new StringBuffer();
                for (Genre genero : genres) {
                    stringBuffer.append(genero.getName() + ", ");
                }
            }
        }
        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 2);
    }

    public static String getStringFromListCompanies(List<ProductionCompany> companies) {

        StringBuffer stringBuffer = new StringBuffer();
        if (companies != null) {
            if (!companies.isEmpty()) {
                stringBuffer = new StringBuffer();
                for (ProductionCompany productionCompany : companies) {
                    stringBuffer.append(productionCompany.getName() + ", ");
                }
            }
        }
        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 2);

    }

}
