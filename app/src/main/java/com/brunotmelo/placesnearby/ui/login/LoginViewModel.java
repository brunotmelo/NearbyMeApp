package com.brunotmelo.placesnearby.ui.login;

import android.arch.lifecycle.ViewModel;
import com.brunotmelo.placesnearby.data.facebook.FacebookRepository;
import com.facebook.AccessToken;

public class LoginViewModel extends ViewModel {

    public void updateAppAccessToken(AccessToken accessToken){
        FacebookRepository.getInstance().setAccessToken(accessToken);
    }




}
