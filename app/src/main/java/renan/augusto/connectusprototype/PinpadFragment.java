package renan.augusto.connectusprototype;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class PinpadFragment extends BottomSheetDialogFragment {

    private EditText pinpadET;
    private Button autenticate;



    public PinpadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pinpad, container, false);

        final DrawerActivity drawerActivity = (DrawerActivity) getActivity();

        pinpadET = view.findViewById(R.id.pinpadEdit);
        autenticate = view.findViewById(R.id.autenticate);

        autenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(pinpadET.getText().toString()) == drawerActivity.pinCode){
                    drawerActivity.onOpenShareActivity();
                }else{

                    // senha errada @Todo ajustar. E arrumar que o NFC esta crashando. tentar fechar esse fragment e abrir a activity pelo mainfragment/draweractivity
                }
            }
        });

        return view;
    }
}