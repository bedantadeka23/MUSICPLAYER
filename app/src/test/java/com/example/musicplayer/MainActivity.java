package com.example.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView MyListViewforsongs;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyListViewforsongs = (ListView) findViewById(R.id.ls);

        runtimepermision();

    }
        public void runtimepermision ()
        {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                                    display();
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();


        }
    public ArrayList<File> findsongs(File file)
    {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File singleFiles: files)
        {
            if(singleFiles.isDirectory()  && !singleFiles.isHidden())
            {
                arrayList.addAll(findsongs(singleFiles));
            }
            else
            {
                if(singleFiles.getName().endsWith(".mp3") ||
                singleFiles.getName().endsWith(".wav"))
                {
                   arrayList.add(singleFiles);

                }
            }
        }
        return arrayList;
    }


    void display()
    {
        final ArrayList<File> mysongs= findsongs(Environment.getExternalStorageDirectory());
        items=new String[mysongs.size()];

        for (int i = 0;i<mysongs.size();i++)
        {
            items[i] =mysongs.get(1).getName().toString().replace(".mp3","").replace("wav","");
        }
        ArrayAdapter<String> myAdapter =new ArrayAdapter<String>( this,android.R.layout.simple_list_item_1,items) ;
        MyListViewforsongs.setAdapter(myAdapter);

        MyListViewforsongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                String songname= MyListViewforsongs.getItemAtPosition(i).toString();
                startActivity(new Intent(getApplicationContext(),playerActivity.class)
                .putExtra("songs",mysongs).putExtra("songsname",songname));
            }
        });
    }
}
