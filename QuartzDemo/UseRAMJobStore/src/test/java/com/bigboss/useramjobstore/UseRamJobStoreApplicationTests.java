package com.bigboss.useramjobstore;

import com.bigboss.useramjobstore.domain.User;
import com.bigboss.useramjobstore.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class UseRamJobStoreApplicationTests {

    @Test
    void contextLoads() throws JsonProcessingException {
//        User user = User.builder()
//                .name("bigboss")
//                .age(18)
//                .build();
//
//        Map<String, String> map = new HashMap<>();
//        map.put("user", JsonUtil.toJson(user));
//        map.put("str", "test");
//        log.info("map: {}", JsonUtil.toJson(map));
        String str = "{\"str\":\"test\",\"user\":\"{\\\"id\\\":null,\\\"name\\\":\\\"bigboss\\\",\\\"age\\\":18}\"}";
        Map<String, String> map = JsonUtil.toMap(str, String.class, String.class);
        log.info("map: {}", map);
    }

}
