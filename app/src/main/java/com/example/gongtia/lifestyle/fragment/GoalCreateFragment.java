package com.example.gongtia.lifestyle.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.gongtia.lifestyle.Goal;
import com.example.gongtia.lifestyle.activity.HomeActivity;
import com.example.gongtia.lifestyle.R;
import com.example.gongtia.lifestyle.repository.GoalRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class GoalCreateFragment extends Fragment implements View.OnClickListener {

    private String mGoal, mLbs, mLifestyle;
    private String userId;
    private FirebaseAuth mAuth;

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private RadioGroup rgGoal, rgLifestyle;
    private RadioButton rbGoal, rbLifestyle;
    private Button mbtSubmit;
    private EditText etLbs;

    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_goal_create, container, false);
        etLbs = view.findViewById(R.id.et_lbs);

        rgGoal = (RadioGroup)  view.findViewById(R.id.rg_goal);
        rbGoal = (RadioButton) view.findViewById(rgGoal.getCheckedRadioButtonId());
        rgGoal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbGoal = (RadioButton) view.findViewById(checkedId);
            }
        });

        rgLifestyle = (RadioGroup) view.findViewById(R.id.rg_active);
        rbLifestyle = (RadioButton) view.findViewById(rgLifestyle.getCheckedRadioButtonId());
        rgLifestyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbLifestyle = (RadioButton) view.findViewById(checkedId);
            }
        });


        mbtSubmit = view.findViewById(R.id.button_create_goal);
        mbtSubmit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(validateLbs()){
            GoalRepository.updateGoal(mGoal, mLifestyle, mLbs);
//           jump to home activity
            Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
            startActivity(homeIntent);
        }
    }

    public boolean validateLbs() {
        mGoal = rbGoal.getText().toString();
        mLifestyle = rbLifestyle.getText().toString();
        mLbs = etLbs.getText().toString();

        if(!mGoal.equals("Maintain")){
            if(TextUtils.isEmpty(etLbs.getText())){
                etLbs.setError("Enter pounds");
                return false;
            }
            double lbs = Double.parseDouble(mLbs);

            if(lbs > 2){
                etLbs.setError("Getting Overzealous! Try <= 2 lbs");
                return false;
            }
        }else{
            etLbs.setText("" + 0);
            mLbs = "0";
        }
        return true;
    }

//    @Override
//    public void storeUserGoal(){
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        mDatabase.child(userId).child("goal").setValue(mGoal);
//        mDatabase.child(userId).child("lifestyle").setValue(mLifestyle);
//        mDatabase.child(userId).child("lbs").setValue(mLbs);
//    }

    @Override public void onResume() {
        super.onResume();
        //lock screen to portrait
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override public void onPause() {
        super.onPause();
        //set rotation to sensor dependent
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }

}

