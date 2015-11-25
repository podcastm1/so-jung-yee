package org.galebar.sogungyee;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.galebar.sogungyee.util.PreferenceHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    private ArrayList<Wallpaper> walls = new ArrayList<Wallpaper>();

    private PreferenceHelper ph;
    private String userID;
    @InjectView(R.id.drawer_name)
    protected TextView txtName;
    @InjectView(R.id.drawer_email)
    protected TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ph = new PreferenceHelper("id", this);
        userID = ph.getPref().getString("id_hash", "null");

     /*   if (userID.equals("null")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } */

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);

        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);

        for (int i = 0; i < 5; i++) {
            walls.add(new Wallpaper("사진 하나", "사진작가", ((BitmapDrawable) getResources().getDrawable(R.drawable.wal_1)).getBitmap()));
            walls.add(new Wallpaper("사진 두울", "사진작가", ((BitmapDrawable) getResources().getDrawable(R.drawable.wal_2)).getBitmap()));
            walls.add(new Wallpaper("사진 세엣", "사진작가", ((BitmapDrawable) getResources().getDrawable(R.drawable.wal_3)).getBitmap()));
            walls.add(new Wallpaper("사진 네엣", "사진작가", ((BitmapDrawable) getResources().getDrawable(R.drawable.wal_4)).getBitmap()));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridView lvTest = (GridView) findViewById(R.id.lvItems);
        lvTest.setAdapter(new WallListAdapter(this, walls));


        String parseData = ""; //Json 데이터가 들어갈 String
        JSONObject mJsonData = null; //Json 오브젝트
        InputStream mIn = null; //스트림
        InputStreamReader mReader = null; //스트림 리더
        BufferedReader mBFReader = null; //스트림 리더
        StrictMode.ThreadPolicy mPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //스테틱 생성
        StrictMode.setThreadPolicy(mPolicy); //스테틱 설정
        try {
            URL url = new URL("http://123.143.18.210:82/memory/userinfo.php?userid=" + userID); //URL 객체 생성
            URLConnection con = url.openConnection(); //커넥션 오픈(웹서버 연결 확립, 실패할 경우 CATCH 로 이동)

            mIn = con.getInputStream(); //스트림 객체 얻기
            mReader = new InputStreamReader(mIn); //리더객체 생성 (바이퍼 스트림리더)
            mBFReader = new BufferedReader(mReader); //버퍼리더 객체 생성 (문자 스트림리더)
            StringBuffer sb = new StringBuffer(); //스트림 버퍼 객체 생성 (누적을 위한 버퍼 사용)

            while((parseData = mBFReader.readLine()) != null) sb.append(parseData); //줄을 읽을수 없을때까지 계속 스트링 버퍼에 축적

            parseData = sb.toString(); //모두 축적이 되면 스트링버퍼에 쌓인 문자열을 스트링화시켜 jsonString 에 저장

        } catch (IOException ei) { Log.d("Connection Error", "Connection Error Cause : " + ei.getMessage()); }
        finally {
            try {
                mBFReader.close();
                mReader.close();
                mIn.close();
            } catch (Exception e) {}
        }


    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

// Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
