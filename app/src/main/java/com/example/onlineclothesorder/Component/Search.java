package com.example.onlineclothesorder.Component;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlineclothesorder.Adapter.ProductDisplayAdapter;
import com.example.onlineclothesorder.Adapter.SearchResultAdapter;
import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;
import java.util.ArrayList;
import java.util.List;


public class Search extends Fragment {
    private EditText searchInput;
    private RecyclerView searchResultsRecyclerView;
    private SearchResultAdapter searchAdapter;
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> filteredProducts = new ArrayList<>();
    private AppDAO appDAO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        searchInput = view.findViewById(R.id.searchInput);
        searchResultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        appDAO = AppDatabase.getInstance(getContext()).appDAO();
        allProducts = appDAO.getAllProducts();
        filteredProducts.addAll(allProducts);


        searchAdapter = new SearchResultAdapter(getContext(), filteredProducts);
        searchResultsRecyclerView.setAdapter(searchAdapter);


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }


            @Override
            public void afterTextChanged(Editable s) {}
        });
        Log.d("SearchDebug", "SearchFragment onCreateView called");
        return view;
    }


    private void filterProducts(String query) {
        filteredProducts.clear();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        searchAdapter.notifyDataSetChanged();
    }
}

