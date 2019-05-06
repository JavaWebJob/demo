package com.itheima;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.User;
import com.itheima.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootSdjApplication.class)
public class SpringbootSdjApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
  	private RedisTemplate<String,String> redisTemplate;

	@Test
	public void contextLoads() {
		List<User> users=userRepository.findAll();
		System.out.println(users.toString());
	}

	@Test
	public void refisTest() throws JsonProcessingException {
		String userList=redisTemplate.boundValueOps("userList").get();
		if (userList==null){
			List<User> users = userRepository.findAll();
			ObjectMapper objectMapper = new ObjectMapper();
			userList = objectMapper.writeValueAsString(users);
			redisTemplate.boundValueOps("userList").set(userList);
			System.out.println("===============从数据库获得数据===============");
		}else{
			System.out.println("===============从redis缓存中获得数据===============");
		} System.out.println(userList);
	}
}



