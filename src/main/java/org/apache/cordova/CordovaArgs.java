/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova;

import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CordovaArgs {

    private JSONArray baseArgs;

    public CordovaArgs(final JSONArray args) {
        this.baseArgs = args;
    }

    // Pass through the basics to the base args.
    public Object get(final int index) throws JSONException {
        return baseArgs.get(index);
    }

    public boolean getBoolean(final int index) throws JSONException {
        return baseArgs.getBoolean(index);
    }

    public double getDouble(final int index) throws JSONException {
        return baseArgs.getDouble(index);
    }

    public int getInt(final int index) throws JSONException {
        return baseArgs.getInt(index);
    }

    public JSONArray getJSONArray(final int index) throws JSONException {
        return baseArgs.getJSONArray(index);
    }

    public JSONObject getJSONObject(final int index) throws JSONException {
        return baseArgs.getJSONObject(index);
    }

    public long getLong(final int index) throws JSONException {
        return baseArgs.getLong(index);
    }

    public String getString(final int index) throws JSONException {
        return baseArgs.getString(index);
    }

    public Object opt(final int index) {
        return baseArgs.opt(index);
    }

    public boolean optBoolean(final int index) {
        return baseArgs.optBoolean(index);
    }

    public double optDouble(final int index) {
        return baseArgs.optDouble(index);
    }

    public int optInt(final int index) {
        return baseArgs.optInt(index);
    }

    public JSONArray optJSONArray(final int index) {
        return baseArgs.optJSONArray(index);
    }

    public JSONObject optJSONObject(final int index) {
        return baseArgs.optJSONObject(index);
    }

    public long optLong(final int index) {
        return baseArgs.optLong(index);
    }

    public String optString(final int index) {
        return baseArgs.optString(index);
    }

    public boolean isNull(final int index) {
        return baseArgs.isNull(index);
    }

    // The interesting custom helpers.
    public byte[] getArrayBuffer(final int index) throws JSONException {
        String encoded = baseArgs.getString(index);
        return Base64.getDecoder().decode(encoded);
    }
}
