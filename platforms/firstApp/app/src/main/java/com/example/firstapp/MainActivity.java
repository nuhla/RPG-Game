package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView t1;
    Button b1;
    Button b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (TextView)findViewById(R.id.textView);
        b1 = (Button)findViewById(R.id.btn);
        b1.setOnClickListener(this);
        TextView myTex = new TextView(this);
//        myTex.setWidth(ActionBar.LayoutParams);
        //      b2.setOnClickListener(this);

}


    @Override
    public void onClick(View v) {

        t1.setText("You Cliked Button One");

    }

    public void ChangeText2(View v ){
        //-------------- Open a new Screen  Expliciet ---------------//
        t1.setText(("YouClicked Button 2"));
        Intent intent = new Intent(this,splashscreen.class);
        intent.putExtra("mystring","Food");
        startActivity(intent);
        startActivityForResult(intent,1);


        //---------------- Open a Web page implicit -------------------//
//        Uri uri = Uri.parse("https://www.google.com");
//        Intent it= new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(it);

        //--------------- Dail a phone number implicit ------------//
//        Uri uri = Uri.parse("tel:+970568729446");
//        Intent it = new Intent(Intent.ACTION_DIAL,uri);
//        startActivity(it);



    }
}
