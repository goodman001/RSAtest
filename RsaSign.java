import java.security.*;
import java.math.BigInteger;
import java.io.*;
import java.util.*;
public class RsaSign {

public static void main(String[] args)throws Exception{
		
		String cmd = args[0];
		String fileName = args[1];
		String encode = "SHA-256";
        //s for signing
		if(cmd.equals("s")){
			File f = new File("privkey.rsa");
			if(!(f.exists())){
				System.out.println("privkey.rsa not existed");
				System.exit(0);
			}
		
			Scanner fileScan = new Scanner(new FileInputStream("privkey.rsa"));
			
			String line = fileScan.nextLine();
			BigInteger D = new BigInteger(line);
			
			line = fileScan.nextLine();
			BigInteger N = new BigInteger(line);
			
			String sha = encode;
	
			SignedMessage sm = new SignedMessage(D, N, sha);
			
			fileScan = new Scanner(new FileInputStream(fileName));
			
			sha = fileScan.nextLine();
			
			//read whole message
			while(fileScan.hasNext()){
				sha = sha + fileScan.nextLine();
			}
			
			//Set message to read in string to signedmessage
			sm.setMessage(sha);
			
			//digital signatrue
			sm.signMessage(D);
			
			FileOutputStream fileOut = new FileOutputStream(fileName + ".signed");
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			
			objectOut.writeObject(sm);
			
			objectOut.close();
			fileOut.close();
			fileScan.close();
			
		}
                //v for verifying
		else if(cmd.equals("v")){
		
			File f = new File("pubkey.rsa");
		
			if(!(f.exists())){
				System.out.println("Please run MyKeyGen.java before running this program...");
				System.exit(0);
			}
			
			Scanner publicScan = new Scanner(new FileInputStream("pubkey.rsa"));
			
			String line = publicScan.nextLine();
			BigInteger E = new BigInteger(line);
			
			FileInputStream fileIn = new FileInputStream(fileName + ".signed");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			
			//retrieve original message
			SignedMessage sm = (SignedMessage) objectIn.readObject();
			
			boolean tester = sm.verifyMessage(E);
			
			if(tester){
				System.out.println("This message is verified: " + sm.getMessage());
			}
			else{
				System.out.println("This message is not verified: " + sm.getMessage());
			}
		}
		
                //stupid finder
		else{
			
			System.out.println("\nWrong input (must be s or v)");
		}
	}
}
//signed message data structure
class SignedMessage implements Serializable{

	private String theMessage; 
	private String hashAlgorithm; 
	private boolean signed;
	private BigInteger theSignature; 
	private BigInteger d; 
	private BigInteger e;
	private BigInteger n;
	
	public SignedMessage(BigInteger d, BigInteger n, String h){
		
		this.d = d;
		this.n = n;
		this.hashAlgorithm = h;
	}
	
	//retrieve message function
	public void setMessage(String s){
		
		theMessage = s;
	}
	
	//return message
	public String getMessage(){
		
		return theMessage;
	}
	
	//function to return boolean for signature
	public boolean isSigned(){
		
		return signed;
	}
	
	//sign file with hash
	public void signMessage(BigInteger d)throws Exception{
		
		MessageDigest m1 = MessageDigest.getInstance(this.hashAlgorithm);
		
		byte[] b1 = theMessage.getBytes();
		m1.update(theMessage.getBytes());
		
		//Get cryptographic hash signature of the message
		byte[] digest1 = m1.digest();
		
		//Convert hashed signature into a BigInteger to perform RSA on it
		BigInteger original= new BigInteger(1, digest1);
		
		//Decrypt signature using the private key D
		theSignature = original.modPow(d, this.n);
		
		signed = true;
	}
	
	//verify two messages
	public boolean verifyMessage(BigInteger E) throws Exception{
		MessageDigest m1 = MessageDigest.getInstance(this.hashAlgorithm);
		byte[] b1 = theMessage.getBytes();
		m1.update(b1);
		byte[] digest1 = m1.digest();
		BigInteger original = new BigInteger(1, digest1);
		BigInteger encrypted = theSignature.modPow(E, this.n);
		if(original.equals(encrypted)){
			return true;
		}
		
		return false;
	}
}
