package com.devarch.mcp;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

@Service
public class ToolsService {

    @McpTool
    public String getWeather(String city) {
        return "The weather in " + city + " is sunny.";
    }

}