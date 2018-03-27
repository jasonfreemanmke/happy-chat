package edu.matc.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoom extends AppCompatActivity
{
    private Button btnSend;
    private EditText inputMsg;
    private TextView conversation;

    private String userName;
    private String roomName;

    private DatabaseReference root;
    private String tempKey;


    private String chatMsg;
    private String chatUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // How to show icon in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        btnSend = (Button) findViewById(R.id.btnSend);
        inputMsg = (EditText) findViewById(R.id.txtBxMessage);



        conversation = (TextView) findViewById(R.id.txtView);
        conversation.setMovementMethod(new ScrollingMovementMethod());   // Set up scrolling

        userName = getIntent().getExtras().get("userName").toString();
        roomName = getIntent().getExtras().get("roomName").toString();
        setTitle("Room - "+roomName);

        root = FirebaseDatabase.getInstance().getReference().child(roomName);

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                tempKey =root.push().getKey();
                root.updateChildren(map);


                DatabaseReference messageRoot = root.child(tempKey);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", userName);
                map2.put("msg", inputMsg.getText().toString());


                messageRoot.updateChildren(map2);

                //this clears the message box after you hit send
                inputMsg.setText("");

            }

        });

        //This code generated when I created the event listener
        root.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                appendChatConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                appendChatConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });



    }


    private void appendChatConversation(DataSnapshot dataSnapshot)
    {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext())
        {
            chatMsg = (String) ((DataSnapshot)i.next()).getValue();
            chatUserName = (String) ((DataSnapshot)i.next()).getValue();


            conversation.append(chatUserName+" : "+chatMsg+" \n");

        }
    }
}
