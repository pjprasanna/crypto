import java.util.*; 
public class hill_cipher {  
    private static int[][] getKeyMatrix() {  
        Scanner sc = new Scanner(System.in);  
        System.out.println("Enter key matrix:");  
        String key = sc.nextLine();  
        double sq = Math.sqrt(key.length());  
        if (sq != (long) sq) {  
            System.out.println("Cannot Form a square matrix");  
        }  
        int len = (int) sq;  
        int[][] keyMatrix = new int[len][len];  
        int k = 0;  
        for (int i = 0; i < len; i++)  
        {  
            for (int j = 0; j < len; j++)  
            {  
                keyMatrix[i][j] = ((int) key.charAt(k)) - 97;  
                k++;  
            }  
        }  
        return keyMatrix;  
    }  
    private static void isValidMatrix(int[][] keyMatrix) {  
        int det = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];  
        if(det == 0) {  
            throw new java.lang.Error("Det equals to zero, invalid key matrix!");  
        }  
    }  
        private static void isValidReverseMatrix(int[][] keyMatrix, int[][] reverseMatrix) {  
        int[][] product = new int[2][2];  
        product[0][0] = (keyMatrix[0][0]*reverseMatrix[0][0] + keyMatrix[0][1] * reverseMatrix[1][0]) % 26;  
        product[0][1] = (keyMatrix[0][0]*reverseMatrix[0][1] + keyMatrix[0][1] * reverseMatrix[1][1]) % 26;  
        product[1][0] = (keyMatrix[1][0]*reverseMatrix[0][0] + keyMatrix[1][1] * reverseMatrix[1][0]) % 26;  
        product[1][1] = (keyMatrix[1][0]*reverseMatrix[0][1] + keyMatrix[1][1] * reverseMatrix[1][1]) % 26;  
        if(product[0][0] != 1 || product[0][1] != 0 || product[1][0] != 0 || product[1][1] != 1) {  
            throw new java.lang.Error("Invalid reverse matrix found!");  
        }  
    }  
    private static int[][] reverseMatrix(int[][] keyMatrix) {  
        int detmod26 = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26; // Calc det  
        int factor;  
        int[][] reverseMatrix = new int[2][2];  
        for(factor=1; factor < 26; factor++)  
        {  
            if((detmod26 * factor) % 26 == 1)  
            {  
                break;  
            }  
        }  
        reverseMatrix[0][0] = keyMatrix[1][1]           * factor % 26;  
        reverseMatrix[0][1] = (26 - keyMatrix[0][1])    * factor % 26;  
        reverseMatrix[1][0] = (26 - keyMatrix[1][0])    * factor % 26;  
        reverseMatrix[1][1] = keyMatrix[0][0]           * factor % 26;  
        return reverseMatrix;  
    }  
    private static void echoResult(String label, int adder, ArrayList<Integer> phrase) {  
        int i;  
        System.out.print(label);  
        for(i=0; i < phrase.size(); i += 2) {  
            System.out.print(Character.toChars(phrase.get(i) + (64 + adder)));  
            System.out.print(Character.toChars(phrase.get(i+1) + (64 + adder)));  
            if(i+2 <phrase.size()) {  
                System.out.print("-");  
            }  
        }  
        System.out.println();  
    }  
    public static void encrypt(String phrase, boolean alphaZero)  
    {  
        int i;  
        int adder = alphaZero ? 1 : 0;
        int[][] keyMatrix;  
        ArrayList<Integer> phraseToNum = new ArrayList<>();  
        ArrayList<Integer> phraseEncoded = new ArrayList<>();  
        phrase = phrase.replaceAll("[^a-zA-Z]","").toUpperCase();  
 
        if(phrase.length() % 2 == 1) {  
            phrase += "Q";  
        }  
        keyMatrix = getKeyMatrix();  
        isValidMatrix(keyMatrix);  
        for(i=0; i < phrase.length(); i++) {  
            phraseToNum.add(phrase.charAt(i) - (64 + adder));  
        }  
        for(i=0; i < phraseToNum.size(); i += 2) {  
            int x = (keyMatrix[0][0] * phraseToNum.get(i) + keyMatrix[0][1] * phraseToNum.get(i+1)) % 26;  
            int y = (keyMatrix[1][0] * phraseToNum.get(i) + keyMatrix[1][1] * phraseToNum.get(i+1)) % 26;  
            phraseEncoded.add(alphaZero ? x : (x == 0 ? 26 : x ));  
            phraseEncoded.add(alphaZero ? y : (y == 0 ? 26 : y ));  
        }  
        echoResult("Encoded phrase: ", adder, phraseEncoded);  
    }  
    public static void decrypt(String phrase, boolean alphaZero)  
    {  
        int i, adder = alphaZero ? 1 : 0;  
        int[][] keyMatrix, revKeyMatrix;  
        ArrayList<Integer> phraseToNum = new ArrayList<>();  
        ArrayList<Integer> phraseDecoded = new ArrayList<>();  
        phrase = phrase.replaceAll("[^a-zA-Z]","").toUpperCase();  
        keyMatrix = getKeyMatrix();  
        isValidMatrix(keyMatrix);  
        for(i=0; i < phrase.length(); i++) {  
            phraseToNum.add(phrase.charAt(i) - (64 + adder));  
        }  
        revKeyMatrix = reverseMatrix(keyMatrix);  
        isValidReverseMatrix(keyMatrix, revKeyMatrix);  
        for(i=0; i < phraseToNum.size(); i += 2) {  
            phraseDecoded.add((revKeyMatrix[0][0] * phraseToNum.get(i) + revKeyMatrix[0][1] * phraseToNum.get(i+1)) % 26);  
            phraseDecoded.add((revKeyMatrix[1][0] * phraseToNum.get(i) + revKeyMatrix[1][1] * phraseToNum.get(i+1)) % 26);  
        }  
        echoResult("Decoded phrase: ", adder, phraseDecoded);  
    }  
    //main method  
    public static void main(String[] args) {  
        String opt, phrase;  
        byte[] p;  
        Scanner sc = new Scanner(System.in);  
        System.out.println("Hill Cipher Implementation");  
        System.out.println("1. Encrypt text :");  
        System.out.println("2. Decrypt text :");  
        System.out.println();  
        System.out.print("Select your choice: ");  
        opt = sc.nextLine();  
        switch (opt)  
        {  
            case "1":  
                System.out.print("Enter text to encrypt: ");  
                phrase = sc.nextLine();  
                encrypt(phrase, true);  
                break;  
            case "2":  
                System.out.print("Enter text to decrypt: ");  
                phrase = sc.nextLine();  
                decrypt(phrase, true);  
                break;  
           
        }  
    }  } 