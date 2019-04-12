package net.tntchina.util;

import java.math.*;

public final class MathHelper extends net.minecraft.util.MathHelper {


	private static final int DEF_DIV_SCALE = 2;
 
	private MathHelper() {}
 
	/**
	 * ˵����
	 * �ṩ��ȷ�ļӷ�����
	 * ������: ���־� ���䣺 
	 * ��������: 2013-9-28
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������ĺ�
	 */
	public static double add(double v2, double v1) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));// ����дstring���͵Ĳ�������ͬ
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	public static long round(double value) {
		final long newValue = Math.round(value);
	
		if (newValue < value) {
			return Math.round(1.0D + value);
		} else {
			return newValue;
		}
	}
 
	/**
	 * ˵����
	 * �ṩ��ȷ�ļ�������
	 * ������: ���־� ���䣺 
	 * ��������: 2013-9-28
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double sub(double v2, double v1) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
 
	/**
	 * ˵����
	 * �ṩ��ȷ�ĳ˷����� 
	 * ��������: 2013-9-28
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double v2, double v1) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
 
	/**
	 * ˵����
	 * �ṩ��Ծ�ȷ�ĳ������㣬���������������������ȷ��.��2λ
	 * ������: ���־� ���� 
	 * ��������: 2013-9-28
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double div(double v2, double v1) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
 
	/**
	 * ˵����
	 * ������: ���־� ���䣺 
	 * ��������: 2013-9-28
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 * @return
	 */
	public static double div(double v2, double v1, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(" the scale must be a positive integer or zero");
		}
		
		if (v1 == 0.0D | v2 == 0.0D) {
			return 0.0D;
		}
		
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();// scale �����������
	}
 
}
