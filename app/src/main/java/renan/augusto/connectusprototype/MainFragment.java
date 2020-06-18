package renan.augusto.connectusprototype;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.nestia.biometriclib.BiometricPromptManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class MainFragment extends Fragment {

    private int fbUsed, instUsed, skyUsed;

    private static String homeName, homeEmail, homeImage;


    private TextView userNameTextView;

    private TextView userEmailTextView;

    private Button parseBt /*DELETE*/, shareButton, configButton, addAccountButton;

    private BiometricPromptManager mManager;


    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout relativeLayoutReal;

    /*CARDVIEWS*/

    private CardView card1, card2, card3, card4;

    private TextView accCard1, accCard2, accCard3, accCard4;

    private ImageView ImageCard1, ImageCard2, ImageCard3, ImageCard4;

    private Switch SwtCard1, SwtCard2, SwtCard3, SwtCard4;


    DrawerActivity drawerActivity = (DrawerActivity) getActivity();

    ViewGroup view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        final DrawerActivity drawerActivity = (DrawerActivity) getActivity();

        mManager = BiometricPromptManager.from(getActivity());

        SwtCard1 = view.findViewById(R.id.main_fragment_switch1);
        if (drawerActivity.swMainState1 == false){
            SwtCard1.setChecked(false);
        }
        SwtCard2 = view.findViewById(R.id.main_fragment_switch2);
        if (drawerActivity.swMainState2 == false){
            SwtCard2.setChecked(false);
        }
        SwtCard3 = view.findViewById(R.id.main_fragment_switch3);
        if (drawerActivity.swMainState3 == false){
            SwtCard3.setChecked(false);
        }
        SwtCard4 = view.findViewById(R.id.main_fragment_switch4);
        if (drawerActivity.swMainState4 == false){
            SwtCard4.setChecked(false);
        }

        card1 = view.findViewById(R.id.main_fragment_card1);
        card2 = view.findViewById(R.id.main_fragment_card2);
        card3 = view.findViewById(R.id.main_fragment_card3);
        card4 = view.findViewById(R.id.main_fragment_card4);

        ImageCard1 = view.findViewById(R.id.main_fragment_image1);
        ImageCard2 = view.findViewById(R.id.main_fragment_image2);
        ImageCard3 = view.findViewById(R.id.main_fragment_image3);
        ImageCard4 = view.findViewById(R.id.main_fragment_image4);

        accCard1 = view.findViewById(R.id.main_fragment_text1);
        accCard2 = view.findViewById(R.id.main_fragment_text2);
        accCard3 = view.findViewById(R.id.main_fragment_text3);
        accCard4 = view.findViewById(R.id.main_fragment_text4);

        SwtCard1 = view.findViewById(R.id.main_fragment_switch1);
        SwtCard2 = view.findViewById(R.id.main_fragment_switch2);
        SwtCard3 = view.findViewById(R.id.main_fragment_switch3);
        SwtCard4 = view.findViewById(R.id.main_fragment_switch4);

        shimmerFrameLayout = view.findViewById(R.id.shimmerLayoutMainFragment);
        relativeLayoutReal = view.findViewById(R.id.realLayoutMainFragment);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        getActivity().setTitle("Carregando...");
        relativeLayoutReal.setVisibility(View.GONE);



        parseBt = (Button) view.findViewById(R.id.parseBt);         //////DELETE
        final String URLShared = "google-oauth2|113506265234905133032";
        userNameTextView = (TextView) view.findViewById(R.id.userNameTitle);
        userEmailTextView = (TextView) view.findViewById(R.id.userEmailTitle);

        shareButton = (Button) view.findViewById(R.id.shareBtn);
        configButton = (Button) view.findViewById(R.id.configMainBtn);
        addAccountButton = (Button) view.findViewById(R.id.addAccountMainBtn);

        card1.setVisibility(View.GONE);
        card2.setVisibility(View.GONE);
        card3.setVisibility(View.GONE);
        card4.setVisibility(View.GONE);

        parseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerActivity.onSharedSucessActivity(drawerActivity.USER_ID);
            }
        });


        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerActivity.setSettingsChecked();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,
                                new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        addAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerActivity.setAccountsChecked();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,
                                new AccountFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DrawerActivity drawerActivity = (DrawerActivity)getActivity();
                String isFingerAuthEnabled = drawerActivity.switchSt;
                if (isFingerAuthEnabled == "1" || isFingerAuthEnabled == null){
                    if (mManager.isBiometricPromptEnable()) {
                        mManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
                            @Override
                            public void onUsePassword() {
                            }

                            @Override
                            public void onSucceeded() {

                                drawerActivity.onOpenShareActivity();
                                //shareButtonPress();
                            }

                            @Override
                            public void onFailed() {
                            }

                            @Override
                            public void onError(int code, String reason) {
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                    }
                }else{
                    PinpadFragment pinpadFragment = new PinpadFragment();
                    pinpadFragment.show(getFragmentManager(), pinpadFragment.getTag());
                    //Show numbpad
                }
            }
        });


        homeName = drawerActivity.sendName();
        homeEmail = drawerActivity.sendEmail();
        homeImage = drawerActivity.sendImage();

        userNameTextView.setText(homeName);
        userEmailTextView.setText(String.format(getString(R.string.useremail), homeEmail));
        if(homeImage!= null) {
            setImageRounded(view, homeImage);
        }

        SwtCard1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    drawerActivity.googleacc = drawerActivity.googleaccBk2;
                    drawerActivity.updateUser();
                    drawerActivity.swMainState1 = true;
                }else{
                    drawerActivity.googleaccBk = drawerActivity.googleacc;
                    drawerActivity.googleacc = null;
                    drawerActivity.updateUser();
                    drawerActivity.swMainState1 = false;
                }
            }
        });


        SwtCard2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (DrawerActivity.facebookacc != null || DrawerActivity.facebookaccBk != null){
                    if (isChecked){
                        drawerActivity.facebookacc = drawerActivity.facebookaccBk2;
                        drawerActivity.updateUser();
                        drawerActivity.swMainState2 = true;
                    }else{
                        drawerActivity.facebookaccBk = drawerActivity.facebookacc;
                        drawerActivity.facebookacc = null;
                        drawerActivity.updateUser();
                        drawerActivity.swMainState2 = false;
                    }
                }else{
                    if(DrawerActivity.instagramacc != null || DrawerActivity.instagramaccBk != null){
                        if (isChecked){
                            drawerActivity.instagramacc = drawerActivity.instagramaccBk2;
                            drawerActivity.updateUser();
                            drawerActivity.swMainState2 = true;
                        }else{
                            drawerActivity.instagramaccBk = drawerActivity.instagramacc;
                            drawerActivity.instagramacc = null;
                            drawerActivity.updateUser();
                            drawerActivity.swMainState2 = false;
                        }
                    }else{
                        if(DrawerActivity.skypeacc != null || DrawerActivity.skypeaccBk != null){
                            if (isChecked){
                                drawerActivity.skypeacc = drawerActivity.skypeaccBk2;
                                drawerActivity.updateUser();
                                drawerActivity.swMainState2 = true;
                            }else{
                                drawerActivity.skypeaccBk = drawerActivity.skypeacc;
                                drawerActivity.skypeacc = null;
                                drawerActivity.updateUser();
                                drawerActivity.swMainState2 = false;
                            }
                        }
                    }
                }
            }
        });

        SwtCard3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(DrawerActivity.instagramacc != null || DrawerActivity.instagramaccBk != null){
                    if (isChecked){
                        drawerActivity.instagramacc = drawerActivity.instagramaccBk2;
                        drawerActivity.updateUser();
                        drawerActivity.swMainState3 = true;
                    }else{
                        drawerActivity.instagramaccBk = drawerActivity.instagramacc;
                        drawerActivity.instagramacc = null;
                        drawerActivity.updateUser();
                        drawerActivity.swMainState3 = false;
                    }
                }else{
                    if(DrawerActivity.skypeacc != null || DrawerActivity.skypeaccBk != null){
                        if (isChecked){
                            drawerActivity.skypeacc = drawerActivity.skypeaccBk2;
                            drawerActivity.updateUser();
                            drawerActivity.swMainState3 = true;
                        }else{
                            drawerActivity.skypeaccBk = drawerActivity.skypeacc;
                            drawerActivity.skypeacc = null;
                            drawerActivity.swMainState3 = false;
                        }
                    }
                }
            }
        });

        SwtCard4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(DrawerActivity.skypeacc != null || DrawerActivity.skypeaccBk != null){
                        if (isChecked){
                            drawerActivity.skypeacc = drawerActivity.skypeaccBk2;
                            drawerActivity.updateUser();
                            drawerActivity.swMainState4 = true;
                        }else{
                            drawerActivity.skypeaccBk = drawerActivity.skypeacc;
                            drawerActivity.skypeacc = null;
                            drawerActivity.updateUser();
                            drawerActivity.swMainState4 = false;
                        }
                    }
            }
        });
        return view;
    }



    private void setImageRounded (View view, String imageURL){

        getActivity().setTitle(getString(R.string.homepage));
        shimmerFrameLayout.stopShimmer();
        relativeLayoutReal.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);
        updateCards();

        ImageView userPicture = (ImageView) view.findViewById(R.id.userPicture);
        if (imageURL != null) {
            Transformation transformationHome = new RoundedTransformationBuilder()
                    .borderColor(getResources().getColor(R.color.colorPrimary))
                    .borderWidthDp(2)
                    .cornerRadiusDp(60)
                    .oval(false)
                    .build();
            Picasso.with(getActivity())
                    .load(imageURL)
                    .transform(transformationHome)
                    .into(userPicture);

        }
    }


    private void updateCards() {
        accCard1.setText(homeEmail);
        card1.setVisibility(View.VISIBLE);
        setImage("Google", ImageCard1);
        if (DrawerActivity.facebookacc != null && fbUsed == 0){
            setImage("Facebook", ImageCard2);
            accCard2.setText(DrawerActivity.facebookacc);
            card2.setVisibility(View.VISIBLE);
            fbUsed = 1;
        }else{
            if(DrawerActivity.instagramacc != null && instUsed == 0){
                setImage("Instagram", ImageCard2);
                accCard2.setText(DrawerActivity.instagramacc);
                card2.setVisibility(View.VISIBLE);
                instUsed = 1;
            }else{
                if(DrawerActivity.skypeacc != null && skyUsed == 0){
                    setImage("Skype", ImageCard2);
                    accCard2.setText(DrawerActivity.skypeacc);
                    card2.setVisibility(View.VISIBLE);
                    skyUsed = 1;
                }
            }
        }
        if (DrawerActivity.instagramacc != null && instUsed == 0){
            setImage("Instagram", ImageCard3);
            accCard3.setText(DrawerActivity.instagramacc);
            card3.setVisibility(View.VISIBLE);
            instUsed = 1;
        }else{
            if(DrawerActivity.skypeacc != null && skyUsed == 0){
                setImage("Skype", ImageCard3);
                accCard3.setText(DrawerActivity.skypeacc);
                card3.setVisibility(View.VISIBLE);
                skyUsed = 1;
            }
        }
        if (DrawerActivity.skypeacc != null && skyUsed == 0){
            setImage("Skype", ImageCard4);
            accCard4.setText(DrawerActivity.skypeacc);
            card4.setVisibility(View.VISIBLE);
            skyUsed = 1;
        }

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
}
