package com.apw;


class Hamming {

    int[] wiad_in;
    String wiad_out = "";
    String wiad_out_pop = "";
    int miejsce_bledu;
    int _parity_count;
    String wiadomosc_poprawiona = "";
    String wiadomosc_oryginalna = "";


    Hamming(int[] a) {
        wiad_in = new int[a.length];
        wiad_in = a;
        for (int i = 0; i < wiad_in.length / 2; i++) {
            int temp = wiad_in[i];
            wiad_in[i] = wiad_in[wiad_in.length - i - 1];
            wiad_in[wiad_in.length - i - 1] = temp;
        }
    }

    Hamming(String s) {
        int[] a = stringToIntArray(s);
        wiad_in = new int[a.length];
        wiad_in = a;
        for (int i = 0; i < wiad_in.length / 2; i++) {
            int temp = wiad_in[i];
            wiad_in[i] = wiad_in[wiad_in.length - i - 1];
            wiad_in[wiad_in.length - i - 1] = temp;
        }
    }

    static int[] generateCode(int[] a) {
        // zainicjowanie zwracanej tablicy
        int[] b = new int[1000];
        // wyszukanie bitow parzystosci
        int i = 0, parity_count = 0, j = 0, k = 0;
        while (i < a.length) {
            // 2^(bit parzystosci) musi byc rowny aktualnej pozycji
            // +1 poniewaz indexowanie od zera.
            if (Math.pow(2, parity_count) == i + parity_count + 1) {
                parity_count++;
            } else {
                i++;
            }
        }
        // dlugosc zwracanej tablicy to dlugosc wejsciowej + ilosc dodawanych bitow
        b = new int[a.length + parity_count];
        // zainicjowanie '2' zeby wymusic pierwsze dwa bity ktore sa zawsze
        for (i = 1; i <= b.length; i++) {
            if (Math.pow(2, j) == i) {
                // znalezienie indexu dla bitu parzystosci

                b[i - 1] = 2;
                j++;
            } else {
                b[k + j] = a[k++];
            }
        }
        for (i = 0; i < parity_count; i++) {
            // ustawianie bitow parzystosci na swoich miejscach

            b[((int) Math.pow(2, i)) - 1] = getParity(b, i);
        }
        return b;
    }

