package renan.augusto.connectusprototype;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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


public class CarouselActivity extends AppCompatActivity {

    private ViewPager nSlideViewPager;

    private SliderAdapter sliderAdapter;

    private Button mloginBtn;

    private Auth0 auth0;
    private SecureCredentialsManager credentialsManager;



    public static final String KEY_ACCESS_TOKEN = "connectusapp.auth0.com";
    private static final int CODE_DEVICE_AUTHENTICATION = 22;
    public static final String KEY_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS";
    public static final String KEY_ID_TOKEN = "com.auth0.ID_TOKEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);


        // Status Bar Transparente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        nSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);

        mloginBtn = (Button) findViewById(R.id.loginBtn);

        sliderAdapter = new SliderAdapter(this);

        auth0 = new Auth0(CarouselActivity.this);
        auth0.setLoggingEnabled(true);
        auth0.setOIDCConformant(true);
        credentialsManager =
                new SecureCredentialsManager(this,
                        new AuthenticationAPIClient(auth0),
                        new SharedPreferencesStorage(this));

        mloginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if (getIntent().getBooleanExtra(KEY_CLEAR_CREDENTIALS, false)) {
                    credentialsManager.clearCredentials();
                }

                if (!credentialsManager.hasValidCredentials()) {
                    setContentView(R.layout.activity_carousel);
                            doLogin();
                    return;
                }
                credentialsManager.getCredentials(new BaseCallback<Credentials, CredentialsManagerException>() {
                    @Override
                    public void onSuccess(final Credentials credentials) {
                        showNextActivity(credentials);
                    }

                    @Override
                    public void onFailure(CredentialsManagerException error) {
                        // Autenticação cancelada pelo usuário. Finaliza Activity
                        finish();
                    }
                });
            }
        });

        nSlideViewPager.setAdapter(sliderAdapter);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (credentialsManager.checkAuthenticationResult(requestCode, resultCode)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showNextActivity(Credentials credentials) {
        Intent intent = new Intent(CarouselActivity.this, DrawerActivity.class);
        intent.putExtra(KEY_ACCESS_TOKEN, credentials.getAccessToken());
        intent.putExtra(KEY_ID_TOKEN, credentials.getIdToken());
        startActivity(intent);
        finish();
    }

    public void doLogin() {
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
                    Toast.makeText(CarouselActivity.this, "Log In - Erro ocorreu", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onSuccess(@NonNull Credentials credentials) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CarouselActivity.this, "Log In - Successo", Toast.LENGTH_SHORT).show();
                }
            });
            credentialsManager.saveCredentials(credentials);
            showNextActivity(credentials);
        }
    };

    public void showAlertDialogButtonClicked() {

        // Configura o builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exit));
        builder.setMessage(getString(R.string.alertexit));

        // Adiciona os botoes
        builder.setPositiveButton(getString(R.string.exitopt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Finaliza o app
                finish();
            }
        });
        // Busca as strings no arquivo Strings.xml . Possibilidade de tradução
        builder.setNegativeButton(getString(R.string.cancelopt), null);          // Cancela e não faz nada
        // Cria e mostra o alerta
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void onBackPressed() {
        showAlertDialogButtonClicked();
    }


}
