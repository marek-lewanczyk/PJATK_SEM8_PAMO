# Ćwiczenia 01 Programowanie Aplikacji Mobilnych
Termin realizacji: 04.04.2026

Zbuduj aplikację składającą się z następujących fragmentów:
* **Ekran startowy** – zawierający grafikę wprowadzającą.
* **Kalkulator BMI** – użytkownik wpisuje wagę i wzrost, a aplikacja oblicza BMI oraz wyświetla interpretację wyniku.
* **Kalkulator dziennego zapotrzebowania kalorycznego** – oblicza liczbę kalorii, jaką użytkownik może spożywać dziennie na podstawie wzoru Benedicta-Harrisa, uwzględniając wiek, wagę, wzrost i poziom aktywności fizycznej.

---

## Zrzuty ekranu z działającej aplikacji

Zrzuty ekranu znajdują się w katalogu `screenshots/`.

---

## Dokładne wytłumaczenie kodu (linijka po linijce)

Aplikacja, oparta o język Java oraz system widoków XML, składa się z 4 głównych ekranów. Poniżej znajduje się szczegółowe wytłumaczenie logiki zawartej w kodzie dla każdego z nich:

### 1. SplashActivity (Ekran startowy)

**Interfejs (activity_splash.xml):**
Widok oparty o **ConstraintLayout**, w którym na samym środku wycentrowano za pomocą więzów (constraints `constraintTop_toTopOf`, `constraintBottom_toBottomOf`, itd.) komponent **ImageView**. Ładuje on startową grafikę (logo) z zasobów `@drawable/logo`.

**Logika (SplashActivity.java):**
Ekran ma za zadanie wyświetlić się użytkownikowi tylko na chwilę (3 sekundy), a następnie przenieść go do menu głównego. 
```java
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}, 3000);
```
*   `new Handler().postDelayed(...)` – klasa **Handler** pozwala na odłożenie wykonania określonego kodu w czasie. Drugi argument `3000` to czas wyrażony w milisekundach (3 sekundy).
*   `public void run()` – metoda z interfejsu **Runnable**, która wykonuje się po upływie wskazanego czasu.
*   `Intent intent = new Intent(SplashActivity.this, MainActivity.class);` – tworzymy nową intencję (**Intent**), która definiuje chęć przejścia z obecnego kontekstu (`SplashActivity.this`) do nowej aktywności (`MainActivity.class`).
*   `startActivity(intent);` – system Android uruchamia zdefiniowaną aktywność (Menu Główne).
*   `finish();` – niezwykle ważna linijka! Zamyka ona obecną aktywność (Splash), usuwając ją ze stosu (Back Stack). Dzięki temu użytkownik, wciskając przycisk "Wstecz", nie wróci do ekranu ładowania.

### 2. MainActivity (Menu główne)

**Interfejs (activity_main.xml):**
Zawiera dwa przyciski typu **Button**, ułożone w centrum za pomocą pionowego łańcucha (`chainStyle="packed"` w **ConstraintLayout**). Służą one do nawigacji po aplikacji.

**Logika (MainActivity.java):**
```java
Button btnBmi = findViewById(R.id.btnBmi);
Button btnCalorie = findViewById(R.id.btnCalorie);
```
*   `findViewById(R.id...)` – wiąże logiczną zmienną w Javie z konkretnym widokiem zdefiniowanym w pliku XML na podstawie jego ID.

```java
btnBmi.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, BmiActivity.class));
    }
});
```
*   `setOnClickListener(...)` – przypisuje "nasłuchiwacza" zdarzeń, który czeka na kliknięcie przycisku przez użytkownika.
*   Wewnątrz metody `onClick(View v)` tworzymy anonimowy obiekt **Intent** i od razu przekazujemy go do metody `startActivity()`, co skutkuje przekierowaniem użytkownika do ekranu `BmiActivity` (analogicznie dla `CalorieActivity`).

### 3. BmiActivity (Kalkulator BMI)

**Interfejs (activity_bmi.xml):**
Oparty m.in. na **LinearLayout**. Zawiera pola do wprowadzania danych liczbowych – **EditText** z atrybutem `inputType="numberDecimal"`, co wymusza na klawiaturze wyświetlenie tylko cyfr i kropki dziesiętnej. Ponadto znajduje się tu **Button** obliczający oraz specjalny **TextView** na wynik.

