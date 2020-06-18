package renan.augusto.connectusprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class HistoryFragment extends Fragment {

    private Button deleteHistoryBt;

    private static CardView[] cardViewHistory = new CardView[4];
    private static TextView[] nameCardHistory = new TextView[4];
    private static ImageView[] imagenCardHistory = new ImageView[4];
    private ImageView nothingToDo;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!= null) {
        }
        getActivity().setTitle(getString(R.string.historycnav));

    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        final DrawerActivity drawerActivity = (DrawerActivity) getActivity();


        deleteHistoryBt = (Button) view.findViewById(R.id.deleteHistoryBt);
        nothingToDo = view.findViewById(R.id.nothingtodohere);
        nothingToDo.setVisibility(View.VISIBLE);
        deleteHistoryBt.setVisibility(View.GONE);

        cardViewHistory[0] = view.findViewById(R.id.historyCard1);
        cardViewHistory[1] = view.findViewById(R.id.historyCard2);
        cardViewHistory[2] = view.findViewById(R.id.historyCard3);
        cardViewHistory[3] = view.findViewById(R.id.historyCard4);

        nameCardHistory[0] = view.findViewById(R.id.historyUserName1);
        nameCardHistory[1] = view.findViewById(R.id.historyUserName2);
        nameCardHistory[2] = view.findViewById(R.id.historyUserName3);
        nameCardHistory[3] = view.findViewById(R.id.historyUserName4);

        imagenCardHistory[0] = view.findViewById(R.id.historyUserPic1);
        imagenCardHistory[1] = view.findViewById(R.id.historyUserPic2);
        imagenCardHistory[2] = view.findViewById(R.id.historyUserPic3);
        imagenCardHistory[3] = view.findViewById(R.id.historyUserPic4);



        for (int i = 0; i < cardViewHistory.length; i++){
            cardViewHistory[i].setVisibility(View.GONE);
        }

        for (int i = 0; i < DrawerActivity.userNameHistory.length; i++){
            if (DrawerActivity.userNameHistory[i] != null){
                nothingToDo.setVisibility(View.GONE);
                deleteHistoryBt.setVisibility(View.VISIBLE);
                if (nameCardHistory[i] != null){
                    cardViewHistory[i].setVisibility(View.VISIBLE);
                    nameCardHistory[i].setText(DrawerActivity.userNameHistory[i]);
                    setImage(DrawerActivity.userImgHistory[i], imagenCardHistory[i]);

                }
            }
        }




        deleteHistoryBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHistoryBt.setVisibility(View.GONE);
                nothingToDo.setVisibility(View.VISIBLE);
                drawerActivity.deleteHistory();
                for (int i = 0; i < DrawerActivity.userNameHistory.length; i++){
                    cardViewHistory[i].setVisibility(View.GONE);
                }
            }
        });

        cardViewHistory[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SavedProfileActivity.class);
                i.putExtra("id", 0);
                startActivity(i);
            }
        });
        cardViewHistory[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SavedProfileActivity.class);
                i.putExtra("id", 1);
                startActivity(i);
            }
        });

        cardViewHistory[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SavedProfileActivity.class);
                i.putExtra("id", 2);
                startActivity(i);
            }
        });

        cardViewHistory[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SavedProfileActivity.class);
                i.putExtra("id", 3);
                startActivity(i);
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

    private void setImage(String image, ImageView viewReceive){
        if (image != null) {
            Transformation transformationHome = new RoundedTransformationBuilder()
                    .borderColor(getResources().getColor(R.color.colorPrimary))
                    .borderWidthDp(2)
                    .cornerRadiusDp(60)
                    .oval(false)
                    .build();
            Picasso.with(getActivity())
                    .load(image)
                    .transform(transformationHome)
                    .into(viewReceive);

        }
    }


}
