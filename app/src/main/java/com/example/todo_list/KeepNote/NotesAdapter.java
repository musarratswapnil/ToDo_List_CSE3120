package com.example.todo_list.KeepNote;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyHolder>
{

    List<Listdata> noteslist;
    private Context context;
    public  NotesAdapter(List<Listdata> noteslist,Context context)
    {
        this.context=context;
        this.noteslist=noteslist;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item,viewGroup,false);

        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        Listdata data=noteslist.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.desc.setText(data.getDesc());
        int backgroundColor = getColorFromCode(data.getColor());
        myHolder.itemView.findViewById(R.id.note_bg).setBackgroundColor(backgroundColor);

    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,desc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Listdata listdata=noteslist.get(getAdapterPosition());
                    Intent i=new Intent(context, Edit.class);
                    i.putExtra("id",listdata.getId());
                    i.putExtra("title",listdata.getTitle());
                    i.putExtra("desc",listdata.getDesc());
                    i.putExtra("color",listdata.getColor());
                    context.startActivity(i);
                }
            });

        }


    }

    private int getColorFromCode(int color) {
        if (color==1) {
            return ContextCompat.getColor(context, R.color.custom_blue);
        } else if (color==2) {
            return ContextCompat.getColor(context, R.color.custom_green);
        } else if (color==3) {
            return ContextCompat.getColor(context, R.color.custom_pink);
        } else {
            return ContextCompat.getColor(context, R.color.custom_blue);
        }
    }

}
