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
    static boolean check(Context c){
        result="";
        final AlertDialog.Builder alert = new AlertDialog.Builder(c);
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        alert.setView(input);    //edit text added to alert
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result = input.getText().toString();
            }
        });
        alert.setTitle("Password Required");   //title
        alert.show();
        String pw= PreferenceManager.getDefaultSharedPreferences(c).getString("password","");
        return pw.equals(result);
    }
}
