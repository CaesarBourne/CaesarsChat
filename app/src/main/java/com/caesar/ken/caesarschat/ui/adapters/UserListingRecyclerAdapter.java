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

    private ItemClicked itemClicked;

    public void setItemClicked(ItemClicked itemClicked) {
        this.itemClicked = itemClicked;
    }

    public interface ItemClicked {
        void onItemSelected(User user);
    }

    public UserListingRecyclerAdapter(List<User> users, ItemClicked itemClicked) {
        this.myUsers = users;
        this.itemClicked = itemClicked;
    }
    //viewholder must be declared static to pass views to it
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView alphabetText, usernameText;
        //this is needed to pass the view to the constructor which is a metadata
        ViewHolder(View itemView) {
            super(itemView);
            alphabetText = (TextView) itemView.findViewById(R.id.text_view_all_user_alphabet);
            usernameText = (TextView) itemView.findViewById(R.id.text_view_username);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            itemClicked.onItemSelected(myUsers.get(position));
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
        String username ="", alphabet ="";
        if(null != initializedUser.email) {
            username = initializedUser.email;
            alphabet = initializedUser.email.substring(0,2);
        }

        holder.alphabetText.setText(alphabet);
        holder.usernameText.setText(username);
    }

    @Override
    public int getItemCount() {
        //if the array list is not empty
        if (myUsers != null){
            return myUsers.size();
        }
        else
            return 0;
    }
 //return the users pon the arraylist with the positio passed from the view and itis later used to start the chat activity
    public User getUsers (int position){
        return myUsers.get(position);
    }

}
