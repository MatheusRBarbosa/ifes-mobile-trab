package com.ifes.mobile.redesocial.services;

import com.ifes.mobile.redesocial.models.Comment;
import com.ifes.mobile.redesocial.models.Post;
import com.ifes.mobile.redesocial.models.PostImage;
import com.ifes.mobile.redesocial.models.PostText;
import com.ifes.mobile.redesocial.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mock {

    //API: Retorna um usuario a partir de um id
    static public User getUser(int id){
        User user4 = new User(4, "Pato", "https://i.imgur.com/91fQ6UO.jpg", "Serra");
        user4.setBirthDate("01/01/2000");
        User user5 = new User(5, "Gato", "https://i.imgur.com/4AiXzf8.jpg", "Cariacica");
        user5.setBirthDate("10/10/1998");
        User user6 = new User(6, "Cachorro", "https://i.imgur.com/H37kxPH.jpeg","Vitoria");
        user6.setBirthDate("12/11/1996");

        User user = new User(1, "Matheus", "http://clipart-library.com/images/kiKo7BoqT.png", "Serra");

        // Mock following
        List<User> following1 = new ArrayList<>();
        following1.add(user4);
        following1.add(user5);
        following1.add(user6);
        user.setFollowing(following1);
        user.setEmail("super@email.com");

        return user;

    }

    //API: Retorna lista de usuários
    static public List<User> getUsers(){
        List<User> users = new ArrayList<>();

        // Mock user
        User user1 = new User(1, "Matheus", "http://clipart-library.com/images/kiKo7BoqT.png", "Serra");
        user1.setBirthDate("01/01/2000");

        User user2 = new User(2, "Barbosa", "https://i.imgur.com/iHsr4Nd.png", "Fundao");
        user2.setBirthDate("01/01/2001");

        User user3 = new User(3, "Fernando da silva", "https://img.icons8.com/bubbles/2x/user.png", "Vitoria");
        user3.setBirthDate("01/01/2002");

        User user4 = new User(4, "Pato", "https://i.imgur.com/91fQ6UO.jpg", "Serra");
        user4.setBirthDate("01/01/2000");

        User user5 = new User(5, "Gato", "https://i.imgur.com/4AiXzf8.jpg", "Cariacica");
        user5.setBirthDate("10/10/1998");

        User user6 = new User(6, "Cachorro", "https://i.imgur.com/H37kxPH.jpeg","Vitoria");
        user6.setBirthDate("12/11/1996");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        return users;
    }

    //API: Retorna os comentarios a partir de id de post
    static public List<Comment> getComments(int id){
        User user2 = new User(2, "Barbosa", "https://i.imgur.com/iHsr4Nd.png", "Fundao");
        User user3 = new User(3, "Fernando da silva", "https://img.icons8.com/bubbles/2x/user.png", "Vitoria");

        List<Comment> comments1 = new ArrayList<>();
        Comment c1 = new Comment(user2, "Voce quase conseguiu fazer funcionar", new Date());
        Comment c2 = new Comment(user3, "Dessa vez nao funcionou!", new Date());
        Comment c3 = new Comment(user3, "Vai lá, você consegue!", new Date());
        comments1.add(c1);
        comments1.add(c2);
        comments1.add(c3);

        return comments1;
    }

    //API: Retorna todos os posts
    static public List<Post> getAllPosts(){
        List<Post> posts = new ArrayList<>();

        // Mock user
        User user1 = new User(1, "Matheus", "http://clipart-library.com/images/kiKo7BoqT.png", "Serra");
        User user2 = new User(2, "Barbosa", "https://i.imgur.com/iHsr4Nd.png", "Fundao");
        User user3 = new User(3, "Fernando da silva", "https://img.icons8.com/bubbles/2x/user.png", "Vitoria");
        User user4 = new User(4, "Pato", "https://i.imgur.com/91fQ6UO.jpg","Serra");
        User user5 = new User(5, "Gato", "https://i.imgur.com/4AiXzf8.jpg", "Cariacica");
        User user6 = new User(6, "Cachorro", "https://i.imgur.com/H37kxPH.jpeg", "Vitoria");

        // Mock comments
        List<Comment> comments1 = new ArrayList<>();
        Comment c1 = new Comment(user2, "Voce quase conseguiu fazer funcionar", new Date());
        Comment c2 = new Comment(user3, "Dessa vez nao funcionou!", new Date());
        Comment c3 = new Comment(user3, "Vai lá, você consegue!", new Date());
        comments1.add(c1);
        comments1.add(c2);
        comments1.add(c3);

        List<Comment> comments2 = new ArrayList<>();
        Comment p1 = new Comment(user2, "Esse post eh incrivel!", new Date());
        Comment p2 = new Comment(user2, "Sera que eu posso ser um desenvolvedor tao bom quanto voce?", new Date());
        comments2.add(p1);
        comments2.add(p2);

        List<Comment> comments3 = new ArrayList<>();
        Comment l1 = new Comment(user3, "Fotao!", new Date());
        Comment l2 = new Comment(user1, "Que bela foto", new Date());
        comments3.add(l1);
        comments3.add(l2);

        List<Comment> comments4 = new ArrayList<>();
        Comment d1 =  new Comment(user5, "Au au", new Date());
        comments4.add(d1);

        // Mock posts
        Post post1 = new Post(
                1,
                user1,
                new Date(),
                comments1,
                "Minha primeira postagem"
        );
        post1.postText = new PostText("Texto generico superficial");
        posts.add(post1);

        Post post11 = new Post(
                11,
                user1,
                new Date(),
                comments1,
                "Minha Segunda postagem"
        );
        post11.postText = new PostText("Texto generico superficial2");
        posts.add(post11);

        Post post2 = new Post(
                2,
                user2,
                new Date(),
                comments2,
                "Postagem do usuario 2"
        );
        post2.postText = new PostText("QOIWEJOIQWJEI)WQJEOIQ )(WQUE QWEOIQWJE QWIEJ Q");
        posts.add(post2);

        Post post3 = new Post(
                3,
                user3,
                new Date(),
                comments3,
                "Postagem de imagem"
        );
        post3.postImage = new PostImage("Nao precisa de titulo", "https://pbs.twimg.com/media/EeqDHZhXsAEY9D3?format=jpg&name=medium");
        posts.add(post3);

        Post post4 = new Post(
                4,
                user6,
                new Date(),
                comments4,
                "Meu novo jogo"
        );
        post4.postImage = new PostImage("Eu, no meu novo jogo", "https://i.imgur.com/kx2WoK4.jpg");
        posts.add(post4);

        return posts;
    }

    //API: Retorna todos os posts
    static public List<Post> getAllPostsFromUserId(int id){
        List<Post> posts = new ArrayList<>();

        // Mock user
        User user1 = new User(1, "Matheus", "http://clipart-library.com/images/kiKo7BoqT.png", "Serra");
        User user2 = new User(2, "Barbosa", "https://i.imgur.com/iHsr4Nd.png", "Fundao");
        User user3 = new User(3, "Fernando da silva", "https://img.icons8.com/bubbles/2x/user.png", "Vitoria");
        User user4 = new User(4, "Pato", "https://i.imgur.com/91fQ6UO.jpg","Serra");
        User user5 = new User(5, "Gato", "https://i.imgur.com/4AiXzf8.jpg", "Cariacica");
        User user6 = new User(6, "Cachorro", "https://i.imgur.com/H37kxPH.jpeg", "Vitoria");

        // Mock comments
        List<Comment> comments1 = new ArrayList<>();
        Comment c1 = new Comment(user2, "Voce quase conseguiu fazer funcionar", new Date());
        Comment c2 = new Comment(user3, "Dessa vez nao funcionou!", new Date());
        Comment c3 = new Comment(user3, "Vai lá, você consegue!", new Date());
        comments1.add(c1);
        comments1.add(c2);
        comments1.add(c3);

        List<Comment> comments2 = new ArrayList<>();
        Comment p1 = new Comment(user2, "Esse post eh incrivel!", new Date());
        Comment p2 = new Comment(user2, "Sera que eu posso ser um desenvolvedor tao bom quanto voce?", new Date());
        comments2.add(p1);
        comments2.add(p2);

        List<Comment> comments3 = new ArrayList<>();
        Comment l1 = new Comment(user3, "Fotao!", new Date());
        Comment l2 = new Comment(user1, "Que bela foto", new Date());
        comments3.add(l1);
        comments3.add(l2);

        List<Comment> comments4 = new ArrayList<>();
        Comment d1 =  new Comment(user5, "Au au", new Date());
        comments4.add(d1);

        // Mock posts
        Post post1 = new Post(
                1,
                user1,
                new Date(),
                comments1,
                "Minha primeira postagem"
        );
        post1.postText = new PostText("Texto generico superficial");
        posts.add(post1);

        Post post11 = new Post(
                11,
                user1,
                new Date(),
                comments1,
                "Minha Segunda postagem"
        );
        post11.postText = new PostText("Texto generico superficial2");
        posts.add(post11);

        Post post111 = new Post(
                111,
                user1,
                new Date(),
                comments1,
                "Minha Segunda postagem"
        );
        post111.postImage = new PostImage("aaaa", "https://i.pinimg.com/originals/cf/c9/d8/cfc9d8bcc3d9132edf5347915df6bb35.jpg");
        posts.add(post111);

        Post post1111 = new Post(
                1111,
                user1,
                new Date(),
                comments1,
                "Minha Segunda postagem"
        );
        post1111.postImage = new PostImage("bbb", "https://i.pinimg.com/564x/12/26/5d/12265d7803951a30bd98d63b3b1e89f7.jpg");
        posts.add(post1111);

        return posts;
    }
}
