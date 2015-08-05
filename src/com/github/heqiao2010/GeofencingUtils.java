package com.github.heqiao2010;

import java.text.DecimalFormat;

public class GeofencingUtils {
	/**
	 * 经纬度保留6为小数
	 */
	public static DecimalFormat df = new DecimalFormat("##0.000000");   
	/**
	 * 比例尺一个单位约为70个像素
	 */
	public static final int SCALELENTH = 70; 
	
	/**
	 * 鼠标点击，修改经纬度坐标时，在比例尺为100km/cm时，<br>
	 * 每PIXPERDEGREE像素，经纬度变化1度
	 */
	public static final int PIXPERDEGREE = 300;
	
	/**
	 * 将经纬度坐标字符串形式转化double<br>
	 * 出错，或者不在范围内，返回null
	 * @param centerStr
	 * @return
	 */
	public static double[] centerStr2Double(String centerStr){
		double d[] = null;
		String[] xyStrs = centerStr.split(",");
		double longitude = 0;
		double latitude = 0;
		if(null == xyStrs || xyStrs.length == 0){
			return d;
		} else {
			try{
				longitude = Double.valueOf(xyStrs[0]);
				latitude = Double.valueOf(xyStrs[1]);
			} catch (NumberFormatException e){
				//如果解析错误，说明输入的字符不能转化为数字，这里直接不处理
				return d; 
			}
		}
		//经度取值范围：-180~+180，纬度取值范围：-74~+74
		if(-180 < longitude && longitude < 180 && -74 < latitude && latitude < 74){
			d = new double[2];
			d[0] = longitude;
			d[1] = latitude;
		}
		return d;
	}
	
	/**
	 * 圆直径大小的计算方法：<br>
	 * 比例尺的像素（即SCALE，单位：像素）除以选择的比例尺的值
	 * （即scale，单位：米），得到每像素代表多少米，然后乘以两
	 * 倍圆半径（即radius，单位：米），得到圆的直径为多少像素。
	 * @param radius
	 * @param scale
	 * @return 像素值
	 */
	public static int caculateOvalSize(int radius, int scale){
		return 2 * GeofencingUtils.SCALELENTH * radius / scale;
	}
	
	/**
	 * 获取比例尺单位（单位米），通过比例尺组合框，选择之后返回具体的比例尺的值<br>
	 * 图中1cm,大致为70个像素（SCALE）<br>
	 * <li>0:20m/cm</li>
	 * <li>1:50m/cm</li>
	 * <li>2:100m/cm</li>
	 * <li>3:200m/cm</li>
	 * <li>4:500m/cm</li>
	 * <li>5:1km/cm</li>
	 * <li>6:2km/cm</li>
	 * <li>7:5km/cm</li>
	 * <li>8:10km/cm</li>
	 * <li>9:20km/cm</li>
	 * <li>10:25km/cm</li>
	 * <li>11:50km/cm</li>
	 * <li>12:100km/cm</li>
	 * @return int
	 */
	public static int getScaleByindex(int index){
		switch (index) {
			case 0: return 20;	case 1: return 50;
			case 2: return 100;	case 3: return 200;
			case 4: return 500;	case 5: return 1000;
			case 6: return 2000;	case 7: return 5000;
			case 8: return 10000;	case 9: return 20000;
			case 10: return 25000;	case 11: return 50000;
			case 12: return 100000; default: return 0;
		}
	}
	
	/**
	 * 鼠标点击图片判断，点击点到图片中心的距离，返回对应的经纬度差值
	 * @param x 鼠标点击点到图片中心的距离（比如横向距离X）
	 * @param scale 当前比例尺值，即getScaleByindex(int)的返回值
	 * @return 返回变化的经纬度坐标差值
	 */
	public static double getTudeX(int x, int scale){
		double ret = 0.000001;  
		return ret * scale * x / PIXPERDEGREE;
	}
}
