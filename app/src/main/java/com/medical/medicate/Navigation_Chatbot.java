package com.medical.medicate;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;


public class Navigation_Chatbot extends AppCompatActivity
{
    private LinearLayout layout;
    private ScrollView scrollView;
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
        layout=(LinearLayout)findViewById(R.id.user_message_fixed);
        scrollView=(ScrollView)findViewById(R.id.scroller);
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final AIConfiguration config = new AIConfiguration("aa8be293155a43b6a46669624870dcaf",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        final AIDataService aiDataService = new AIDataService(config);


        mdatabase=FirebaseDatabase.getInstance();
        mReference=mdatabase.getReference();

        mReference.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot messageSnapshot:dataSnapshot.child("Chatbot").getChildren())
                {
                    Message message = messageSnapshot.getValue(Message.class);
                    addMessageBox(message.Message,message.User);
                }
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
                addMessageBox(msg,"User");
                final AIRequest aiRequest = new AIRequest();
                aiRequest.setQuery(msg);

                    new  AsyncTask<AIRequest, Void, AIResponse>() {
                        @Override
                        protected AIResponse doInBackground(AIRequest... requests) {
                            final AIRequest request = requests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(AIResponse aiResponse) {
                            if (aiResponse != null) {
                                String reply=aiResponse.getResult().getFulfillment().getSpeech().toString();
                                Log.d("Reply",reply);
                                Message obmsg=new Message("Chatbot",reply);
                                mReference.child("users").child(userId).child("Chatbot").push().setValue(obmsg);
                                /*Log.d("Status",aiResponse.getStatus().toString());
                                Log.d("Result",aiResponse.getResult().toString());
                                Log.d("Speech",aiResponse.getResult().getFulfillment().getSpeech().toString());*/
                                addMessageBox(reply,"Chatbot");
                            }

                        }
                    }.execute(aiRequest);
                }
        });
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
    public void addMessageBox(String message, String type)
    {
        TextView textView = new TextView(Navigation_Chatbot.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,6);

        if(type.equals("User"))
        {
            textView.setBackgroundResource(R.drawable.rounded_corner_right);
            lp.setMargins(200, 0, 20, 20);
            textView.setPadding(80,40,80,40);
            lp.gravity= Gravity.RIGHT;
            textView.setLayoutParams(lp);
        }
        else
        {
            textView.setBackgroundResource(R.drawable.rounded_corner_left);
            lp.setMargins(20, 0, 200, 20);
            textView.setPadding(80,40,80,40);
            lp.gravity=Gravity.LEFT;
            textView.setLayoutParams(lp);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
