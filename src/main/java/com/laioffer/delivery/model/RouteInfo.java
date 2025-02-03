package com.laioffer.delivery.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.delivery.utils.JsonUtils;

import java.util.Map;

public class RouteInfo {
    private RouteType type;  // 路线类型（StraightLine 或 EncodedPolyline）
    private Map<String, Double> start; // 起点坐标
    private Map<String, Double> end;   // 终点坐标
    private String polyline;  // Encoded Polyline 数据

    // Jackson ObjectMapper（用于 JSON 解析）
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public RouteInfo() {}

    // 构造方法
    public RouteInfo(RouteType type, Map<String, Double> start, Map<String, Double> end, String polyline) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.polyline = polyline;
    }

    // Getter 和 Setter 方法
    public RouteType getType() {
        return type;
    }

    public void setType(RouteType type) {
        this.type = type;
    }

    public Map<String, Double> getStart() {
        return start;
    }

    public void setStart(Map<String, Double> start) {
        this.start = start;
    }

    public Map<String, Double> getEnd() {
        return end;
    }

    public void setEnd(Map<String, Double> end) {
        this.end = end;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    // 将 JSON 转换成 RouteInfo
    public static RouteInfo convertJsonToRouteInfo(String json) {
        // 将 JSON 转换为 Map
        Map<String, Object> map = JsonUtils.convertJsonToMap(json);
        RouteInfo routeInfo = new RouteInfo();

        // 解析类型
        Object typeValue = map.get("type");
        if (typeValue instanceof String) {
            routeInfo.setType(RouteInfo.RouteType.valueOf(((String) typeValue).toUpperCase()));
        }

        // 解析坐标或编码路径
        if (routeInfo.getType() == RouteInfo.RouteType.STRAIGHT_LINE) {
            routeInfo.setStart(JsonUtils.convertJsonToMapDouble(map.get("start").toString()));
            routeInfo.setEnd(JsonUtils.convertJsonToMapDouble(map.get("end").toString()));
        } else if (routeInfo.getType() == RouteInfo.RouteType.ENCODED_POLYLINE) {
            routeInfo.setPolyline((String) map.get("polyline"));
        }

        return routeInfo;
    }

    // 将 RouteInfo 转换回 JSON
    public String toJson() {
        return JsonUtils.convertObjectToJson(this);
    }
    @Override
    public String toString() {
        return "RouteInfo{" +
                "type='" + type + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", polyline='" + polyline + '\'' +
                '}';
    }

    public enum RouteType {
        STRAIGHT_LINE,      // 直线路径
        ENCODED_POLYLINE;   // 多段路径
    }
}
