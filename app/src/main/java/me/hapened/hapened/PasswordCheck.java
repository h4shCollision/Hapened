package me.hapened.hapened;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

/**
 * Created by Lucy on 2015-12-21.
 */
public class PasswordCheck {
    private static String result;

    //return true if password matches, else false
    static boolean check(Context c) {
        if (PreferenceManager.getDefaultSharedPreferences(c).getBoolean("pswd", false)) {
            result = "";
            AlertDialog.Builder alert = new AlertDialog.Builder(c);
            EditText input = new EditText(c);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());
            alert.setView(input);    //edit text added to alert
            alert.setTitle("Password Required");
            alert.setPositiveButton("OK", new cl(input));
            AlertDialog a = alert.create();
            a.show();//a.dismiss();
            String pw = PreferenceManager.getDefaultSharedPreferences(c).getString("password", "");
            //a.dismiss();
            return pw.equals(result);
        }
        return true;
    }

    static class cl implements DialogInterface.OnClickListener {
        EditText e;

        cl(EditText et) {
            e = et;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            result = e.getText().toString();
        }
    }
}
