package com.example.thomaslysaght.mytesttoolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2Activity extends Activity {
    Button b1,b2;
    TextView tv;
    EditText ed1;

    String data;
    private String file = "mydata";
    FileInputStream fin;
    loadSomeStuff lss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);

        ed1=(EditText)findViewById(R.id.editText);
        tv=(TextView)findViewById(R.id.textView2);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data=ed1.getText().toString();
                try {
                    //MODE_PRIVATE is the default mode (also can use 0)
                    //the created file can only be accessed by the calling application
                    FileOutputStream fOut = openFileOutput(file,MODE_PRIVATE);//MODE_WORLD_READABLE);
                    fOut.write(data.getBytes());
                    fOut.close();
                    Toast.makeText(getBaseContext(),"file saved",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
               // new loadSomeStuff().execute(file);

                try {
                    fin = openFileInput(file);
                    int c;
                    String temp="";
                    while( (c = fin.read()) != -1){
                        temp = temp + Character.toString((char)c);
                    }
                    tv.setText(temp);
                    Toast.makeText(getBaseContext(),"file read", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                } finally {
                    try {
                        fin.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }





















    public class loadSomeStuff extends AsyncTask<String, Integer, String> {

        ProgressDialog dialog;

        protected void onPreExecute() {
            // example of setting up something
            dialog = new ProgressDialog(Main2Activity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {                   //. . . Java varargs - let you pass any number of objects of a specific type
            String collected = "";
            FileInputStream fis = null;

            for(int i = 0; i < 20; i++){
                publishProgress(5);  //used to increment the progress bar
                try {
                    Thread.sleep(88);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
            try {
                fis = openFileInput(file);
                int c;

                while( (c = fis.read()) != -1){
                    collected = collected + Character.toString((char)c);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    return collected;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return collected;//null;
            // tv.setText(temp);
            // Toast.makeText(getBaseContext(),"file read", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {  //ellipses mean many of same type so array
            super.onProgressUpdate(values);

            dialog.incrementProgressBy(values[0]);            //this is the value 5 from publishProgress(5) above

            Log.d("prog "," "+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }
    }


}

