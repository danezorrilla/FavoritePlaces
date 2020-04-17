package com.bb.favoriteplaces.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bb.favoriteplaces.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText loginUsername;
    EditText loginPassword;

    Button viewFavoritePlaces;

    MapFragment mapFragment = new MapFragment();
    FavoritePlacesFragment favoritePlaceFragment = new FavoritePlacesFragment();

    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        viewFavoritePlaces = findViewById(R.id.view_favorite_places);

        name = mAuth.getCurrentUser().getEmail();
        Log.d("TAG_X", "USERNAME: " + name);

    }

    public void openMapFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map_fragment, mapFragment)
                .commit();
    }

    public void openFavoritePlacesFragment(View view){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.favorite_places_list_fragment, favoritePlaceFragment)
                .commit();
    }

    public void loginUser(View view){
        String userName = loginUsername.getText().toString();
        String passWord = loginPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("TAG_X", "Sign In Successful");
                            openMapFragment();
                            viewFavoritePlaces.setVisibility(View.VISIBLE);
                        }else{
                            Log.d("TAG_X", "Sign In Failed", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void registerUser(View view){
        String userName = loginUsername.getText().toString();
        String passWord = loginPassword.getText().toString();
        Log.d("TAG_X", "User info: " + userName + " " + passWord);

        mAuth.createUserWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("TAG_X", "User Registered");
                            Toast.makeText(MainActivity.this, "User Created",
                                    Toast.LENGTH_LONG).show();
                        }else {
                            Log.d("TAG_X", "User Register Failed", task.getException());
                            Toast.makeText(MainActivity.this,
                                    task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void backToMain(){
        getSupportFragmentManager()
                .beginTransaction()
                .remove(favoritePlaceFragment)
                .commit();
    }

}
