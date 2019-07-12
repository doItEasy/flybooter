package com.github.flybooter.core.xxljob;

import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class MyShardingUtils {

    public static List<String> getShardingIds(String param) {
        String[] params = StringUtils.split(param, ",");
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());
        return IntStream.range(0, params.length)
            .filter(i -> i % shardingVO.getTotal() == shardingVO.getIndex())
            .mapToObj(i -> StringUtils.trim(params[i]))
            .collect(Collectors.toList());
    }
}
