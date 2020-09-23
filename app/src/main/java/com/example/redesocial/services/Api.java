package com.example.redesocial.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.redesocial.LoginActivity;
import com.example.redesocial.MainActivity;
import com.example.redesocial.Utils.Const;
import com.example.redesocial.Utils.Holder;
import com.example.redesocial.Utils.Streams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Api {

    Context context;
    AsyncResponse response;

    public Api(Context context) {
        this.context = context;
        this.response = null;
    }

    public Api(Context context, AsyncResponse response) {
        this.context = context;
        this.response = response;
    }

    public void postLogin(String login, String password, String token) {
        final String apiUrl = Const.apiUrl("login.php");

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "POST");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("senha", strings[1]);
                http.addParam("appid", strings[2]);

                try {
                    InputStream is = http.execute();
                    String result = Streams.inputStream2String(is);
                    http.finish();

                    JSONObject json = new JSONObject(result);
                    json.put("login", strings[0]);
                    return json;
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject json) {
                try {
                    http.handleResult(context, json);
                    int status = json.getInt("status");
                    if(status == Const.SUCCESS) {
                        String login = json.getString("login");
                        String token = json.getString("token");
                        if(response != null){
                            response.authenticate(login, token);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(login, password, token);
    }

    public void postPost(String login, String token, String text, String imgPath) {
        final String apiUrl = Const.apiUrl("post.php");

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "POST");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("token", strings[1]);
                http.addParam("texto", strings[2]);
                if(strings[3] != null){
                    http.addFile("foto", new File(strings[3]));
                }

                try {
                    InputStream is = http.execute();
                    String result = Streams.inputStream2String(is);
                    http.finish();

                    return new JSONObject(result);
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject json) {
                try {
                    http.handleResult(context, json);
                    int status = json.getInt("status");
                    if(status == Const.SUCCESS) {
                        String message = "Post de texto criado com sucesso!";
                        if(!json.getString("message").isEmpty()) {
                            message = json.getString("message");
                        }
                        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
                        toast.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(login, token, text, imgPath);
    }

    public JSONObject getFollowing(String login, String token) {
        final String apiUrl = Const.apiUrl("pegar_seguindo.php");
        final Holder<JSONObject> response = new Holder();

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "GET");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("token", strings[1]);

                try {
                    InputStream is = http.execute();
                    String result = Streams.inputStream2String(is);
                    http.finish();

                    return new JSONObject(result);
                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(JSONObject json) {
                try {
                    http.handleResult(context, json);
                    int status = json.getInt("status");
                    if(status == Const.SUCCESS) {
                        response.set(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(login, token);

        return response.get();
    }
}
