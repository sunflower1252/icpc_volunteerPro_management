package com.sunflower.icpc_volunteer_management.commom;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 *
 * @author yg
 * @date 2024/04/19
 */
@Data
public class Result implements Serializable {
    /**
     * code 200 500 401 404\
     * msg 信息
     * data 数据
     */

    private Integer code;

    private String msg;

    private Object data;

    private Map map = new HashMap();

    public static Result success(Object object) {
        Result r = new Result();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public Result add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
