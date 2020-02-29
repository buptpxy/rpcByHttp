package com.pxy.rpcserver.rpc;

import com.pxy.rpcserver.utils.RpcUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping("/")
    public Result rpcMain(RpcParam rpcParam) {
        return RpcUtil.getServerResult(rpcParam);
    }

}