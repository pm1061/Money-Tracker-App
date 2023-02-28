package com.pedromalavet.pedrosmoneytracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.pedromalavet.pedrosmoneytracker.R;

public class EditDialog extends AppCompatDialogFragment
{

    private EditText editName;
    private EditText editMoney;
    private EditDialogListener editDialogListener;
    int pos;
    String currentName;
    double currentVal;

    public EditDialog(int pos,String currentName,double currentVal)
    {
        this.pos = pos;
        this.currentName = currentName;
        this.currentVal = currentVal;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog,null);

        editName = view.findViewById(R.id.editName);
        editMoney = view.findViewById(R.id.editMoney);

        editName.setText(currentName);
        editMoney.setText(Double.toString(currentVal));

        builder.setView(view).setTitle("Edit Source")
                .setPositiveButton("Change", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String name = editName.getText().toString();
                        double money = Double.parseDouble(editMoney.getText().toString());
                        editDialogListener.applyData(name,money,pos);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });



        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            editDialogListener = (EditDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement edit listener");
        }
    }


    public interface EditDialogListener
    {
        void applyData(String name, double value,int pos);
    }

}
