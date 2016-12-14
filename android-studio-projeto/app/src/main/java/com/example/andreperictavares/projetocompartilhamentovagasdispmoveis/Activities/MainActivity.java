package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.User;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Network.UserServices;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.SharedPreferencesUtils;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Utils.VolleyJsonOBJCallback;

import org.json.JSONException;
import org.json.JSONObject;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!SharedPreferencesUtils.hasUserRegistered(this)){
            goToLoginActivity();
            MainActivity.this.finish();
        }
        else {
            User currentUser = User.getCurrentUser(this);
            UserServices.authUser(currentUser, this, new VolleyJsonOBJCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    try {
                        String token = result.getString("token");
                        SharedPreferencesUtils.setToken(MainActivity.this, token);
                        Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                        // test
//                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        // fim test
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onErrorResponse(String errorMsg) {
                    if (errorMsg != null){
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
            });
//            goToMainScreen();
//            Intent intent = new Intent(this, LocationActivity.class);
        }
    }

    public void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
