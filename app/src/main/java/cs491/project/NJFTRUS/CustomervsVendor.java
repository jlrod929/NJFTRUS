package cs491.project.NJFTRUS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CustomervsVendor extends AppCompatActivity {
    Button customer, vendor, ContactUs, CalendarPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customervendor);

        customer = findViewById(R.id.customer);
        vendor = findViewById(R.id.vendor);
        ContactUs = findViewById(R.id.ContactUs);
        CalendarPage = findViewById(R.id.CalendarPage);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                startActivity(intent);
            }

        });

        CalendarPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), Calendar.class);
                startActivity(intent);
            }
        });

        ContactUs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), InfoPage.class);
                startActivity(intent);
            }
        });



        vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StripePayment.class);
                startActivity(intent);
            }
        });
    }
}
