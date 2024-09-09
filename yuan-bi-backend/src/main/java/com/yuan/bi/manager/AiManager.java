package com.yuan.bi.manager;

import com.yuan.bi.common.ErrorCode;
import com.yuan.bi.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用于对接AI平台
 *
 */
@Service
public class AiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;

    /**
     * Ai对话
     * @param message 消息
     * @return String
     */
    public String doChat(long modelId,String message) {
        // 3.构建请求
        DevChatRequest devChatRequest = new DevChatRequest();
        // 模型id，尾后加L，转成long类型
        //devChatRequest.setModelId(1793202600891064322L);
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        // 4.发送请求,获取响应结果
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        // 如果响应为null，抛出异常，提示"AI 响应错误"错误
        if(response == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"AI 响应错误");
        }
        return response.getData().getContent();
    }
}
