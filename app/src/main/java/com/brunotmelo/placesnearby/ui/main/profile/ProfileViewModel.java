package com.brunotmelo.placesnearby.ui.main.profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.brunotmelo.placesnearby.data.facebook.FacebookRepository;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Profile;

public class ProfileViewModel extends ViewModel {

    private LiveData<Profile> liveProfile;

    LiveData<Profile> getProfile() {
        if (liveProfile== null) {
            loadProfile();
        }
        return liveProfile;
    }

    public void loadProfile(){
        FacebookRepository fbRepository = FacebookRepository.getInstance();
        liveProfile = fbRepository.getUserProfile();
    }
}
