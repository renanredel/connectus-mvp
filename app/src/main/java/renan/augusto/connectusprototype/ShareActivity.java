package renan.augusto.connectusprototype;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends Activity implements
                                                        NfcAdapter.CreateNdefMessageCallback,
                                                        NfcAdapter.OnNdefPushCompleteCallback {

    TextView textInfo;
    EditText textOut;

    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);



        textInfo = (TextView)findViewById(R.id.info);
        textOut = (EditText)findViewById(R.id.textout);


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter==null){

            Toast.makeText(ShareActivity.this,
                    "Modulo NFC não encontrado!",
                    Toast.LENGTH_LONG).show();
        }else{
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);

            if (nfcAdapter == null) {
                Toast.makeText(ShareActivity.this,
                        "Modulo NFC não encontrado",
                        Toast.LENGTH_LONG).show();
            } else if (!nfcAdapter.isEnabled()) {
                Toast.makeText(ShareActivity.this,
                        "Modulo NFC Desabilitado!",
                        Toast.LENGTH_LONG).show();
            }

            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();
        if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
            Parcelable[] parcelables =
                    intent.getParcelableArrayExtra(
                            NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage inNdefMessage = (NdefMessage)parcelables[0];
            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
            NdefRecord NdefRecord_0 = inNdefRecords[0];
            String inMsg = new String(NdefRecord_0.getPayload());
            textInfo.setText(inMsg);

            Intent i = new Intent(this, ShareSucessActivity.class);
            i.putExtra("ID", inMsg);
            startActivity(i);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }



    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        eventString,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        //String stringOut = textOut.getText().toString();
        String stringOut = DrawerActivity.USER_ID;
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord ndefRecordOut = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),            //textplain define que tipo de aplicativo ele irá chamar quando receber no outro dispositivo
                new byte[] {},
                bytesOut);

        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
        return ndefMessageout;
    }
}