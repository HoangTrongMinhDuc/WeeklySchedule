package com.hoangtrongminhduc.dev.weeklyschedule;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.BrokenBarrierException;

public class MainActivity extends AppCompatActivity {
    TextView tvDate, tvTime;
    EditText edtCongviec, edtNoidung;
    Button btnDate, btnTime, btnAdd;
    ArrayList<JobInWeek>arrJob=new ArrayList<JobInWeek>();
    ArrayAdapter<JobInWeek>adapter=null;
    ListView lvCongviec;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate=(TextView)findViewById(R.id.tvDate);
        tvTime=(TextView)findViewById(R.id.tvTime);
        edtCongviec=(EditText)findViewById(R.id.edtCongviec);
        edtNoidung=(EditText)findViewById(R.id.edtNoidung);
        btnDate=(Button)findViewById(R.id.btnDate);
        btnTime=(Button)findViewById(R.id.btnTime);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        lvCongviec=(ListView)findViewById(R.id.lvCongviec);
        adapter=new ArrayAdapter<JobInWeek>(this,android.R.layout.simple_list_item_1,arrJob);
        lvCongviec.setAdapter(adapter);

        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        tvDate.setText(strDate);
        dft=new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime=dft.format(cal.getTime());
        tvTime.setText(strTime);
        dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
        tvTime.setTag(dft.format(cal.getTime()));
        edtCongviec.requestFocus();
        dateFinish=cal.getTime();
        hourFinish=cal.getTime();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCongviec.length()==0 || edtNoidung.length()==0){
                    Toast.makeText(MainActivity.this, "Mời bạn nhập đủ thông tin trước khi thêm vào danh sách!",Toast.LENGTH_LONG).show();
                }else {
                    String title=edtCongviec.getText()+"";
                    String description=edtNoidung.getText()+"";
                    JobInWeek job=new JobInWeek(title, description, dateFinish, hourFinish);
                    arrJob.add(job);
                    adapter.notifyDataSetChanged();
                    edtCongviec.setText("");
                    edtNoidung.setText("");
                    edtCongviec.requestFocus();
                }
            }
        });
        lvCongviec.setOnItemClickListener(new MyListViewEvent());
        lvCongviec.setOnItemLongClickListener(new MyListViewEvent());
    }
    private class MyListViewEvent implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this,"Đã xóa "+arrJob.get(position).getTitle(),Toast.LENGTH_LONG).show();
            arrJob.remove(position);
            adapter.notifyDataSetChanged();
            return false;
        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this,arrJob.get(position).getDesciption(),Toast.LENGTH_LONG).show();
        }
    }
    public void showDate(View view){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DATE);
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvDate.setText(year+"/"+(dayOfMonth+1)+"/"+month);
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(this, onDateSetListener, year, month, day);
        dialog.setTitle("Chọn giờ hoàn thành");
        dialog.show();
    }
    public void showTime(View view){
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR);
        int min=calendar.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tvTime.setText(hourOfDay+": "+minute);
            }
        };
        TimePickerDialog dialog= new TimePickerDialog(this, onTimeSetListener, hour, min, true);
        dialog.setTitle("Chọn giờ hoàn thành");
        dialog.show();
    }
}
