package com.simpleapps9.staffonechristian.nevergiveup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.AUTHOR_NULL_FIELD_TOAST_MESSAGE;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.CATEGORY_INSPIRATIONAL;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.QUOTE_NULL_FIELD_TOAST_MESSAGE;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.TOAST_LENGTH_LONG;

public class QuoteGenerator extends AppCompatActivity {

    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.quote)
    EditText quote;

    @BindView(R.id.authorName)
    EditText author;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_generator);
        ButterKnife.bind(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateAndSubmitQuote();
            }
        });
    }

    private void ValidateAndSubmitQuote() {
        if(quote.getText().toString().equals("")) {
            ToastUtility.ToastCreateLong(getApplicationContext(),QUOTE_NULL_FIELD_TOAST_MESSAGE);
        }else if(author.getText().toString().equals("")){
            ToastUtility.ToastCreateLong(getApplicationContext(),AUTHOR_NULL_FIELD_TOAST_MESSAGE);
        }else{
            QuoteModel quoteModel = new QuoteModel();
            quoteModel.setAuthor(author.getText().toString());
            quoteModel.setQuote(quote.getText().toString());
            quoteModel.setCategory(CATEGORY_INSPIRATIONAL);
            FireStoreCrudOperation fireStoreCrudOperation = new FireStoreCrudOperation(getApplicationContext());
            fireStoreCrudOperation.CreateQuote(quoteModel);
        }

    }
}
