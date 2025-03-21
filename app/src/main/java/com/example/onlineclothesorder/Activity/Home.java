package com.example.onlineclothesorder.Activity;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.example.onlineclothesorder.Adapter.BannerAdapter;
import com.example.onlineclothesorder.Adapter.CategoryAdapter;
import com.example.onlineclothesorder.Adapter.ProductDisplayAdapter;
import com.example.onlineclothesorder.Component.HeaderComponent;
import com.example.onlineclothesorder.DAO.AppDAO;
import com.example.onlineclothesorder.DAO.AppDatabase;
import com.example.onlineclothesorder.Entity.Banner;
import com.example.onlineclothesorder.Entity.Category;
import com.example.onlineclothesorder.Entity.Product;
import com.example.onlineclothesorder.R;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Home extends AppCompatActivity {
    private RecyclerView categoryRecyclerView, productRecyclerView;
    private CategoryAdapter categoryAdapter;
    private ProductDisplayAdapter productDisplayAdapter;
    private AppDAO appDAO;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler bannerHandler = new Handler();
    private int bannerCurrentPosition = 0;
    private ViewPager2 bannerViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HeaderComponent.setupHeader(this);
        appDAO = AppDatabase.getInstance(this).appDAO();


        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        bannerViewPager = findViewById(R.id.bannerViewPager);




        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        setupBanner();
        loadData();
    }


    private void setupBanner() {
        executor.execute(() -> {
            if (appDAO.getAllBanners().isEmpty()) {
                List<Banner> banners = Arrays.asList(
                        new Banner("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuEBEPjZUrUWpyV2eoacGUHX_oUGASwA8NQg&s"),
                        new Banner("https://inkythuatso.com/uploads/images/2022/01/banner-quan-ao-inkythuatso-13-10-16-26.jpg"),
                        new Banner("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQF6oxmYhSSMWKUoDYLvYN40J3wUftQm9Q1KQ&s")
                );
                appDAO.insertBanners(banners);
            }
            List<Banner> banners = appDAO.getAllBanners();
            List<String> bannerImages = new ArrayList<>();
            for (Banner banner : banners) {
                bannerImages.add(banner.getImage());
            }
            for (String url : bannerImages) {
                Log.d("BannerDebug", "Image URL: " + url);
            }


            runOnUiThread(() -> {
                BannerAdapter bannerAdapter = new BannerAdapter(Home.this, bannerImages);
                bannerViewPager.setAdapter(bannerAdapter);
                bannerHandler.postDelayed(bannerRunnable, 3000);
            });
        });
    }




    private final Runnable bannerRunnable = new Runnable() {
        @Override
        public void run() {
            if (bannerCurrentPosition < 2) {
                bannerCurrentPosition++;
            } else {
                bannerCurrentPosition = 0;
            }
            bannerViewPager.setCurrentItem(bannerCurrentPosition, true);
            bannerHandler.postDelayed(this, 3000);
        }
    };
    private void loadData() {
        executor.execute(() -> {
            if (appDAO.getAllCategories().isEmpty()) {
                List<Category> categories = Arrays.asList(
                        new Category("Áo thun", ""),
                        new Category("Quần jeans", ""),
                        new Category("Áo sơ mi", ""),
                        new Category("Giày sneaker", ""),
                        new Category("Dép sandal", ""),
                        new Category("Mũ lưỡi trai", ""),
                        new Category("Ba lô", ""),
                        new Category("Túi xách", ""),
                        new Category("Kính râm", "")
                );
                appDAO.insertCategories(categories);
            }
            if (appDAO.getAllProducts().isEmpty()) {
                List<Product> products = Arrays.asList(
                        new Product("Áo thun nam", 150000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Quần jeans rách", 250000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Áo sơ mi trắng", 200000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Áo thun nam", 150000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Quần jeans rách", 250000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Áo sơ mi trắng", 200000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Áo thun nam", 150000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Quần jeans rách", 250000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Áo sơ mi trắng", 200000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Áo thun nam", 150000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Quần jeans rách", 250000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg"),
                        new Product("Áo", 200000, 12, "https://images2.thanhnien.vn/Uploaded/chungntk/2022_10_24/anh-3-2789.jpg")


                );
                appDAO.insertProducts(products);
            }


            List<Category> categories = appDAO.getAllCategories();
            List<Product> products = appDAO.getAllProducts();


            runOnUiThread(() -> {
                categoryAdapter = new CategoryAdapter(categories);
                productDisplayAdapter = new ProductDisplayAdapter(Home.this, products);


                categoryRecyclerView.setAdapter(categoryAdapter);
                productRecyclerView.setAdapter(productDisplayAdapter);
            });
        });
    }
    public void showSortMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.menu_sort, popup.getMenu());


        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();


            if (id == R.id.sort_price_low_high) {
                sortProducts(true);
                return true;
            } else if (id == R.id.sort_price_high_low) {
                sortProducts(false);
                return true;
            }
            return false;
        });


        popup.show();
    }
    public void sortProducts(boolean lowToHigh) {
        executor.execute(() -> {
            List<Product> sortedProducts = appDAO.getAllProducts();


            Collections.sort(sortedProducts, (p1, p2) -> {
                if (lowToHigh) {
                    return Float.compare(p1.getPrice(), p2.getPrice());
                } else {
                    return Float.compare(p2.getPrice(), p1.getPrice());
                }
            });


            runOnUiThread(() -> {
                productDisplayAdapter.updateProducts(sortedProducts);
            });
        });
    }






}

