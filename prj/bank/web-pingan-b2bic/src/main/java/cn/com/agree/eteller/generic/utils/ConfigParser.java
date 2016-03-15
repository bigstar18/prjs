package cn.com.agree.eteller.generic.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ConfigParser {
	private HashMap blocks_k_v = new HashMap();
	private HashMap blocks_v_k = new HashMap();
	Map block_k_v;
	Map block_v_k;
	String coding = "GB18030";
	private Pattern blankPattern = Pattern.compile("^\\s*$");
	private Pattern commentPattern = Pattern.compile("^\\s*#.*$");
	private Pattern block_pattern = Pattern.compile("^\\s*\\[\\s*\\S+\\s*\\].*$");
	private Pattern line_pattern = Pattern.compile("(\\s*.+:.+\\s*)|(\\s*.+=.+\\s*)|(\\s*.+-.+\\s*)");
	private Pattern split_pattern = Pattern.compile("(\\s*:\\s*)|(\\s*=\\s*)|(\\s*-\\s*)");

	public ConfigParser(String configFileName) {
		try {
			BufferedReader configData = new BufferedReader(new InputStreamReader(new FileInputStream(configFileName), this.coding));
			String line;
			while ((line = configData.readLine()) != null) {
				handleLine(line);
			}
			configData.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConfigParser(InputStream configFileInputStream) {
		try {
			BufferedReader configData = new BufferedReader(new InputStreamReader(configFileInputStream, this.coding));
			String line;
			while ((line = configData.readLine()) != null) {
				handleLine(line);
			}
			configData.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String find_KeyToValue(String blockName, String key) {
		String value = null;
		HashMap block = (HashMap) this.blocks_k_v.get(blockName);
		if (block != null) {
			value = (String) block.get(key);
		}
		return value;
	}

	public String find_ValueToKey(String blockName, String value) {
		String key = null;
		HashMap block = (HashMap) this.blocks_v_k.get(blockName);
		if (block != null) {
			key = (String) block.get(value);
		}
		return key;
	}

	public String[] getValues(String blockName) {
		String[] values = new String[0];
		HashMap block = (HashMap) this.blocks_k_v.get(blockName);
		if (block != null) {
			values = (String[]) block.values().toArray(new String[0]);
		}
		return values;
	}

	public String[] getKeyValues(String blockName) {
		return getKeyValues(blockName, "-");
	}

	public String[] getKeyValues(String blockName, String splitor) {
		String[] keyValues = new String[0];
		HashMap block = (HashMap) this.blocks_k_v.get(blockName);
		if (block != null) {
			keyValues = new String[block.size()];
			int i = 0;
			for (Iterator iter = block.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				keyValues[i] = (entry.getKey() + splitor + entry.getValue());
				i++;
			}
		}
		return keyValues;
	}

	public boolean isBlank(String line) {
		return this.blankPattern.matcher(line).matches();
	}

	public boolean isComment(String line) {
		return this.commentPattern.matcher(line).matches();
	}

	public boolean isBlock(String line) {
		return this.block_pattern.matcher(line).matches();
	}

	public boolean isItem(String line) {
		return this.line_pattern.matcher(line).matches();
	}

	public void handleLine(String line) {
		if (isBlank(line)) {
			return;
		}
		if (isComment(line)) {
			return;
		}
		if (isBlock(line)) {
			String block_name = StringUtils.trim(line.substring(line.indexOf('[') + 1, line.indexOf(']')));

			this.block_k_v = new LinkedHashMap();
			this.block_v_k = new HashMap();
			this.blocks_k_v.put(block_name, this.block_k_v);
			this.blocks_v_k.put(block_name, this.block_v_k);
			return;
		}
		if (isItem(line)) {
			String[] kv = line.split(this.split_pattern.pattern(), 2);
			String key = StringUtils.trim(kv[0]);
			String value = StringUtils.trim(kv[1]);
			this.block_k_v.put(key, value);
			this.block_v_k.put(value, key);
		}
	}

	public HashMap getBlocks_k_v() {
		return this.blocks_k_v;
	}

	public void setBlocks_k_v(HashMap blocks_k_v) {
		this.blocks_k_v = blocks_k_v;
	}

	public HashMap getBlocks_v_k() {
		return this.blocks_v_k;
	}

	public void setBlocks_v_k(HashMap blocks_v_k) {
		this.blocks_v_k = blocks_v_k;
	}

	public static void main(String[] argv) {
		ConfigParser configParser = new ConfigParser(ConfigUtil.getResource("cn/com/agree/eteller/gwmgr/conf/gwmgr.conf"));
		System.out.println(configParser.blocks_k_v);
	}
}
