package com.example.gongtia.lifestyle.ViewModel;

import android.app.Application;

import com.example.gongtia.lifestyle.model.User;
import com.example.gongtia.lifestyle.repository.ProfileRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ProfileViewModel extends AndroidViewModel {
    private MutableLiveData<User> jsonData;
    private ProfileRepository mProfileRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mProfileRepository = new ProfileRepository(application);
        jsonData = mProfileRepository.getData();
    }

    public void setUser(String userName, String user){

        mProfileRepository.setUser(userName, user);
    }

    public LiveData<User> getData(){
        return jsonData;
    }
}
