package com.github.heqiao2010;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 调用百度API，提供位置提示的功能
 * 发送Http请求（GET）返回JSON格式的信息
 * 例如：http://api.map.baidu.com/place/v2/suggestion?query=
 *	%E5%A4%A9%E5%AE%89%E9%97%A8&region=131
 *	&output=json&ak=3OwsREAN0XK5707kEbbniePY
 * @author joel
 */
public class PlaceSuggession {
	// 申请的密钥
	public static final String AK = "3OwsREAN0XK5707kEbbniePY";
	// 服务地址
	public static final String SUGGESTION_URL = "http://api.map.baidu.com/place/v2/suggestion";
	// 接口参数（一共7个，有些是非必输的）
	public static HashMap<String, String> parameters = new HashMap<String, String>();
	static {
		parameters.put("query", ""); //输入建议关键字（支持拼音）
		parameters.put("region", ""); //所属城市/区域名称或代号
		parameters.put("location", ""); //传入location参数后，返回结果将以距离进行排序
		parameters.put("output", "json"); //返回数据格式，可选json、xml两种
		parameters.put("ak", AK); //开发者访问密钥，必选
		parameters.put("sn", ""); //用户的权限签名
		parameters.put("timestamp", ""); //设置sn后该值必选
	};
	
	/**
	 * 检查参数是否有问题
	 * @param aparameters
	 * @throws ParameterException
	 */
	private static void checkParameter(HashMap<String, String> aparameters)
			throws ParameterException {
		String errorMsg = "";
		if (null == aparameters) {
			errorMsg = "Parameter is empty!";
		} else if (StringUtils.isBlank(parameters.get("query"))) {
			errorMsg = "Parameter: query is blank!";
		} else if (StringUtils.isBlank(parameters.get("region"))) {
			errorMsg = "Parameter: region is blank!";
		} else if (StringUtils.isBlank(parameters.get("ak"))) {
			errorMsg = "Parameter: ak is blank!";
		}
		if (StringUtils.isNotEmpty(errorMsg)) {
			throw new ParameterException(errorMsg);
		}
	}
	
	/**
	 * 获取字符串形式的参数列表，例如a=1&b=2
	 * @param aparameters
	 * @return
	 */
	public static String getParameterStr(HashMap<String, String> aparameters){
		try {
			checkParameter(aparameters);
		} catch (ParameterException e) {
			e.printStackTrace();
		}
		StringBuilder retStr = new StringBuilder();
		Iterator<String> it = aparameters.keySet().iterator();
		while(it.hasNext()){
			String name = it.next();
			String value = aparameters.get(name);
			if(StringUtils.isNotEmpty(value)){
				retStr.append(name + "=" + aparameters.get(name));
				if(it.hasNext()){
					retStr.append('&');
				}
			}
		}
		return retStr.toString();
	}
	
	/**
	 * 日志暂时输出到终端
	 * @param str
	 */
	public static void logger(final Object o){
		System.out.println(o.toString());
	}
	
	public static JsonElement[] parseJsonStr(final String retStr){
		List<JsonElement> retList = new ArrayList<JsonElement>();
		JsonParser parser = new JsonParser();
		JsonElement ele = parser.parse(retStr);
		JsonArray resuts = ele.getAsJsonObject().get("result").getAsJsonArray();
		for(int i=0; i<resuts.size(); i++){
			retList.add(resuts.get(i));
		}
		return retList.toArray( new JsonElement[0]);
	}
	
	public static ResultVO[] parse2ResultVOs(JsonElement[] jsonObjects) {
		if (null == jsonObjects || jsonObjects.length == 0) {
			return null;
		}
		List<ResultVO> retList = new ArrayList<ResultVO>();
		for (JsonElement e : jsonObjects) {
			Gson gson = new Gson();
			retList.add(gson.fromJson(e, ResultVO.class));
		}
		return retList.toArray(new ResultVO[0]);
	}
	
	public static void printLocations(JsonElement[]  locations){
		for( int i=0; null!=locations && locations.length > i; i++){
			logger(locations[i].toString());
		}
	}
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		PlaceSuggession.parameters.put("region", "全国");
		String queryCode = "beijing";
		while (!"-1".equals(queryCode)) {
			logger("Please Input Query Code(-1 will End this Program): ");
			queryCode = in.nextLine();
			if("-1".equals(queryCode)){
				return; 
			}
			PlaceSuggession.parameters.put("query", queryCode);
			String param = PlaceSuggession.getParameterStr(parameters);
			String returnStr = HttpHelper.sendGet(SUGGESTION_URL, param);

			logger("GET: " + SUGGESTION_URL + param);
			logger("RETURN: " + returnStr);
			JsonElement[] locations = parseJsonStr(returnStr);
			logger(locations.length);
			printLocations(locations);
		}
		in.close();
	}
}
