package com.example.db01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etName, etTel, etEmail, etSearch, etDel;
    Button btnSave, btnSearch, btnUpdate, btnDelete;
    String name, tel, email, key;
    Handler handler = new Handler();
    static View[] TAB_ITEM = new View[3];
    ListView listView;
    CustomAdapter adapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvItem);
        listView.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tbItem);
        TAB_ITEM[0] = findViewById(R.id.item1);
        TAB_ITEM[1] = findViewById(R.id.item2);
        TAB_ITEM[2] = findViewById(R.id.item3);

        for(View item : TAB_ITEM)
            item.setVisibility(View.GONE);
        TAB_ITEM[0].setVisibility(View.VISIBLE);

        etName = (EditText) findViewById(R.id.etName);
        etTel = (EditText) findViewById(R.id.etTel);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etDel = (EditText) findViewById(R.id.etDelete);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etDel.getText().toString();
                delete();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                tel = etTel.getText().toString();
                email = etEmail.getText().toString();
                update();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
                key = etSearch.getText().toString();
                Toast.makeText(MainActivity.this, "search.", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                tel = etTel.getText().toString();
                email = etEmail.getText().toString();
                dataInsert();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for(View i : TAB_ITEM)
                    i.setVisibility(View.GONE);
                TAB_ITEM[tab.getPosition()].setVisibility(View.VISIBLE);
                if(tab.getPosition() == 2) {
                    dataLoad();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void delete() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL setURL = new URL("http://10.0.2.2/delete.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) setURL.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("name").append("=").append(name);
                    OutputStreamWriter
                            outStream = new OutputStreamWriter(http.getOutputStream(), "euc-kr");
                    outStream.write(buffer.toString());
                    outStream.flush();
                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "euc-kr");
                    final BufferedReader reader = new BufferedReader(tmp);
                    while(reader.readLine() != null) {
                        System.out.println(reader.readLine());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, name + " deleted.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e) {
                    Log.e("","Error", e);
                }
            }
        }.start();
    }

    public void update() {
        new Thread() {
            @Override
            public void run() {
                try{
                    URL setURL = new URL("http://10.0.2.2/update.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) setURL.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("name").append("=").append(name).append("/").append(tel).append("/").append(email).append("/");
                    OutputStreamWriter
                            outStream = new OutputStreamWriter(http.getOutputStream(), "euc-kr");
                    outStream.write(buffer.toString());
                    outStream.flush();
                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "euc-kr");
                    final BufferedReader reader = new BufferedReader(tmp);
                    while(reader.readLine() != null) {
                        System.out.println(reader.readLine());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, name + " updated.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.e("", "Error", e);
                }
            }
        }.start();
    }

    public void dataInsert() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL setURL = new URL("http://10.0.2.2/insert.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) setURL.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("name").append("=").append(name).append("/").append(tel).append("/").append(email).append("/");
                    OutputStreamWriter
                            outStream = new OutputStreamWriter(http.getOutputStream(), "euc-kr");
                    outStream.write(buffer.toString());
                    outStream.flush();
                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "euc-kr");
                    final BufferedReader reader = new BufferedReader(tmp);
                    while(reader.readLine() != null) {
                        System.out.println(reader.readLine());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, name + " saved.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch (Exception e) {
                    Log.e("", "에러", e);
                }
            }
        }.start();
    }

    public void dataLoad() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2/dataLoad.php");
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    OutputStreamWriter outputStream = new OutputStreamWriter(http.getOutputStream(), "utf-8");
                    outputStream.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String str;
                    while((str = reader.readLine()) != null) {
                        builder.append(str+"\n");
                    }
                    String resultData = builder.toString();
                    final String[] sResult = resultData.split("\n");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clearItem();
                            try {
                                for (String i : sResult) {
                                    String[] item = i.split("/");
                                    adapter.addItem(item[0], item[1], item[2], item[3]);
                                }
                            } catch (Exception e) {
                                adapter.addItem("null", "null", "null", "null");
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.e("", "Error: ", e);
                }
            }
        }.start();
        Toast.makeText(MainActivity.this, "load.", Toast.LENGTH_SHORT).show();
    }

    public void search() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2/selectSearch.php");
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("name").append("=").append(key);

                    OutputStreamWriter outputStream = new OutputStreamWriter(http.getOutputStream(), "utf-8");
                    outputStream.write(buffer.toString());
                    outputStream.flush();

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    String str;
                    while((str = reader.readLine()) != null) {
                        builder.append(str+"\n");
                    }
                    String resultData = builder.toString();
                    final String[] sResult = resultData.split("\n");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clearItem();
                            try {
                                for (String i : sResult) {
                                    String[] item = i.split("/");
                                    adapter.addItem(item[0], item[1], item[2], item[3]);
                                }
                            } catch (Exception e) {
                                adapter.addItem("null", "null", "null", "null");
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    Log.e("", "Error", e);
                }
            }
        }.start();
    }
}