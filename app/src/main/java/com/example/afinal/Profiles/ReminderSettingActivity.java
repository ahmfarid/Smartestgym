package com.example.afinal.Profiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.afinal.R;
import com.example.afinal.dbHelper;

import java.util.ArrayList;

public class ReminderSettingActivity extends AppCompatActivity {

    dbHelper dbHelper;
    ArrayAdapter<String>madapter;
    ListView fTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);
        dbHelper=new dbHelper(this);
        fTask=findViewById(R.id.fTask);
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList =dbHelper.getTaskList();
        if(madapter==null){
            madapter=new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            fTask.setAdapter(madapter);
        }
        else {
            madapter.clear();
            madapter.addAll(taskList);
            madapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        Drawable icon=menu.getItem(0).getIcon();
        icon.mutate();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addTaskAction:
                final EditText taskEdittext=new EditText(this);
                AlertDialog dialog= new AlertDialog.Builder(this)
                        .setTitle("add New Task").setMessage("What do you want to do next ?")
                        .setView(taskEdittext).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task=String.valueOf(taskEdittext.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("cancel",null) .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteTask(View view){
        View parent= (View) view.getParent();
        TextView taskTextView=(TextView)findViewById(R.id.task_title);
        String task=String.valueOf(taskTextView);
        dbHelper.deleteTask(task);
        loadTaskList();
    }
}
