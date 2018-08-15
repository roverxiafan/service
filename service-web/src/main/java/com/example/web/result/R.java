package com.example.web.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: R
 * @Description: 返回结果
 * @author: roverxiafan
 * @date: 2017/01/29 09:38
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = -4692479594224462074L;

	private R() {
		super();
		put("ret", RetCodeEnum.OK.val());
		put("msg", RetCodeEnum.OK.msg());
	}
	
	public static R error() {
		return error(RetCodeEnum.ERROR.val(), RetCodeEnum.ERROR.msg());
	}
	
	public static R error(String msg) {
		return error(500, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("ret", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		return ok().put("msg", msg);
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	@Override
	@Nonnull
	public R put(String key, Object value) {
		if(key != null) {
			super.put(key, value);
		}
		return this;
	}

	@Nonnull
	public R put(Object obj) {
		if(obj != null) {
			if (obj.getClass() != Map.class) {
				super.putAll(JSONObject.parseObject(JSON.toJSONString(obj), new TypeReference<Map<String, Object>>() {
				}));
			} else {
				super.putAll((Map) obj);
			}
		}
		return this;
	}
}
