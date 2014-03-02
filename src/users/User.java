package users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class User {
	private static int IDGEN = 0;
	private String username;
	private int id;
	private byte[] hash, salt;
	
	public User(String username, String password) {
		this.username = username;
		this.id = IDGEN++;
		try{
			this.hash = hashPassword(password);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private byte[] hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		final Random r = new SecureRandom();
		byte[] salt = new byte[32];
		r.nextBytes(salt);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		this.salt = salt;
		return digest.digest(password.getBytes("UTF-8"));
	}
	
	public boolean authenticate(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(salt);
			byte[] newhash = digest.digest(password.getBytes("UTF-8"));
			return Arrays.equals(hash,newhash);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//Setters
	
	//Getters
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
}
