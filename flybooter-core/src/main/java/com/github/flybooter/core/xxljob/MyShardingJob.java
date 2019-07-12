package com.github.flybooter.core.xxljob;

import com.alibaba.fastjson.JSONObject;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public abstract class MyShardingJob extends IJobHandler {

	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		String shardingIds = null;
		String jobParam = null;

		if (StringUtils.startsWith(param, "{")) {
			JSONObject JsonObject = JSONObject.parseObject(param);
			shardingIds = JsonObject.getString("shardingIds");
			jobParam = JsonObject.getString("param");
		} else {
			shardingIds = param;
		}

		List<String> list = MyShardingUtils.getShardingIds(shardingIds);
		XxlJobLogger.log("分片id" + list + "执行参数：" + jobParam);

		try {
			for (String shardingId : list) {
				log.info("scheduleJob for Id:{}", shardingId);
				XxlJobLogger.log("任务开始，Name:" + shardingId);
				runJob(shardingId,jobParam);
				XxlJobLogger.log("任务完成，Name" + shardingId);
			}
		} catch (Exception e) {
			XxlJobLogger.log(e);
			return FAIL;
		}
		return SUCCESS;
	}


	
	public void runJob(String shardingId,String param) throws Exception {
	}
	

}
