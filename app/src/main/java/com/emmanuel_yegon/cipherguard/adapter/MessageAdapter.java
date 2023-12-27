package com.emmanuel_yegon.cipherguard.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emmanuel_yegon.cipherguard.Model.Message;
import com.emmanuel_yegon.cipherguard.R;
import com.emmanuel_yegon.cipherguard.database.RoomDB;
import com.emmanuel_yegon.cipherguard.databinding.ItemMessageBinding;
import com.emmanuel_yegon.cipherguard.utility.ETUtility;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.myViewHolder> {

    ItemMessageBinding binding;
    List<Message> messageList;
    LayoutInflater inflater;
    RoomDB database;

    public MessageAdapter(List<Message> messageList, Context context, RoomDB database) {
        this.messageList = messageList;
        this.inflater = LayoutInflater.from(context);
        this.database = database;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_message,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.txtPlain.setText(messageList.get(position).getPlain_text());
        holder.txtEncrypted.setText(messageList.get(position).getEncrypted_text());
        holder.txtDate.setText(messageList.get(position).getCreation_time().toString());

        holder.btnCopyToClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ETUtility.setClipboard(inflater.getContext(),messageList.get(position).getEncrypted_text());
                Toast.makeText(inflater.getContext(), "Copied to clipboard",Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(inflater.getContext())
                        .setTitle("Delete !!")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                database.mainDao().delete(messageList.get(position));
                                messageList.remove(messageList.get(position));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(R.drawable.ic_delete)
                        .show();
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,messageList.get(position).getEncrypted_text());
                intent.setType("text/plain");

                if (intent.resolveActivity(inflater.getContext().getPackageManager()) != null){
                    inflater.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView txtPlain, txtEncrypted, txtDate;
        ImageButton btnShare, btnCopyToClipboard, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPlain = itemView.findViewById(R.id.txtPlainText);
            txtEncrypted = itemView.findViewById(R.id.txtEncryptedText);
            txtDate = itemView.findViewById(R.id.txtCreationTime);

            btnShare = itemView.findViewById(R.id.btnShare);
            btnCopyToClipboard = itemView.findViewById(R.id.btnCopyToClipboard);
            btnDelete = itemView.findViewById(R.id.btnDelete);


        }
    }
}
