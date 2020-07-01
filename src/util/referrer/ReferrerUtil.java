package util.referrer;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import util.cryptography.DecryptionUtil;
import util.cryptography.EncryptionUtil;
import util.string.RandomUtil;
import util.type.CrytographyType.DecType;
import util.type.CrytographyType.EncType;

public class ReferrerUtil {
	
	public static final int RANDOM_LENGTH = 6;
	public static final String MESSAGE_DIGEST = "SHA-256";
	public enum ConvertKey {
		LEVEL1("+", "$")
		, LEVEL2("===", ".3")
		, LEVEL3("==", ".2")
		, LEVEL4("=", ".1")
		;
 
		private String key;
		private String value;
 
		private ConvertKey(String key, String value) {
			this.key = key;
			this.value = value;
		}
 
		public String getValue() {
			return value;
		}
		
		public String getKey() {
			return key;
		}
	}
	
	/**
	 * แปลง ID เป็น Referrer
	 * @param username use username and session id to salt
	 * @param sessionId use username and session id to salt
	 * @param id id to converting to ref
	 * @return
	 * @throws Exception
	 */
	public static String convertIdToReferrer(String username, String sessionId, String id) throws Exception {
		return encryptConvert(encrypt(username, sessionId, id));
	}
	
	/**
	 * แปลง Referrer เป็น ID 
	 * @param username use username and session id to salt
	 * @param sessionId use username and session id to salt
	 * @param referrer ref to converting to id 
	 * @return
	 * @throws Exception
	 */
	public static String convertReferrerToId(String username, String sessionId, String referrer) throws Exception {
		return decrypt(username, sessionId, decryptConvert(referrer));
	}
	
	/**
	 * 
	 * @param username use username and session id to salt
	 * @param sessionId use username and session id to salt
	 * @param referrers refs to converting to ids 
	 * @return
	 * @throws Exception
	 */
	public static String convertReferrersToIds(String username, String sessionId, String referrers) throws Exception {
		StringBuilder ids = new StringBuilder();
		String[] referrersArray = referrers.split(",");
		for (String referrer : referrersArray) {
			if (ids.length() > 0) {
				ids.append(",");	
			}
			ids.append(convertReferrerToId(username, sessionId, referrer.trim()));
		}
		return ids.toString();
	}
	
	private static String getKey(String username, String sessionId) throws Exception{
		String baseKey = "";
		try {
			baseKey = RandomUtil.getRandomString(RANDOM_LENGTH, true, true, true);
			MessageDigest digest = MessageDigest.getInstance(MESSAGE_DIGEST);
			byte[] usernameHash = digest.digest(
					username.getBytes(StandardCharsets.UTF_8));
			
			byte[] sessionIdHash = digest.digest(
					sessionId.getBytes(StandardCharsets.UTF_8));
			byte[] keyByte = new byte[8];
			keyByte[0] = usernameHash[2];
			keyByte[1] = sessionIdHash[7];
			keyByte[2] = usernameHash[4];
			keyByte[3] = sessionIdHash[5];
			keyByte[4] = usernameHash[6];
			keyByte[5] = sessionIdHash[3];
			keyByte[6] = usernameHash[8];
			keyByte[7] = sessionIdHash[1];
 
			baseKey = new String(keyByte);
		} catch (Exception e) {
			throw e;
		}
		return baseKey;
	}
	
	private static String encrypt(String username, String sessionId, String data) throws Exception {
		String key = getKey(username, sessionId);
		return EncryptionUtil.doEncrypt(data, key, EncType.AES256);
	}
	
	private static String decrypt(String username, String sessionId, String data) throws Exception {
		String key = getKey(username, sessionId);
		return DecryptionUtil.doDecrypt(data, key, DecType.AES256);
	}
	
	private static String encryptConvert(String dataEnc) {
		return dataEnc
				.replace(ConvertKey.LEVEL1.getKey(), ConvertKey.LEVEL1.getValue())
				.replace(ConvertKey.LEVEL2.getKey(), ConvertKey.LEVEL2.getValue())
				.replace(ConvertKey.LEVEL3.getKey(), ConvertKey.LEVEL3.getValue())
				.replace(ConvertKey.LEVEL4.getKey(), ConvertKey.LEVEL4.getValue());
	}
	 
	private static String decryptConvert(String dataDec) {
		return dataDec
				.replace(ConvertKey.LEVEL1.getValue(), ConvertKey.LEVEL1.getKey())
				.replace(ConvertKey.LEVEL2.getValue(), ConvertKey.LEVEL2.getKey())
				.replace(ConvertKey.LEVEL3.getValue(), ConvertKey.LEVEL3.getKey())
				.replace(ConvertKey.LEVEL4.getValue(), ConvertKey.LEVEL4.getKey());
	}
	
}