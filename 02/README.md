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

---

---

# Ćwiczenia 02 Programowanie Aplikacji Mobilnych
Termin realizacji: 16.05.2026

## Rozbuduj istniejącą aplikację

### Wykres zmian BMI (`BmiChartActivity`)

Do aplikacji dodano ekran z interaktywnym wykresem liniowym prezentującym historię BMI na przestrzeni 12 miesięcy. Dane są zamockowane i pokazują realistyczną redukcję wagi (27.5 → 24.1, czyli przejście z kategorii Nadwaga do Normy).

**Użyta biblioteka:** [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) (via JitPack)

**Kluczowe elementy implementacji:**
```java
// Zamockowane dane wejściowe
private static final float[] BMI_VALUES = {
    27.5f, 27.2f, 26.8f, 26.5f, 26.1f, 25.8f,
    25.4f, 25.1f, 24.8f, 24.6f, 24.3f, 24.1f
};

// Wypełnienie wykresu punktami
List<Entry> entries = new ArrayList<>();
for (int i = 0; i < BMI_VALUES.length; i++) {
    entries.add(new Entry(i, BMI_VALUES[i]));
}
LineDataSet dataSet = new LineDataSet(entries, "BMI");
```
*   `Entry(x, y)` – pojedynczy punkt na wykresie. Oś X to indeks miesiąca, oś Y to wartość BMI.
*   `LineDataSet` – zbiór punktów tworzący jedną linię na wykresie, z możliwością stylizacji (kolor, grubość, wypełnienie obszaru pod linią).
*   `LimitLine` – pozioma linia graniczna, zastosowana dla wartości 25.0 (Nadwaga) i 18.5 (Niedowaga), wizualnie zaznaczając strefy na osi Y.
*   `IndexAxisValueFormatter` – zamienia numeryczne indeksy osi X na etykiety tekstowe (nazwy miesięcy: "Sty", "Lut", ...).

### Lista zakupów (`ShoppingListActivity` + RecyclerView)

Do aplikacji dodano ekran z listą składników potrzebnych do przygotowania przepisu **Spaghetti Bolognese**. Lista umożliwia odznaczanie zakupionych produktów.

**Architektura komponentów:**

| Plik | Rola |
|---|---|
| `ShoppingItem.java` | Model danych — nazwa, ilość, stan zakupu |
| `ShoppingListAdapter.java` | Adapter RecyclerView |
| `item_shopping.xml` | Layout pojedynczego wiersza listy |
| `ShoppingListActivity.java` | Activity z danymi i konfiguracją RecyclerView |

**Konfiguracja RecyclerView:**
```java
RecyclerView recyclerView = findViewById(R.id.recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
recyclerView.setAdapter(new ShoppingListAdapter(buildShoppingList()));
```
*   `LinearLayoutManager` – wyświetla elementy w pionowej liście jeden pod drugim.
*   `DividerItemDecoration` – automatycznie rysuje poziomą linię separatora między elementami.
*   `setAdapter(...)` – łączy RecyclerView z adapterem dostarczającym dane i widoki.

**Wzorzec ViewHolder w adapterze:**
```java
static class ViewHolder extends RecyclerView.ViewHolder {
    final CheckBox checkBox;
    final TextView tvName;
    final TextView tvQuantity;

    ViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
        tvName   = itemView.findViewById(R.id.tvItemName);
        tvQuantity = itemView.findViewById(R.id.tvItemQuantity);
    }
}
```
*   **ViewHolder** przechowuje referencje do widoków pojedynczego wiersza. RecyclerView recykluje obiekty ViewHolder przy przewijaniu zamiast tworzyć nowe, co znacznie poprawia wydajność.

**Obsługa stanu CheckBox — zapobieganie fałszywym zdarzeniom:**
```java
// Najpierw usuwamy stary listener, żeby uniknąć wywołania przy recyklingu widoku
holder.checkBox.setOnCheckedChangeListener(null);
holder.checkBox.setChecked(item.isPurchased());
applyStrikethrough(holder, item.isPurchased());

holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
    item.setPurchased(isChecked);
    applyStrikethrough(holder, isChecked);
});
```
*   Ustawienie `null` przed `setChecked()` jest kluczowe — bez tego przy recyklingu widoku zmiana stanu checkboxa (z poprzedniego elementu na nowy) wywołałaby listener i błędnie modyfikowałaby dane modelu.

**Efekt wizualny przekreślenia zakupionych produktów:**
```java
private void applyStrikethrough(ViewHolder holder, boolean strike) {
    if (strike) {
        holder.tvName.setPaintFlags(flags | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvName.setAlpha(0.4f);
    } else {
        holder.tvName.setPaintFlags(flags & ~Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvName.setAlpha(1f);
    }
}
```
*   `Paint.STRIKE_THRU_TEXT_FLAG` – flaga bitowa dodawana do flag renderowania tekstu, powodująca rysowanie poziomej linii przez środek napisu.
*   Operacja `& ~FLAG` – bitowe AND z zanegowaną flagą, służące do bezpiecznego usunięcia konkretnego bitu bez wpływu na pozostałe.

---

## Testy

### Analiza statyczna — Android Lint

Raport HTML generowany komendą:
```bash
./gradlew lint
# Wynik: app/build/reports/lint-results-debug.html
```

**Wyniki przed poprawkami (33 ostrzeżenia):**

