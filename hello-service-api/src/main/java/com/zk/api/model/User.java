package com.zk.api.model;

import lombok.*;

/**
 * Created by zhuk on 2018/4/4.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class User {
    private String name;
    private Integer age;
}
