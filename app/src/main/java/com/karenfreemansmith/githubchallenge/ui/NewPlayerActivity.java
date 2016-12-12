package com.karenfreemansmith.githubchallenge.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.karenfreemansmith.githubchallenge.Constants;
import com.karenfreemansmith.githubchallenge.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPlayerActivity extends AppCompatActivity {
    public static final String TAG = NewPlayerActivity.class.getSimpleName();
    private String mGithubName;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog mAuthProgressDialog;


    @Bind(R.id.nameEditText) EditText mNameEditText;
    @Bind(R.id.emailEditText) EditText mEmailEditText;
    @Bind(R.id.passwordEditText) EditText mPasswordEditText;
    @Bind(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        createAuthStateListener();
        createauthProgressDialog();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @OnClick(R.id.buttonNewPlayer)
    public void showResults(View v) {
        createNewUser();
    }

    @OnClick(R.id.buttonLogin)
    public void showLogin(View v) {
        Intent intent = new Intent(NewPlayerActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void createauthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Loading...");
        mAuthProgressDialog.setMessage("Authenticating with Firebase...");
        mAuthProgressDialog.setCancelable(false);
    }

    private void createNewUser() {
        final String email = mEmailEditText.getText().toString().trim();
        mGithubName = mNameEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mGithubName);
        boolean validPassword = isValidPassword(password, confirmPassword);
        if(!validEmail || !validName || !validPassword) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Authentication successful");
                            createFirebaseUserProfile(task.getResult().getUser());
                        } else {
                            Toast.makeText(NewPlayerActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mGithubName)
                .build();
        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, user.getDisplayName());
                        }
                    }
                });
    }

    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    String username = mNameEditText.getText().toString().trim();
                    addToSharedPreferences(username);
                    Intent intent = new Intent(NewPlayerActivity.this, BattleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    private void addToSharedPreferences(String username) {
        mEditor.putString(Constants.LOGGED_IN_PLAYER_KEY, username).apply();
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail) {
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        if(name.equals("")) {
            mNameEditText.setError("Please enter your github username");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if(password.length() < 8) {
            mPasswordEditText.setError("Your password should be at least 8 characters long!");
            return false;
        } else if(!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}
