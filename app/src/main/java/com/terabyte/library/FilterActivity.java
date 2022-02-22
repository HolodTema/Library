package com.terabyte.library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FilterActivity extends AppCompatActivity {
    EditText editSortedStringAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        editSortedStringAuthor = findViewById(R.id.editSortedStringAuthor);
    }

    public void onClickButtonApply(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(Constant.INTENT_KEY_SORTED_STRING, editSortedStringAuthor.getText().toString());
        startActivity(intent);
    }

    public void onClickButtonClearFilter(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}