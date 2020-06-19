package com.example.demofirebase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ShowActivity extends AppCompatActivity {

    private RecyclerView RecyclerList;
    private DatabaseReference UsersRef;
    private ArrayList<GetSet>arrayList;
     Button logout;
     Button Showmap;
    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    public FirebaseRecyclerAdapter<GetSet, ShowViewHolder> adapter;


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child(uid).child("Users");


        RecyclerList = findViewById(R.id.recycler_list);


        logout=findViewById(R.id.logout);
        Showmap=findViewById(R.id.button_map);
        RecyclerList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerList.setHasFixedSize(true);
        UsersRef.keepSynced(true);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(ShowActivity.this,MainActivity.class);
            startActivity(intent);


            }
        });

      Showmap.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(ShowActivity.this,MapsActivity2.class);
              startActivity(intent);
          }
      });
        FirebaseRecyclerOptions<GetSet> options = new FirebaseRecyclerOptions.Builder<GetSet>().setQuery(UsersRef, GetSet.class).build();

        adapter = new FirebaseRecyclerAdapter<GetSet, ShowViewHolder>(options) {
            @Override
            protected void onBindViewHolder(ShowViewHolder showViewHolder, int i, GetSet getSet) {
                String username = String.valueOf(getSet.getUsername());
                String location = String.valueOf(getSet.getLocation());
                String datetime=String.valueOf(getSet.getDatetime());
                showViewHolder.username.setText(username);
                showViewHolder.location.setText(location);
                showViewHolder.datetime.setText(datetime);

            }

            @NonNull
            @Override
            public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ShowActivity.this).inflate(R.layout.latlng, parent, false);
                ShowViewHolder showViewHolder = new ShowViewHolder(view);
                return showViewHolder;
            }
        };

        RecyclerList.setAdapter(adapter);
    }


}

    class ShowViewHolder extends RecyclerView.ViewHolder
    {
        TextView username,location,datetime;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            location=itemView.findViewById(R.id.location);
            datetime=itemView.findViewById(R.id.datetime);

        }
    }



