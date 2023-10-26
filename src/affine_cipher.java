import static java.lang.Character.isUpperCase;
import java.util.Scanner;
public class affine_cipher {
    public static void encrypt(int a,int b,String plainText){
        String cipher="";
        if(inverse(a)==-1){
                System.out.println("Affine Can't be possible");
                return;
         }
        for(int i=0;i<plainText.length();i++){
            int ascii;
            if(isUpperCase(plainText.charAt(i))){
                ascii=plainText.charAt(i)-65;
            }
            else{
                ascii=plainText.charAt(i)-97;
            }
            int en=(((a*ascii)+b)%26);
            if(isUpperCase(plainText.charAt(i))){
                cipher=cipher+(char)(en +65);
            }
            else{
                cipher=cipher+(char)(en +97);
            }
        }
        System.out.println(cipher);
    }
   
    public static void decrypt(int a,int b,String plainText){
        String cipher="";
        for(int i=0;i<plainText.length();i++){
            int ascii;
            if(isUpperCase(plainText.charAt(i))){
                ascii=plainText.charAt(i)-65;
            }
            else{
                ascii=plainText.charAt(i)-97;
            }
            if(inverse(a)==-1){
                System.out.println("Affine Can't be possible");
                return;
            }
            int en=(inverse(a)*(ascii-b))%26;
            if(en<0){
                en=en+26;
            }
            if(isUpperCase(plainText.charAt(i))){
                cipher=cipher+(char)(en +65);
            }
            else{
                cipher=cipher+(char)(en +97);
            }
        }
        System.out.println(cipher);
    }
    public static int inverse(int r2){
        if(GCD(26,r2)==1){
            int r1=26;
            int t1=0;
            int t2=1
                    ;
            int q,r,t;
            while(r2!=0){
                q=r1/r2;
                r=r1%r2;
                t=t1-(q*t2);
                r1=r2;
                r2=r;
                t1=t2;
                t2=t;
            }
            if(t1<0){
                return t1+26;
            }
            else{
                return t1;
            }
        }
        else{
            return -1;
        }
    }
    public static int GCD(int a,int b){
        if(a==0 || b==0){
            return 0;
        }
        if(a==b){
            return a;
        }
        if(a>b){
            return GCD(a-b, b);
        }
        else{
            return GCD(a, b-a);
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        while(true){
            int ch;
            System.out.println("\n1.Encrypt\t2.Decrypt\t3.Exit\nEnter your Choice:");
            ch=sc.nextInt();
            if(ch==1){
                int a,b;
                String s;
                System.out.println("Enter the key a and b");
                a=sc.nextInt();
                b=sc.nextInt();
                System.out.println("Enter the PlainText");
                s=sc.next();
                encrypt(a,b,s);
            }
            if(ch==2){
                int a,b;
                String s;
                System.out.println("Enter the key a and b");
                a=sc.nextInt();
                b=sc.nextInt();
                System.out.println("Enter the Ciphertext");
                s=sc.next();
                decrypt(a,b,s);
            }
            if(ch==3){
                break;   
            }
        }   
    }
}