package com.simpleapps9.staffonechristian.nevergiveup;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CallBack{

    FirebaseFirestore db;

    @BindView(R.id.fab_plus)
    FloatingActionButton fabPlus;

    @BindView(R.id.quote_random_list)
    RecyclerView quoteRandomRecyclerView;

    QuotesAdaptor quotesAdaptor;

    private List<QuoteModel> quoteModelList = new ArrayList<>();

    FireStoreCrudOperation fireStoreCrudOperation;

    CallBack callBack;


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fireStoreCrudOperation = new FireStoreCrudOperation(getApplicationContext());
        callBack = (CallBack) this;
        InitialiseFireStoreInstance();
       // CreateTempDataForTesting();

      //  quotesAdaptor = new QuotesAdaptor(quoteModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        quoteRandomRecyclerView.setLayoutManager(mLayoutManager);
        quoteRandomRecyclerView.setItemAnimator(new DefaultItemAnimator());


        fireStoreCrudOperation.ReadQuote(callBack);

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuoteGenerator.class);
                startActivity(intent);
            }
        });


    }

    private void GetDataFromCloud() {


    }

//    private void CreateTempDataForTesting() {
//        // Create a new quote with a first and last name
//        Map<String, Object> quote = new HashMap<>();
//        quote.put(KEY_DOC_AUTHOR, "Walt Disney");
//        quote.put(KEY_DOC_CATEGORY, "Inspirational");
//        quote.put(KEY_DOC_QUOTE, "It's kind of fun to do the impossible.");
//
//
//
//        db.collection("Quotes")
//                .add(quote)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG_DEBUG_GENERAL, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG_DEBUG_GENERAL, "Error adding document", e);
//                    }
//                });
//    }

    public void InitialiseFireStoreInstance() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void RefreshRecyclerView(List<QuoteModel> quoteModels) {

        quotesAdaptor = new QuotesAdaptor(quoteModels);
        quoteRandomRecyclerView.setAdapter(quotesAdaptor);
        //quoteModelList = new ArrayList<QuoteModel>(quoteModels);
        quotesAdaptor.notifyDataSetChanged();

    }
}
