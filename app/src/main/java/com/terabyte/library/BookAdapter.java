package com.terabyte.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Book> books;
    private OnBookClickListener onBookClickListener;
    Context context;

    public BookAdapter(Context context, List<Book> books, OnBookClickListener onBookClickListener) {
        this.context = context;
        this.books = books;
        this.inflater = LayoutInflater.from(context);
        this.onBookClickListener = onBookClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.textBookTitle.setText(book.getTitle());
        holder.textBookAuthor.setText(book.getAuthor());
        holder.textBookDescription.setText(book.getDescription());
        if(book.getImage()!=null) {
            Bitmap selectedImage = BitmapFactory.decodeByteArray(book.getImage(), 0, book.getImage().length);
            holder.imageBook.setImageBitmap(selectedImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBookClickListener.onBookClick(book, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textBookTitle, textBookAuthor, textBookDescription;
        final ImageView imageBook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textBookTitle = itemView.findViewById(R.id.textBookTitle);
            textBookAuthor = itemView.findViewById(R.id.textBookAuthor);
            textBookDescription = itemView.findViewById(R.id.textBookDescription);
            imageBook = itemView.findViewById(R.id.imageBook);
        }
    }

    public interface OnBookClickListener {
        void onBookClick(Book book, int position);
    }
}
