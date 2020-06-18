package renan.augusto.connectusprototype;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private Button savePincode;
    private EditText edittext_pinpad;

    public BottomSheetFragment() {
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
        View view = inflater.inflate(R.layout.fragment_bottompinpad, container, false);

        final DrawerActivity drawerActivity = (DrawerActivity) getActivity();

        edittext_pinpad = view.findViewById(R.id.edittext_pinpad);
        savePincode = view.findViewById(R.id.savePinBtn);
        savePincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(edittext_pinpad.getText().toString()) == 4444){
                    //NÃO SALVAR, PIN NAO ALTERADO ---- MOSTRAR ALERTA
                    Toast.makeText(getActivity(), "Pin não alterado!", Toast.LENGTH_SHORT).show();

                }else{
                    drawerActivity.pinCode = Integer.parseInt(edittext_pinpad.getText().toString());
                    Toast.makeText(getActivity(), "Pin salvo!", Toast.LENGTH_SHORT).show();
                    dismiss();

                }
                //save pincode na activitymain
            }
        });


        return view;
    }
}