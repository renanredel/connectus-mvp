package renan.augusto.connectusprototype;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class SavedProfileActivity extends AppCompatActivity {

    private CardView cardViewTop, cardView1, cardView2, cardView3, cardView4;

    private TextView name, social1, social2, social3, social4;

    private ImageView profileImageTop, imageSocial1, imageSocial2, imageSocial3, imageSocial4;

    private String typeCard1, typeCard2, typeCard3, typeCard4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_profile);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");

        cardViewTop = findViewById(R.id.savedProfileTopCard);
        cardView1 = findViewById(R.id.savedCard1);
        cardView2 = findViewById(R.id.savedCard2);
        cardView3 = findViewById(R.id.savedCard3);
        cardView4 = findViewById(R.id.savedCard4);

        name = findViewById(R.id.profileSavedName);
        social1 = findViewById(R.id.savedSocial1);
        social2 = findViewById(R.id.savedSocial2);
        social3 = findViewById(R.id.savedSocial3);
        social4 = findViewById(R.id.savedSocial4);

        profileImageTop = findViewById(R.id.userSavedProfilePic);
        imageSocial1 = findViewById(R.id.savedSocialImage1);
        imageSocial2 = findViewById(R.id.savedSocialImage2);
        imageSocial3 = findViewById(R.id.savedSocialImage3);
        imageSocial4 = findViewById(R.id.savedSocialImage4);

        name.setText(DrawerActivity.userNameHistory[id]);
        setImage(DrawerActivity.userImgHistory[id], profileImageTop);
        setSocialCards(id);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewClicked(typeCard1, social1);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewClicked(typeCard2, social2);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewClicked(typeCard3, social3);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewClicked(typeCard4, social4);
            }
        });

    }

    private void cardViewClicked(String typeCard, TextView socialAccount) {
        if (typeCard == "Google"){
            String finalMail = "mailto:"+socialAccount.getText().toString()+"?subject=";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse(finalMail + "ConnectUS - Projeto Integrador III" + "&body=" + "Olá Mundo!");
            intent.setData(data);
            startActivity(intent);
        }
        if (typeCard == "Facebook"){
            String finalMail = "fb://facewebmodal/f?href="+socialAccount.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse(finalMail);
            intent.setData(data);
            startActivity(intent);
        }
        if (typeCard == "Instagram"){
            Uri uri = Uri.parse("https://www."+socialAccount.getText().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if (typeCard == "Skype"){
            String finalSkype = "skype:"+socialAccount.getText().toString()+"?chat";
            Uri skypeUri = Uri.parse(finalSkype);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
            myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
        }

    }

    private void setSocialCards(int id) {
        if (DrawerActivity.userGoogleHistory[id] == null || DrawerActivity.userGoogleHistory[id].equals("")) {
        } else {
            if (DrawerActivity.userGoogleHistory[id] != null || !DrawerActivity.userGoogleHistory[id].equals("")) {
                if (social1.getText().toString().matches("")) {
                    social1.setText(DrawerActivity.userGoogleHistory[id]);
                    setImageSocial("Google", imageSocial1);
                    cardView1.setVisibility(View.VISIBLE);
                    typeCard1 = "Google";
                } else {
                    if (social2.getText().toString().matches("")) {
                        social2.setText(DrawerActivity.userGoogleHistory[id]);
                        setImageSocial("Google", imageSocial2);
                        typeCard2 = "Google";
                    } else {
                        if (social3.getText().toString().matches("")) {
                            social3.setText(DrawerActivity.userGoogleHistory[id]);
                            setImageSocial("Google", imageSocial3);
                            typeCard3 = "Google";
                        } else {
                            if (social4.getText().toString().matches("")) {
                                social4.setText(DrawerActivity.userGoogleHistory[id]);
                                setImageSocial("Google", imageSocial4);
                                cardView4.setVisibility(View.VISIBLE);
                                typeCard4 = "Google";
                            }
                        }
                    }
                }
            }
        }
        if (DrawerActivity.userFaceHistory[id] == null || DrawerActivity.userFaceHistory[id].equals("")) {
        } else {
            if (DrawerActivity.userFaceHistory[id] != null || !DrawerActivity.userFaceHistory[id].equals("")) {
                if (social1.getText().toString().matches("")) {
                    social1.setText(DrawerActivity.userFaceHistory[id]);
                    setImageSocial("Facebook", imageSocial1);
                    cardView1.setVisibility(View.VISIBLE);
                    typeCard1 = "Facebook";
                } else {
                    if (social2.getText().toString().matches("")) {
                        social2.setText(DrawerActivity.userFaceHistory[id]);
                        setImageSocial("Facebook", imageSocial2);
                        cardView2.setVisibility(View.VISIBLE);
                        typeCard2 = "Facebook";
                    } else {
                        if (social3.getText().toString().matches("")) {
                            social3.setText(DrawerActivity.userFaceHistory[id]);
                            setImageSocial("Facebook", imageSocial3);
                            cardView3.setVisibility(View.VISIBLE);
                            typeCard3 = "Facebook";
                        } else {
                            if (social4.getText().toString().matches("")) {
                                social4.setText(DrawerActivity.userFaceHistory[id]);
                                setImageSocial("Facebook", imageSocial4);
                                cardView4.setVisibility(View.VISIBLE);
                                typeCard4 = "Facebook";
                            }
                        }
                    }
                }
            }
        }

        if (DrawerActivity.userInstaHistory[id] == null || DrawerActivity.userInstaHistory[id].equals("")) {
        } else {
            if (DrawerActivity.userInstaHistory[id] != null || !DrawerActivity.userInstaHistory[id].equals("")) {
                if (social1.getText().toString().matches("")) {
                    social1.setText(DrawerActivity.userInstaHistory[id]);
                    setImageSocial("Instagram", imageSocial1);
                    cardView1.setVisibility(View.VISIBLE);
                    typeCard1 = "Instagram";
                } else {
                    if (social2.getText().toString().matches("")) {
                        social2.setText(DrawerActivity.userInstaHistory[id]);
                        setImageSocial("Instagram", imageSocial2);
                        cardView2.setVisibility(View.VISIBLE);
                        typeCard2 = "Instagram";
                    } else {
                        if (social3.getText().toString().matches("")) {
                            social3.setText(DrawerActivity.userInstaHistory[id]);
                            setImageSocial("Instagram", imageSocial3);
                            cardView3.setVisibility(View.VISIBLE);
                            typeCard3 = "Instagram";
                        } else {
                            if (social4.getText().toString().matches("")) {
                                social4.setText(DrawerActivity.userInstaHistory[id]);
                                setImageSocial("Instagram", imageSocial4);
                                cardView4.setVisibility(View.VISIBLE);
                                typeCard4 = "Instagram";
                            }
                        }
                    }
                }
            }
        }

        if (DrawerActivity.userSkypeHistory[id] == null || DrawerActivity.userSkypeHistory[id].equals("")) {
        } else {
            if (DrawerActivity.userSkypeHistory[id] != null || !DrawerActivity.userSkypeHistory[id].equals("")) {
                if (social1.getText().toString().matches("")) {
                    social1.setText(DrawerActivity.userSkypeHistory[id]);
                    setImageSocial("Skype", imageSocial1);
                    cardView1.setVisibility(View.VISIBLE);
                    typeCard1 = "Skype";
                } else {
                    if (social2.getText().toString().matches("")) {
                        social2.setText(DrawerActivity.userSkypeHistory[id]);
                        setImageSocial("Skype", imageSocial2);
                        cardView2.setVisibility(View.VISIBLE);
                        typeCard2 = "Skype";
                    } else {
                        if (social3.getText().toString().matches("")) {
                            social3.setText(DrawerActivity.userSkypeHistory[id]);
                            setImageSocial("Skype", imageSocial3);
                            cardView3.setVisibility(View.VISIBLE);
                            typeCard3 = "Skype";
                        } else {
                            if (social4.getText().toString().matches("")) {
                                social4.setText(DrawerActivity.userSkypeHistory[id]);
                                setImageSocial("Skype", imageSocial4);
                                cardView4.setVisibility(View.VISIBLE);
                                typeCard4 = "Skype";
                            }
                        }
                    }
                }
            }
        }
    }

    private void setImageSocial (String socialType, ImageView view){
        int url;

        switch (socialType){
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
        Picasso.with(this)
                .load(url)
                .into(view);
    }

    private void setImage(String image, ImageView viewReceive){
        if (image != null) {
            Transformation transformationHome = new RoundedTransformationBuilder()
                    .borderColor(getResources().getColor(R.color.colorPrimary))
                    .borderWidthDp(2)
                    .cornerRadiusDp(60)
                    .oval(false)
                    .build();
            Picasso.with(this)
                    .load(image)
                    .transform(transformationHome)
                    .into(viewReceive);

        }
    }
}
