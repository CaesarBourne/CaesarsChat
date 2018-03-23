package com.caesar.ken.caesarschat.ui.fragments;

import android.content.Context;
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
import com.caesar.ken.caesarschat.core.registration.RegisterMainCore;
import com.caesar.ken.caesarschat.core.users.AddUserMainCore;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by e on 3/1/2018.
 */
//// TODO: 3/7/2018 NOTE: the aactivity is what is passed by the fragment and not the context
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private AddUserMainCore addUsers;
    private EditText emailEdit, passwordEdit;
    private Button buttonRegister;
    private RegisterMainCore registrationChild;

    public static RegisterFragment newInstance(){
        Bundle args = new Bundle();
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(args);
        return registerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container,false);
        bindViews(view);
        return view;
    }

    public void bindViews(View view){
        emailEdit = (EditText)view.findViewById(R.id.edit_text_email_id);
        passwordEdit =(EditText)view.findViewById(R.id.edit_text_password);
        buttonRegister =(Button) view.findViewById(R.id.button_register);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init();
        super.onActivityCreated(savedInstanceState);
    }
    public void init(){
        buttonRegister.setOnClickListener(this);
        //this is passed below cos their is an object of this fragment in the class below that has to be initialized with the variables of this fragment
        registrationChild = new RegisterMainCore(this);
        addUsers = new AddUserMainCore(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_register:
                onRegister(v);
                break;
        }
    }
    //the is passed directly to the d registrationMainCore to create user with email and password
    public void onRegister(View v){
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        registrationChild.onPerformFirebaseRegistration(getActivity(),email,password);
    }

    //this method below is just implemented when the user as aleeady been registered and passed from the RegisterMainCore
    public void onRegistrationSuccess(FirebaseUser firebaseUser){
        Toast.makeText(getActivity(), "youre logged in succesfully", Toast.LENGTH_SHORT).show();
        //add the user to firebase and show a toast if its succesful
        //the context is passed to be used retrieve the earlier saved token from the sharedPrefUtility
        addUsers.addUserToFirebase(getActivity().getApplicationContext(), firebaseUser);
    }

    public void onRegistrationFailure (String message){
        Toast.makeText(getActivity(), "Registration failed +\n " +message, Toast.LENGTH_LONG).show();
    }
    public void onAddUserSuccess(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
    }
    public void onAddUserFailure(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
    }
}
