package org.galebar.sogungyee.parser;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public abstract class JsonParser {

    public abstract HashMap<String, String> works(); //추상 메서드

    public abstract Object start(); //추상 메서드

    public final JSONObject getJsonObject(String mParseUrl) { //JSONObject 를 반환
        String mJsonString = ""; //Json 데이터가 들어갈 String
        JSONObject mJsonData = null; //Json 오브젝트
        InputStream mIn = null; //스트림
        InputStreamReader mReader = null; //스트림 리더
        BufferedReader mBFReader = null; //스트림 리더
        StrictMode.ThreadPolicy mPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //스테틱 생성
        StrictMode.setThreadPolicy(mPolicy); //스테틱 설정
        try {
            URL url = new URL(mParseUrl); //URL 객체 생성
            URLConnection con = url.openConnection(); //커넥션 오픈(웹서버 연결 확립, 실패할 경우 CATCH 로 이동)

            mIn = con.getInputStream(); //스트림 객체 얻기
            mReader = new InputStreamReader(mIn); //리더객체 생성 (바이퍼 스트림리더)
            mBFReader = new BufferedReader(mReader); //버퍼리더 객체 생성 (문자 스트림리더)
            StringBuffer sb = new StringBuffer(); //스트림 버퍼 객체 생성 (누적을 위한 버퍼 사용)

            while((mJsonString = mBFReader.readLine()) != null) sb.append(mJsonString); //줄을 읽을수 없을때까지 계속 스트링 버퍼에 축적

            mJsonString = sb.toString(); //모두 축적이 되면 스트링버퍼에 쌓인 문자열을 스트링화시켜 jsonString 에 저장

            mJsonData = new JSONObject(mJsonString); //jsonObject 객체 생성

        } catch (IOException ei) { Log.d("Connection Error", "Connection Error Cause : " + ei.getMessage()); }
        catch (JSONException ej) { Log.d("JsonParse Error", "JsonParse Error Cause : " + ej.getMessage()); }
        finally {
            try {
                mBFReader.close();
                mReader.close();
                mIn.close();
            } catch (Exception e) {}
        }

        return mJsonData;
    }




}
