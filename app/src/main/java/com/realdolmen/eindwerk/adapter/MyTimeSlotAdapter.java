package com.realdolmen.eindwerk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.realdolmen.eindwerk.Interface.IRecycleritemSelectedListener;
import com.realdolmen.eindwerk.R;
import com.realdolmen.eindwerk.data.Common;
import com.realdolmen.eindwerk.data.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {

    Context context;
    List<TimeSlot> timeSlotList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyTimeSlotAdapter(Context context) {
        this.context = context;
        this.timeSlotList = new ArrayList<>();
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
    }

    public MyTimeSlotAdapter(Context context, List<TimeSlot> timeSlotList) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_time_slot, viewGroup,  false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.txt_time_slot.setText(new StringBuilder(Common.convertTimeSlotToString(i)).toString());

        if (timeSlotList.size() == 0)
        {
            myViewHolder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

            myViewHolder.txt_time_slot_description.setText("Available");
            myViewHolder.txt_time_slot_description.setTextColor(context.getResources()
                    .getColor(android.R.color.black));
            myViewHolder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.black));


        }
        else
        {
            for (TimeSlot slotValue: timeSlotList)
            {
                int slot = Integer.parseInt(slotValue.getSlot().toString());
                if (slot  == i)
                {
                    myViewHolder.card_time_slot.setTag(Common.DISABLE_TAG);
                    myViewHolder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));

                    myViewHolder.txt_time_slot_description.setText("Full");
                    myViewHolder.txt_time_slot_description.setTextColor(context.getResources()
                            .getColor(android.R.color.white));
                    myViewHolder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.white));
                }
            }
        }

        //voeg alle card toe aan de lijst
        if (!cardViewList.contains(myViewHolder.card_time_slot))
            cardViewList.add(myViewHolder.card_time_slot);

        //kijken of de card time slot beschikbaar is
        myViewHolder.setiRecycleritemSelectedListener(new IRecycleritemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                for (CardView cardView:cardViewList)
                {
                    if (cardView.getTag() == null)
                        cardView.setCardBackgroundColor(context.getResources()
                                .getColor(android.R.color.white));
                }
                //geselecteerde kaart veranderd van kleur
                myViewHolder.card_time_slot.setCardBackgroundColor(context.getResources()
                        .getColor(android.R.color.holo_orange_dark));

                //enable de button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_TIME_SLOT,i);
                intent.putExtra(Common.KEY_STEP, 3);
                localBroadcastManager.sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Common.TIME_SLOT_TOTAL;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_time_slot,txt_time_slot_description;
        CardView card_time_slot;

        IRecycleritemSelectedListener iRecycleritemSelectedListener;

        public void setiRecycleritemSelectedListener(IRecycleritemSelectedListener iRecycleritemSelectedListener) {
            this.iRecycleritemSelectedListener = iRecycleritemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_time_slot = (CardView)itemView.findViewById(R.id.card_time_slot);
            txt_time_slot = (TextView)itemView.findViewById(R.id.txt_time_slot);
            txt_time_slot_description = (TextView)itemView.findViewById(R.id.txt_time_slot_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecycleritemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}
