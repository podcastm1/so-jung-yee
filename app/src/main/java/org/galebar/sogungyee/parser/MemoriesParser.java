package org.galebar.sogungyee.parser;

import android.util.Log;

import org.galebar.sogungyee.Memory;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Code on 2014-12-18.
 * OTPMaker JsonParser를 상속해서 제작.
 */

public class MemoriesParser extends JsonParser {

    @Override
    public HashMap<String, String> works() {
        return null;
    }

    @Override
    public Memory start() {
        JSONObject jb = getJsonObject("http://123.143.18.210:82/memory/login.php");

        Memory mom = new Memory();


        return mom;
    }
}
