package renan.augusto.connectusprototype;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class NewAccountFragment extends BottomSheetDialogFragment {



    private Button typeAcc;
    private Button saveAccBt;
    private EditText newAccProfile;
    private ImageView imgSocialAccount;
    private String type;

    public static NewAccountFragment newInstance() {
        NewAccountFragment fragment = new NewAccountFragment();
        return fragment;
    }


    public NewAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newaccount, container, false);

        final DrawerActivity drawerActivity = (DrawerActivity) getActivity();

        newAccProfile = view.findViewById(R.id.newAccProfile);
        imgSocialAccount = view.findViewById(R.id.imgSocialAccount);

        saveAccBt = view.findViewById(R.id.saveAccBt);
        typeAcc = view.findViewById(R.id.accountType);

        saveAccBt.setVisibility(View.INVISIBLE);

        typeAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), typeAcc);
                popup.getMenuInflater()
                        .inflate(R.menu.account_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                    public boolean onMenuItemClick(MenuItem item) {
                            typeAcc.setText(item.getTitle());

                            if (item.getItemId() != R.id.acc_op1 && item.getItemId() != R.id.acc_op2 && item.getItemId() != R.id.acc_op3){
                                saveAccBt.setVisibility(View.INVISIBLE);
                            }
                            if (item.getItemId() == R.id.acc_op1){
                                newAccProfile.setText("facebook.com/");
                                imgSocialAccount.setImageResource(R.drawable.facebook);
                                type = "Facebook";
                                saveAccBt.setVisibility(View.VISIBLE);

                            }
                            if (item.getItemId() == R.id.acc_op2){
                                newAccProfile.setText("instagram.com/");
                                imgSocialAccount.setImageResource(R.drawable.instagram);
                                type = "Instagram";
                                saveAccBt.setVisibility(View.VISIBLE);
                            }
                            if (item.getItemId() == R.id.acc_op3){
                                newAccProfile.setText("");
                                imgSocialAccount.setImageResource(R.drawable.skype);
                                type = "Skype";
                                saveAccBt.setVisibility(View.VISIBLE);
                            }
                            return true;
                    }
                });

                popup.show();
            }
        });

        saveAccBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == "Facebook"){
                    DrawerActivity.facebookacc = newAccProfile.getText().toString();
                    DrawerActivity.facebookaccBk = newAccProfile.getText().toString();
                    DrawerActivity.facebookaccBk2 = newAccProfile.getText().toString();
                    drawerActivity.updateUser();

                }
                if (type == "Instagram"){
                    DrawerActivity.instagramacc = newAccProfile.getText().toString();
                    DrawerActivity.instagramaccBk = newAccProfile.getText().toString();
                    DrawerActivity.instagramaccBk2 = newAccProfile.getText().toString();
                    drawerActivity.updateUser();

                }
                if (type == "Skype"){
                    DrawerActivity.skypeacc = newAccProfile.getText().toString();
                    DrawerActivity.skypeaccBk = newAccProfile.getText().toString();
                    DrawerActivity.skypeaccBk2 = newAccProfile.getText().toString();
                    drawerActivity.updateUser();
                }

                if (type == "Facebook"){
                    if (drawerActivity.accType2 == null){
                        drawerActivity.accType2 = "Facebook";
                        drawerActivity.accProfile2.setText(newAccProfile.getText().toString());
                        drawerActivity.StringProfile2 = newAccProfile.getText().toString();
                    }else{
                        if (drawerActivity.accType3 == null){
                            drawerActivity.accType3 = "Facebook";
                            drawerActivity.accProfile3.setText(newAccProfile.getText().toString());
                            drawerActivity.StringProfile3 = newAccProfile.getText().toString();
                        }else{
                            if (drawerActivity.accType4 == null){
                                drawerActivity.accType4 = "Facebook";
                                drawerActivity.accProfile4.setText(newAccProfile.getText().toString());
                                drawerActivity.StringProfile4 = newAccProfile.getText().toString();
                            }
                        }
                    }
                }
                if (type == "Instagram"){
                    if (drawerActivity.accType2 == null){
                        drawerActivity.accType2 = "Instagram";
                        drawerActivity.accProfile2.setText(newAccProfile.getText().toString());
                        drawerActivity.StringProfile2 = newAccProfile.getText().toString();
                    }else{
                        if (drawerActivity.accType3 == null){
                            drawerActivity.accType3 = "Instagram";
                            drawerActivity.accProfile3.setText(newAccProfile.getText().toString());
                            drawerActivity.StringProfile3 = newAccProfile.getText().toString();
                        }else{
                            if (drawerActivity.accType4 == null){
                                drawerActivity.accType4 = "Instagram";
                                drawerActivity.accProfile4.setText(newAccProfile.getText().toString());
                                drawerActivity.StringProfile4 = newAccProfile.getText().toString();
                            }
                        }
                    }
                }

                if (type == "Skype"){
                    if (drawerActivity.accType2 == null){
                        drawerActivity.accType2 = "Skype";
                        drawerActivity.accProfile2.setText(newAccProfile.getText().toString());
                        drawerActivity.StringProfile2 = newAccProfile.getText().toString();
                    }else{
                        if (drawerActivity.accType3 == null){
                            drawerActivity.accType3 = "Skype";
                            drawerActivity.accProfile3.setText(newAccProfile.getText().toString());
                            drawerActivity.StringProfile3 = newAccProfile.getText().toString();
                        }else{
                            if (drawerActivity.accType4 == null){
                                drawerActivity.accType4 = "Skype";
                                drawerActivity.accProfile4.setText(newAccProfile.getText().toString());
                                drawerActivity.StringProfile4 = newAccProfile.getText().toString();
                            }
                        }
                    }
                }
                drawerActivity.refreshAccountFragment();
                dismiss();
            }
        });


        return view;
    }

}