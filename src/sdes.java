import java.util.*;
public class sdes {
    public static String shiftLeft(String s,int value){
        String res="";
        for(int j=0;j<value;j++){
            res="";
            for(int i=0;i<s.length()-1;i++){
                res=res+s.charAt(i+1);
            }
            res=res+s.charAt(0);
            s=res;
        }
        return res;
    }
    public static String[] keyGeneration(String plaintext,String key){
        int[] compressionTable={6,3,7,4,8,5,10,9};
        int[] StraightPBox={3,5,2,7,4,10,1,9,8,6};
        String[] keys=new String[2];
        String newVal ="";
        for(int i=0;i<StraightPBox.length;i++){
            newVal=newVal+key.charAt(StraightPBox[i]-1);
        }
        String left=newVal.substring(0, 5);
        String right=newVal.substring(5, 10);
        for(int i=0;i<2;i++){
            String left_shifted=shiftLeft(left, i+1);
            String right_shifted=shiftLeft(right, i+1);
            String concat=left_shifted+right_shifted;
            String k="";
            for(int j=0;j<compressionTable.length;j++){
                k=k+concat.charAt(compressionTable[j]-1);
            }
            keys[i]=k;
            left=left_shifted;
            right=right_shifted;
        }
       
        return keys;
    } 
    public static  String XOR(String a,String b){
        String ans="";
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)==b.charAt(i)){
                ans=ans+"0";
            }
            else{
                ans=ans+"1";
            }
        }
        return ans;
    }
    public static String sbox(String s,int i){
        int sbox1[][] ={{1,0,3,2},{3,2,1,0},{0,2,1,3},{3,1,3,2}};    
        int sbox2[][] ={{0,1,2,3},{2,0,1,3},{3,0,1,0},{2,1,0,3}}; 
        String row=""+s.charAt(0)+s.charAt(3);
        String col=""+s.charAt(1)+s.charAt(2);
        int irow=Integer.parseInt(row,2);
        int icol=Integer.parseInt(col,2);
        String res;
        if(i==1){
            res=Integer.toBinaryString(sbox1[irow][icol]);
        }
        else{
            res=Integer.toBinaryString(sbox2[irow][icol]);
        }
        if(res.length()==1){
            res="0"+res;
        }
        return res;       
    }
    public static String func(String s,String[] keys,int i){
        int[] EP={4,1,2,3,2,3,4,1};
        int[] straightPBox={2,4,3,1};
        String newVal="";
        for(int j=0;j<EP.length;j++){
            newVal+=s.charAt(EP[j]-1);
       }
        String xor_value=XOR(newVal, keys[i]);
        String left=xor_value.substring(0, 4);
        String right=xor_value.substring(4, 8);
        String res=sbox(left, 1)+sbox(right, 2);
        String out="";
        for(int k=0;k<straightPBox.length;k++){
            out=out+res.charAt(straightPBox[k]-1);
        }
        return out;
    }

    public static String encrypt(String PlainText,String[] keys,boolean flag){
       int[] IP={2,6,3,1,4,8,5,7};
       int[] FP={4,1,3,5,7,2,8,6};
       String newVal="";
       for(int j=0;j<IP.length;j++){
            newVal+=PlainText.charAt(IP[j]-1);
       }
       String left=newVal.substring(0, 4);
       String right=newVal.substring(4, 8);
       
       for(int i=0;i<2;i++){
           String funcApplied;
           if(flag){
               funcApplied=func(right,keys,i);
           }
           else{
               funcApplied=func(right,keys,keys.length-i-1);
           }
           String newRight=XOR(left, funcApplied);
           if(i==0){
               left=right;
               right=newRight;
           }
           else{
               left=newRight;
           }
       }
       String lastval=left+right;
       String cipherText="";
       for(int j=0;j<FP.length;j++){
            cipherText+=lastval.charAt(FP[j]-1);
       }
       return cipherText;
    }
    public static void main(String[] args) {
        String PlainText;
        String Key;
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter plaintext (8 bits): ");
        PlainText=sc.next();
        System.out.println("Enter key (10 bits): ");
        Key=sc.next();
        String[] keys=keyGeneration(PlainText, Key);
        System.out.println("Generated Keys");
         for(int i=0;i<keys.length;i++){
            System.out.println("Key " + i +" "+keys[i]);
        }
        System.out.println("Encryption");
        String cipherText=encrypt(PlainText,keys,true);
        System.out.println("Encrypted Text: "+cipherText);
        System.out.println("Decryption");
        String decryptedText=encrypt(cipherText, keys,false);
        System.out.println("Decrypted Text: "+decryptedText);
        
    }
}