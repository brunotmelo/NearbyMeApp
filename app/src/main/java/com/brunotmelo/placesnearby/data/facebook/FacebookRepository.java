package com.brunotmelo.placesnearby.data.facebook;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.util.Log;
import com.brunotmelo.placesnearby.data.facebook.datatypes.GraphProfileResponse;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Profile;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import org.json.JSONObject;

public class FacebookRepository {

    private static FacebookRepository instance;

    private MutableLiveData<Profile> liveProfile;
    private AccessToken accessToken;

    public static FacebookRepository getInstance() {
        if (instance == null) {
            instance = new FacebookRepository();
        }
        return instance;
    }

    private FacebookRepository() {
        liveProfile = new MutableLiveData<>();
    }

    public LiveData<Profile> getUserProfile() {
        updateProfileInfo();
        return liveProfile;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    private void updateProfileInfo() {
        GraphRequest request = GraphRequest.newMeRequest(this.accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());


                        Profile profile = convertGraphToProfile(object);
                        liveProfile.postValue(profile);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture.height(496)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private Profile convertGraphToProfile(JSONObject object){
        Gson gson = new Gson();
        GraphProfileResponse responseObj = gson.fromJson(object.toString(), GraphProfileResponse.class );
        Profile profile = new Profile();
        profile.name = responseObj.name;
        profile.profilePictureUrl = responseObj.picture.data.url;
        return profile;
    }
}
