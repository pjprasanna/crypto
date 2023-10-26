import java.util.*;
import static java.lang.Character.isUpperCase;
public class shift_cipher {
    public static void main(String[] args) {
        shift_cipher ob=new shift_cipher();
        Scanner sc=new Scanner(System.in);
        while(true){
        int ch;
        System.out.println("\n1.Encrypt\t2.Decrypt\t3.Exit\nEnter your Choice:");
        ch=sc.nextInt();
        if(ch==1){
            int key;
            String s;
            System.out.println("Enter the key:");
            key=sc.nextInt();
            System.out.println("Enter the string:");
            s=sc.next();
            ob.encrypt(key,s);
        }
        if(ch==2){
            int key;
            String s;
            System.out.println("Enter the key:");
            key=sc.nextInt();
            System.out.println("Enter the string:");
            s=sc.next();
            ob.decrypt(key,s);
        }
        if(ch==3){
            break;   }
    }
}
    public void encrypt(int key,String s){
        String ans="";
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                ans=ans+s.charAt(i);
                continue;
            }
            int n;
            if(isUpperCase(s.charAt(i))){
                n=s.charAt(i)-65;
            }
            else{
                n=s.charAt(i)-97;
            }
            int en=(n+key)%26;
            char q;
            if(isUpperCase(s.charAt(i))){
                q=(char)(en+65);
            }
            else{
                q=(char)(en+97);
            }
            ans=ans+q;
        }
        System.out.println(ans);
    }
    public void decrypt(int key,String s){
            int j=key;
            String ans="";
            for(int i=0;i<s.length();i++){
                if(s.charAt(i)==' '){
                    ans=ans+s.charAt(i);
                    continue;
                }
                int n;
                if(isUpperCase(s.charAt(i))){
                    n=s.charAt(i)-65;
                }
                else{
                    n=s.charAt(i)-97;
                }
                int en=(n-j)%26;
                if(en<0){
                    en=26+en;
                }
                char q;
                if(isUpperCase(s.charAt(i))){
                    q=(char)(en+65);
                }
                else{
                    q=(char)(en+97);
                }
                ans=ans+q;
            }
            System.out.println(ans+"\n");
        }
}