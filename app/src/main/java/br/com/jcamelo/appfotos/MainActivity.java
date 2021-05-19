package br.com.jcamelo.appfotos;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.List;

import br.com.jcamelo.appfotos.database.OrdemServico;
import br.com.jcamelo.appfotos.view.UserFragment;
import io.realm.OrderedCollectionChangeSet;

public class MainActivity extends AppCompatActivity {

    private UserFragment userFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onInject();
    }

    private void onInject() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        userFragment = new UserFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main_frame_layout, userFragment).commit();

        createFolder();
        requestPermissionCamera();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        int positionOfMenuItem = 0;
        MenuItem item = menu.getItem(positionOfMenuItem);
        SpannableString s = new SpannableString("SAIR");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        item.setTitle(s);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sair){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void createFolder() {
        String[] folders = getResources().getStringArray(R.array.folder);
        for (int i = 0; i < folders.length; i++) {
            File file = new File(Environment.getExternalStorageState(), String.valueOf(getExternalFilesDir(folders[i])));
            if (file.exists()) {
            } else {
                file.mkdir();
            }
        }
    }

    private void requestPermissionCamera(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA}, 1);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}