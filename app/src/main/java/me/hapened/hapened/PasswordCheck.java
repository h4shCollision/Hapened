package me.hapened.hapened;

/**
 * Created by Lucy on 2015-12-21.
 */
public class PasswordCheck {
    private static String result;

    //return true if password matches, else false
   /* static void check(final Context c) {
        if (PreferenceManager.getDefaultSharedPreferences(c).getBoolean("pswd", false)) {
            result = "";
            AlertDialog.Builder alert = new AlertDialog.Builder(c);
            final EditText input = new EditText(c);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());
            alert.setView(input);    //edit text added to alert
            alert.setTitle("Password Required");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result=input.getText().toString();
                    String pw = PreferenceManager.getDefaultSharedPreferences(c).getString("password", "");
                    //a.dismiss();
                    System.out.println("result"+result);
                    if(pw.equals(result)){
                        Thread welcomeThread = new Thread() {

                            @Override
                            public void run() {
                                try {
                                    FileManager.getInstance(c);
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                } finally {
                                    //mHandler.post(new Runnable() {
                                    //@Override
                                    //public void run() {
                                    Intent i = new Intent(c, ListAll.class);
                                    c.startActivity(i);
                                    ((LoadScreen)c).finish();
                                    //}
                                    //});
                                }
                            }
                        };
                        welcomeThread.start();
                    }
                }
            });
            AlertDialog a = alert.create();
            a.show();//a.dismiss();
            String pw = PreferenceManager.getDefaultSharedPreferences(c).getString("password", "");
            //a.dismiss();
            //System.out.println("result" + result);
        }
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
    }*/
}
