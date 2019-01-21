package com.example.demo.user;

import com.example.demo.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@JsonTest
public class UserTests {
//
//    private User user;
//
//    @Autowired
//    private JacksonTester<User> json;
//
//    @Before
//    public void setUp(){
//        User user = new User("user","Maxim","Sukhodolets","123@mail.ru");
//        user.setId(0);
//
//        this.user = user;
//    }
//
//    @Test
//    public void deserializeJson() throws IOException {
//        String content = "{\n" +
//                "  \"username\": \"user\",\n" +
//                "  \"firstName\": \"Maxim\",\n" +
//                "  \"lastName\": \"Sukhodolets\",\n" +
//                "  \"email\": \"123@mail.ru\",\n" +
//                "}";
//
//        assertThat(this.json.parse(content)).isEqualTo(
//                new User("user","Maxim","Sukhodolets","123@mail.ru"));
//        assertThat(this.json.parseObject(content).getUserName()).isEqualTo("user");
//
//    }

}
