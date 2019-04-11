package ognl;

import org.apache.ibatis.domain.blog.Author;
import org.apache.ibatis.domain.blog.Blog;
import org.apache.ibatis.domain.blog.Post;
import org.apache.ibatis.domain.blog.Section;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OgnlContextTest {

    private OgnlContext context;
    private static List<Post> posts;
    private static Blog blog;
    private static Author author;

    @Before
    public void before() {
        Blog.staticField = "static field";
        author = new Author(2, "username", "password ", "email", "bio", Section.NEWS);
        Post post = new Post();
        post.setBody("PostContent");
        post.setAuthor(author);
        posts = new ArrayList<>();
        posts.add(post);

        blog = new Blog(1, "", author, posts);

        context = new OgnlContext();
        context.put("blog", blog);
        context.setRoot(blog);
    }

    @Test
    public void testl() throws OgnlException {
        context.put("author", new Author(2, "username2", "password 2 ", "email2", "bio2", Section.NEWS));

//        11 Ognl.paraseExpression （）方法负责解析O GNL 表达式，author 是root 对象（即blog 对象）的属性
        Object value = Ognl.getValue(Ognl.parseExpression("author"), context, context.getRoot());
        System.out.println(value);

        value = Ognl.getValue(Ognl.parseExpression("author.username"), context, context.getRoot());
        System.out.println(value);

        //II #author 表示需要操作的对象不是root 对象，而是O g n lC o ntext 中key 为author 的对象
        value = Ognl.getValue(Ognl.parseExpression("#author.username"), context, context.getRoot());
        System.out.println(value);

        Object obj = Ognl.getValue("author.getEmail()", context, context.getRoot());
        System.out.println(obj); //输出是emaill

        // 调用的是Blog. staticMethod （）这个静态方法
        obj = Ognl.getValue("@org.apache.ibatis.domain.blog.Blog@staticMethod()", context, context.getRoot());
        System.out.println(obj); //输出是static Method
        //访问Blog.staticF 工eld 这个静态字段
        obj = Ognl.getValue("@org.apache.ibatis.domain.blog.Blog@staticField", context, context.getRoot());
        System.out.println(obj); //输出是static Method


        obj = Ognl.getValue("posts[0]", context, context.getRoot());
        System.out.println(obj instanceof Post); //输出是true

        Map<String, String> map = new HashMap<>();
        map.put("kl", "vl");
        map.put("k2", "v2");
        context.put("map", map);

        obj = Ognl.getValue("#map['k2']", context, context.getRoot());
        System.out.println(obj); //输出是v2
    }
}
