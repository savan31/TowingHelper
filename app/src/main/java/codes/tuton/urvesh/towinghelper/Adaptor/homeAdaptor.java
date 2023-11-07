package codes.tuton.urvesh.towinghelper.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import codes.tuton.urvesh.towinghelper.Home;
import codes.tuton.urvesh.towinghelper.Model.MemoModel;
import codes.tuton.urvesh.towinghelper.R;
import codes.tuton.urvesh.towinghelper.imageActivity;
import codes.tuton.urvesh.towinghelper.interace_call.GoToNextActivity;

public class homeAdaptor extends RecyclerView.Adapter<homeAdaptor.HomeViewHolder> {
    Context context;
    GoToNextActivity goToNextActivity;

    public homeAdaptor(Context context, GoToNextActivity goToNextActivity) {
        this.context = context;
        this.goToNextActivity = goToNextActivity;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder{

        public TextView textview_vehicleNumber,textview_dateTime,textview_name,textview_number,textview_location,textview_status;
        public Button button_pp,button_view_image;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            textview_vehicleNumber = itemView.findViewById(R.id.textview_vehicleNumber);
            textview_dateTime = itemView.findViewById(R.id.textview_dateTime);
            textview_name = itemView.findViewById(R.id.textview_name);
            textview_number = itemView.findViewById(R.id.textview_number);
            textview_location = itemView.findViewById(R.id.textview_location);
            textview_status = itemView.findViewById(R.id.textview_status);
            button_pp = itemView.findViewById(R.id.button_payPenalty);
            button_view_image = itemView.findViewById(R.id.button_view_image);


        }
    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardpaymenthistory, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {
        final MemoModel memoModel = Home.memoModelList.get(position);
        holder.textview_vehicleNumber.setText(memoModel.getVehicle_no());
        holder.textview_dateTime.setText(memoModel.getTime()+","+memoModel.getDate());
        holder.textview_name.setText(memoModel.getP_name());
        holder.textview_number.setText(memoModel.getP_mobileno());
        holder.textview_location.setText(memoModel.getP_area());
        holder.textview_status.setText(memoModel.getStatus());

        if (!memoModel.getStatus().toLowerCase().equals("pending")) {
            holder.textview_status.setTextColor(Color.parseColor("#45CE30"));
            holder.button_pp.setVisibility(View.GONE);
        }

        holder.button_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.position = position;
                FirebaseDatabase.getInstance().getReference().child("Memo").child(memoModel.getId()).child("status").setValue("confirm");

            }
        });

        holder.button_view_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToNextActivity.goToNextActivity(memoModel.getTowing_image());
            }
        });


    }

    @Override
    public int getItemCount() {
        return Home.memoModelList.size();
    }


}
