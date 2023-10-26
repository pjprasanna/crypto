import java.util.*;
public class rsa {
    public static long square_muliply(long a, long x, long n) {
        long y = 1;
        while (x > 0) {
            long rem = x % 2;
            if (x / 2 > 0) {
                if (rem == 1) {
                    y = (y * a) % n;
                    a = a * a % n;
                } else {
                    a = a * a % n;
                }
            } else {
                y = (y * a) % n;
                break;
            }
            x = x / 2;
        }
        return y;
    }
    public static int inverse(int a, int n) {
        int m0 = n;
        int x0 = 0;
        int x1 = 1;
        if (n == 1) {
            return 0;
        }
        while (a > 1) {
            int q = a / n;
            int t = n;

            n = a % n;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }
        if (x1 < 0) {
            x1 += m0;
        }
        return x1;
    }
    public static int gcd(int a, int b) {
        int r1 = a;
        int r2 = b;
        if (a > b) {
            r1 = a;
            r2 = b;
        } else {
            r2 = a;
            r2 = b;
        }
        int q, r = 0;
        while (r2 != 0) {
            q = r1 / r2;
            r = r1 % r2;
            r1 = r2;
            r2 = r;
        }
        return r1;
    }
      public static long encrypt_decrypt(long p, int e, int n) {
        long ans = square_muliply(p, e, n);
        return ans;
    }
    public static long [] conv_txt(String str,int e,int n){
        long[] arr=new long[str.length()];
        for(int i=0;i<str.length();i++){
            long c=(long)str.charAt(i);
            long enc=encrypt_decrypt(c,e,n);
            arr[i]=enc;
        }
        return arr;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter  String :");
        String plaintext=sc.next();
        int p = 7919;
        int q = 7789;
        int n = p * q;
        int phi_n = (p - 1) * (q - 1);

                int e = 2;
        while (e < phi_n) {
            if (gcd(e, phi_n) == 1) {
                break;
            }
            else {
                e++;
            }
        }
        int d = inverse(e, phi_n);
        System.out.println("Public key: " + e);
        System.out.println("Private key: " + d);
        long[] enc=conv_txt(plaintext,e,n);
        System.out.print("Encrypted message: ");
        for(long i:enc){
            System.out.print(i+" ");
        }
        String ans="";
        for(long i:enc){
            long dec=encrypt_decrypt(i, d, n);
            ans=ans+(char)dec;
        }
        System.out.println("\nDecrypted message: "+ans);
    }
}