package codes.tuton.urvesh.towinghelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import codes.tuton.urvesh.towinghelper.Adaptor.homeAdaptor;
import codes.tuton.urvesh.towinghelper.Model.MemoModel;
import codes.tuton.urvesh.towinghelper.interace_call.GoToNextActivity;

public class Home extends AppCompatActivity {

    private TextView textview_name,textview_number,textview_address;
    public static List<MemoModel> memoModelList = new ArrayList<>();
    public static int position = 0;

    SharedPreferences sharedpreferences ;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textview_name = findViewById(R.id.textview_name);
        textview_number = findViewById(R.id.textview_number);
        textview_address = findViewById(R.id.textview_address);

        sharedpreferences  = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);



        textview_name.setText(sharedpreferences.getString("username",""));
        textview_number.setText(sharedpreferences.getString("usernumber",""));
        textview_address.setText(sharedpreferences.getString("useraddress",""));


        recyclerView = findViewById(R.id.recyclerPaymentHistory);
        layoutManager = new LinearLayoutManager(this);
        adapter = new homeAdaptor(this, new GoToNextActivity() {
            @Override
            public void goToNextActivity(String image) {
                Intent i  = new Intent(Home.this, imageActivity.class);
                i.putExtra("image", image);
                startActivity(i);
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        memoInformation();

    }

        void memoInformation(){
        String vehiclenumber = sharedpreferences.getString("vehicle_no","");
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Query query = databaseReference.child("Memo").orderByChild("vehicle_no").equalTo(vehiclenumber);
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    MemoModel memoModel = dataSnapshot.getValue(MemoModel.class);
                    memoModel.setId(dataSnapshot.getKey());
                    memoModelList.add(memoModel);
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    MemoModel memoModel = dataSnapshot.getValue(MemoModel.class);
                    memoModel.setId(dataSnapshot.getKey());
                    memoModelList.set(position, memoModel);
                    adapter.notifyItemChanged(position);

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}
