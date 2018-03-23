package com.caesar.ken.caesarschat.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.core.login.LoginMainCore;
import com.caesar.ken.caesarschat.ui.activities.RegisterActivity;
import com.caesar.ken.caesarschat.ui.activities.UserListingActivity;

import java.security.PublicKey;
import java.util.zip.Inflater;

/**
 * Created by Caesar on 3/1/2018.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {


    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);//used to supply construction arguments for fragments
        return fragment;
    }

    private Button loginBuuton, registerButton;
    private EditText textEmail, textPassword;
    private ProgressDialog progressDialog;
    public LoginMainCore loginMainCorechild;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login,container, false);
        bindViews(fragmentView);
        return fragmentView;
    }
    public void bindViews(View view){

        loginBuuton = (Button) view.findViewById(R.id.button_login);
        registerButton = (Button) view.findViewById(R.id.button_register);
        textEmail = (EditText) view.findViewById(R.id.edit_text_email_id);
        textPassword = (EditText) view.findViewById(R.id.edit_text_password);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    public void init(){
        loginMainCorechild = new LoginMainCore(this);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);

        registerButton.setOnClickListener(this);
        loginBuuton.setOnClickListener(this);
        setDummyCredentials();
    }

    private void setDummyCredentials() {
        textEmail.setText("test@test.com");
       textPassword.setText("123456");
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_login:
                onLogin(v);
            case R.id.button_register:
                onRegister(v);
                break;
        }
    }
    public void onLogin(View v){
       String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        loginMainCorechild.performFirebaseLogin(getActivity(),email,password);
        progressDialog.show();
    }
    public void onRegister(View v){
        RegisterActivity.startActivity(getActivity());
    }
    public void onLoginSuccesful(String message){
        Toast.makeText(getActivity(),"Login successful: "+message,Toast.LENGTH_SHORT).show();
        UserListingActivity.startActivity(getActivity(), Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    public void onLoginFailure(String message){
        Toast.makeText(getActivity(), "Erron in "+ message,Toast.LENGTH_SHORT).show();
    }
}
