package com.caesar.ken.caesarschat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caesar.ken.caesarschat.R;
import com.caesar.ken.caesarschat.models.Chat;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by e on 3/8/2018.
 */
//the recycleviewholser passed into is the default viewholder that would be typecasted to either my chat or other chat
public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //the layout below has to be aconstant expression
    private static final int VIEW_MINE = 1;
    private static final int VIEW_OTHER = 2;
    List<Chat> myList;

    //the list is passed from chat fragment its the list ofchat data like uid message and token gotten from thefirebase
    public ChatRecyclerAdapter(List<Chat> myList){
        //the list in the argument above is of type chat
        this.myList = myList;
    }
    private static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView myText, myAlphabet;
        public MyViewHolder(View itemView) {
            super(itemView);
            myText = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            myAlphabet = (TextView) itemView.findViewById(R.id.text_view_all_user_alphabet);
        }
    }
   private static class OtherViewHolder extends RecyclerView.ViewHolder{
        private TextView otherText, otherAlphabet;
        public OtherViewHolder(View itemView) {
            super(itemView);
            otherText = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            otherAlphabet = (TextView) itemView.findViewById(R.id.text_view_all_user_alphabet);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater myInflater =  LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case VIEW_MINE: //first get the layoyt in the viewobject
           View viewMine =   myInflater.inflate(R.layout.item_chat_mine, parent,false);
                //then pass the view to the viewHolder cause we are crearing a viewHolder
                viewHolder = new MyViewHolder(viewMine);
                break;
            case VIEW_OTHER:
          //first get the layoyt in the viewobject
           View viewOther =   myInflater.inflate(R.layout.item_chat_mine, parent,false);
                //then pass the view to the viewHolder cause we are crearing a viewHolder
                viewHolder = new OtherViewHolder(viewOther);
                break;
        }
        return viewHolder;
    }
//bind the data to the views
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //this is better than passing it to the constructor as the get gets the individual values to pass all at once

        if (TextUtils.equals(FirebaseAuth.getInstance().getCurrentUser().getUid(), myList.get(position).senderUid )){
   //i.e if its me that is chatting get the right thing to bind to my chatholder layout views like message for the textview
            Chat chat = myList.get(position);
            //I'M typecasting the holder to my type of view holder
           MyViewHolder myChatHolder = (MyViewHolder)holder;
            // the listView passed iinto the recycleview adap[ter constructor already uses th chat type Class for its list
            myChatHolder.myText.setText(myList.get(position).message);
            myChatHolder.myAlphabet.setText(myList.get(position).sender.substring(0,2));
        }//if its the receiver bind to viewws for receiver
        else {
            OtherViewHolder otherChatHolder = (OtherViewHolder)holder;
            otherChatHolder.otherText.setText(myList.get(position).message);
            otherChatHolder.otherAlphabet.setText(myList.get(position).sender.substring(0,2));
        }
    }
//after the cycle of getting data from the cat fragmentit returns 0 o clear the adapter for the next data
    @Override
    public int getItemCount() {
        if (myList != null){
           return myList.size();
        }
        return 0;
    }

    public void add(Chat chat){
        myList.add(chat);
 // move the notifyItemInserted notifys item in the list to change there position to position+1 to enable space for the new added item to eh
//move the new added data to close position before the bottom size
        notifyItemInserted(myList.size() - 1);
    }
//the default implementation of belowreturns 0 cause its a single viewHolder unlike below whereby there are 2 viewholdrs
    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                myList.get(position).senderUid)){
            return VIEW_MINE;
        }
        else {
            return VIEW_OTHER;
        }
//        return super.getItemViewType(position);
    }
}
