package renan.augusto.connectusprototype;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

    private static int fingerAuth;
    IFragmentInteraction mListener;


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        if (!(activity instanceof IFragmentInteraction)){
            throw new RuntimeException("A activity deve implementar a interface");
        }
        mListener = (IFragmentInteraction) activity;
    }

    Switch finger;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!= null) {
            finger = getActivity().findViewById(R.id.autenticationSwitch);
            int finger2 = savedInstanceState.getInt("finger", 1);
            if (finger2 == 1) {
               // Toast.makeText(getActivity(), "finger ativado", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(getActivity(), "finger Desativado", Toast.LENGTH_SHORT).show();
            }
        }
        getActivity().setTitle(getString(R.string.settingspage));

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        final DrawerActivity drawerActivity = (DrawerActivity) getActivity();

        ///BOTÃO DE CONTAS
        final Button account = view.findViewById(R.id.account_settings);
        account.setText(drawerActivity.sendEmail());
        account.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), account);
                popup.getMenuInflater()
                        .inflate(R.menu.account_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                getActivity(),
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show();
            }
        });



        Switch bussinessOPT = view.findViewById(R.id.bussinessOPT);
        bussinessOPT.setAlpha(.2f);

        final Button pinCodeBtn = view.findViewById(R.id.set_pin);
        Switch biometricSwt = view.findViewById(R.id.autenticationSwitch);
        if (biometricSwt.isChecked()){
            pinCodeBtn.setEnabled(false);
            pinCodeBtn.setAlpha(.2f);


        }else{
            pinCodeBtn.setEnabled(true);
            pinCodeBtn.setAlpha(1);
        }
        pinCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenBottonSheet();
            }
        });


        biometricSwt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pinCodeBtn.setEnabled(false);
                    pinCodeBtn.setAlpha(.2f);
                    onCloseBottonSheet();
                }else{
                    pinCodeBtn.setEnabled(true);
                    pinCodeBtn.setAlpha(1);
                    onOpenBottonSheet();


                }
            }
        });



        //BOTAO DE NOTIFICACOES
        final Button notication = view.findViewById(R.id.notificationSettings);
        notication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), notication);
                popup.getMenuInflater()
                        .inflate(R.menu.notification_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                getActivity(),
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show();
            }
        });

        TextView cleanhistory = view.findViewById(R.id.cleanHistory);
        cleanhistory.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.atention));
                builder.setMessage(getString(R.string.cleanhistoryAlert));
                builder.setPositiveButton(getString(R.string.yesopt), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),
                                "Deletar historico",
                                Toast.LENGTH_SHORT).show();
                        drawerActivity.deleteHistory();
                    }
                });
                builder.setNegativeButton(getString(R.string.noOpt), null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        //BOTAO DE SAIR
        TextView logOut = view.findViewById(R.id.settingsOut);
        logOut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra(LoginActivity.KEY_CLEAR_CREDENTIALS, true);
                startActivity(intent);
                getActivity().finish();
            }
        });

        TextView deleteAccount = view.findViewById(R.id.deleteAcc);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.deleteAcc));
                builder.setMessage(getString(R.string.sureToDelete));
                builder.setPositiveButton(getString(R.string.yesopt), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawerActivity.deleteAccount();
                    }
                });
                builder.setNegativeButton(getString(R.string.noOpt), null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        finger = getActivity().findViewById(R.id.autenticationSwitch);
        finger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (mListener!=null){
                        mListener.onSwitchChanges("1");
                    }
                    fingerAuth = 1;
                }else{
                    if (mListener!=null){
                        mListener.onSwitchChanges("2");
                    }
                    fingerAuth = 0;

                }
            }
        });

        super.onSaveInstanceState(outState);
        outState.putInt("finger", fingerAuth);
    }

    @Override
    public void onStop() {
        finger = getActivity().findViewById(R.id.autenticationSwitch);

        if (finger.isChecked()){
            fingerAuth = 0;
            if (mListener!=null){
                mListener.onSwitchChanges("1");
            }
        }else{
            fingerAuth = 1;
            if (mListener!=null){
                mListener.onSwitchChanges("2");
            }
        }
        super.onStop();
    }

    @Override
    public void onResume() {

        finger = getActivity().findViewById(R.id.autenticationSwitch);
        if (fingerAuth == 0) {
            finger.setChecked(true);
        } else {
            finger.setChecked(false);
        }
        super.onResume();
    }

    private void onOpenBottonSheet(){
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
}

    private void onCloseBottonSheet(){

    }
}
