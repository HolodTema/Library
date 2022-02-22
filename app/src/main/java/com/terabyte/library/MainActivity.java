package com.terabyte.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerBooks = findViewById(R.id.recyclerBooks);
        TextView textSortedBy = findViewById(R.id.textSortedBy);

        ArrayList<Book> books = new ArrayList<>();
        TreeSet<Book> treeSetBooks = DatabaseClient.selectAllBooks(getApplicationContext());

        if(getIntent().getExtras()==null) {
            textSortedBy.setText(getResources().getString(R.string.sorted_by)+" "+getResources().getString(R.string.all_authors));
            books.addAll(treeSetBooks);
        }
        else {
            String sortedStr = getIntent().getExtras().getString(Constant.INTENT_KEY_SORTED_STRING);
            textSortedBy.setText(getResources().getString(R.string.sorted_by)+" "+sortedStr);
            Book book = treeSetBooks.ceiling(new Book(0,"",sortedStr, "", new byte[0]));
            while(book.getAuthor().equals(sortedStr)) {
                books.add(book);
                book = treeSetBooks.higher(book);
                if(book==null) break;
            }
        }

        BookAdapter adapter = new BookAdapter(getApplicationContext(), books, new BookAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(Book book, int position) {
                Intent intent = new Intent(getApplicationContext(), BookEditorActivity.class);
                intent.putExtra(Constant.INTENT_KEY_MODE, Constant.MODE_MODIFICATION);
                intent.putExtra(Constant.INTENT_KEY_BOOK_ID, book.getId());
                startActivity(intent);
            }
        });
        recyclerBooks.setAdapter(adapter);
        recyclerBooks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void onClickButtonAddBook(View view) {
        Intent intent = new Intent(getApplicationContext(), BookEditorActivity.class);
        intent.putExtra(Constant.INTENT_KEY_MODE, Constant.MODE_CREATING);
        startActivity(intent);
    }

    public void onClickButtonFilter(View view) {
        startActivity(new Intent(getApplicationContext(), FilterActivity.class));
    }
}