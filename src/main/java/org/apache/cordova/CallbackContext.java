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

import org.json.JSONArray;
import org.json.JSONObject;

public interface CallbackContext {

    public boolean isFinished();

    public boolean isChangingThreads();

    public String getCallbackId();

    public void sendPluginResult(final PluginResult pluginResult);

    public void success(final JSONObject message);

    public void success(final String message);

    public void success(final JSONArray message);

    public void success(final byte[] message);

    public void success(final int message);

    public void success();

    public void error(final JSONObject message);

    public void error(final String message);

    public void error(final int message);
}