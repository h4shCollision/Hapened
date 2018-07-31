package me.hapened.hapened;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Edit extends ActionBarActivity {

    static final String INDEX = "I";
    private EditText editTitle, editContent;
    private ActionBar ab;
    private boolean addimage = false, save = true;
    private int index, TVPadding, imageIndex = -1;
    private Entry entry;
    private static final int CAMERA_REQUEST_CODE = 100, GALLERY_REQUEST_CODE = 200, LIST_TO_LAYOUT = 3;
    private static final String DATA_STRING = "data";
    private LinearLayout ll;
    private ArrayList<Bitmap> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent i = getIntent();
        index = i.getIntExtra(INDEX, 0);
        System.out.println("edit" + index);
        entry = FileManager.getInstance().getItem(this, index);
        String title = entry.getTitle();
        if (title == null) {
            title = (new SimpleDateFormat("yyyyMMdd")).format(new Date());
        }
        ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setDisplayHomeAsUpEnabled(true);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editTitle.setText(title);
        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                titleChanged();
            }
        });
        editContent = (EditText) findViewById(R.id.editText);
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contentChanged();
            }
        });
        editContent.setText(entry.getText());
        ((TextView) findViewById(R.id.date)).setText("Date: " + entry.getDate());
        TVPadding = (int) (8 * getResources().getDisplayMetrics().density + 0.5f);
        ll = (LinearLayout) findViewById(R.id.editll);
        photos = FileManager.getInstance().loadImages(this, entry, index);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        for (int j = 0; j < photos.size(); j++) {
            ImageView iv = new ImageView(this);
            Bitmap b = photos.get(j);
            iv.setImageBitmap(b);
            int width = dm.widthPixels, height = width * b.getHeight() / b.getWidth();
            if (height > dm.heightPixels) {
                height = dm.heightPixels;
                width = height * b.getWidth() / b.getHeight();
            }
            iv.setPadding(0, 8, 0, 8);
            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClick(v);
                    return true;
                }
            });
            ll.addView(iv, j + LIST_TO_LAYOUT, new LinearLayout.LayoutParams(width, height));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            SA.startSettings(this);
            return true;
        } else if (id == 16908332) {
            exitDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void titleChanged() {
        ab.setTitle(editTitle.getText().toString());
        entry.setTitle(editTitle.getText().toString());
        helper();
    }

    private void helper() {
        FileManager.getInstance().setItem(this, index, entry);
    }

    private void contentChanged() {
        entry.setText(editContent.getText().toString());
        helper();
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    private void exitDialog() {
        if (entry.getTitle() == null || entry.getTitle().equals("") && (entry.getText() != null && !entry.getText().equals("") || photos.size() > 0)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Title cannot be empty");
            TextView tv = new TextView(this);
            tv.setText("Your entry will be deleted if the title is empty");
            tv.setPadding(TVPadding, TVPadding, TVPadding, TVPadding);
            alert.setView(tv);
            alert.setPositiveButton("Delete entry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Edit.super.onBackPressed();
                }
            });
            alert.setNegativeButton("Do not delete", null);
            AlertDialog a = alert.create();
            a.show();
        } else {
            super.onBackPressed();
        }
    }

    public void newImage(View v) {
        addimage = true;
        getImageMethod();
    }

    private void getImageMethod() {
        if (hasCamera()) {
            CharSequence options[] = new CharSequence[]{"Camera", "Gallery"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Image");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        startCamera();
                    } else if (which == 1) {
                        findExisting();
                    }
                }
            });
            builder.show();
        } else {
            findExisting();
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void findExisting() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get(DATA_STRING);
                updateImageView(photo);
            } else {
                addimage = false;
                imageIndex = -1;
            }
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri chosenImageUri = data.getData();
                Bitmap bm = null;
                try {
                    bm = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateImageView(bm);
            } else {
                addimage = false;
                imageIndex = -1;
            }
        }

    }

    private void updateImageView(Bitmap bm) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels, height = width * bm.getHeight() / bm.getWidth();
        if (height > dm.heightPixels) {
            height = dm.heightPixels;
            width = height * bm.getWidth() / bm.getHeight();
        }
        Bitmap newbm = Bitmap.createScaledBitmap(bm, width, height, false);
        bm.recycle();
        if (addimage) {
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(newbm);
            iv.setPadding(0, 8, 0, 8);
            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClick(v);
                    return true;
                }
            });
            photos.add(newbm);
            ll.addView(iv, photos.size() + LIST_TO_LAYOUT - 1, new LinearLayout.LayoutParams(width, height));
            addimage = false;
        } else {
            if (imageIndex >= 0) {
                ImageView iv = (ImageView) ll.getChildAt(imageIndex + LIST_TO_LAYOUT);
                iv.setImageBitmap(newbm);
                photos.set(imageIndex, newbm);
            }
        }
    }

    private void longClick(View v) {
        imageIndex = ll.indexOfChild(v) - LIST_TO_LAYOUT;
        if (imageIndex < 0) return;
        CharSequence options[] = new CharSequence[]{"Change", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Add Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    getImageMethod();
                } else if (which == 1) {
                    removeImage();
                }
            }
        });
        builder.show();
    }

    private void removeImage() {
        if (imageIndex >= 0) {
            ll.removeViewAt(imageIndex + LIST_TO_LAYOUT);
            photos.remove(imageIndex);
        }
        imageIndex = -1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        entry.setImage(photos.size());
        if (!entry.isEmpty()) {
            FileManager.getInstance().setItem(this, index, entry);
            FileManager.getInstance().saveImages(this, entry, index, photos);
        }
    }
}
