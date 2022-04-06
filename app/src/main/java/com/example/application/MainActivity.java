package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    // declaring required variables
    private Socket client;
    private PrintWriter printwriter;
    private EditText textField;
    private Button button;
    private  Button connect;
    private String message;
    private String CapMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textField = (EditText) findViewById(R.id.editText1);
        button = (Button) findViewById(R.id.button1);
        connect=(Button) findViewById(R.id.button2);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"connection initiated",Toast.LENGTH_LONG).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                message = textField.getText().toString();
                CapMessage=message.toUpperCase();


                new Thread(new ClientThread(message)).start();
                Toast.makeText(MainActivity.this,CapMessage,Toast.LENGTH_LONG).show();

            }
        });


    }

    // the ClientThread class performs
    // the networking operations
    class ClientThread implements Runnable {
        private final String message;

        ClientThread(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            try {
                client = new Socket("192.168.1.5", 7540);
                printwriter = new PrintWriter(client.getOutputStream(),true);
                printwriter.write(message);
                printwriter.flush();
                printwriter.close();

                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textField.setText("");
                }
            });
        }
    }
}