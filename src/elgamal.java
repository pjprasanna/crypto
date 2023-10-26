import java.util.*;
public class elgamal {
static Scanner s = new Scanner(System.in);
static int Q = 0,mi=0;
    public static void main(String[] args) {
        
        int m,q,a,x_a,y_a,k,s1,s2,k_inv,t,v1,v2,t1;
        System.out.println("Enter the value of p and q : ");
        a=s.nextInt();
        q=s.nextInt();
        System.out.println("Enter the private key of A : ");
        x_a=s.nextInt();
        y_a=mod(a,x_a,q);
        System.out.println("Public component of A: "+y_a);
        System.out.println("Private key : {"+x_a+"}");
        System.out.println("Public key : {"+q+","+a+","+y_a+"}");
        System.out.println("Enter hash(M) value : ");
        m=s.nextInt();
        System.out.println("Enter the value of k : ");
        k=s.nextInt();
        s1=mod(a,k,q);
        k_inv=inv(1,0,(q-1),0,1,k,0,0,0);
        t=k_inv*(m-(x_a*s1));
        s2=mod(t,1,(q-1));
        System.out.println("Signature Pair : {"+s1+","+s2+"}");
        v1=mod(a,m,q);
        t1= ((int)Math.pow(y_a,s1))*((int)Math.pow(s1,s2));
        v2=mod(t1,1,q);
        System.out.println("v1 : "+v1+" v2 : "+v2);
    }
    public static int mod(int a,int p,int n)
    {
        int x=1;
        int j=a;
         while (p > 0) {
            if (p % 2 == 1) {
                x = (x * a);
                if (x > n) {
                    x %= n;
                }
            }
            a = (a * a);
            if (a > n) {
                a %= n;
            }
            p /= 2;
        }
         return x;
    }
    public static int inv(int A1,int A2,int A3,int B1,int B2,int B3,int T1,int T2,int T3)
    {

    if(B3==1) {
        mi=B2;
    }
    else{
        Q = Math.abs(A3/B3);
        update(A1,A2,A3,B1,B2,B3,T1,T2,T3,Q);}
        return mi;
    }
    public static void update(int A1,int A2,int A3,int B1,int B2,int B3,int T1,int T2,int T3,int Q){
        T1=A1-Q*B1;
        T2=A2-Q*B2;
        T3=A3-Q*B3;
        A1=B1;A2=B2; A3=B3; B1=T1; B2=T2; B3=T3;
        inv(A1,A2,A3,B1,B2,B3,T1,T2,T3);
    }
}