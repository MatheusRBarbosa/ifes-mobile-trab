package com.example.redesocial.services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.redesocial.Utils.Const;
import com.example.redesocial.Utils.DateHandler;
import com.example.redesocial.Utils.Holder;
import com.example.redesocial.Utils.Streams;
import com.example.redesocial.interfaces.AsyncResponse;
import com.example.redesocial.interfaces.AsyncList;
import com.example.redesocial.models.Comment;
import com.example.redesocial.models.Post;
import com.example.redesocial.models.PostImage;
import com.example.redesocial.models.PostText;
import com.example.redesocial.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Api {

    Context context;
    AsyncResponse response;
    AsyncList listResponse = null;

    public Api(Context context) {
        this.context = context;
        this.response = null;
    }

    public Api(Context context, AsyncResponse response) {
        this.context = context;
        this.response = response;
    }

    public void setListResponse(AsyncList listResponse) {
        this.listResponse = listResponse;
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

    public List<User> getFollowing(String login, String token) {
        final String apiUrl = Const.apiUrl("pegar_seguindo.php");
        final List<User> users = new ArrayList<>();

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
                        JSONArray list = json.getJSONArray("seguindo");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            // User
                            String userPhoto = Const.apiUrl(object.getString("foto"));
                            String login =  object.getString("login");
                            User user = new User(
                                    login,
                                    object.getString("nome"),
                                    userPhoto
                            );
                            if(!login.equals("")) {
                                String birtdate = object.getString("data_nascimento");
                                user.setBirthDate(birtdate);
                                user.city = object.getString("cidade");
                                users.add(user);
                            }
                        }
                    }
                    if(listResponse != null) {
                        listResponse.retrieve(users);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(login, token);
        return users;
    }

    public List<Post> getPosts(String login, String token, int scope) {
        final String apiUrl = Const.apiUrl("pegar_posts.php");
        final List<Post> posts = new ArrayList<>();

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "GET");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("tipo_timeline", strings[0]);

                if(!strings[1].isEmpty() && !strings[2].isEmpty()) {
                    http.addParam("login", strings[1]);
                    http.addParam("token", strings[2]);
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
                        JSONArray list = json.getJSONArray("posts");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            int postId = object.getInt("idpost");
                            // User
                            String userPhoto = Const.apiUrl(object.getString("foto_usuario"));
                            User user = new User(
                                    object.getString("login"),
                                    object.getString("nome"),
                                    userPhoto
                            );
                            // Post
                            Post post = new Post(
                                    postId,
                                    user,
                                    object.getString("data_hora"),
                                    "Super titulo"
                            );

                            String imagePath = object.getString("imagem");
                            String text = object.getString("texto");
                            if(imagePath.isEmpty()) {
                                post.postText = new PostText(text);
                            }
                            else {
                                String imageContent = Const.apiUrl(object.getString("imagem"));
                                post.postImage = new PostImage(text, imageContent);
                            }

                            List<Comment> comments = getComements(postId);
                            post.setComments(comments);
                            posts.add(post);
                        }
                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }.execute("" + scope, login, token);

        return posts;
    }

    public List<Comment> getComements(int postId) {
        final String apiUrl = Const.apiUrl("pegar_comentarios.php");
        final List<Comment> comments = new ArrayList<>();

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "GET");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("idpost", strings[0]);

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
                        JSONArray list = json.getJSONArray("comentarios");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            // User
                            String userPhoto = Const.apiUrl(object.getString("foto_usuario"));
                            User user = new User(
                                    "",
                                    object.getString("nome"),
                                    userPhoto
                            );
                            // Comment
                            Comment comment = new Comment(
                                    user,
                                    object.getString("texto"),
                                    object.getString("data_hora")

                            );
                            comments.add(comment);
                        }
                    }
                    if(listResponse != null) {
                        listResponse.retrieve(comments);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(""+postId);
        return comments;
    }

    public void postComment(String login, String token, int postId, String comment) {
        final String apiUrl = Const.apiUrl("comentar.php");

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "POST");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("token", strings[1]);
                http.addParam("idpost", strings[2]);
                http.addParam("comentario", strings[3]);

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
                        String message = "Comentário criado com sucesso!";
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
        }.execute(login, token, postId+"", comment);
    }

    public List<User> getFindUsers(String login, String search) {
        final String apiUrl = Const.apiUrl("buscar_usuario.php");
        final List<User> users = new ArrayList<>();

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "GET");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("busca", strings[1]);

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
                        JSONArray list = json.getJSONArray("usuarios");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject object = list.getJSONObject(i);
                            // User
                            String userPhoto = Const.apiUrl(object.getString("foto"));
                            User user = new User(
                                    object.getString("login"),
                                    object.getString("nome"),
                                    userPhoto
                            );
                            user.setIsFollowing(object.getInt("seguindo"));
                            JSONObject birtdate = object.getJSONObject("data_nascimento");
                            String millis = "" + DateHandler.convertToLong(birtdate.getString("date"), "yyyy-MM-dd HH:mm:ss");
                            user.setBirthDate(millis);
                            user.city = object.getString("cidade");
                            users.add(user);
                        }
                    }
                    if(listResponse != null) {
                        listResponse.retrieve(users);
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }.execute(login, search);
        return users;
    }

    public void postFollow(String login, String token, String following) {
        final String apiUrl = Const.apiUrl("seguir.php");

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "POST");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("token", strings[1]);
                http.addParam("quem", strings[2]);

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
                        String message = "Agora você está seguindo " + json.getString("login");
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
        }.execute(login, token, following);
    }

    public void postUnFollow(String login, String token, String following) {
        final String apiUrl = Const.apiUrl("desfazer_seguir.php");

        new AsyncTask<String, Void, JSONObject>() {
            HttpRequest http = new HttpRequest(apiUrl, "POST");
            @Override
            protected JSONObject doInBackground(String... strings) {
                http.addParam("login", strings[0]);
                http.addParam("token", strings[1]);
                http.addParam("quem", strings[2]);

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
                        String message = "Você não segue mais " + json.getString("login");
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
        }.execute(login, token, following);
    }
}
