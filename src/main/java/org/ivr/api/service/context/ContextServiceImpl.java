package org.ivr.api.service.context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.ivr.api.helper.ThreadLocalHelper;
import org.springframework.stereotype.Service;

/**
 * @author zhangyaoxin@yiwise.com
 * @description TODO
 * @create 2019/04/12
 **/
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContextServiceImpl implements ContextService {

    Cache<String, Object> cache = CacheBuilder.newBuilder().build();

    @Override
    public <T> void set(String property, T value) {
        Integer requestId = ThreadLocalHelper.getRequestId();
        String key = property + requestId;
        cache.put(key, value);
    }

    @Override
    public <T> T get(String key) {
        return (T) cache.getIfPresent(key);
    }
}