    static int getParity(int[] b, int power) {
        int parity = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] != 2) {
                // jeżeli i nie zawiera nieustalonej wartosci
                // zapamietujmy i+1
                // zamieniamy na
                int k = i + 1;
                //String s = Integer.toBinaryString(k);
                String s = Integer.toString(k, 2);
                //jezeli bit na pozycji 2^(power) ma wartosc 1
                //sprawdzamy zapamietana wartosc
                //jezeli jest 1 or 0, wtedy obliczamy wartosc parzystosci
                int x = ((Integer.parseInt(s)) / ((int) Math.pow(10, power))) % 10;
                if (x == 1) {
                    if (b[i] == 1) {
                        parity = (parity + 1) % 2;
                    }
                }
            }
        }
        return parity;
    }

    public int[] stringToIntArray(String tekst) {
        int dlugosc = tekst.length();
        int tab[] = new int[dlugosc];
        char c;
        for (int i = 0; i < dlugosc; i++) {
            c = tekst.charAt(i);
            tab[i] = Character.getNumericValue(c);
        }
        return tab;
    }

    void licz() {
        int[] b = generateCode(wiad_in);

        for (int i = 0; i < b.length; i++) {
            wiad_out += b[b.length - i - 1];
        }
        _parity_count = wiad_out.length() - wiad_in.length;
    }

    String receive(String przeklamana) {
        if (przeklamana.equals(wiad_out)) {
//            return "Wiadomości są takie same";
        }

        int[] a;
        if (przeklamana.isEmpty()) {
            return "";
        } else {
            a = stringToIntArray(przeklamana);
        }

        System.out.println(wiad_out);
        int parity_count = a.length - wiad_in.length; // nie dodajemy bitow w przypadku bledow tylko zaieniamy
        int power;
        int[] parity = new int[parity_count];
        // 'parity' zawiera wartosci sprawdzonych bitow parzystosci
        String syndrome = "";
        // 'syndrome' zawiera miejsce bledu
        for (power = 0; power < parity_count; power++) {
            // sprawdzamy taką ilosc razy ile dodanyc bitow przystosci
            for (int i = 0; i < a.length; i++) {
                // wyciaganie bitow z pozycji 2^(power):
                int k = i + 1;
                String s = Integer.toBinaryString(k);
                int bit = ((Integer.parseInt(s)) / ((int) Math.pow(10, power))) % 10;
                if (bit == 1) {
                    if (a[i] == 1) {
                        parity[power] = (parity[power] + 1) % 2;
                    }
                }
            }
            syndrome = parity[power] + syndrome;
        }
        System.out.println(syndrome + " syndrome");
        int error_location = Integer.parseInt(syndrome, 2);
        //int error_location = Integer.parseInt(syndrome, 2);

        if (error_location != 0) {
            try {
                System.out.println("Błąd na pozycji " + error_location + ".");

                miejsce_bledu = error_location - 1;
                //System.out.println("Błąd na pozycji o indexie " + miejsce_bledu + "." , "Wykryto błąd.");
                a[error_location - 1] = (a[error_location - 1] + 1) % 2;
                System.out.println("Poprawiony ciag to:");
                for (int i = 0; i < a.length; i++) {
                    wiadomosc_poprawiona += a[a.length - i - 1];
                    System.out.print(a[a.length - i - 1]);
                }
                System.out.println();
            } catch (Exception e) {

            }
        } else {
            System.out.println("Brak błędów");
            return "Brak błędów";
        }

        // wyciaganie wiadomosci z poprawionego ciagu
        for (int i = 0; i < a.length; i++) {
            wiad_out_pop += a[a.length - i - 1];
        }
        System.out.println("Oryginalna wiadomosc to:");
        power = parity_count - 1;
        for (int i = a.length; i > 0; i--) {
            if (Math.pow(2, power) != i) {
                System.out.print(a[i - 1]);
                wiadomosc_oryginalna += a[i - 1];
            } else {
                power--;
            }
        }
        System.out.println();
        return "Błąd na pozycji " + error_location + ".";
    }

    public boolean pobierzKod(String przeklamana) {
        int[] a;
        if (przeklamana.isEmpty()) {
            return false;
        } else {
            a = stringToIntArray(przeklamana);
        }
        int parity_count = _parity_count;
        // a[] kod hamminga i numery bitów parzystości dodane do przesyłanych danych
        int power;  // znalezc poprawne bity zeby sprawdzic parzystosc
        int parity[] = new int[parity_count];   // przechowuje wartosci sprawdzonych parzystosci
        String syndrome = new String(); // miejsce błędu
        for (power = 0; power < parity_count; power++) {    // We need to check the parities, the same no of times as the no of parity bits added.
            for (int i = 0; i < a.length; i++) {    // Extracting the bit from 2^(power):
                int k = i + 1;
                String s = Integer.toBinaryString(k);
                int bit = ((Integer.parseInt(s)) / ((int) Math.pow(10, power))) % 10;
                if (bit == 1) {
                    if (a[i] == 1) {
                        parity[power] = (parity[power] + 1) % 2;
                    }
                }
            }
            syndrome = parity[power] + syndrome;
        }
        // This gives us the parity check equation values.
        // Using these values, we will now check if there is a single bit error and then correct it.
        int error_location = Integer.parseInt(syndrome, 2);
        if (error_location != 0) {
            a[error_location - 1] = (a[error_location - 1] + 1) % 2;
            return true;
        }
//        return false;

        String original = "";

        power = parity_count - 1;
        for (int i = a.length; i > 0; i--) {
            if (Math.pow(2, power) != i) {
                original += a[i - 1];
            } else {
                power--;
            }
        }
        String reverse = new StringBuffer(original).reverse().toString();
        System.out.println(reverse);
        return false;
    }
}