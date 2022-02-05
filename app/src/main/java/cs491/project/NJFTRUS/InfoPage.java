package cs491.project.NJFTRUS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.text.*;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        ImageView img = (ImageView)findViewById(R.id.logoId);
        TextView Phone_Number;

        //textView=(TextView)findViewById(R.id.Phone_Number);

        //Phone_Number = findViewById(R.id.Phone_Number);


        //public void onClick(View v){
        //Intent callIntent = new Intent(Intent.ACTION_CALL);
        //callIntent.setData(Uri.parse("tel:123456789"));
        //startActivity(callIntent);}
        //TextView.setOnClickListener(View v){
           // public void onClick(View v) {
                //Intent callIntent = new Intent(Intent.ACTION_CALL);
                //callIntent.setData(Uri.parse("tel:123456789"));
                //startActivity(callIntent);

            //}
       // }

        img.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.njfoodtrucksrus.com/"));
                startActivity(intent);
            }



        });
    }
}