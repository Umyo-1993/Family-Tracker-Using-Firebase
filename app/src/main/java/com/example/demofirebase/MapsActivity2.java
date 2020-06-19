package com.example.demofirebase;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng>mapdlatlng=new ArrayList<LatLng>();
    ArrayList<Double> testlist=new ArrayList<Double>();
    private DatabaseReference UsersRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        UsersRef= FirebaseDatabase.getInstance().getReference().child(uid).child("Users");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Toast.makeText(MapsActivity2.this, "Hello !", Toast.LENGTH_SHORT).show();


     /*   for(int i = 0; i < mapdlatlng.size(); i++) {
            testlist.add (Double.parseDouble(mapdlatlng.get(i)));
        }
*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               /*   for(DataSnapshot data: dataSnapshot.getChildren())
                {
                  if(data.getKey().equals("location")){
                        mapdlatlng.add(data.getValue().toString());
                        Toast.makeText(MapsActivity2.this, ""+mapdlatlng, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MapsActivity2.this, "Hello !", Toast.LENGTH_SHORT).show();

                    }
                }*/
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    for(DataSnapshot dataSnapshot1:data.getChildren())
                    {

                        if(dataSnapshot1.getKey().equals("Latitudelangitude")) {
                            //        mapdlatlng.add((String) dataSnapshot1.getValue());
                            //   mapdlatlng.add((LatLng) dataSnapshot1.getValue());
                            //  LatLng location=new LatLng(Double.parseDouble(String.valueOf(dataSnapshot1.getKey().equals("latitude"))),Double.parseDouble(String.valueOf(dataSnapshot1.getKey().equals("longitude"))));
                            String lat=dataSnapshot1.child("latitude").getValue().toString();
                            String lng=dataSnapshot1.child("longitude").getValue().toString();

                            double latitude = Double.parseDouble(lat);
                            double longitude = Double.parseDouble(lng);
                            LatLng loc = new LatLng(latitude, longitude);

                            //  String ans = dataSnapshot1.getValue().toString();
                            // Toast.makeText(MapsActivity2.this, "" + location, Toast.LENGTH_SHORT).show();
                            //   Toast.makeText(MapsActivity2.this, "Hello !", Toast.LENGTH_SHORT).show();
                            mMap.addMarker(new MarkerOptions().position(loc).title("Marker"));


                        }

                    }



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


 /*       Double a=22.46118709,b=92.07199533;
        LatLng sydney = new LatLng(a,b);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}
