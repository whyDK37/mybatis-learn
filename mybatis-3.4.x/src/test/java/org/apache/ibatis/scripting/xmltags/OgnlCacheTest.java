package org.apache.ibatis.scripting.xmltags;

import org.apache.ibatis.domain.blog.Author;
import org.apache.ibatis.domain.blog.Blog;
import org.apache.ibatis.domain.blog.Post;
import org.apache.ibatis.domain.blog.Section;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OgnlCacheTest {

    @Test
    public void getValue() {
        Blog.staticField = "static field";
        Author author = new Author(2, "username", "password ", "email", "bio", Section.NEWS);
        Post post = new Post();
        post.setBody("PostContent");
        post.setAuthor(author);
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        Blog blog = new Blog(1, "", author, posts);

        Object value = OgnlCache.getValue("author", blog);
        System.out.println(value);

    }
}