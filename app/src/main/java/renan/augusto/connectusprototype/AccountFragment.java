package renan.augusto.connectusprototype;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    private ImageView accImg1;
    private ImageView accImg2;
    private ImageView accImg3;
    private ImageView accImg4;

    private CardView accCard1;
    private CardView accCard2;
    private CardView accCard3;
    private CardView accCard4;

    public TextView accName1;
    public TextView accProfile1;
    public String accType1;




    private Button addAccount;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!= null) {//
        }

        getActivity().setTitle(getString(R.string.accountpage));

    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_accounts, container, false);

        final DrawerActivity drawerActivity = (DrawerActivity) getActivity();

        accCard1 = view.findViewById(R.id.accCard1);
        accCard2 = view.findViewById(R.id.accCard2);
        accCard3 = view.findViewById(R.id.accCard3);
        accCard4 = view.findViewById(R.id.accCard4);

        accCard2.setVisibility(View.INVISIBLE);
        accCard3.setVisibility(View.INVISIBLE);
        accCard4.setVisibility(View.INVISIBLE);

        accImg1 = view.findViewById(R.id.accImg1);
        accImg2 = view.findViewById(R.id.accImg2);
        accImg3 = view.findViewById(R.id.accImg3);
        accImg4 = view.findViewById(R.id.accImg4);

        accName1 = view.findViewById(R.id.accName1);

        accProfile1 = view.findViewById(R.id.accProfile1);
        drawerActivity.accProfile2 = view.findViewById(R.id.accProfile2);
        drawerActivity.accProfile3 = view.findViewById(R.id.accProfile3);
        drawerActivity.accProfile4 = view.findViewById(R.id.accProfile4);

        addAccount = view.findViewById(R.id.addNewAccount);

        accName1.setText(drawerActivity.sendName());
        accProfile1.setText(drawerActivity.sendEmail());
        accType1 = "Google";
        setImage(accType1, accImg1);

        if (drawerActivity.accType2 != null){
            setImage(drawerActivity.accType2, accImg2);
            drawerActivity.accProfile2.setText(drawerActivity.StringProfile2);
            accCard2.setVisibility(View.VISIBLE);
        }

        if (drawerActivity.accType3 != null){
            setImage(drawerActivity.accType3, accImg3);
            drawerActivity.accProfile3.setText(drawerActivity.StringProfile3);
            accCard3.setVisibility(View.VISIBLE);
        }

        if (drawerActivity.accType4 != null){
            setImage(drawerActivity.accType4, accImg4);
            drawerActivity.accProfile4.setText(drawerActivity.StringProfile4);
            accCard4.setVisibility(View.VISIBLE);
        }

        accCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), accCard2);
                popup.getMenuInflater()
                        .inflate(R.menu.account_menu_opt, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(drawerActivity.accType2 == "Facebook"){
                            DrawerActivity.facebookacc = null;
                        }
                        if (drawerActivity.accType2 == "Instagram"){
                            DrawerActivity.instagramacc = null;
                        }
                        if (drawerActivity.accType2 == "Skype"){
                            DrawerActivity.skypeacc = null;
                        }
                        drawerActivity.accType2 = null;
                        drawerActivity.accProfile2.setText(null);

                        drawerActivity.refreshAccountFragment();
                        drawerActivity.updateUser();
                        return true;
                    }
                });

                popup.show();
            }
        });


        accCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), accCard3);
                popup.getMenuInflater()
                        .inflate(R.menu.account_menu_opt, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(drawerActivity.accType3 == "Facebook"){
                            DrawerActivity.facebookacc = null;
                        }
                        if (drawerActivity.accType3 == "Instagram"){
                            DrawerActivity.instagramacc = null;
                        }
                        if (drawerActivity.accType3 == "Skype"){
                            DrawerActivity.skypeacc = null;
                        }
                        drawerActivity.accType3 = null;
                        drawerActivity.accProfile3.setText(null);

                        drawerActivity.refreshAccountFragment();
                        drawerActivity.updateUser();
                        return true;
                    }
                });

                popup.show();
            }
        });

        accCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), accCard4);
                popup.getMenuInflater()
                        .inflate(R.menu.account_menu_opt, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(drawerActivity.accType4 == "Facebook"){
                            DrawerActivity.facebookacc = null;
                        }
                        if (drawerActivity.accType4 == "Instagram"){
                            DrawerActivity.instagramacc = null;
                        }
                        if (drawerActivity.accType4 == "Skype"){
                            DrawerActivity.skypeacc = null;
                        }
                        drawerActivity.accType4 = null;
                        drawerActivity.accProfile4.setText(null);

                        drawerActivity.refreshAccountFragment();
                        drawerActivity.updateUser();
                        return true;
                    }
                });

                popup.show();
            }
        });


        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenNewAccounts();
            }
        });


        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
       super.onStop();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    private void setImage(String accType, ImageView imageView){

        int url;

        switch (accType){
            case "Google":
                url = R.drawable.google;
                break;
            case "Facebook":
                url = R.drawable.facebook;
                break;
            case "Instagram":
                url = R.drawable.instagram;
                break;
            case "Skype":
                url = R.drawable.skype;
                break;
            default:
                url = R.drawable.ic_accounts;
                break;
        }
            Picasso.with(getActivity())
                    .load(url)
                    .into(imageView);
    }

    private void onOpenNewAccounts(){/*
        NewAccountFragment newAccountFragment = new NewAccountFragment();
        newAccountFragment.show(getFragmentManager(), newAccountFragment.getTag());*/
        NewAccountFragment bottomSheetDialog = NewAccountFragment.newInstance();
        bottomSheetDialog.show(getFragmentManager(), "Bottom Sheet Dialog Fragment");
    }


}
