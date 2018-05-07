package com.simpleapps9.staffonechristian.nevergiveup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.KEY_DOC_AUTHOR;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.KEY_DOC_CATEGORY;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.KEY_DOC_COLLECTION_OF_QUOTE;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.KEY_DOC_ID;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.KEY_DOC_QUOTE;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.TAG_DEBUG_GENERAL;
import static com.simpleapps9.staffonechristian.nevergiveup.ConstantVariable.TOAST_LENGTH_LONG;

/**
 * Created by staffonechristian on 2018-05-03.
 */

public class FireStoreCrudOperation {

    FirebaseFirestore db;
    Context context;

    List<QuoteModel> quoteModelList = new ArrayList<>();

    public FireStoreCrudOperation(Context context) {
       if(db==null) {
           db = FirebaseFirestore.getInstance();
       }
       this.context = context;
    }

    public void CreateQuote(QuoteModel quoteModel) {
        final Map<String, Object> quote = new HashMap<>();
        quote.put(KEY_DOC_AUTHOR, quoteModel.getAuthor());
        quote.put(KEY_DOC_CATEGORY, quoteModel.getCategory());
        quote.put(KEY_DOC_QUOTE, quoteModel.getQuote());


        db.collection(KEY_DOC_COLLECTION_OF_QUOTE).orderBy("id", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>=1) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    QuoteModel singleObject = documentSnapshot.toObject(QuoteModel.class);
                    quote.put(KEY_DOC_ID, singleObject.getId() + 1);
                }else{
                    quote.put(KEY_DOC_ID,1);
                }
                    db.collection(KEY_DOC_COLLECTION_OF_QUOTE)
                            .add(quote)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    ToastUtility.ToastCreateLong(context,"Quote has been uploaded Successfully");
                                    Log.d(TAG_DEBUG_GENERAL, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    ToastUtility.ToastCreateLong(context,"Upload fail");
                                    Log.w(TAG_DEBUG_GENERAL, "Error adding document", e);
                                }
                            });

            }
        });


    }

    public void ReadQuote(final CallBack callBack) {

        quoteModelList.clear();
        db.collection(KEY_DOC_COLLECTION_OF_QUOTE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                quoteModelList.add(document.toObject(QuoteModel.class));
                                Log.d(TAG_DEBUG_GENERAL, document.getId() + " => " + document.getData());
                            }
                            callBack.RefreshRecyclerView(quoteModelList);
                        } else {
                            Log.w(TAG_DEBUG_GENERAL, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}
