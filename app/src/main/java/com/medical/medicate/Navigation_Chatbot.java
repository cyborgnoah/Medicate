package com.medical.medicate;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;



public class Navigation_Chatbot extends AppCompatActivity
{
    private ImageView send;
    private EditText message;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mReference;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_chatbot_main);

        send=(ImageView)findViewById(R.id.chatbot_send);
        message=(EditText)findViewById(R.id.chatbot_message);
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mdatabase=FirebaseDatabase.getInstance();
        mReference=mdatabase.getReference();

        mReference.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });


        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String msg=message.getText().toString();
                message.setText("");
                Message obmsg=new Message("User",msg);
                mReference.child("users").child(userId).child("Chatbot").push().setValue(obmsg);
            }
        });
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView messageTextView;
        public MessageViewHolder(View v)
        {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.user_textView);
        }
    }
    public static class Message
    {
        public String User;
        public String Message;
        public Message()
        {}
        public Message(String User,String Message)
        {
            this.User=User;
            this.Message=Message;
        }
    }

}
