/**
 * 
 */
package org.hxx.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author xxhuang
 * 
 */
public class IPv4Util {

	private static TreeMap<Long, Integer> chinaIP = new TreeMap<Long, Integer>();
	private static AtomicBoolean loadFlag = new AtomicBoolean(false);

	public static void loadIPRecords(Configuration conf) {
		if (!loadFlag.compareAndSet(false, true)) {
			return;
		}

		BufferedReader br = null;
		try {
			Reader reader = null;
			if (conf != null)
				reader = new InputStreamReader(FileSystem.get(conf).open(new Path("/resources/chinaip.txt")));
			else
				reader = new FileReader("d:/chinaip.txt");
			br = new BufferedReader(reader);

			String line = null;
			while ((line = br.readLine()) != null) {
				if (StringUtils.isNotEmpty(line)) {
					String[] tmp = line.split("/");
					chinaIP.put(ip2Long(tmp[0]), Integer.parseInt(tmp[1]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("china ips=" + chinaIP.size());
	}

	// ��127.0.0.1 ��ʽ��IP��ַת����ʮ������������û�н����κδ�����
	public static long ip2Long(String strIP) {
		long[] ip = new long[4];
		// ���ҵ�IP��ַ�ַ��С�.����λ��
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		// ���á�.���ָ���ÿ���ַ�ת��������
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	// ��ʮ����������ʽת����127.0.0.1��ʽ��IP��ַ
	public static String long2IP(long longIP) {
		return (longIP >> 24 & 0xFF) + "." + ((longIP >> 16) & 0xFF) + "." + ((longIP >> 8) & 0xFF) + "."
				+ (longIP & 0xFF);
	}

	public static boolean isChinaIP(String ip) {
		long longIP;
		try {
			longIP = ip2Long(ip);
		} catch (Exception e) {
			System.out.println("error ip=" + ip);
			return true;
		}
		if (chinaIP.containsKey(longIP)) {
			return true;
		}
		Long lower = chinaIP.lowerKey(longIP);
		if (lower == null)
			return false;

		int mask = 0xFFFFFFFF << (32 - chinaIP.get(lower));

		// System.out.println(long2IP(lower));
		// System.out.println(mask);

		return (longIP & mask) == (lower.longValue() & mask);
	}

	public static boolean isInRange(String ip, String range) {
		String[] ips = ip.split("\\.");
		int intIp = (Integer.parseInt(ips[0]) << 24) | (Integer.parseInt(ips[1]) << 16)
				| (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
		int type = Integer.parseInt(range.replaceAll(".*/", ""));
		int mask = 0xFFFFFFFF << (32 - type);
		String sectionIp = range.replaceAll("/.*", "");
		String[] sectionIps = sectionIp.split("\\.");
		int intSectionIp = (Integer.parseInt(sectionIps[0]) << 24) | (Integer.parseInt(sectionIps[1]) << 16)
				| (Integer.parseInt(sectionIps[2]) << 8) | Integer.parseInt(sectionIps[3]);

		// System.out.println(intIp + "           " + intSectionIp);
		return (intIp & mask) == (intSectionIp & mask);
	}

	public static void main(String[] args) {
		loadIPRecords(null);

		System.out.println(isChinaIP("202.102.24.35"));
		System.out.println(isChinaIP("8.8.8.8"));
		System.out.println(isChinaIP("122.96.62.34"));

		// System.out.println(isInRange("192.168.1.1", "192.168.0.0/22"));
		// System.out.println(isInRange("192.168.1.1", "192.168.0.0/23"));
		// System.out.println(isInRange("192.168.1.1", "192.168.0.0/24"));
		// System.out.println(isInRange("192.168.1.1", "192.168.0.0/32"));
		//
		// System.out.println(isInRange("192.168.1.127", "192.168.1.64/26"));
		// System.out.println(isInRange("192.168.1.2", "192.168.0.0/23"));
		// System.out.println(isInRange("192.168.0.1", "192.168.0.0/24"));
		// System.out.println(isInRange("192.168.0.0", "192.168.0.0/32"));
		//
		// String ipStr = "192.168.0.1";
		// long ipLong = IPv4Util.ip2Long(ipStr);
		// System.out.println("192.168.0.1 ��������ʽΪ: " + ipLong);
		// System.out.println("����" + ipLong + "ת�����ַ�IP��ַ: " +
		// IPv4Util.long2IP(ipLong));
		// // IP��ַת���ɶ�������ʽ���
		// System.out.println("192.168.0.1 �Ķ�������ʽΪ: " +
		// Long.toBinaryString(ipLong));
	}
}
