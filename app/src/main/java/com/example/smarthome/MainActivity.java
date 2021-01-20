package com.example.smarthome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricPrompt.PromptInfo;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.hardware.biometrics.BiometricPrompt;
//import android.hardware.biometrics.BiometricPrompt.PromptInfo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.Login;
import com.example.smarthome.Network.NetworkService;
import com.example.smarthome.Network.Tokens;
import com.example.smarthome.Network.utils.CommonUtils;
import com.example.smarthome.application.HomeApplication;
import com.example.smarthome.constants.Urls;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    //private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private Button loginButton;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private BiometricPrompt.PromptInfo promptInfo;

    public void saveJWTToken(String token) {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs = this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit = prefs.edit();
        try {
            edit.putString("token", token);
            Log.i("Login", token);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getToken().getRefreshToken()!=""&&getToken().getRefreshToken()!=null){
            Button b = findViewById(R.id.biometric);
            b.setVisibility(View.VISIBLE);
            BiometricPrompt.PromptInfo promptI = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Підтверження входу в додаток")
                    .setSubtitle("Підтвердіть ваші біометричні дані")
                    .setDeviceCredentialAllowed(true)
                    // Can't call setNegativeButtonText() and
                    // setAllowedAuthenticators(...|DEVICE_CREDENTIAL) at the same time.
                    // .setNegativeButtonText("Use account password")
                    .build();
            Executor executor = ContextCompat.getMainExecutor(this);
            BiometricPrompt bp = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                    Toast.makeText(HomeApplication.getAppContext(),
//                            "Authentication error", Toast.LENGTH_SHORT)
//                            .show();
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onAuthenticationFailed() {
//                    Toast.makeText(HomeApplication.getAppContext(), "Authentication failed",
//                            Toast.LENGTH_SHORT)
//                            .show();
                }
            });
            bp.authenticate(promptI);
        }
        loginButton = (Button) findViewById(R.id.button);
        //imageRequester = ImageRequester.getInstance();
        //editImage =findViewById(R.id.photo);
        //NetworkImageView nv = (NetworkImageView) findViewById(R.id.photo);
        //imageRequester.setImageFromUrl(editImage, Urls.BASE_URL+"Images/div4una.jpg");
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
//        SignInButton signInButton = findViewById(R.id.google_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
        SignInButton button = findViewById(R.id.google_button);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                                          startActivityForResult(signInIntent, 1488);
                                      }
                                  }
        );

//        BiometricManager biometricManager = BiometricManager.from(this);
////        int t = biometricManager.canAuthenticate();


    }
    public void biometricClick(View view) {
        if(getToken().getRefreshToken()!=""&&getToken().getRefreshToken()!=null){
            BiometricPrompt.PromptInfo promptI = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Підтверження входу в додаток")
                    .setSubtitle("Підтвердіть ваші біометричні дані")
                    .setDeviceCredentialAllowed(true)
                    // Can't call setNegativeButtonText() and
                    // setAllowedAuthenticators(...|DEVICE_CREDENTIAL) at the same time.
                    // .setNegativeButtonText("Use account password")
                    .build();
            Executor executor = ContextCompat.getMainExecutor(this);
            BiometricPrompt bp = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                    Toast.makeText(HomeApplication.getAppContext(),
//                            "Authentication error", Toast.LENGTH_SHORT)
//                            .show();
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onAuthenticationFailed() {
//                    Toast.makeText(HomeApplication.getAppContext(), "Authentication failed",
//                            Toast.LENGTH_SHORT)
//                            .show();
                }
            });
            bp.authenticate(promptI);
        }
    }
    public void click(View view) {
        CommonUtils.showLoading(this);
        final TextInputEditText password = findViewById(R.id.password);
        final TextInputEditText email = findViewById(R.id.email);
        final TextInputLayout passwordLayout = findViewById(R.id.passwordLayout);
        final TextInputLayout emailLayout = findViewById(R.id.emailLayout);
        if (email.getText().toString() == "" || password.getText().toString() == "") {
            passwordLayout.setError("Fill all fields!");
        } else {
            passwordLayout.setError("");
        }


        Login m = new Login();
        m.setEmail(email.getText().toString());
        m.setPassword(password.getText().toString());
        NetworkService.getInstance()
                .getJSONApi()
                .login(m)
                .enqueue(new Callback<Tokens>() {
                    @Override
                    public void onResponse(@NonNull Call<Tokens> call, @NonNull Response<Tokens> response) {


                        if (response.errorBody() == null && response.isSuccessful()) {
                            passwordLayout.setError("");
                            //loginButton.setError("");
                            Tokens post = response.body();
                           // Toast toast = Toast.makeText(getApplicationContext(), "All done! your ref token :" + post.getRefreshToken(), Toast.LENGTH_LONG);
                            //toast.show();
                            saveJWTToken(post.getToken(),post.getRefreshToken());
                            CommonUtils.hideLoading();
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(intent);
                        } else {
                            //emailLayout.setError("");
                            //password.setError("Login or password was wrong");
                            CommonUtils.hideLoading();
                            passwordLayout.setError("Login or password was wrong");
                            loginButton.setError("Login or password was wrong");
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<Tokens> call, @NonNull Throwable t) {

                        CommonUtils.hideLoading();
                        //textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });

//        Intent intent = new Intent(this, MenuSideBarActivity.class);
//        startActivity(intent);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1488) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast toast = Toast.makeText(getApplicationContext(), "All done! your email:" + account.getEmail(), Toast.LENGTH_LONG);
            toast.show();
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }
}