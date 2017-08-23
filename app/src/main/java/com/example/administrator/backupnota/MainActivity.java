package com.example.administrator.backupnota;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainActivity extends AppCompatActivity {

    private Uri UrlGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "BackupNota");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            //Toast.makeText(MainActivity.this, "Folder telah berhasil dibuat", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(MainActivity.this, "Gagal membuat folder", Toast.LENGTH_SHORT).show();
        }

        Button ambil = (Button) findViewById(R.id.ambil);
        ambil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String random = String.valueOf(System.currentTimeMillis());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(),
                        "BackupNota/foto_nota_" + random + ".jpg");
                UrlGambar = Uri.fromFile(file);

                try {
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, UrlGambar);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, CAMERA);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button galeri = (Button) findViewById(R.id.lihat);
        galeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "content://media/internal/images/media"));
                startActivity(intent);
            }
        });

        ImageButton info = (ImageButton) findViewById(R.id.informasi);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Info.class);
                startActivityForResult(i,0);
            }
        });

        ImageButton as = (ImageButton) findViewById(R.id.assalaam);
        as.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Assalaam.class);
                startActivityForResult(i,0);
            }
        });
    }

    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Apakah anda benar-benar ingin keluar ? ")
                .setTitle("Backup Nota")
                .setCancelable(false)
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int id)
                    {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        MainActivity.this.finish();
                    }
                }).show();
    }
}