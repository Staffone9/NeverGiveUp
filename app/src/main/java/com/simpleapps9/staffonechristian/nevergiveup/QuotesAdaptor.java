package com.simpleapps9.staffonechristian.nevergiveup;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by staffonechristian on 2018-05-04.
 */

public class QuotesAdaptor extends RecyclerView.Adapter<QuotesAdaptor.QuoteViewHolder> {


    private List<QuoteModel> quoteModelList;
    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_quote_item_view, parent, false);
        return new QuoteViewHolder(itemView);
    }

    public QuotesAdaptor(List<QuoteModel> quoteModelList) {
        this.quoteModelList = quoteModelList;
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        QuoteModel singleQuote = quoteModelList.get(position);
        holder.quote.setText(singleQuote.getQuote());
        holder.author.setText(singleQuote.getAuthor());
      //  holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        if(quoteModelList!=null)
        return quoteModelList.size();
        else
        return 0;
    }



    public class QuoteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quote)
        public TextView quote;

        @BindView(R.id.authorName)
        public TextView author;



        public QuoteViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
