package com.example.demofirebase;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UserloginregActivity extends AppCompatActivity {
    EditText username,password;
    Button userlogin,userregister;
    LocationManager locationManager;
    LocationListener locationListener;
    FirebaseAuth mAuth;
    String deviceuniqueid;
    String finaaddress="";

    LatLng userlatlng;
    String userlocstring;



   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length >0 &&
            (grantResults[0]
                    + grantResults[1]
                    + grantResults[2]
                    == PackageManager.PERMISSION_GRANTED)) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    {

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                        String deviceid = mTelephonyManager.getDeviceId();
                      //  Toast.makeText(this, ""+deviceid, Toast.LENGTH_SHORT).show();
                        deviceuniqueid=deviceid;

                    }

                }

            }

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userloginreg);

         username=findViewById(R.id.username);
         password=findViewById(R.id.userpassword);
         userlogin=findViewById(R.id.userLogin);
         userregister=findViewById(R.id.userRegister);
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
     //   Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

       /*  userregister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 FirebaseUser currentuser=mAuth.getCurrentUser();
                 String Id=currentuser.getUid();
                  String name=username.getText().toString();
                 DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                 ref.child(Id).child(name).setValue(1);
             }
         });
*/



        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {

                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    userlatlng=userLocation;
                    userlocstring=userlatlng.toString();
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                    try {

                        List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        if (listAddresses != null && listAddresses.size() > 0) {

                            Log.i("PlaceInfo", listAddresses.get(0).toString());

                            String address = "";

                            if (listAddresses.get(0).getSubThoroughfare() != null) {

                                address += listAddresses.get(0).getSubThoroughfare() + " ";

                            }

                            if (listAddresses.get(0).getThoroughfare() != null) {

                                address += listAddresses.get(0).getThoroughfare() + ", ";

                            }

                            if (listAddresses.get(0).getLocality() != null) {

                                address += listAddresses.get(0).getLocality() + ", ";

                            }

                            if (listAddresses.get(0).getPostalCode() != null) {

                                address += listAddresses.get(0).getPostalCode() + ", ";

                            }

                            if (listAddresses.get(0).getCountryName() != null) {

                                address += listAddresses.get(0).getCountryName();
                                finaaddress=address;

                            }



                        }

                    } catch (IOException e) {

                        e.printStackTrace();

                    }


                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    + ContextCompat.checkSelfPermission(
                    this,Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String deviceid = mTelephonyManager.getDeviceId();
             //   Toast.makeText(this, ""+deviceid, Toast.LENGTH_SHORT).show();
                deviceuniqueid=deviceid;

            }


        }




        userregister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                  final String name=username.getText().toString();
                  final String userpassword=password.getText().toString();
              //    FirebaseUser currentuser=mAuth.getCurrentUser();
              //    String Id=currentuser.getUid();
                 FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                 String uid = user.getUid();

                  final DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child(uid);

                  userref.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          if(!dataSnapshot.child("Users").child(name).exists())
                          {
                              HashMap<String, Object> userdataMap = new HashMap<>();
                              userdataMap.put("username", name);
                              userdataMap.put("password", userpassword);
                            //  userdataMap.put("location",finaaddress);
                              userdataMap.put("deviceid",deviceuniqueid);

                              userref.child("Users").child(name).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                          if(task.isSuccessful())
                                          {
                                              Toast.makeText(UserloginregActivity.this, "You have been registered successfully..", Toast.LENGTH_SHORT).show();
                                          }
                                          else
                                          {
                                              Toast.makeText(UserloginregActivity.this, "Network error ..Try again later..", Toast.LENGTH_SHORT).show();
                                          }
                                  }
                              });
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });

             }
         });

           userlogin.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   final String name=username.getText().toString();
                   final String userpassword=password.getText().toString();
                   //    FirebaseUser currentuser=mAuth.getCurrentUser();
                   //    String Id=currentuser.getUid();
                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                   String uid = user.getUid();

                   final DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child(uid);
                   userref.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                  if(dataSnapshot.child("Users").child(name).exists())

                                  {
                                      Toast.makeText(UserloginregActivity.this, "yes..", Toast.LENGTH_SHORT).show();
                                      Userdata usersData = dataSnapshot.child("Users").child(name).getValue(Userdata.class);
                                    if(usersData.getUsername().equals(name) && usersData.getPassword().equals(userpassword) && usersData.getDeviceid().equals(deviceuniqueid))
                                      {
                                          Toast.makeText(UserloginregActivity.this, "successfully logged in to activity", Toast.LENGTH_SHORT).show();
                                          Log.i("show","done");



                                              HashMap<String, Object> userdataMap = new HashMap<>();

                                              userdataMap.put("location", finaaddress.toString());
                                              userdataMap.put("Latitudelangitude",userlatlng);

                                              userdataMap.put("datetime",formattedDate);

                                          userref.child("Users").child(name).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                              @Override
                                              public void onComplete(@NonNull Task<Void> task) {
                                                  Toast.makeText(UserloginregActivity.this, "location saved in the database successfully...", Toast.LENGTH_SHORT).show();

                                              }
                                          });



                                          Intent intent=new Intent(UserloginregActivity.this,ShowActivity.class);
                                          startActivity(intent);

                                      }
                                    else{
                                        Toast.makeText(UserloginregActivity.this, "Recheck your username and password...", Toast.LENGTH_SHORT).show();
                                        }

                                  }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
               }
           });

    }


}
