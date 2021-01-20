package com.example.smarthome.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smarthome.MainActivity;
import com.example.smarthome.MenuActivity;
import com.example.smarthome.Network.AuthorizedService;
import com.example.smarthome.Network.ImageRequester;
import com.example.smarthome.Network.NetworkService;
import com.example.smarthome.Network.Tokens;
import com.example.smarthome.Network.models.Profile;
import com.example.smarthome.Network.utils.CommonUtils;
import com.example.smarthome.R;
import com.example.smarthome.constants.Urls;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageRequester imageRequester;
    private NetworkImageView editImage;
    private TextInputEditText email;
    private TextInputEditText firstName;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        email =view.findViewById(R.id.email);
        firstName =view.findViewById(R.id.firstName);
        CommonUtils.showLoading(getContext());
        AuthorizedService.getInstance()
                .getJSONApi()
                .profile()
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.errorBody() == null && response.isSuccessful()) {
                            Profile p = response.body();
                            imageRequester = ImageRequester.getInstance();
                            editImage =view.findViewById(R.id.photo);
                            NetworkImageView nv = (NetworkImageView) view.findViewById(R.id.photo);
                            imageRequester.setImageFromUrl(editImage, Urls.BASE_URL+"/UserImages/"+p.getImage());
                            email.setText(p.getEmail());
                            firstName.setText(p.getFirstName());
                            CommonUtils.hideLoading();

                        }
                        else{
                            CommonUtils.hideLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        CommonUtils.hideLoading();
                    }
                });
        return view;
    }
}
