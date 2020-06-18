package renan.augusto.connectusprototype;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;


public class LoginActivity extends AppCompatActivity {

    private Auth0 auth0;
    private SecureCredentialsManager credentialsManager;



    public static final String KEY_ACCESS_TOKEN = "connectusapp.auth0.com";
    private static final int CODE_DEVICE_AUTHENTICATION = 22;
    public static final String KEY_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String KEY_ID_TOKEN = "com.auth0.ID_TOKEN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup CredentialsManager
        auth0 = new Auth0(this);
        auth0.setLoggingEnabled(true);
        auth0.setOIDCConformant(true);
        credentialsManager =
                new SecureCredentialsManager(this,
                new AuthenticationAPIClient(auth0),
                new SharedPreferencesStorage(this));

        if (getIntent().getBooleanExtra(KEY_CLEAR_CREDENTIALS, false)) {
            credentialsManager.clearCredentials();
        }

        if (!credentialsManager.hasValidCredentials()) {
            setContentView(R.layout.activity_login);
            final Button loginButton = (Button) findViewById(R.id.loginButton);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doLogin();
                }
            });
            return;
        }


        // Obtain the existing credentials and move to the next activity

        credentialsManager.getCredentials(new BaseCallback<Credentials, CredentialsManagerException>() {
            @Override
            public void onSuccess(final Credentials credentials) {
                showNextActivity(credentials);
            }

            @Override
            public void onFailure(CredentialsManagerException error) {
                //Authentication cancelled by the user. Exit the app
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (credentialsManager.checkAuthenticationResult(requestCode, resultCode)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showNextActivity(Credentials credentials) {
        Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
        intent.putExtra(KEY_ACCESS_TOKEN, credentials.getAccessToken());
        intent.putExtra(KEY_ID_TOKEN, credentials.getIdToken());
        startActivity(intent);
        finish();
    }

    private void doLogin() {
        WebAuthProvider.init(auth0)
                .withScheme("demo")
                .withAudience(String.format("https://%s/api/v2/", getString(R.string.com_auth0_domain))) //https://%s/userinfo"
                .withScope("openid profile email offline_access read:current_user update:current_user_metadata")
                .start(this, webCallback);
    }

    private final AuthCallback webCallback = new AuthCallback() {
        @Override
        public void onFailure(@NonNull final Dialog dialog) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.show();
                }
            });
        }

        @Override
        public void onFailure(AuthenticationException exception) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onSuccess(@NonNull Credentials credentials) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this, "Log In - Success", Toast.LENGTH_SHORT).show();
                }
            });
            credentialsManager.saveCredentials(credentials);
            showNextActivity(credentials);
        }
    };

}