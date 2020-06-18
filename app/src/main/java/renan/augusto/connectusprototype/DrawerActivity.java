package renan.augusto.connectusprototype;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.management.ManagementException;
import com.auth0.android.management.UsersAPIClient;
import com.auth0.android.result.UserProfile;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IFragmentInteraction{

    public boolean swMainState1 = true, swMainState2 = true, swMainState3 = true, swMainState4 = true;

    public APIServer apiserver;
    public static String USER_ID, googleacc, googleaccBk, googleaccBk2;
    public static String facebookacc, facebookaccBk, facebookaccBk2;
    public static String instagramacc, instagramaccBk, instagramaccBk2;
    public static String skypeacc, skypeaccBk, skypeaccBk2;

    public static String[] userNameHistory = new String[4];
    public static String[] userImgHistory = new String[4];
    public static String[] userFaceHistory = new String[4];
    public static String[] userInstaHistory = new String[4];
    public static String[] userSkypeHistory = new String[4];
    public static String[] userGoogleHistory = new String[4];

    MainFragment mainFragment = new MainFragment();

    //account variables
    public String accType2;
    public TextView accProfile2;
    public String StringProfile2;


    public String accType3;
    public TextView accProfile3;
    public String StringProfile3;

    public String accType4;
    public TextView accProfile4;
    public String StringProfile4;


    private UserProfile userProfile;

    private DrawerLayout drawer;

    public String switchSt;
    private String publicName, publiEmail, imageURL;

    //PINCODE
    public int pinCode;

    ///VARIAVEIS DO DRAWER MENU

    private ImageView imageNavBar;
    private TextView userNameNavTextView;

    private AuthenticationAPIClient authenticationAPIClient;
    private UsersAPIClient usersClient;


    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout linearLayoutReal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
       startupToolbar(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restserver-pc6.conveyor.cloud/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiserver = retrofit.create(APIServer.class);

        shimmerFrameLayout = findViewById(R.id.drawer_layout_shimmer);
        linearLayoutReal = findViewById(R.id.drawer_layout_real);

        linearLayoutReal.setVisibility(View.GONE);


    }

    public String sendName (){ return publicName;    }
    public String sendEmail (){ return publiEmail;  }
    public String sendImage () { return imageURL;   }

    public void refreshAccountFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        new AccountFragment())
                .commit();
    }


   public void startupToolbar(Bundle savedInstanceState){
       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

       drawer = findViewById(R.id.drawer_layout);
       NavigationView navigationView = findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
               R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.addDrawerListener(toggle);
       toggle.syncState();


       String accessToken = getIntent().getStringExtra(LoginActivity.KEY_ACCESS_TOKEN);

       Auth0 auth0 = new Auth0(this);
       auth0.setOIDCConformant(true);
       auth0.setLoggingEnabled(true);
       authenticationAPIClient = new AuthenticationAPIClient(auth0);
       usersClient = new UsersAPIClient(auth0, accessToken);
       getProfile(accessToken);


       if (savedInstanceState == null) {
           getSupportFragmentManager()
                   .beginTransaction()
                   .replace(R.id.fragment_container,
                           new MainFragment())
                   .commit();
           navigationView.setCheckedItem(R.id.nav_home);
       }


   }

   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){

           case R.id.nav_home:
               getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.fragment_container,
                       new MainFragment())
                       .commit();
               break;

                //CONTAS CADASTRADAS
           case R.id.nav_accounts:
               getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.fragment_container,
                               new AccountFragment())
                       .commit();
               break;

           case R.id.nav_history:
               getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.fragment_container,
                               new HistoryFragment())
                       .commit();
               break;

               ///LOGOUT BUTTON
           case R.id.nav_logout:
               Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(this, LoginActivity.class);
               intent.putExtra(LoginActivity.KEY_CLEAR_CREDENTIALS, true);
               startActivity(intent);
               finish();
               break;

               //Settings
           case R.id.nav_settings:
               getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.fragment_container,
                               new SettingsFragment())
                       .commit();
               break;

               //Share app
           case R.id.nav_send:
               Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
               break;
       }
       drawer.closeDrawer(GravityCompat.START);
       return true;
   }

   @Override
   public void onBackPressed() {
       if (drawer.isDrawerOpen(GravityCompat.START)) {
           drawer.closeDrawer(GravityCompat.START);
       } else {
           super.onBackPressed();
       }
   }


    private void getProfile(String accessToken) {
        authenticationAPIClient.userInfo(accessToken)
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(UserProfile userinfo) {
                        usersClient.getProfile(userinfo.getId())
                                .start(new BaseCallback<UserProfile, ManagementException>() {
                                    @Override
                                    public void onSuccess(UserProfile profile) {
                                        userProfile = profile;
                                        publicName = userProfile.getName();
                                        publiEmail = userProfile.getEmail();
                                        imageURL = userProfile.getPictureURL();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                refreshScreenInformation();
                                                PostInicialize();
                                                USER_ID = userProfile.getId();
                                                googleacc = userProfile.getEmail();
                                                googleaccBk = userProfile.getEmail();
                                                googleaccBk2 = userProfile.getEmail();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(ManagementException error) {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                              //  Toast.makeText(this, "User Profile Request Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                               // Toast.makeText(this, "User Info Request Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }

    private void refreshScreenInformation() {

        userNameNavTextView = findViewById(R.id.userNameNav);
        imageNavBar = findViewById(R.id.userNavImage);
        if (userProfile.getPictureURL() != null) {
            Transformation transformationDrawer = new RoundedTransformationBuilder()
                    .borderColor(getResources().getColor(R.color.colorPrimary))
                    .borderWidthDp(2)
                    .cornerRadiusDp(50)
                    .oval(false)
                    .build();
            Picasso.with(this)
                    .load(userProfile.getPictureURL())
                    .resizeDimen(R.dimen.drawerPictureDim,R.dimen.drawerPictureDim)
                    .transform(transformationDrawer)
                    .into(imageNavBar);
        }
            userNameNavTextView.setText(userProfile.getName());


        linearLayoutReal.setVisibility(View.VISIBLE);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);

    }

    @Override
    public void onSwitchChanges(String status) {
        switchSt = status;
    }

    public void setSettingsChecked (){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_settings); }


    public void setAccountsChecked (){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_accounts); }

    public void onOpenShareActivity (){
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent shareIntent = new Intent(DrawerActivity.this, ShareActivity.class);
                shareIntent.setAction("action_name");
                startActivity(shareIntent);
            }
        }, 1000);
    }

    public void onSharedSucessActivity (String URLShared){
        Intent i = new Intent(this, ShareSucessActivity.class);
        i.putExtra("URL", URLShared);
        startActivity(i);
    }

    public void deleteAccount (){
        Call<User> call = apiserver.deleteUser(USER_ID);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()) {
                    Log.e("", "Error : " + response.code() );
                    return;
                }
                User userResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n\n";
                Log.e("", "Sucess : " + content );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("", "Error : " + t ); }
        });

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.KEY_CLEAR_CREDENTIALS, true);
        startActivity(intent);
        this.finish();
    }


    private void PostInicialize(){
        User user = new User (userProfile.getName(), userProfile.getId(), userProfile.getPictureURL() ,userProfile.getEmail(),
                "","","");

        Call<User> call = apiserver.createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()) {
                    Log.e("", "Erro : " + response.code() );
                    return;
                }
                User userResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n\n";
                Log.e("", "Sucess : " + content );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("", "Erro : " + t);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        new MainFragment())
                .commit();

    }


    public void updateUser(){
        User user = new User(userProfile.getName(), userProfile.getId(), userProfile.getPictureURL() ,googleacc,
                facebookacc, instagramacc, skypeacc);

        Call<User> call = apiserver.updateUser(USER_ID, user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()) {
                    Log.e("", "Erro : " + response.code() );
                    return;
                }
                User userResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n\n";
                Log.e("", "Sucess : " + content );
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("", "Erro : " + t);
            }
        });
    }


    public void deleteHistory(){
        for(int i = 0; i<userNameHistory.length; i++){
            userNameHistory[i] = null;
            userImgHistory[i] = null;
            userFaceHistory[i] = null;
            userInstaHistory[i] = null;
            userSkypeHistory[i] = null;
            userGoogleHistory[i] = null;
        }
    }
}