**Logika (BmiActivity.java):**
Pobieranie i walidacja danych:
```java
String weightStr = etWeight.getText().toString();
String heightStr = etHeight.getText().toString();

if (TextUtils.isEmpty(weightStr) || TextUtils.isEmpty(heightStr)) { ... }
```
*   `getText().toString()` – wyciąga tekst wpisany przez użytkownika. Zwracany typ to klasa narzędziowa Editable, dlatego wymuszamy konwersję do bazowego `String`.
*   Zabezpieczenie przy pomocy metody `TextUtils.isEmpty(...)` sprawdza, czy pola nie puste (zapobiegając ewentualnemu `NumberFormatException` (Crash aplikacji) w następnej linijce przy próbie przeliczenia pustego Stringa na liczbę typu Double).

Obliczenia matematyczne i instrukcje warunkowe:
```java
double weight = Double.parseDouble(weightStr);
double heightCm = Double.parseDouble(heightStr);
double heightM = heightCm / 100.0;
double bmi = weight / (heightM * heightM);
```
*   Konwersja danych tekstowych do formatu zmiennoprzecinkowego (`Double.parseDouble()`), zamiana jednostki wzrostu z cm na metry i zastosowanie klasycznego wzoru.

```java
String category;
if (bmi < 18.5) {
    category = "Niedowaga";
} else if (bmi <= 24.9) {
    category = "Norma";
} else if (bmi <= 29.9) {
    category = "Nadwaga";
} else {
    category = "Otyłość";
}
```
*   Zastosowanie łańcucha logiki warunkowej `if / else if / else`. Warunki są sprawdzane po kolei od góry. Jeżeli system znajdzie dopasowanie (np. wynik 22, czyli `bmi <= 24.9`), zapisuje tekst "Norma" do nowej zmiennej i natychmiast opuszcza blok wykonawczy.
*   Z pomocą `String.format(Locale.getDefault(), "%.2f", bmi)` wynik został estetycznie sformatowany (zaokrąglony i ucięty) do 2 miejsc po przecinku w finalnym widoku **TextView**.

### 4. CalorieActivity (Zapotrzebowanie Kaloryczne)

**Interfejs (activity_calorie.xml):**
Więcej pobieranych danych (**EditText** na Wzg, Wagę, Wzrost). Tu kluczowe jest użycie klasy **RadioGroup** z dwoma wewnętrznymi **RadioButton**, które gwarantują wzajemne wykluczanie się wyboru płci. W interfejsie umieszczono także wyżej wymieniony widok **Spinner** do tworzenia eleganckich rozwijanych list wyboru.

**Logika (CalorieActivity.java):**
Konfiguracja rozwijanej listy wielokrotnego wyboru:
```java
String[] activities = {"Brak", "Niska", "Średnia", "Wysoka"};
ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, activities);
spinnerActivity.setAdapter(adapter);
```
*   Aby wypełnić **Spinner** danymi (elementami typu String), musimy zastosować tzw. **Adapter**. Klasa `ArrayAdapter` służy za "pomost" łączący czyste dane w języku Java (nasza ułożona tablica pożądanych poziomów aktywności) w strukturę listownego widoku graficznego w Androidzie.

Po kliknięciu przycisku wykonywany jest poniższy blok obliczeniowy:
```java
boolean isMale = rbMale.isChecked();
double bmr;
if (isMale) {
    bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
} else {
    bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
}
```
*   Metoda `isChecked()` wywołana na męskim **RadioButton** sprawdza, która opcja w przycisku grupy została fizycznie wybrana przez użytkownika (zwraca typ logiczny binarnego `boolean`).
*   W zależności od wartości parametru (IsMale to true / false), stosowany jest inny wzór Harrisa-Benedicta.

Obliczenie mnożnika PAL i zaokrąglanie wartości:
```java
double pal = 1.2;
int selectedPosition = spinnerActivity.getSelectedItemPosition();
if (selectedPosition == 1) { // 1 = Niska (z racji indeksowania tablicy od 0)
    pal = 1.375;
} else if (selectedPosition == 2) { // 2 = Średnia
    pal = 1.55;
} else if (selectedPosition == 3) { // 3 = Wysoka
    pal = 1.725;
}

double tdee = bmr * pal;
int tdeeRounded = (int) Math.round(tdee);
```
*   `spinnerActivity.getSelectedItemPosition()` – wyłuskuje pozycję wybranego przez użytkownika elementu. Indeksowanie, jak w tablicy, zaczyna się od `0` ("Brak"), więc pozycje `1,2,3` to kolejne (odpowiednio mnożone) poziomy aktywności PAL.
*   Ostatecznie całkowite dzienne zapotrzebowanie (`tdee`) jest zaokrąglone do liczb całkowitych przy pomocy statycznej metody pomocniczej `Math.round()` oraz zrzutowane (castowane) do typu `int`. Całość umieszczana jest klasycznie w **TextView**.

