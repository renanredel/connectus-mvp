package renan.augusto.connectusprototype;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isConnected = isNetworkAvailable();

        if(isConnected){
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, CarouselActivity.class));
                    finish();
                }
            }, 200);
        }else{
            showAlertDialogButtonClicked();
        }


    }

    public void showAlertDialogButtonClicked() {

        // Configura o builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.noInternet));
        builder.setMessage(getString(R.string.noConnection));

        // Adiciona os botoes
        builder.setPositiveButton(getString(R.string.exitopt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Finaliza o app
                finish();
            }
        });
        //@tODO nao esta aparecendo o segundo botao
        builder.setNegativeButton(getString(R.string.retryOpt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartApp();
            }
        });
        // Cria e mostra o alerta
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void restartApp() {
        Intent i = this.getBaseContext()
                        .getPackageManager()
                         .getLaunchIntentForPackage(this.getBaseContext()
                                                       .getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}