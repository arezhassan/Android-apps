package com.example.luxuria;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private ImageView search;
    private FirebaseFirestore db;
    ArrayList<productdata> products;
    ArrayList<productdata> searchdata;
    RecyclerView rvsearch;
    int i=0;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searching, container, false);
//        search = view.findViewById(R.id.searchbtn);
        searchEditText = view.findViewById(R.id.searchEditText);


       rvsearch=view.findViewById(R.id.rvsearch);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvsearch.setLayoutManager(layoutManager);
        products=new ArrayList<>();
        fillRecyclerView();
        Context context = requireContext();
        searchEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // This method is not used in this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Called whenever the text is changed


                String searchText = s.toString().trim();
                performSearch(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is not used in this implementation
            }
        });





        // Set click listener for search icon


        return view;
    }
private void fillRecyclerView() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("products")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {


                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String productName = documentSnapshot.getString("productname");
                    String productprice = documentSnapshot.getString("productprice");
                    String img = documentSnapshot.getString("img");

                    productdata product = new productdata(productName, productprice, img, 1);
                    products.add(product);
                }




                });
}
    private void performSearch(String searchText) {
        searchdata=new ArrayList<>();
searchdata.clear();





        for (int f = 0; f < products.size(); f++) {
            String productName = products.get(f).getProductname();
            if (productName.toLowerCase().contains(searchText.toLowerCase())) {
                searchdata.add(products.get(f));


            }

        }

// Check if any search results were found
        if (searchdata.isEmpty()) {
            Toast.makeText(getContext(), "Product not found!", Toast.LENGTH_SHORT).show();
        } else {
            // Create and set the adapter after the loop
            ProductAdapter adapter = new ProductAdapter(searchdata);
            rvsearch.setAdapter(adapter);


        }


// Process the search results as needed
// ...


    }
}
