package com.blog4java.hsqldb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class UserEntity {
    private Long id;
    private String name;
    private Date createTime;
    private String password;
    private String phone;
    private String nickName;

    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    @Data
    @Accessors(fluent = true, chain = true)
    public static class UserEntityBuilder {
        private Long id;
        private String name;
        private Date createTime;
        private String password;
        private String phone;
        private String nickName;

        public UserEntity build() {
            return new UserEntity(id, name, createTime, password, phone, nickName);
        }
    }

    public UserEntity load() {
        this.setId(1L);
        this.setName("Lemur");
        this.setPassword("123456");
        this.setNickName("Lemur");
        this.setPhone("19878987678");
        this.setCreateTime(new Date(System.currentTimeMillis()));
        return this;
    }
}
