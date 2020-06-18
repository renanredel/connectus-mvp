package renan.augusto.connectusprototype;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShareSucessActivity extends AppCompatActivity {

    private APIServer apiserver;

    private String name, image, google, facebook, instagram, skype;

    private CardView[] cardview = new CardView[4];

    private ImageView[] ImageCard = new ImageView[4];

    private TextView NameOnTop;
    private ImageView userImageProfileReceived;

    private TextView socialMedia1;
    private TextView socialMedia2;
    private TextView socialMedia3;
    private TextView socialMedia4;

    private Button saveBt;

    private TextView[] resultEmail = new TextView[4];

    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout relativeLayoutReal;

    DrawerActivity drawerActivity = new DrawerActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_sucess);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restserver-pc6.conveyor.cloud/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiserver = retrofit.create(APIServer.class);

        saveBt = findViewById(R.id.saveToHistory);

        shimmerFrameLayout = findViewById(R.id.card_top_shimmer);
        relativeLayoutReal = findViewById(R.id.card_top_real_layout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        relativeLayoutReal.setVisibility(View.GONE);

        NameOnTop = findViewById(R.id.NameOnTop);
        userImageProfileReceived = findViewById(R.id.userImageProfileReceived);

        socialMedia1 = findViewById(R.id.socialMedia1);
        socialMedia2 = findViewById(R.id.socialMedia2);
        socialMedia3 = findViewById(R.id.socialMedia3);
        socialMedia4 = findViewById(R.id.socialMedia4);



        cardview[0] = findViewById(R.id.cardview);
        cardview[1] = findViewById(R.id.cardview1);
        cardview[2] = findViewById(R.id.cardview2);
        cardview[3] = findViewById(R.id.cardview3);

        ImageCard[0] = findViewById(R.id.imageCard);
        ImageCard[1] = findViewById(R.id.imageCard1);
        ImageCard[2] = findViewById(R.id.imageCard2);
        ImageCard[3] = findViewById(R.id.imageCard3);


        for (int i = 0; i < cardview.length; i++) {
            cardview[i].setVisibility(View.GONE);
        }
        Bundle b = getIntent().getExtras();
        String userID = b.getString("ID");

        //String userID = DrawerActivity.USER_ID; //para testes

        //String userID = "google-oauth2|113506265234905133032"; //meu user id

        getSpecificUser(userID);


    }

    private void getSpecificUser(String usercode) {
        Call<List<User>> call = apiserver.getSpecificUser(usercode);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, retrofit2.Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Log.e("", "Error : " + response.code());
                    return;
                }
                List<User> users = response.body();
                for (User user : users) {
                    if (user.getName() != null) {

                        int instPost = 0;
                        int skypPost = 0;
                        String content = "";
                        content += "Nome: " + user.getName() + "\n";
                        content += "RegistrationNumber: " + user.getRegistrationNumber() + "\n";
                        content += "GoogleAccount: " + user.getGoogleAcc() + "\n";
                        content += "FacebookAccount: " + user.getFacebookacc() + "\n";
                        content += "InstagramAccount: " + user.getInstagramacc() + "\n";
                        content += "SkypeAccount: " + user.getSkypeacc() + "\n\n\n";
                        NameOnTop.setText(user.getName());
                        socialMedia1.setText(user.getGoogleAcc());
                        cardview[0].setVisibility(View.VISIBLE);

                        if (user.getUserImage() != null) {
                            Transformation transformationHome = new RoundedTransformationBuilder()
                                    .borderColor(getResources().getColor(R.color.colorPrimary))
                                    .borderWidthDp(2)
                                    .cornerRadiusDp(50)
                                    .oval(false)
                                    .build();
                            Picasso.with(ShareSucessActivity.this)
                                    .load(user.getUserImage())
                                    .transform(transformationHome)
                                    .into(userImageProfileReceived);

                            stopshimmer();
                        setImage("Google", ImageCard[0]);
                        name = user.getName();
                        google = user.getGoogleAcc();
                        image = user.getUserImage();
                        facebook = user.getFacebookacc();
                        instagram = user.getInstagramacc();
                        skype = user.getSkypeacc(); }

                        if (user.getFacebookacc()!=null && !user.getFacebookacc().equals("")){
                            socialMedia2.setText(user.getFacebookacc());
                            cardview[1].setVisibility(View.VISIBLE);
                            setImage("Facebook", ImageCard[1]);

                        }else{
                            if(user.getInstagramacc()!=null && !user.getInstagramacc().equals("")){
                                socialMedia2.setText(user.getInstagramacc());
                                cardview[1].setVisibility(View.VISIBLE);
                                instPost = 1;
                                setImage("Instagram", ImageCard[1]);
                            }else{
                                if(user.getSkypeacc() != null && !user.getSkypeacc().equals("")){
                                    socialMedia2.setText(user.getSkypeacc());
                                    cardview[1].setVisibility(View.VISIBLE);
                                    skypPost = 1;
                                    setImage("Skype", ImageCard[1]);
                                }
                            }
                        }

                        if(user.getInstagramacc()!=null && !user.getInstagramacc().equals("") && instPost == 0){
                            socialMedia3.setText(user.getInstagramacc());
                            cardview[2].setVisibility(View.VISIBLE);
                            instPost = 1;
                            setImage("Instagram", ImageCard[2]);
                            }else{
                                if(user.getSkypeacc() != null && !user.getSkypeacc().equals("") && skypPost == 0){
                                    socialMedia3.setText(user.getSkypeacc());
                                    cardview[2].setVisibility(View.VISIBLE);
                                    skypPost = 1;
                                    setImage("Skype", ImageCard[2]);
                                }
                            }
                        if(user.getSkypeacc() != null && !user.getSkypeacc().equals("") && skypPost == 0){
                            socialMedia4.setText(user.getSkypeacc());
                            cardview[3].setVisibility(View.VISIBLE);
                            skypPost = 1;
                            setImage("Skype", ImageCard[3]);
                                }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("", "Error : " + t);
            }
        });
    }

    private void stopshimmer() {
        relativeLayoutReal.setVisibility(View.VISIBLE);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

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
        Picasso.with(this)
                .load(url)
                .into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, CarouselActivity.class);
        startActivity(i);
        finish();
        }

    public void saveToHistory(View view) {
        for(int i = 0; i<DrawerActivity.userNameHistory.length; i++){
            if (DrawerActivity.userNameHistory[i] == null){
                DrawerActivity.userNameHistory[i] = name;
                DrawerActivity.userImgHistory[i] = image;
                DrawerActivity.userGoogleHistory[i] = google;
                DrawerActivity.userFaceHistory[i] = facebook;
                DrawerActivity.userSkypeHistory[i] = skype;
                DrawerActivity.userInstaHistory[i] = instagram;

                Snackbar.make(view, "Contato salvo!", Snackbar.LENGTH_SHORT).show();
                break;
            }
            if ((i+1) == DrawerActivity.userNameHistory.length && DrawerActivity.userNameHistory[i] != null){
                Snackbar.make(view, "Limite atingido! Contato não salvo.", Snackbar.LENGTH_LONG).show();

            }
        }


    }
}

