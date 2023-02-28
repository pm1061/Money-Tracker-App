package com.pedromalavet.pedrosmoneytracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements recyclerApdater.OnSourceListener,EditDialog.EditDialogListener
{

    private ArrayList<MoneySource> sList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.rView);
        load();
        calcTotal();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addSourceInfo();
            }
        });
    }

    private void save()
    {
        SharedPreferences sp =getSharedPreferences("myData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sList);
        editor.putString("data",json);
        editor.apply();

    }

    private void load()
    {
        SharedPreferences sp =getSharedPreferences("myData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("data",null);
        Type type = new TypeToken<ArrayList<MoneySource>>(){}.getType();
        sList = gson.fromJson(json,type);

        if(sList == null)
        {
            sList = new ArrayList<>();
            save();
        }

    }

    private void setApdater()
    {
        recyclerApdater apdater = new recyclerApdater(sList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(apdater);
    }

    private void addSourceInfo()
    {
        sList.add(new MoneySource());
        save();
        setApdater();
    }

    private void removeSourceInfo(int pos)
    {
        sList.remove(pos);
        save();
        setApdater();
    }

    private void calcTotal()
    {
        double total = 0.0;
        TextView totalTV = findViewById(R.id.TotalTextView);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();


        for(int i = 0; i < sList.size(); i++)
        {
            total += sList.get(i).getMoney();
        }
        String totalString = formatter.format(total);

        totalTV.setText("Total: " + totalString);
        setApdater();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSourceClick(int pos)
    {
        EditDialog editDialog = new EditDialog(pos,sList.get(pos).getName(),sList.get(pos).getMoney());
        editDialog.show(getSupportFragmentManager(),"example dialog");
    }

    @Override
    public boolean onSourceLongClick(int pos)
    {
        final int I = pos;
        //Toast.makeText(this,"Long Clicked at: " + pos,Toast.LENGTH_SHORT).show();

        final AlertDialog dialog = new AlertDialog.Builder(this)
            .setTitle("Delete Source")
            .setMessage("Will you like to delete this source?")
            .setPositiveButton("yes",null)
            .setNegativeButton("No",null)
            .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(MainActivity.this,"Yes Clicked",Toast.LENGTH_SHORT).show();
                removeSourceInfo(I);
                calcTotal();
                dialog.dismiss();
            }
        });

        return true;
    }


    @Override
    public void applyData(String name, double money,int pos)
    {
        sList.get(pos).setName(name);
        sList.get(pos).setMoney(money);
        save();
        calcTotal();
    }
}
