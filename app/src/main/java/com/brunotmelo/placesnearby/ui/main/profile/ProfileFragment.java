package com.brunotmelo.placesnearby.ui.main.profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.brunotmelo.placesnearby.R;
import com.brunotmelo.placesnearby.ui.main.commons.datatypes.Profile;
import com.bumptech.glide.Glide;

public class ProfileFragment extends Fragment {

    private ImageView mProfileImage;
    private TextView mPersonName;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        mProfileImage = view.findViewById(R.id.profile_image);
        mPersonName = view.findViewById(R.id.name_text);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProfileViewModel viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        LiveData<Profile> livePlaces = viewModel.getProfile();

        livePlaces.observe(getViewLifecycleOwner(), new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                mPersonName.setText(profile.name);
                Glide.with(getContext())
                        .load(profile.profilePictureUrl)
                        .placeholder(mProfileImage.getDrawable())
                        .centerCrop()
                        .into(mProfileImage);
            }
        });




    }

}
