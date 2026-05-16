package com.s29420.zad01;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new ShoppingListAdapter(buildShoppingList()));
    }

    // Ingredients for Spaghetti Bolognese (4 servings)
    private List<ShoppingItem> buildShoppingList() {
        List<ShoppingItem> items = new ArrayList<>();
        items.add(new ShoppingItem("Makaron spaghetti", "400 g"));
        items.add(new ShoppingItem("Mielone mięso wołowe", "500 g"));
        items.add(new ShoppingItem("Cebula", "1 sztuka"));
        items.add(new ShoppingItem("Czosnek", "3 ząbki"));
        items.add(new ShoppingItem("Marchew", "1 sztuka"));
        items.add(new ShoppingItem("Seler naciowy", "1 łodyga"));
        items.add(new ShoppingItem("Pomidory krojone (puszka)", "400 g"));
        items.add(new ShoppingItem("Passata pomidorowa", "250 ml"));
        items.add(new ShoppingItem("Wino czerwone wytrawne", "100 ml"));
        items.add(new ShoppingItem("Oliwa z oliwek", "2 łyżki"));
        items.add(new ShoppingItem("Parmezan", "50 g"));
        items.add(new ShoppingItem("Świeża bazylia", "kilka liści"));
        items.add(new ShoppingItem("Sól i pieprz czarny", "do smaku"));
        return items;
    }
}
