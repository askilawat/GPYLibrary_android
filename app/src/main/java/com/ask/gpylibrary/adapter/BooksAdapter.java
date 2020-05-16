package com.ask.gpylibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ask.gpylibrary.datemodel.Book;
import com.ask.gpylibrary.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksHolder> {

    private Context context;
    private List<Book> books;

    public BooksAdapter(Context context, List<Book> book) {
        this.context = context;
        books = new ArrayList<>();
        this.books = book;
    }

    @NonNull
    @Override
    public BooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        LayoutInflater l = LayoutInflater.from(context);
        v = l.inflate(R.layout.bookview,parent,false);
        return new BooksHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksHolder holder, int position) {

        holder.title.setText(books.get(position).getName());
        holder.category.setText("Category: "+books.get(position).getCategory());
        holder.author.setText("BY: "+books.get(position).getAuthor());
        Glide.with(context)
                .load(books.get(position).getThumbnail_link())
                .into(holder.bimg);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BooksHolder extends RecyclerView.ViewHolder{

        ImageView bimg;
        TextView title;
        TextView author;
        TextView category;

        public BooksHolder(@NonNull View itemView) {
            super(itemView);

            bimg = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.aut);
            category = itemView.findViewById(R.id.cat);
        }
    }
}
