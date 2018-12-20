package demo.util;/*
 * @program: FlinkTest
 * @Date: 2018/12/17 9:19
 * @Author: yqq
 * @Description:从命令行中提取参数
 * 任务id
 */


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ParamUtils {
    public static Long getTaskIdFromArgs(String[] args){
        try {
            if (args != null && args.length > 0) {
                return Long.valueOf(args[0]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*
    从JSON对象中提取参数
     */
    public static String getParam(JSONObject jsonObject, String field) {
        JSONArray jsonArray = jsonObject.getJSONArray(field);
        if (jsonArray != null && jsonArray.size() > 0) {
            return jsonArray.getString(0);
        }
        return null;
    }
}
