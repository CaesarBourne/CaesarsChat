package com.caesar.ken.caesarschat.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.core.chat.ChatMainCore;
import com.caesar.ken.caesarschat.events.PushNotificationEvent;
import com.caesar.ken.caesarschat.models.Chat;
import com.caesar.ken.caesarschat.ui.adapters.ChatRecyclerAdapter;
import com.caesar.ken.caesarschat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by e on 3/1/2018.
 */

public class ChatFragment extends Fragment implements TextView.OnEditorActionListener {
    private EditText chat_text;
    private RecyclerView myRecyclerView;
    private ChatMainCore chatMainCoreChild;
    private ChatRecyclerAdapter chatRecyclerAdapter;
    //save the arguments of this method as the bundle arguments or constructor
    public static ChatFragment newInstance( String receiver, String receiverUID, String receivertoken) {
        Bundle args = new Bundle();
        args.putString(Constants.ARG_RECEIVER, receiver);
        args.putString(Constants.ARG_RECEIVER_UID, receiverUID);
        args.putString(Constants.ARG_FIREBASE_TOKEN, receivertoken);
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(args);
        return chatFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container,false);
        bindViews(view);
        return view;
    }
    public  void bindViews(View view){
        chat_text = (EditText) view.findViewById(R.id.edit_text_message);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_chat);
    }
//fetch previous message from firebase when activity is created
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //pass the current user and reciver id to the chatMainCore to get the sent or previous messge from the database from the right room
        chatMainCoreChild.getMessageFromFirebaseUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                Constants.ARG_RECEIVER_UID);
        super.onActivityCreated(savedInstanceState);
    }




    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND){
            sendMessage();
        }
        return false;
    }
    public void sendMessage(){
        String sender = FirebaseAuth.getInstance().getCurrentUser().toString();
        String receiver = getArguments().getString(Constants.ARG_RECEIVER);
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverUid = getArguments().getString(Constants.ARG_RECEIVER_UID);
        String messageChat = chat_text.getText().toString();
        String receiverToken = getArguments().getString(Constants.ARG_FIREBASE_TOKEN);
        Chat chat = new Chat(sender, receiver, senderUid, receiverUid,messageChat, System.currentTimeMillis() );
        chatMainCoreChild.onSendMesageToFirebase(getActivity().getApplicationContext(), chat, receiverToken);
    }

    //after getting message from firebase from the chat maincore pass it bto the chatRecyclerAdapter
    public void onGetMesageSuccess(Chat chat){

        if (chatRecyclerAdapter == null){
            //create a new recycleradapter object everytime the adapter is empty and a new arraylist that nw data would be added to below
            chatRecyclerAdapter = new ChatRecyclerAdapter( new ArrayList<Chat>());
            myRecyclerView.setAdapter(chatRecyclerAdapter);
        }
        chatRecyclerAdapter.add(chat);
        myRecyclerView.smoothScrollToPosition(chatRecyclerAdapter.getItemCount()- 1 );
    }
    //if recycleradapter is null get a notification mesage
    @Subscribe
    public void onPushNotificationEvent(PushNotificationEvent pushNotificationEvent) {
        if (chatRecyclerAdapter == null || chatRecyclerAdapter.getItemCount() == 0) {
            chatMainCoreChild.getMessageFromFirebaseUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pushNotificationEvent.getUid());
        }
    }
}
