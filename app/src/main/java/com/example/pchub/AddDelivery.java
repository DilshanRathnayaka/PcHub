package com.example.pchub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pchub.Models.DeliveryItems;
import com.example.pchub.Models.ProductDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDelivery extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    Button button;
    DatabaseReference ref;
    Integer deliveryCharge=300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery);

        ref = FirebaseDatabase.getInstance().getReference("DeliveryDetails");

        editText1 = (EditText)findViewById(R.id.didd);
        editText2 = (EditText)findViewById(R.id.dnamee);
        editText3 = (EditText)findViewById(R.id.dprisee);
        editText4 = (EditText)findViewById(R.id.dqtye);
        editText5 = (EditText)findViewById(R.id.dcustomernamee);
        editText6 = (EditText)findViewById(R.id.dcustomerNice);
        editText7 = (EditText)findViewById(R.id.dcustomerContacte);
        editText8 = (EditText)findViewById(R.id.dcustomerAddresse);
        button = (Button)findViewById(R.id.adddeliverye);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = editText1.getText().toString();
                final String name = editText2.getText().toString();
                final String price = editText3.getText().toString();
                final String qty = editText4.getText().toString();
                final String uname = editText5.getText().toString();
                final String unic = editText6.getText().toString();
                final String uContact = editText7.getText().toString();
                final String uaddress = editText8.getText().toString();

                if (code.isEmpty()) {
                    editText1.setError("Deliver Id is required");
                }else if (name.isEmpty()) {
                    editText2.setError("Deliver Name is required");
                }else if (price.isEmpty()) {
                    editText3.setError("Deliver Price Number is required");
                }else if (qty.isEmpty()) {
                    editText4.setError("Deliver Quantity is required");
                }else if (uname.isEmpty()) {
                    editText5.setError("Customer Name is required");
                }else if (unic.isEmpty()) {
                    editText6.setError("Customer NIC is required");
                }else if (uContact.isEmpty()) {
                    editText7.setError("Customer Contact is required");
                }else if (uaddress.isEmpty()) {
                    editText8.setError("Customer Address is required");
                }else {

                    Integer itemprice = Integer.valueOf(editText3.getText().toString());
                    String total = String.valueOf(itemprice + deliveryCharge);


                    DeliveryItems deliveryItems = new DeliveryItems(code,name,total,qty,uname,unic,uContact,uaddress);
                    ref.child(code).setValue(deliveryItems);

                    Toast.makeText(AddDelivery.this, "Successfully added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddDelivery.this, ManageProduct.class);
                    startActivity(intent);
                }
            }
        });
    }
}