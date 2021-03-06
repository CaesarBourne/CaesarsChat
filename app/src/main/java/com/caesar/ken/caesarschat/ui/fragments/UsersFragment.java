package com.caesar.ken.caesarschat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.core.users.GetUsersMainCore;
import com.caesar.ken.caesarschat.models.Chat;
import com.caesar.ken.caesarschat.models.User;
import com.caesar.ken.caesarschat.ui.activities.ChatActivity;
import com.caesar.ken.caesarschat.ui.adapters.UserListingRecyclerAdapter;
import com.caesar.ken.caesarschat.utils.ItemClickSupport;

import java.util.List;

/**
 * Created by e on 3/1/2018.
 */

public class UsersFragment extends Fragment implements  ItemClickSupport.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener{
    public static final String TYPE_CHATS = "type chats";
    public static final String TYPE_ALL = "type all";
    public static final String ARG_TYPE = "type";
    private SwipeRefreshLayout swipeRefreshLayoutUsers;
    private RecyclerView recyclerViewUsers;
    private UserListingRecyclerAdapter myuserListingRecyclerAdapter;
    private GetUsersMainCore getallusers;

    public UsersFragment() {
    }

//    private UserFragmentInterface userClick;
//    public interface UserFragmentInterface {
//        void userClicked(User user);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            userClick = (UserFragmentInterface) context;
//        }
//        catch (ClassCastException i) {
//            throw new ClassCastException(context.toString()
//                    +" Implement interface to User userFragmentInterface on UserActivity");
//        }
//    }
    public static UsersFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewUsers = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerViewUsers = (RecyclerView) viewUsers.findViewById(R.id.recycler_view_all_user_listing);
        swipeRefreshLayoutUsers = (SwipeRefreshLayout) viewUsers.findViewById(R.id.swipe_refreh_layout);

        return viewUsers;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //pass the context of this instance to get the users
        getallusers = new GetUsersMainCore(this);
        getUsers();
//        swipeRefreshLayoutUsers.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayoutUsers.setRefreshing(true);
//            }
//        });
        ItemClickSupport.addTo(recyclerViewUsers).setOnItemClickListener(this);
        swipeRefreshLayoutUsers.setOnRefreshListener(this);
    }
    //this calls the getusers method in the getUsersMainCore as it gets users registered on firebase and sends them to getAllUsers below
    @Override
    public void onRefresh() {
//        getallusers.getAllUsersFromFirebase();
    }

    //    private void getUsers() {
//        if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_CHATS)) {
//
//        } else if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_ALL)) {
//            //this adds the arraylist of users to the gett all users for success below through the get all usersFromFirebase method
//            getallusers.getAllUsersFromFirebase();
//        }
//    }
//this is triggered by the onRefresh above to iterate through the database to get all users for the

    private void getUsers() {
        if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_CHATS)) {

        } else if (TextUtils.equals(getArguments().getString(ARG_TYPE), TYPE_ALL)) {
            //this adds the arraylist of users to the gett all users for success below through the get all usersFromFirebase method
            getallusers.getAllUsersFromFirebase();
        }
    }
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        ChatActivity.startActivity(getActivity(),
                myuserListingRecyclerAdapter.getUser(position).email,
                myuserListingRecyclerAdapter.getUser(position).uid,
                myuserListingRecyclerAdapter.getUser(position).token);
    }
    public void onGetAllUsersFailure(String message) {
        swipeRefreshLayoutUsers.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayoutUsers.setRefreshing(false);
            }
        });
        Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    public void getallUsersSuccess (List<User> users ){
        swipeRefreshLayoutUsers.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayoutUsers.setRefreshing(false);
            }
        });//pass the users fetched from database to the recyclerview adapter for users
        Log.i("UserFragment", "saw list");
        myuserListingRecyclerAdapter = new UserListingRecyclerAdapter(users);
        recyclerViewUsers.setAdapter(myuserListingRecyclerAdapter);
        myuserListingRecyclerAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onItemSelected(User user) {
//        userClick.userClicked(user);
//    }

    //this below gets the recieverUid and token with the email saved earlier  under  and pass it to chat fragment to use to send message to receiver
//the position o0f the clicked item is used to get the firebase token saved earlier for the user and pased to the constantsUid
    //the startActivity method is used to start the ChatActivity with the intent in the startActivity method i.e getActivity here is passed
//
  }
