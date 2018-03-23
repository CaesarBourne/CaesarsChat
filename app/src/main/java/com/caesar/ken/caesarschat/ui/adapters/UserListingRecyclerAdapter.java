package com.caesar.ken.caesarschat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.models.User;

import java.util.List;

/**
 * Created by e on 3/8/2018.
 */
//alaways note the viewholder has the views while the recycler view adapter has the data that is passed to it in the constructor
public class UserListingRecyclerAdapter extends RecyclerView.Adapter<UserListingRecyclerAdapter.ViewHolder>{
    List<User> myUsers;

    public UserListingRecyclerAdapter(List<User> users) {
        this.myUsers = users;
    }
    //viewholder must be declared static to pass views to it
     static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView alphabetText, usernameText;
        //this is needed to pass the view to the constructor which is a metadata
        ViewHolder(View itemView) {
            super(itemView);
            alphabetText = (TextView) itemView.findViewById(R.id.text_view_all_user_alphabet);
            usernameText = (TextView) itemView.findViewById(R.id.text_view_username);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View myView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_user_listing,parent,false);
        return new ViewHolder(myView);//pass the new view to the constructor of view holder
    }
//this is used for binding the databto the views
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User initializedUser = myUsers.get(position);
        String username = initializedUser.email;
        String alphabet = initializedUser.email.substring(0,2);

        holder.alphabetText.setText(alphabet);
        holder.usernameText.setText(username);
    }

    @Override
    public int getItemCount() {
        //if the array list is not empty
        if (myUsers != null){
            myUsers.size();
        }
        return 0;
    }
 //return the users pon the arraylist with the positio passed from the view and itis later used to start the chat activity
    public User getUsers (int position){
        return myUsers.get(position);
    }

}
