package com.example.tap.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CheckInActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";
    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        setContentView(R.layout.activity_check_in);
        findViewById(R.id.signIn_check_In_buttons).setVisibility(View.VISIBLE);
        findViewById(R.id.email_password_fields).setVisibility(View.GONE);
        findViewById(R.id.sign_in_buttons).setVisibility(View.GONE);
        findViewById(R.id.verification_1).setVisibility(View.GONE);
        findViewById(R.id.verification_2).setVisibility(View.GONE);
        findViewById(R.id.check_in_buttons).setVisibility(View.GONE);
        findViewById(R.id.signI_in).setOnClickListener(this);
        findViewById(R.id.check_in).setOnClickListener(this);
        findViewById(R.id.sign_in_button2).setOnClickListener(this);
        findViewById(R.id.check_In_2).setOnClickListener(this);
        findViewById(R.id.verification_but_2).setOnClickListener(this);
        findViewById(R.id.verification_but_1).setOnClickListener(this);
        findViewById(R.id.back_but_verification_2).setOnClickListener(this);
        findViewById(R.id.back_butverification_1).setOnClickListener(this);
        findViewById(R.id.back_but_check_in).setOnClickListener(this);
        findViewById(R.id.back_but_sign_in).setOnClickListener(this);
        findViewById(R.id.change_password_but).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(CheckInActivity.this, "Регистрация успешна",
                                    Toast.LENGTH_SHORT).show();
                            findViewById(R.id.signIn_check_In_buttons).setVisibility(View.GONE);
                            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
                            findViewById(R.id.check_in_buttons).setVisibility(View.GONE);
                            findViewById(R.id.verification_2).setVisibility(View.GONE);
                            findViewById(R.id.verification_1).setVisibility(View.VISIBLE);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Toast.makeText(CheckInActivity.this, "Не удалось зарегистрировать аккаунт, проверьте введённые данные",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(CheckInActivity.this, "Не удалось войти, проверьте введённые данные",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        mEmailField.setText(null);
        mPasswordField.setText(null);
        findViewById(R.id.sign_in_buttons).setVisibility(View.GONE);
        findViewById(R.id.email_password_fields).setVisibility(View.GONE);
        findViewById(R.id.check_in_buttons).setVisibility(View.GONE);
        findViewById(R.id.verification_2).setVisibility(View.GONE);
        findViewById(R.id.verification_1).setVisibility(View.GONE);
        findViewById(R.id.signIn_check_In_buttons).setVisibility(View.VISIBLE);
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(CheckInActivity.this,
                                    "Письмо для подтверждения аккаунта отправлено на адрес " + user.getEmail(),
                                    Toast.LENGTH_LONG).show();
                            findViewById(R.id.signIn_check_In_buttons).setVisibility(View.GONE);
                            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
                            findViewById(R.id.sign_in_buttons).setVisibility(View.GONE);
                            findViewById(R.id.check_in_buttons).setVisibility(View.GONE);
                            findViewById(R.id.verification_1).setVisibility(View.GONE);
                            findViewById(R.id.verification_2).setVisibility(View.VISIBLE);
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(CheckInActivity.this,
                                    "Не удалось отправить письмо для подтвержения аккаунта",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {

        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Заполните поле");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Заполните поле");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private boolean validateEmailForm() {

        mEmailField = findViewById(R.id.field_email);
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Заполните поле");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        return valid;
    }

    public void updateUI(FirebaseUser user) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.isEmailVerified()) {
                Intent intent = new Intent(CheckInActivity.this, MainActivity.class);
                startActivity(intent);
            }

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View v) {
        if (!isOnline()) {
            Toast.makeText(CheckInActivity.this, "Проверьте подключение к интернету",
                    Toast.LENGTH_SHORT).show();
        } else {
            switch (v.getId()) {
                case (R.id.signI_in):
                    findViewById(R.id.signIn_check_In_buttons).setVisibility(View.GONE);
                    findViewById(R.id.verification_1).setVisibility(View.GONE);
                    findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
                    findViewById(R.id.sign_in_buttons).setVisibility(View.VISIBLE);
                    break;
                case (R.id.check_in):
                    findViewById(R.id.signIn_check_In_buttons).setVisibility(View.GONE);
                    findViewById(R.id.verification_1).setVisibility(View.GONE);
                    findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
                    findViewById(R.id.check_in_buttons).setVisibility(View.VISIBLE);
                    break;
                case (R.id.sign_in_button2):
                    if (!validateForm()) {
                        return;
                    }
                    signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    break;
                case (R.id.check_In_2):
                    if (!validateForm()) {
                        return;
                    }
                    createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

                    break;
                case (R.id.verification_but_2):
                    final FirebaseUser currentUser = mAuth.getCurrentUser();
                    currentUser.reload();
                    if (currentUser.isEmailVerified()) {
                        Intent intent = new Intent(CheckInActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    if (!currentUser.isEmailVerified()) {
                        Toast.makeText(CheckInActivity.this,
                                "Перейдите по ссылке в письме",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case (R.id.verification_but_1):
                    sendEmailVerification();
                    break;
                case (R.id.back_but_verification_2):
                    final FirebaseUser mCurrentUser1 = mAuth.getCurrentUser();
                    AuthCredential credential1 = EmailAuthProvider
                            .getCredential("user@example.com", "password1234");
                    mAuth.getCurrentUser().reauthenticate(credential1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mCurrentUser1.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User account deleted.");
                                                    }
                                                }
                                            });

                                }
                            });
                    signOut();
                    break;
                case (R.id.back_butverification_1):
                    final FirebaseUser mCurrentUser = mAuth.getCurrentUser();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential("user@example.com", "password1234");
                    mAuth.getCurrentUser().reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mCurrentUser.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User account deleted.");
                                                    }
                                                }
                                            });

                                }
                            });
                    signOut();
                    break;
                case (R.id.back_but_check_in):
                    signOut();
                    break;
                case (R.id.back_but_sign_in):
                    signOut();
                    break;
                case (R.id.change_password_but):
                    if (!validateEmailForm()) {
                        return;
                    }
                    if (mAuth != null) {
                        mEmailField = findViewById(R.id.field_email);
                        Log.w(" if Email authenticated", "Recovery Email has been  sent to " + mEmailField.getText());
                        mAuth.sendPasswordResetEmail(String.valueOf(mEmailField.getText()));
                        Toast.makeText(CheckInActivity.this,
                                "Письмо для смены пароля отправлено на " + String.valueOf(mEmailField.getText()),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Log.w(" error ", " bad entry ");
                        Toast.makeText(CheckInActivity.this,
                                "Не удалось восстановить пароль",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }
}

