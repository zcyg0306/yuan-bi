package com.yuan.bi.api;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;

public class YuAPI {

    public static void main(String[] args) {

        String accessKey = "xxx";
        String secretKey = "xxx";
        YuCongMingClient client = new YuCongMingClient(accessKey, secretKey);

        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1793202600891064322L);
        devChatRequest.setMessage("你能做什么");

        BaseResponse<DevChatResponse> response = client.doChat(devChatRequest);
        System.out.println(response.getData());

    }
}
