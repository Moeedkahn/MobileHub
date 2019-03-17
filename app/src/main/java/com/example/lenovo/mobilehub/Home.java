package com.example.lenovo.mobilehub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Home extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public static final String EXTRA_MESSAGE = "";
    arrayAdapter ItemAdapter;
    GridView viewList;
    DatabaseReference myRef;
    TextView logoutButton;
    private FirebaseAuth firebaseAuth;
    final int REQUEST_CODE = 1;
    SearchView editsearch;
    static final ArrayList<Item> ItemList=new ArrayList<>();
    TextView watch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser User=firebaseAuth.getCurrentUser();
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
        logoutButton=(TextView) findViewById(R.id.logout);
        watch=(TextView) findViewById(R.id.livestream);
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PlayerActivity.class);
                startActivity(intent);
            }
        });
            myRef = FirebaseDatabase.getInstance().getReference("Items");
            retrieveObjects();
            ItemAdapter = new arrayAdapter(this, ItemList);
            viewList = (GridView) findViewById(R.id.HomeList);
            viewList.setAdapter(ItemAdapter);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebaseAuth.signOut();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });
            viewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    updateDetail(position);
                }
            });

        ItemAdapter = new arrayAdapter(this, ItemList);
        viewList = (GridView) findViewById(R.id.HomeList);
        viewList.setAdapter(ItemAdapter);
    }

    public void onBackPressed() {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void updateDetail(int i) {
        Intent intent = new Intent(this, ItemInfo.class);
        intent.putExtra("MyClass", ItemList.get(i));
        startActivity(intent);
    }

    public void retrieveObjects(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Item value;
                String s="";
                ItemList.clear();
                for(DataSnapshot x:dataSnapshot.getChildren()){

                    value=x.getValue(Item.class);
                    s+=value.toString()+"\n";
                    ItemList.add(value);
                }

                //tv.setText(s);


                ItemAdapter.notifyDataSetChanged();


               /*
                Item value = dataSnapshot.getValue(Item.class);
                tv.setText(value.getItemid());
*/
                //               Log.d("abc",value.getItemid());

                //Log.d("abc", "Value is: " + value);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("abc", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        ItemAdapter.filter(text);
        return false;
    }
}
