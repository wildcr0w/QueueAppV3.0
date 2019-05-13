package com.yashodainfotech.queuingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yashodainfotech.queuingapp.POJOS.DashPOJO;
import com.yashodainfotech.queuingapp.R;

import java.util.List;

public class TablequeueAdapter extends RecyclerView.Adapter<TablequeueAdapter.TablequeueClass> {

    Context context;
    List<DashPOJO> dashPOJOS;

    public TablequeueAdapter(Context context, List<DashPOJO> dashPOJOS) {
        this.context = context;
        this.dashPOJOS = dashPOJOS;
    }

    @Override
    public TablequeueAdapter.TablequeueClass onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.table_queue_layout, parent, false);
        return new TablequeueClass(v);
    }

    @Override
    public void onBindViewHolder(TablequeueAdapter.TablequeueClass holder, int position) {
        DashPOJO dashPOJO = dashPOJOS.get(position);
        holder.check.setText(dashPOJO.getCount());
        /*if(holder.check.getText().toString().equals("1")){
            holder.textView.setText(dashPOJO.getCusname());
        }else if(holder.check.getText().toString().equals("2")){
            holder.text1.setText(dashPOJO.getCusname());
        }else if(holder.check.getText().toString().equals("3")){
            holder.text2.setText(dashPOJO.getCusname());
        }else if(holder.check.getText().toString().equals("4")){
            holder.text3.setText(dashPOJO.getCusname());
        }*/
        if(dashPOJO.getCusname()!=null)
        holder.textView.setText(dashPOJO.getCusname());
        if(dashPOJO.getCusname1()!=null)
            holder.text1.setText(dashPOJO.getCusname1());
        if(dashPOJO.getCusname2()!=null)
            holder.text2.setText(dashPOJO.getCusname2());
        if(dashPOJO.getCusname3()!=null)
            holder.text3.setText(dashPOJO.getCusname3());




    }

    @Override
    public int getItemCount() {
        return dashPOJOS.size();
    }

    public class TablequeueClass extends RecyclerView.ViewHolder {
        TextView textView,text1,text2,text3,text4,text5,check;
        CardView cardView;

        public TablequeueClass(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
            text4 = itemView.findViewById(R.id.text44);
            cardView = itemView.findViewById(R.id.card_view);
            check=itemView.findViewById(R.id.check);
        }
    }
}
