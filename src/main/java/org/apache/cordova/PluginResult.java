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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PluginResult {
    private final int status;
    private final int messageType;
    private boolean keepCallback = false;
    private String strMessage;
    private String encodedMessage;
    private List<PluginResult> multipartMessages;

    public PluginResult(final Status status) {
        this(status, PluginResult.StatusMessages[status.ordinal()]);
    }

    public PluginResult(final Status status, final String message) {
        this.status = status.ordinal();
        this.messageType = message == null ? MESSAGE_TYPE_NULL : MESSAGE_TYPE_STRING;
        this.strMessage = message;
    }

    public PluginResult(final Status status, final JSONArray message) {
        this.status = status.ordinal();
        this.messageType = MESSAGE_TYPE_JSON;
        encodedMessage = message.toString();
    }

    public PluginResult(final Status status, final JSONObject message) {
        this.status = status.ordinal();
        this.messageType = MESSAGE_TYPE_JSON;
        encodedMessage = message.toString();
    }

    public PluginResult(final Status status, final int i) {
        this.status = status.ordinal();
        this.messageType = MESSAGE_TYPE_NUMBER;
        this.encodedMessage = ""+i;
    }

    public PluginResult(final Status status, final float f) {
        this.status = status.ordinal();
        this.messageType = MESSAGE_TYPE_NUMBER;
        this.encodedMessage = ""+f;
    }

    public PluginResult(final Status status, final boolean b) {
        this.status = status.ordinal();
        this.messageType = MESSAGE_TYPE_BOOLEAN;
        this.encodedMessage = Boolean.toString(b);
    }

    public PluginResult(final Status status, final byte[] data) {
        this(status, data, false);
    }

    public PluginResult(final Status status, final byte[] data, final boolean binaryString) {
        this.status = status.ordinal();
        this.messageType = binaryString ? MESSAGE_TYPE_BINARYSTRING : MESSAGE_TYPE_ARRAYBUFFER;
        this.encodedMessage = Base64.getEncoder().encodeToString(data);
    }

    // The keepCallback and status of multipartMessages are ignored.
    public PluginResult(final Status status, final List<PluginResult> multipartMessages) {
        this.status = status.ordinal();
        this.messageType = MESSAGE_TYPE_MULTIPART;
        this.multipartMessages = multipartMessages;
    }

    public void setKeepCallback(final boolean b) {
        this.keepCallback = b;
    }

    public int getStatus() {
        return status;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getMessage() {
        if (encodedMessage == null) {
            encodedMessage = JSONObject.quote(strMessage);
        }
        return encodedMessage;
    }

    public int getMultipartMessagesSize() {
        return multipartMessages.size();
    }

    public PluginResult getMultipartMessage(final int index) {
        return multipartMessages.get(index);
    }

    /**
     * If messageType == MESSAGE_TYPE_STRING, then returns the message string.
     * Otherwise, returns null.
     */
    public String getStrMessage() {
        return strMessage;
    }

    public boolean getKeepCallback() {
        return this.keepCallback;
    }

    @Deprecated // Use sendPluginResult instead of sendJavascript.
    public String getJSONString() {
        return "{\"status\":" + this.status + ",\"message\":" + this.getMessage() + ",\"keepCallback\":" + this.keepCallback + "}";
    }

    @Deprecated // Use sendPluginResult instead of sendJavascript.
    public String toCallbackString(final String callbackId) {
        // If no result to be sent and keeping callback, then no need to sent back to JavaScript
        if ((status == PluginResult.Status.NO_RESULT.ordinal()) && keepCallback) {
            return null;
        }

        // Check the success (OK, NO_RESULT & !KEEP_CALLBACK)
        if ((status == PluginResult.Status.OK.ordinal()) || (status == PluginResult.Status.NO_RESULT.ordinal())) {
            return toSuccessCallbackString(callbackId);
        }

        return toErrorCallbackString(callbackId);
    }

    @Deprecated // Use sendPluginResult instead of sendJavascript.
    public String toSuccessCallbackString(final String callbackId) {
        return "cordova.callbackSuccess('"+callbackId+"',"+this.getJSONString()+");";
    }

    @Deprecated // Use sendPluginResult instead of sendJavascript.
    public String toErrorCallbackString(final String callbackId) {
        return "cordova.callbackError('"+callbackId+"', " + this.getJSONString()+ ");";
    }

    public static final int MESSAGE_TYPE_STRING = 1;
    public static final int MESSAGE_TYPE_JSON = 2;
    public static final int MESSAGE_TYPE_NUMBER = 3;
    public static final int MESSAGE_TYPE_BOOLEAN = 4;
    public static final int MESSAGE_TYPE_NULL = 5;
    public static final int MESSAGE_TYPE_ARRAYBUFFER = 6;
    // Use BINARYSTRING when your string may contain null characters.
    // This is required to work around a bug in the platform :(.
    public static final int MESSAGE_TYPE_BINARYSTRING = 7;
    public static final int MESSAGE_TYPE_MULTIPART = 8;

    public static String[] StatusMessages = new String[] {
        "No result",
        "OK",
        "Class not found",
        "Illegal access",
        "Instantiation error",
        "Malformed url",
        "IO error",
        "Invalid action",
        "JSON error",
        "Error"
    };

    public enum Status {
        NO_RESULT,
        OK,
        CLASS_NOT_FOUND_EXCEPTION,
        ILLEGAL_ACCESS_EXCEPTION,
        INSTANTIATION_EXCEPTION,
        MALFORMED_URL_EXCEPTION,
        IO_EXCEPTION,
        INVALID_ACTION,
        JSON_EXCEPTION,
        ERROR
    }
}