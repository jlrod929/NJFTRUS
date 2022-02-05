package cs491.project.NJFTRUS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VendorForm extends AppCompatActivity {
    //Variables
    TextInputLayout regName, regTruckName, regEmail, regPhoneNo;
    Button regBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorform);

//Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regTruckName = findViewById(R.id.reg_truckname);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regBtn = findViewById(R.id.reg_btn);


//Save data in FireBase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
//Get all the values
                String name = regName.getEditText().getText().toString();
                String truckname = regTruckName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();


                VendorHelp helperClass = new VendorHelp(name, truckname, email, phoneNo);

                reference.setValue(helperClass);
            }
        });//Register Button method end
    }
}//onCreate Method End
