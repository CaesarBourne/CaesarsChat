package com.caesar.ken.caesarschat.core.chat;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.caesar.ken.caesarschat.fcm.FcmNotificationBuilder;
import com.caesar.ken.caesarschat.models.Chat;
import com.caesar.ken.caesarschat.ui.fragments.ChatFragment;
import com.caesar.ken.caesarschat.utils.Constants;
import com.caesar.ken.caesarschat.utils.SharedPrefUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by e on 3/12/2018.
 */

public class ChatMainCore {
    private static final String TAG ="ChatMainCore";
    private ChatFragment chatFragmentChild;

    public ChatMainCore(ChatFragment chatFragmentChild) {
        this.chatFragmentChild = chatFragmentChild;
    }

    public void onSendMesageToFirebase(final Context context, final Chat chat, final String receiverFirebaseToken) {
        final String chat_room_1 = chat.senderUid + "_ " + chat.receiverUid;
        final String chat_room_2 = chat.receiverUid + "_ " + chat.senderUid;
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//that is the constants.child which is the datasnapshot as a child of sender_recieiveruid
                if (dataSnapshot.hasChild(chat_room_1)) {
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(chat_room_1)
                            .child(String.valueOf(chat.timestamp)).child(chat.message).setValue(chat);
                } else if (dataSnapshot.hasChild(chat_room_2)) {
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(String.valueOf(chat.timestamp)).setValue(chat);
                } else {
                    databaseReference.child(Constants.ARG_CHAT_ROOMS).child(chat_room_1).child(String.valueOf(chat.timestamp))
                            .setValue(chat);
                    getMessageFromFirebaseUser(chat.senderUid, chat.receiverUid);
                }
                // send push notification to the receiver
                sendPushNotificationToReceiver(chat.sender,
                        chat.message,
                        chat.senderUid,
                        new SharedPrefUtil(context).getString(Constants.ARG_FIREBASE_TOKEN),
                        receiverFirebaseToken);
                //below used to make a toast that message is sent at the chat fragment
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void sendPushNotificationToReceiver(String username,
                                                String message,
                                                String uid,
                                                String firebaseToken,
                                                String receiverFirebaseToken) {
        FcmNotificationBuilder.initialize()
                .title(username)
                .message(message)
                .username(username)
                .uid(uid)
                .firebaseToken(firebaseToken)
                .receiverFirebaseToken(receiverFirebaseToken)
                .send();
    }
    //the reciever gets the messsage from the firebase after its sent by the sender its called in ne init() method in the chat fragment
    //it gets the previous messages to set the chat recyclerview with

    public void getMessageFromFirebaseUser(String sender_Uid, String receiver_Uid) {
        final DatabaseReference mydataref = FirebaseDatabase.getInstance().getReference();
        final String chat_room_1 = sender_Uid + "_ " + receiver_Uid;
        final String chat_room_2 = receiver_Uid+ "_ " + sender_Uid;
        mydataref.child(Constants.ARG_CHAT_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(chat_room_1)) {
                    mydataref.child(Constants.ARG_CHAT_ROOMS).child(chat_room_1).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Log.e(TAG,"child is added "+chat_room_1+ " exists");
                            Chat chat = dataSnapshot.getValue(Chat.class);
                            chatFragmentChild.onGetMesageSuccess(chat);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else if (dataSnapshot.hasChild(chat_room_2)){
                    mydataref.child(Constants.ARG_CHAT_ROOMS).child(chat_room_2)
                            .addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    Log.e(TAG, "new child added "+ chat_room_2+" it exists");
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                }
                else {
                    Log.e(TAG, "no rom available mister");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}