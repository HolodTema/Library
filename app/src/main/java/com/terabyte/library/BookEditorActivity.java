package com.terabyte.library;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BookEditorActivity extends AppCompatActivity {
    EditText editTitle, editAuthor, editDescription;
    ImageView imageBook;
    int mode = 0;
    int bookId = 0;

    final int IMAGE = 1;
    byte[] imageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_editor);
        mode = getIntent().getExtras().getInt(Constant.INTENT_KEY_MODE);

        editTitle = findViewById(R.id.editBookTitle);
        editAuthor = findViewById(R.id.editBookAuthor);
        editDescription = findViewById(R.id.editBookDescription);
        imageBook = findViewById(R.id.imageBook);

        if(mode == Constant.MODE_CREATING) {
            Button buttonDelete = findViewById(R.id.buttonDelete);
            buttonDelete.setVisibility(View.GONE);
        }
        if(mode==Constant.MODE_MODIFICATION) {
            bookId = getIntent().getExtras().getInt(Constant.INTENT_KEY_BOOK_ID);
            Book editedBook = DatabaseClient.selectBookById(getApplicationContext(), bookId);
            editTitle.setText(editedBook.getTitle());
            editAuthor.setText(editedBook.getAuthor());
            editDescription.setText(editedBook.getDescription());
            if(editedBook.getImage()!=null) {
                imageBook.setImageBitmap(BitmapFactory.decodeByteArray(editedBook.getImage(), 0, editedBook.getImage().length));
            }
        }
    }

    public void onClickButtonDelete(View view) {
        if(mode==Constant.MODE_MODIFICATION) {
            DatabaseClient.deleteBookById(getApplicationContext(), bookId);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    public void onClickButtonApply(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.CV_KEY_TITLE, editTitle.getText().toString());
        contentValues.put(Constant.CV_KEY_AUTHOR, editAuthor.getText().toString());
        contentValues.put(Constant.CV_KEY_DESCRIPTION, editDescription.getText().toString());
        contentValues.put(Constant.CV_KEY_IMAGE_BYTE_ARRAY, imageByteArray);

        if(mode==Constant.MODE_CREATING) {
            DatabaseClient.insertBook(getApplicationContext(), contentValues);
        }
        if(mode==Constant.MODE_MODIFICATION) {
            DatabaseClient.updateBookById(getApplicationContext(), contentValues, bookId);
        }
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onClickButtonCancel(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClickButtonChooseImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE) {
            if(resultCode == RESULT_OK) {
                try {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageBook.setImageBitmap(selectedImage);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    imageByteArray = byteArrayOutputStream.toByteArray();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}