| Kategoria | Liczba |
|---|---|
| `HardcodedText` — stringi wpisane na sztywno w XML | 20 |
| `Autofill` — brak atrybutu `autofillHints` w `EditText` | 5 |
| `GradleDependency` — przestarzałe wersje zależności | 3 |
| `AndroidGradlePluginVersion` — nowsze wersje AGP/Gradle | 2 |
| `UnusedResources` — nieużywany zasób `R.color.black` | 1 |
| `Overdraw` — podwójne rysowanie tła w `item_shopping.xml` | 1 |
| `IconLocation` — ikona w folderze niezależnym od gęstości | 1 |

**Zastosowane poprawki:**
- Wszystkie 20 stringów przeniesiono z layoutów XML do `res/values/strings.xml` i zastąpiono referencjami `@string/...`
- Dodano `android:importantForAutofill="no"` do pól kalkulatorów (dane numeryczne, nie dane osobowe)
- Zaktualizowano wersje: `material` 1.13→1.14, `activity` 1.12→1.13, `recyclerview` 1.3.2→1.4.0
- Usunięto nieużywany `R.color.black` z `colors.xml`; komunikaty błędów w Java zostały przepięte na `getString(R.string....)`
- Dodano `tools:ignore="Overdraw"` — użycie `selectableItemBackground` jest świadome (efekt ripple przy kliknięciu)

**Wyniki po poprawkach (3 ostrzeżenia):** wyłącznie informacje o dostępnych nowszych wersjach narzędzi deweloperskich, nie błędy kodu.

---

### Testy jednostkowe JUnit

Logika biznesowa została wydzielona do osobnych klas, co umożliwia testowanie bez uruchamiania Androida:

**`BmiCalculator.java`** — obliczanie i kategoryzacja BMI:
```java
public static double calculate(double weightKg, double heightCm) {
    if (heightCm <= 0) throw new IllegalArgumentException("Height must be positive");
    double heightM = heightCm / 100.0;
    return weightKg / (heightM * heightM);
}

public static Category categorize(double bmi) {
    if (bmi < 18.5) return Category.UNDERWEIGHT;
    if (bmi <= 24.9) return Category.NORMAL;
    if (bmi <= 29.9) return Category.OVERWEIGHT;
    return Category.OBESE;
}
```

**`CalorieCalculator.java`** — wzór Harrisa-Benedicta i mnożnik PAL:
```java
public static double calculateBmr(double weightKg, double heightCm, double age, boolean male) {
    if (male) return 88.362 + (13.397 * weightKg) + (4.799 * heightCm) - (5.677 * age);
    else      return 447.593 + (9.247 * weightKg) + (3.098 * heightCm) - (4.330 * age);
}
```

**Uruchamianie testów JUnit (nie wymaga emulatora):**
```bash
./gradlew testDebugUnitTest
# Wyniki: app/build/reports/tests/testDebugUnitTest/index.html
```

**Wyniki: 22 testy, 0 błędów, 100% powodzenia.**

Przykładowe przypadki testowe w `BmiCalculatorTest.java`:
- Poprawne obliczenie BMI dla znanych danych wejściowych (70 kg / 175 cm → 22.86)
- Wyjątek `IllegalArgumentException` przy zerowej lub ujemnej wysokości
- Wartości graniczne kategorii: 18.5 (granica Niedowaga/Norma), 25.0 (Norma/Nadwaga), 30.0 (Nadwaga/Otyłość)
- Poprawne polskie etykiety dla każdej kategorii

---

### Testy Espresso

Testy instrumentalne uruchamiane na emulatorze/urządzeniu, weryfikujące zachowanie UI.

**`MainActivityEspressoTest.java`** — nawigacja z menu głównego:
```java
@Test
public void clickBmiButton_opensBmiActivity() {
    onView(withId(R.id.btnBmi)).perform(click());
    onView(withId(R.id.btnCalculateBmi)).check(matches(isDisplayed()));
}
```

**`BmiActivityEspressoTest.java`** — pełny przepływ obliczania BMI:
```java
@Test
public void calculateBmi_normalWeight_showsCorrectResult() {
    onView(withId(R.id.etWeightBmi)).perform(typeText("70"), closeSoftKeyboard());
    onView(withId(R.id.etHeightBmi)).perform(typeText("175"), closeSoftKeyboard());
    onView(withId(R.id.btnCalculateBmi)).perform(click());

    onView(withId(R.id.tvResultBmi)).check(matches(withText(containsString("22.86"))));
    onView(withId(R.id.tvResultBmi)).check(matches(withText(containsString("Norma"))));
}
```

**Uruchamianie testów Espresso (wymaga emulatora):**
```bash
./gradlew connectedDebugAndroidTest
```

---

### Monkey — test stabilności

Narzędzie Monkey wysyła do aplikacji losowe zdarzenia (kliknięcia, gesty, klawisze) w celu wykrycia crashy.

**Uruchomienie:**
```bash
adb shell monkey -p com.s29420.zad01 --throttle 150 -v 500
```

| Parametr | Znaczenie |
|---|---|
| `-p com.s29420.zad01` | Ograniczenie zdarzeń do naszej aplikacji |
| `--throttle 150` | 150 ms przerwy między zdarzeniami |
| `-v` | Tryb szczegółowy (verbose) |
| `500` | Liczba losowych zdarzeń |

**Wynik:**
```
Events injected: 500
Dropped: keys=0 pointers=0 trackballs=0
// Monkey finished
```

Aplikacja przeszła test bez żadnych crashy. Zrzut ekranu z wyniku działania Monkey zapisany jest w pliku `monkey_screenshot.png`.
