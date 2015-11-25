package org.galebar.sogungyee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
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
import java.util.List;

import butterknife.InjectViews;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    private PreferenceHelper ph;

    private ImageView imgBackground;
    private TextView txtRegister, txtForgetPw;
    private EditText edtEmail, edtPassword, edtName, edtCellPhone;
    private Button btnClick;

    private boolean isRegister;

    private ProgressDialog pd;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ph = new PreferenceHelper("id", this);
        pd = new ProgressDialog(this);
        mHandler = new Handler();

        imgBackground = (ImageView)findViewById(R.id.background);
        imgBackground.setImageBitmap(resizeBitmapImageFn(((BitmapDrawable) getResources().getDrawable(R.drawable.wallpaper)).getBitmap(), getWindowManager().getDefaultDisplay().getHeight()));

        txtRegister = (TextView)findViewById(R.id.txt_register);
        txtForgetPw = (TextView)findViewById(R.id.txt_forgetpw);

        txtRegister.setOnClickListener(this);

        edtEmail = (EditText)findViewById(R.id.edt_email);
        edtPassword = (EditText)findViewById(R.id.edt_password);
        edtCellPhone = (EditText)findViewById(R.id.edt_cellphone);
        edtName = (EditText)findViewById(R.id.edt_name);

        btnClick = (Button)findViewById(R.id.btn_click);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public Bitmap resizeBitmapImageFn(Bitmap bmpSource, int maxResolution){
        int rWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int rHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = rWidth ;
        int newHeight = rHeight ;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(rWidth > rHeight ){
            if(maxResolution < rWidth ){
                rate = maxResolution / (float) rWidth ;
                newHeight = (int) (rHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < rHeight ){
                rate = maxResolution / (float) rHeight ;
                newWidth = (int) (rWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }

    public void openActivity(View v) {
        final String email, password, realname, phone;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();
        realname = edtName.getText().toString();
        phone = edtCellPhone.getText().toString();

        if(isRegister) {

            if(email.equals("") || password.equals("") || realname.equals("") || phone.equals("")) {
                Toast.makeText(this, "빈칸을 체워주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            (new Thread() {
                @Override
                public void run() {
                    super.run();
                    // Create a new HttpClient and Post Header
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://123.143.18.210:82/memory/register.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("email", email));
                        nameValuePairs.add(new BasicNameValuePair("phone", phone));
                        nameValuePairs.add(new BasicNameValuePair("realname", realname));
                        nameValuePairs.add(new BasicNameValuePair("password", password));

                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                        BasicResponseHandler responseHandler = new BasicResponseHandler();
                        httpPost.setEntity(entity);
                        final String htmlBody = httpClient.execute(httpPost, responseHandler);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ph.getEditor().putString("id_hash", htmlBody);
                                ph.save();
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (ClientProtocolException e) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "인터넷 상태가 불안정 합니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "인터넷 상태가 불안정 합니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            }).start();
        } else {
            if(email.equals("") || password.equals("")) {
                Toast.makeText(this, "빈칸을 체워주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            (new Thread() {
                @Override
                public void run() {
                    super.run();
                    // Create a new HttpClient and Post Header
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://123.143.18.210:82/memory/login.php");

                    try {
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("email", email));
                        nameValuePairs.add(new BasicNameValuePair("password", password));

                        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                        BasicResponseHandler responseHandler = new BasicResponseHandler();
                        httpPost.setEntity(entity);
                        final String htmlBody = httpClient.execute(httpPost, responseHandler);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ph.getEditor().putString("id_hash", htmlBody);
                                ph.save();
                                pd.dismiss();
                            }
                        });
                    } catch (ClientProtocolException e) {

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "인터넷 상태가 불안정 합니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, "인터넷 상태가 불안정 합니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            }).start();
        }

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_register :
                if(isRegister) {
                    onKeyUp(KeyEvent.KEYCODE_BACK, null);
                    return;
                }
                isRegister = true;
                btnClick.setText("회원가입");
                edtName.setVisibility(View.VISIBLE);
                edtCellPhone.setVisibility(View.VISIBLE);
                txtRegister.setText("로그인");
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && isRegister) {
            isRegister = false;
            btnClick.setText("로그인");
            edtName.setVisibility(View.GONE);
            txtRegister.setText("회원가입");
            edtCellPhone.setVisibility(View.GONE);
            return false;
        }

        return super.onKeyUp(keyCode, event);
    }
}
