package com.laioffer.delivery.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.delivery.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteInfo {
    private RouteType type;  // 路线类型（StraightLine 或 EncodedPolyline）
    private Map<String, Double> start; // 起点坐标
    private Map<String, Double> end;   // 终点坐标
    private String polyline;  // Encoded Polyline 数据

    // Jackson ObjectMapper（用于 JSON 解析）
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将 JSON 转换成 RouteInfo
    public static RouteInfo convertJsonToRouteInfo(String json) {
        // 将 JSON 转换为 Map
        Map<String, Object> map = JsonUtils.convertJsonToMap(json);
        RouteInfo routeInfo = new RouteInfo();

        // 解析类型
        Object typeValue = map.get("type");
        if (typeValue instanceof String) {
            routeInfo.setType(RouteType.valueOf(((String) typeValue).toUpperCase()));
        }

        // 解析坐标或编码路径
        if (routeInfo.getType() == RouteType.STRAIGHT_LINE) {
            routeInfo.setStart(JsonUtils.convertJsonToMapDouble(map.get("start").toString()));
            routeInfo.setEnd(JsonUtils.convertJsonToMapDouble(map.get("end").toString()));
        } else if (routeInfo.getType() == RouteType.ENCODED_POLYLINE) {
            routeInfo.setPolyline((String) map.get("polyline"));
        }

        return routeInfo;
    }

    // 将 RouteInfo 转换回 JSON
    public String toJson() {
        return JsonUtils.convertObjectToJson(this);
    }

    public enum RouteType {
        STRAIGHT_LINE,      // 直线路径
        ENCODED_POLYLINE;   // 多段路径
    }
}
