package com.example.pchub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pchub.Models.DeliveryItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageDelivery extends AppCompatActivity {

    Button button;
    ListView listView;
    List<DeliveryItems> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_delivery);

        button = (Button)findViewById(R.id.addDilivey);
        listView = (ListView)findViewById(R.id.listview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageDelivery.this, AddDelivery.class);
                startActivity(intent);
            }
        });

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("DeliveryDetails");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    DeliveryItems deliveryItems = studentDatasnap.getValue(DeliveryItems.class);
                    user.add(deliveryItems);
                }

                MyAdapter adapter = new MyAdapter(ManageDelivery.this, R.layout.custom_delivery_details, (ArrayList<DeliveryItems>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        Button button1;
        Button button2;
    }

    class MyAdapter extends ArrayAdapter<DeliveryItems> {
        LayoutInflater inflater;
        Context myContext;
        List<DeliveryItems> user;


        public MyAdapter(Context context, int resource, ArrayList<DeliveryItems> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_delivery_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.id);
                holder.COL2 = (TextView) view.findViewById(R.id.productName);
                holder.COL3 = (TextView) view.findViewById(R.id.userName);
                holder.COL4 = (TextView) view.findViewById(R.id.contact);
                holder.button1 = (Button) view.findViewById(R.id.deledit);
                holder.button2 = (Button) view.findViewById(R.id.deldelete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Delivery ID:- "+user.get(position).getId());
            holder.COL2.setText("Product Name:- "+user.get(position).getName());
            holder.COL3.setText("Customer Name:- "+user.get(position).getUserName());
            holder.COL4.setText("Customer Contact:- "+user.get(position).getUserContact());
            System.out.println(holder);

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("DeliveryDetails").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_delivery_details, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.dname);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.dprise);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.dqty);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.dcustomername);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.dcustomerNic);
                    final EditText editText6 = (EditText) view1.findViewById(R.id.dcustomerContact);
                    final EditText editText7 = (EditText) view1.findViewById(R.id.dcustomerAddress);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatedelivery);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("DeliveryDetails").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String price = (String) snapshot.child("price").getValue();
                            String qty = (String) snapshot.child("qty").getValue();
                            String uname = (String) snapshot.child("userName").getValue();
                            String nic = (String) snapshot.child("userNIC").getValue();
                            String contact = (String) snapshot.child("userContact").getValue();
                            String address = (String) snapshot.child("userAddress").getValue();

                            editText1.setText(name);
                            editText2.setText(price);
                            editText3.setText(qty);
                            editText4.setText(uname);
                            editText5.setText(nic);
                            editText6.setText(contact);
                            editText7.setText(address);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String name = editText1.getText().toString();
                            String price = editText2.getText().toString();
                            String qty = editText3.getText().toString();
                            String uname = editText4.getText().toString();
                            String unic = editText5.getText().toString();
                            String uContact = editText6.getText().toString();
                            String uaddress = editText7.getText().toString();

                          if (name.isEmpty()) {
                                editText1.setError("Deliver Name is required");
                            }else if (price.isEmpty()) {
                                editText2.setError("Deliver Price Number is required");
                            }else if (qty.isEmpty()) {
                                editText3.setError("Deliver Quantity is required");
                            }else if (uname.isEmpty()) {
                                editText4.setError("Customer Name is required");
                            }else if (unic.isEmpty()) {
                                editText5.setError("Customer NIC is required");
                            }else if (uContact.isEmpty()) {
                                editText6.setError("Customer Contact is required");
                            }else if (uaddress.isEmpty()) {
                                editText7.setError("Customer Address is required");
                            }else {

                                HashMap map = new HashMap();
                                map.put("name", name);
                                map.put("price", price);
                                map.put("qty", qty);
                                map.put("userName", uname);
                                map.put("userNIC", unic);
                                map.put("userContact", uContact);
                                map.put("userAddress", uaddress);
                                reference.updateChildren(map);

                                Toast.makeText(ManageDelivery.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;

        }
    }
}
