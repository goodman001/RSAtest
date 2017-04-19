import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
public class RsaKeyGen {
	static int bits = 512;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ps = RandomPrime.generate(bits, new Random());
		String qs = RandomPrime.generate(bits, new Random());
		//System.out.println(bigNumberAdd("179815190350347681007635825587202042217","0"));
		//binaryToDec(ps);
        String str1 = "232057003083953133419023119035237902933";  
        String str2 = "232057003083953133419023119035237902900";  //232057003083953133419023119035237902933
        //multiplymain(str1,str2);
        System.out.println(bigNumberSub(str1,str2));
		String n = multiplymain(ps,qs);
		System.out.println("n:" + n);
		String psub1 = bigNumberSub(ps,"1");
		System.out.println("p-1:" + psub1);
		String qsub1 = bigNumberSub(qs,"1");
		System.out.println("q-1:" + qsub1);
		String phi = multiplymain(psub1,qsub1);
		System.out.println("(p-1)(q-1):" + phi);
		String es = getE(phi);
		String ds = getD(es,phi);
		System.out.println("D:" +ds);
		String data = es +"\n" + n;  
		String data2 = ds +"\n" + n; 
		File file = new File("pubkey.rsa");  
        try {  
            file.createNewFile(); //   
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
     //
        String str = data;  
        byte bt[] = new byte[1024];  
        bt = str.getBytes();  
        try {  
            FileOutputStream in = new FileOutputStream(file);  
            try {  
                in.write(bt, 0, bt.length);  
                in.close();  
                // boolean success=true;   
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        
        File file2 = new File("privkey.rsa");  
        try {  
            file2.createNewFile(); // 
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        String str21 = data2;  
        byte bt2[] = new byte[1024];  
        bt2 = str21.getBytes();  
        try {  
            FileOutputStream in = new FileOutputStream(file2);  
            try {  
                in.write(bt2, 0, bt2.length);  
                in.close();  
                // boolean success=true;   
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } 

		//System.out.println(gcd("1238","2"));
		//BigDivision("0001234567","12345");
		//Subtraction("1230495","123");
		//System.out.print("chu:"+Subtraction("1230495","123"));
		//bigDivision("001010","00000000000000000000011110");
		//System.out.print(mod("1010","11"));
		//String es = getE(phi);
		//System.out.println("e:" + es);
		//getD(es,phi);
	}
	public static String bigNumberAdd(String f, String s) {  
    
        char[] a = new StringBuffer(f).reverse().toString().toCharArray();  
        char[] b = new StringBuffer(s).reverse().toString().toCharArray();  
        int lenA = a.length;  
        int lenB = b.length;  
        int len = lenA > lenB ? lenA : lenB;  
        int[] result = new int[len + 1];  
        for (int i = 0; i < len + 1; i++) {  
            int aint = i < lenA ? (a[i] - '0') : 0;  
            int bint = i < lenB ? (b[i] - '0') : 0;  
            result[i] = aint + bint;  
        }  
        for (int i = 0; i < result.length; i++) {  
            if (result[i] > 10) {  
                result[i + 1] += result[i] / 10;  
                result[i] %= 10;  
            }  
        }  
        StringBuffer sb = new StringBuffer();  
        boolean flag = true;  
        for (int i = len; i >= 0; i--) {  
            if (result[i] == 0 && flag) {  
                continue;  
            } else {  
                flag = false;  
            }  
            sb.append(result[i]);  
        }  
        return sb.toString();  
    }  
	public static String multiplymain(String str1, String str2) {  
        // System.out.println("Hello world");  
  
        //String str1 = "1234";  
        //String str2 = "1234";  
        //String str1 = "1076060999";  
        //String str2 = "90188905567";    
        int len1 = str1.length();  
        int len2 = str2.length();  
  
        char[] s1 = str1.toCharArray();  
        char[] s2 = str2.toCharArray();  
        covertdata(s1, len1);  
        covertdata(s2, len2);  
  
        String res = multiply(s1, len1, s2, len2);  
        return res;
  
    }  
  
    public static void covertdata(char data[], int len) {   
        for (int i = 0; i < len / 2; i++) {  
            data[i] += data[len - 1 - i];  
            data[len - 1 - i] = (char) (data[i] - data[len - 1 - i]);  
            data[i] = (char) (data[i] - data[len - 1 - i]);  
        }  
    }  
    public static String bigNumberSub(String f, String s) {  
        System.out.print("sub:" + f + "-" + s + "=");  
        //  
        char[] a = new StringBuffer(f).reverse().toString().toCharArray();  
        char[] b = new StringBuffer(s).reverse().toString().toCharArray();  
        int lenA = a.length;  
        int lenB = b.length;  
        // 
        int len = lenA > lenB ? lenA : lenB;  
        int[] result = new int[len];  
        // 
        char sign = '+';  
        // 
        if (lenA < lenB) {  
            sign = '-';  
        } else if (lenA == lenB) {  
            int i = lenA - 1;  
            while (i > 0 && a[i] == b[i]) {  
                i--;  
            }  
            if (a[i] < b[i]) {  
                sign = '-';  
            }  
        }  
        // 
        for (int i = 0; i < len; i++) {  
            int aint = i < lenA ? (a[i] - '0') : 0;  
            int bint = i < lenB ? (b[i] - '0') : 0;  
            if (sign == '+') {  
                result[i] = aint - bint;  
            } else {  
                result[i] = bint - aint;  
            }  
        }  
        // 
        for (int i = 0; i < result.length - 1; i++) {  
            if (result[i] < 0) {  
                result[i + 1] -= 1;  
                result[i] += 10;  
            }  
        }  
  
        StringBuffer sb = new StringBuffer();  
        // 
        if (sign == '-') {  
            sb.append('-');  
        }  
        //  
        boolean flag = true;  
        for (int i = len - 1; i >= 0; i--) {  
            if (result[i] == 0 && flag) {  
                continue;  
            } else {  
                flag = false;  
            }  
            sb.append(result[i]);  
        }  
        // 
        if (sb.toString().equals("")) {  
            sb.append("0");  
        }  
        // return
        System.out.println(sb.toString());  
        return sb.toString();  
    }  
    
    public static boolean Compare(String first,String second)
    {
        first = ltrim(first,"0");
        second = ltrim(second,"0");
        int len1=first.length();
        int len2=second.length();
        if(len1<len2)
            return false;
        else if(len1==len2 && first.compareTo(second)<0)
            return false;
        return true;
    }
    public static int Compare2(String first,String second)
    {
        first = ltrim(first,"0");
        second = ltrim(second,"0");
        int len1=first.length();
        int len2=second.length();
        if(len1<len2)
            return -1;
        else if(len1==len2)
        {
        	if(first.compareTo(second)<0){
        		return -1;
        	}
        	else if(first.compareTo(second) ==0){
        		return 0;
        	}
        	else{
        		return 1;
        	}
        }
        return 1;
    }
    static String Subtraction(String s1,String s2)
    {
        if(s1.equals(s2) == true)
            return "0";
        int len1=s1.length();
        int len2=s2.length();
        char[] ss1 = s1.toCharArray();
        char[] ss2 = s2.toCharArray();
        for(int i=len2-1;i>=0;i--)
        {
            ss1[len1-1-i] =(char) ('0'+ ss1[len1-1-i] -ss2[len2-1-i]); 
        }
        //cout<<s1<<endl;
        for(int i=len1-1;i>=0;i--)
        {
            if(ss1[i]<'0')
            {
                ss1[i] += 10;
                ss1[i-1]--;
            }
        }
        String res = "";
        for(int i=0;i<ss1.length;i++){
        	res = res + ss1[i];
        }
        res = ltrim(res,"0");
        //s1.erase(0,i);  //
        //cout<<s1<<endl;
        return res;
    }
    public static void BigDivision(String a,String b){
    	a = ltrim(a,"0");
        b = ltrim(b,"0");
    	String result = "";
    	String s = "";    //result
        int count = 0;
        if("0"== b)
        {
            System.out.println("NULL");
            return;
        }
        if(!Compare(a,b))
        {
        	System.out.println("shang: 0 " + "yu:" + a);
            return;
        }
        int len=a.length();
        for(int i=0;i<len;i++)
        {
            count=0;
            s = s + a.charAt(i);
            while(Compare(s,b))
            {

                s=Subtraction(s,b);
                count++;
            }

            result+= count+'0';    //s>b
        }
        result = ltrim(result,"0");
        s = ltrim(s,"0");
        System.out.println("shang:" + result);
        System.out.println("yu:" + s);
        
    }
    public static String bigMod(String a,String b){
    	a = ltrim(a,"0");
        b = ltrim(b,"0");
    	String result = "";
    	String s = "";    //
        int count = 0;
        if("0"== b)
        {
            //cout<<"Error,divisor can not be zero!"<<endl;
            System.out.println("NULL");
            return "";
        }
        if(!Compare(a,b))
        {
        	System.out.println("shang: 0 " + "yu:" + a);
            return a;
        }
        int len=a.length();
        for(int i=0;i<len;i++)
        {
            count=0;
            s = s + a.charAt(i);
            while(Compare(s,b))
            {

                s=Subtraction(s,b);
                count++;
            }

            result+= count+'0';    //
        }
        result = ltrim(result,"0");
        s = ltrim(s,"0");
        if(s.length()==0){
        	s = "0";
        }
        System.out.println("shang:" + result);
        System.out.println("yu:" + s);
        return s;
        
    }
    public static String bigS(String a,String b){
    	a = ltrim(a,"0");
        b = ltrim(b,"0");
    	String result = "";
    	String s = "";    //
        int count = 0;
        if("0"== b)
        {
            System.out.println("NULL");
            return "";
        }
        if(!Compare(a,b))
        {
        	System.out.println("shang: 0 " + "yu:" + a);
            return "0";
        }
        int len=a.length();
        for(int i=0;i<len;i++)
        {
            count=0;
            s = s + a.charAt(i);
            while(Compare(s,b))
            {

                s=Subtraction(s,b);
                count++;
            }

            result+= count+'0';    
        }
        result = ltrim(result,"0");
        s = ltrim(s,"0");
        if(s.length()==0){
        	s = "0";
        }
        System.out.println("shang:" + result);
        System.out.println("yu:" + s);
        return result;
        
    }
    public static String  gcd(String m,String n)//a>=b
	{
		System.out.println("cal");
		if(Compare2(m,n) == -1){
			//System.out.println("[gcd]a:"+ a);
			//System.out.println("b:"+ b);
			String temp = m;
			m = n;
			n = temp;
			//System.out.println("a:"+ a);
			//System.out.println("b:"+ b);
		}
		//System.out.println("m:"+ m);
		//System.out.println("n:"+ n);
		
		while (n.equals("0") == false ) {
            String temp = n;
            n = bigMod(m,n);  
            m = temp;  
        }  
		return m;
		
	}
	public static String getE(String phi)
	{
		String egcd = "";
		while(true){
			String es = RandomPrime.generate(bits, new Random());
			if(Compare2(es,"1") == 1 && Compare2(phi,es) == 1){
				egcd = gcd(es,phi);
				//egcd = gcd("1011010","1000001");
				if(egcd.equals("1"))
				{
					System.out.println("gcd:" + egcd);
					System.out.println("es:" + es);
					return es;
					//break;
				}
				
			}
		}
		
		
	}
	public static String getD(String e,String phi){
		 
		return RandomPrime.getD(e, phi);
		
		
	}
    /*****/  
    public static String multiply(char a[], int alen, char b[], int blen) {  
        int csize = alen + blen + 3;  
        int[] c = new int[csize];  
        for (int ii = 0; ii < csize; ii++) {  
            c[ii] = 0;  
        }  
        for (int j = 0; j < blen; j++) {  
            for (int i = 0; i < alen; i++) {  
                c[i + j] +=  Integer.parseInt(String.valueOf(a[i]))* Integer.parseInt(String.valueOf(b[j]));  
            }  
        }  
        int m = 0;  
        for (m = 0; m < csize; m++) {  
            int carry = c[m] / 10;  
            c[m] = c[m] % 10;  
            if (carry > 0)  
                c[m + 1] += carry;  
        }  
        for (m = csize - 1; m >= 0;) {  
            if (c[m] > 0)  
                break;  
            m--;  
        }
        String res = "";
        for (int n = 0; n <= m; n++) {  
            //System.out.print(c[m - n]);  
            res = res + c[m - n];
        }  
        //System.out.println("");  
        //System.out.println(res);  
        return res;
    }  
	
	public static int isGreate(String a,String b){
		//System.out.println("[greate]a:"+ a);
		//System.out.println("[greate]b:"+ b);
		a = ltrim(a,"0");
		b = ltrim(b,"0");
		System.out.println("[greate]a:"+ a);
		System.out.println("[greate]b:"+ b);
		int[] num1 = new int[a.length()];  
        int[] num2 = new int[b.length()]; 
		for(int i=0;i<a.length();i++){
			num1[i] = a.charAt(i)- '0';
		}
		for(int i=0;i<b.length();i++){
			num2[i] = b.charAt(i)- '0';
		}
		if(a.length() > b.length()){
			return 1;
		}else if(a.length() < b.length())
		{
			return -1;
		}else
		{
			for(int i=0;i<a.length();i++){
				if(num1[i]>num2[i]){
					return 1;
				}else if(num1[i]<num2[i])
				{
					return -1;
				}
			}
		}
		return 0;
	}
	public static String byteToString(byte[] b){
		//transfer to binary String
		StringBuffer result = new StringBuffer();  
        for(int i = 0;i<b.length;i++)  
        {  
            result.append(Long.toString(b[i] & 0xff, 2)+",");  
        }  
        String res = result.toString().substring(0, result.length()-1);
        System.out.println("bytetostring:" + res);
        return res;
		
	}
	public static void binaryToDec(String a){
		String item = "0";
		System.out.println("len:" + a.length());
		for(int i=0;i<a.length();i++){
			char tt = a.charAt(i);
			String cell  = String.valueOf(tt);
			System.out.println("cell first:" + cell);
			if(cell.equals("0") == false){
				for(int j=0;j<a.length()-i-1;j++){
					cell = multiplymain(cell,"2");
					System.out.println("j:" + j);
					System.out.println("cell:" + cell);
				}
				break;
			}
			System.out.println("22:" + cell);
			System.out.println("item:" + item);
			item = bigNumberAdd(cell,item);
			System.out.println("sum:" + item);
			
		}
		System.out.println(item);
		//System.out.println("2:" + cell);
	}
	public static String ltrim(String str,String substr){
		int i=0;
		for(;i<str.length();i++){
			if(substr.indexOf(str.charAt(i))==-1){
				break;
			}
		}
		return str.substring(i);
	}
	

}
/**
 * A helper class for generating random prime integers
 */
class RandomPrime {
	/**
	 * Generates a random n-bit number that is probably prime (2^-100 chance of
	 * being composite). Returns this (positive) integer as a byte[].
	 * @param n the bitlength of the requested integer
	 * @return a random n-bit (probable) prime
	 */
	public static String generate(int n, Random rnd) {
		BigInteger p = BigInteger.probablePrime(n, rnd);
		System.out.println("p10:" + p.toString());
		System.out.println("p2:" + p.toString(2));
		//System.out.println("p16:" + p.toString(16));
		return p.toString(10);
	}
	public static String getD(String e,String phi){
		BigInteger d;
		BigInteger es=new BigInteger(e);
		BigInteger ps=new BigInteger(phi);
		d = es.modInverse(ps);	
		return d.toString();
		
	}
}
