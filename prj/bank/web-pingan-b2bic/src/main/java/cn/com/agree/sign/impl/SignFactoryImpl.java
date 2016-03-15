package cn.com.agree.sign.impl;

import java.util.List;
import java.util.Map;

import cn.com.agree.sign.ISign;
import cn.com.agree.sign.ISignFactory;
import cn.com.pingan.b2bic.web.SignVo;

public class SignFactoryImpl implements ISignFactory {
	public ISign createSignTool(final String signMode, Map param) {
		return new ISign() {
			public boolean verify(byte[] hash, byte[] signData) throws Exception {
				return false;
			}

			public byte[] sign(byte[] hash) throws Exception {
				return null;
			}

			public void setHashAlg(String hashAlg) {
			}

			public String name() {
				return null;
			}

			public boolean hashAndVerify(String srcFile, byte[] signData) throws Exception {
				return false;
			}

			public boolean hashAndVerify(byte[] src, byte[] signData) throws Exception {
				return false;
			}

			public byte[] hashAndSign(String srcFile) throws Exception {
				return null;
			}

			public byte[] hashAndSign(byte[] src) throws Exception {
				return null;
			}

			public String getSubject() {
				return "签名方式：" + signMode + "\n“企业客户端”按照交易码确定是否做签名，签名之后\n附加签名数据后按照指定格式通过https协议发送报文到“SSL网关”（或HTTPS直连到外联通讯前置）。";
			}

			public byte[] getCert() throws Exception {
				return null;
			}
		};
	}

	public List<String> findCerts(SignVo signVo) throws Exception {
		return null;
	}
}
