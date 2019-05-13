package com.yashodainfotech.queuingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.R;
import com.yashodainfotech.queuingapp.TableActivity;

import java.util.List;
import java.util.Locale;

public class DashboardnewAdapter extends RecyclerView.Adapter<DashboardnewAdapter.holder> {
    Context context;
    List<DashPOJO> dashPOJOS;

    public DashboardnewAdapter(Context context, List<DashPOJO> dashPOJOS) {
        this.context = context;
        this.dashPOJOS = dashPOJOS;
    }

    @Override
    public DashboardnewAdapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dash_adapter, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(DashboardnewAdapter.holder holder, int position) {
        DashPOJO dashPOJO = dashPOJOS.get(position);
        holder.status.setText(dashPOJO.getStatus());
        holder.time.setText(dashPOJO.getWait_time());
        if (holder.status.getText().toString().equals("Open") && holder.time.getText().toString().equals("0") ) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.cpb_green));
            holder.time.setText(dashPOJO.getWait_time());
holder.countText.setText(dashPOJO.getMember_count());
           /* long millisInput = Long.parseLong(holder.time.getText().toString()) * 60000;
            mStartTimeInMillis = millisInput;
            int hours = (int) (mStartTimeInMillis / 1000) / 3600;
            int minutes = (int) ((mStartTimeInMillis / 1000) % 3600) / 60;
            int seconds = (int) (mStartTimeInMillis / 1000) % 60;
            String time=String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);*/
            holder.Timenew.setText("00:00:00");
            holder.Timenew.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.GONE);
            holder.table_time.setText(dashPOJO.getTable_time()); holder.seat.setText(dashPOJO.getSeat());
            holder.table_nmae.setText(dashPOJO.getTable_name());
            holder.id.setText(dashPOJO.getTable_id());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to add your name to queue?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                            intent.putExtra("table_id", holder.id.getText().toString());
                            context.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Added", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(view.getContext(), TableActivity.class);
                            intent.putExtra("table_id", holder.id.getText().toString());
                            context.startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();*/
                }
            });
        } else if (holder.status.getText().toString().equals("Reserved")) {holder.countText.setText(dashPOJO.getMember_count());

            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.gray_light));
            holder.time.setText(dashPOJO.getWait_time());
            holder.table_time.setText(dashPOJO.getTable_time());
            holder.time.setVisibility(View.GONE); holder.seat.setText(dashPOJO.getSeat() );
            holder.table_nmae.setText(dashPOJO.getTable_name());
            holder.id.setText(dashPOJO.getTable_id());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else if (holder.status.getText().toString().equals("Wait") && !holder.time.getText().toString().equals("0")) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.oranage));
            holder.time.setText(dashPOJO.getWait_time());holder.countText.setText(dashPOJO.getMember_count());

            holder.Timenew.setVisibility(View.GONE); holder.seat.setText(dashPOJO.getSeat());
            long millisInput = Long.parseLong(holder.time.getText().toString()) * 60000;
            /*mStartTimeInMillis = millisInput;
            int hours = (int) (mStartTimeInMillis / 1000) / 3600;
            int minutes = (int) ((mStartTimeInMillis / 1000) % 3600) / 60;
            int seconds = (int) (mStartTimeInMillis / 1000) % 60;
            String time=String.format(Locale.getDefault(),
                    "%02d:%02d:%02d", hours, minutes, seconds);*/
            holder.time.setVisibility(View.VISIBLE);
            holder.table_time.setText(dashPOJO.getTable_time());
            holder.table_nmae.setText(dashPOJO.getTable_name());
            holder.id.setText(dashPOJO.getTable_id());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

    }
    @Override
    public int getItemCount() {
        return dashPOJOS.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView status, time, id, table_time, table_nmae,Timenew,seat,countText;
        CardView cardView;
        public holder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.status);
            time = itemView.findViewById(R.id.Time);
            cardView = itemView.findViewById(R.id.cardview);
            id = itemView.findViewById(R.id.id);
            table_time = itemView.findViewById(R.id.table_time);
            table_nmae = itemView.findViewById(R.id.table_nmae);countText=itemView.findViewById(R.id.member_count);            Timenew=itemView.findViewById(R.id.Timenew); seat=itemView.findViewById(R.id.seat);
        }
    }
}
