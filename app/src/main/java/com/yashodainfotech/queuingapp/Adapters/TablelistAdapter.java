package com.yashodainfotech.queuingapp.Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.R;

import java.util.List;

public class TablelistAdapter extends RecyclerView.Adapter<TablelistAdapter.TableClass> {
    Context context;
    List<DashPOJO> dashPOJOS;
    AlertDialog alertDialog;
    TextView table_id_text;
    EditText textView, seat, tabletime;
    String id;

    public TablelistAdapter(Context context, List<DashPOJO> dashPOJOS, EditText textView,
                            AlertDialog alertDialog, TextView table_id_text,
                            EditText seat, EditText tabletime,String id) {
        this.context = context;
        this.dashPOJOS = dashPOJOS;
        this.textView = textView;
        this.alertDialog = alertDialog;
        this.table_id_text = table_id_text;
        this.seat = seat;
        this.tabletime = tabletime;
        this.id=id;
    }

    @Override
    public TablelistAdapter.TableClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tablelistlayout, parent, false);
        return new TableClass(view);
    }

    @Override
    public void onBindViewHolder(final TablelistAdapter.TableClass holder, int position) {
        DashPOJO dashPOJO = dashPOJOS.get(position);
        holder.table_name.setText(dashPOJO.getTablenmaes());
        holder.table_id.setText(dashPOJO.getTableids());
        holder.table_seat.setText(dashPOJO.getSeat_web());
        holder.table_time.setText(dashPOJO.getTable_timeweb());
        holder.table_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(holder.table_name.getText().toString());
                table_id_text.setText(holder.table_id.getText().toString());
                id=table_id_text.getText().toString();
                seat.setText(holder.table_seat.getText().toString());
                tabletime.setText(holder.table_time.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashPOJOS.size();
    }

    public class TableClass extends RecyclerView.ViewHolder {
        TextView table_name, table_id, table_time, table_seat;

        public TableClass(View itemView) {
            super(itemView);
            table_name = itemView.findViewById(R.id.tablename);
            table_id = itemView.findViewById(R.id.tableid);
            table_time = itemView.findViewById(R.id.table_time);
            table_seat = itemView.findViewById(R.id.table_seat);
        }
    }
}
