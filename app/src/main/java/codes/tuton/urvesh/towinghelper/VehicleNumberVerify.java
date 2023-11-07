package codes.tuton.urvesh.towinghelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import codes.tuton.urvesh.towinghelper.Model.UserInfoModel;

public class VehicleNumberVerify extends AppCompatActivity {

    private EditText edittext_state_code,edittext_city_code,edittext_time_code,edittext_vehicle_code;
    private FloatingActionButton floatingActionButton;
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_number_verify);

        edittext_state_code = findViewById(R.id.edittext_state_code);
        edittext_city_code = findViewById(R.id.edittext_city_code);
        edittext_time_code = findViewById(R.id.edittext_time_code);
        edittext_vehicle_code = findViewById(R.id.edittext_vehicle_code);

        floatingActionButton = findViewById(R.id.floatingActionButton2);

        sharedpreferences  = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String state_code = edittext_state_code.getText().toString();
                final String city_code = edittext_city_code.getText().toString();
                final String time_code = edittext_time_code.getText().toString();
                final String vehicle_code = edittext_vehicle_code.getText().toString();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("USERS").child("user_info").child(state_code).child(city_code).child(time_code).child(vehicle_code);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(state_code.isEmpty()|| city_code.isEmpty()|| time_code.isEmpty()|| vehicle_code.isEmpty()){
                            Toast.makeText(VehicleNumberVerify.this, "Please Enter Vehicle Number", Toast.LENGTH_SHORT).show();
                        }else if (dataSnapshot == null){
                            Toast.makeText(VehicleNumberVerify.this, "Please Enter Valid Vehicle Number", Toast.LENGTH_SHORT).show();
                        }else {
                            UserInfoModel userInfoModel = dataSnapshot.getValue(UserInfoModel.class);
                            String vehicle_number_plate = state_code.toUpperCase()+""+city_code+""+time_code.toUpperCase()+""+vehicle_code;
                            SharedPreferences.Editor editor =sharedpreferences.edit();
                            editor.putString("username",userInfoModel.getName());
                            editor.putString("usernumber",userInfoModel.getMobile_no());
                            editor.putString("useraddress",userInfoModel.getAddress());
                            editor.putString("vehicle_no",vehicle_number_plate);
                            editor.apply();
                            Intent intent = new Intent(VehicleNumberVerify.this,OtpVerify.class);
                            intent.putExtra("user_mobileNo",userInfoModel.getMobile_no());
                            startActivity(intent);
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
