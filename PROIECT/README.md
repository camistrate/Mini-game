CUM TE JOCI:
1. Linia de comanda
In clasa Game, metoda play() se alege daca se doreste a se folosi harta generata random sau nu:
- pentru harta generata random decometam:
  Random r = new Random();
  board = Grid.createRandomGrid(4 + r.nextInt(6), 4 + r.nextInt(6), character);
- pentru harta hardcodata decomentam:
  board = Grid.createGrid(5, 5, character);

2. Interfata grafica
   In clasa CharacterSelectionPage, metoda selectCharacter(Characterrr character) se alege daca se doreste a se folosi harta generata random sau nu:
- pentru harta generata random decometam:
  Random r = new Random();
  Grid grid = Grid.createRandomGrid(4 + r.nextInt(6), 4 + r.nextInt(6), character);
  new GamePage(grid, character);
  dispose();
- pentru harta hardcodata decomentam:
  Grid grid = Grid.createGrid(5, 5, character);
  new GamePage(grid, character);
  dispose();


1. Arhitectura proiectului

Pachetul org.example - include clasele principale care controleaza fluxul jocului:
- Game: Gestioneaza logica generala, de la autentificarea utilizatorilor pana la finalizarea nivelurilor.
- Test: Punctul de intrare al aplicatiei.

Pachetul org.example.player - contine structura de date necesara pentru gestionarea utilizatorilor:
- Account: Reprezinta conturile utilizatorilor, incluzand informatii despre personajele asociate.
- Credentials: Gestionarea email-ului si parolei.

Pachetul org.example.game - dedicat gestionarii hartii si interactiunilor pe aceasta:
- Grid: Reprezinta harta jocului, o matrice de celule.
- Cell: Fiecare celula are un tip specific (PLAYER, ENEMY, SANCTUARY, PORTAL, VOID).
- Events: Gestioneaza interactiunile cu celulele (ex: lupte, regenerare viata, trecerea la urmatorul nivel).

Pachetul org.example.characters
Include toate entitatile care pot interactiona in joc:
- Characterrr: Clasa abstracta pentru personajele jucatorului (Warrior, Mage, Rogue).
- Entity: Clasa abstracta comuna pentru toate entitatile, inclusiv dusmanii.
- Enemy: O entitate inamic generata aleatoriu.

Pachetul org.example.characters.spells
Gestioneaza vrajile utilizate de entitati:
- Spell: Clasa abstracta pentru vraji.
- Fire, Ice, Earth: Implementari concrete ale vrajilor.

Pachetul org.example.graphicInterface
Gestioneaza interfata grafica a jocului folosind biblioteca Swing:
- LoginPage: Pagina de autentificare.
- CharacterSelectionPage: Pagina de selectare a caracterului.
- GamePage: Pagina cu tabla de joc.
-  EnemyEventPage: Pagina dedicata luptei dintre caracter si un inamic.
- LabelOutputStream: Gestioneaza mesajele din consola.


2. Logica jocului

Autentificarea utilizatorului
Jocul incepe prin autentificarea utilizatorului utilizand un fisier JSON care stocheaza datele utilizatorilor:
- Email-ul si parola sunt validate folosind clasa Credentials.
- Daca autentificarea este reusita, utilizatorul isi poate alege unul dintre personajele asociate contului sau.

Gestionarea personajelor
Fiecare personaj este reprezentat de o instanta a unei clase derivate din Characterrr (Warrior, Mage, Rogue).
- Atribute precum nivelul, experienta si caracteristicile (forta, dexteritate, carisma) sunt gestionate separat.
- Personajele se pot imbunatati trecand prin nivele si castigand experienta.

Generarea hartii
Harta este gestionata de clasa Grid:
- Fiecare harta este o matrice de celule (Cell), generate fie hardcodat, fie aleatoriu (createRandomGrid).
- Tipurile de celule sunt definite prin enumerarea CellEntityType.
- Utilizatorul navigheaza pe harta folosind tastele W, A, S, D.

Interactiunea cu celulele
Evenimentele sunt gestionate prin clasa Events. in functie de tipul celulei:
- ENEMY: Initiaza o lupta intre jucator si inamic.
- SANCTUARY: Ofera regenerare de viata si mana.
- PORTAL: Permite avansarea la urmatorul nivel.

Luptele
Luptele au loc intre jucator si un inamic si sunt formate din mai multe runde:
- Jucatorul poate alege intre un atac standard si utilizarea unei abilitati (daca are suficienta mana).
- Inamicul ataca aleatoriu, folosind abilitati sau atacuri standard.
- Luptele continua pana cand una dintre entitati ramane fara viata.

3. Interfata grafica

Pagina de autentificare:
- Pagina de autentificare este punctul de intrare in joc.
- Aceasta permite utilizatorului sa introduca datele de autentificare (email si parola).
- Daca autentificarea reuseste, utilizatorul este directionat catre pagina de selectie a caracterului.
- Aceasta contine un panou central dedicat login-ului, care include campuri pentru email si parola, 
un buton de "Login" si un mesaj de eroare in caz de date incorecte.


Pagina de selectie a unui caracter:
- Aceasta pagina permite utilizatorului sa selecteze un personaj din lista disponibila.
- Fiecare personaj este afisat intr-un card individual, care include:
  - O imagine reprezentativa.
  - Numele, nivelul, experienta si tipul personajului.
  - Un buton de selectie care permite continuarea jocului cu acel personaj.

Pagina principala a jocului:
- Pagina principala a jocului ofera o reprezentare vizuala a hartii si controle pentru deplasare si lupta.
- Harta jocului este reprezentata sub forma de matrice cu campuri, fiecare camp corespunzand unei celule de pe harta.
- In partea dreapta sunt afisate detalii despre caracter, precum nivelul, experienta, sanatatea si mana.
- In partea de jos exista o zona pentru afisarea mesajelor despre actiunile curente.
- Metoda updateGridUI() actualizeaza pagina si datele la fiecare mutare.
- Evenimentele sunt gestionate de metoda handleCellEvent().

Pagina bataliei:
- Pagina dedicata luptelor intre jucator si inamic.
- Fiecare entitate (jucatorul si inamicul) are propriul panou care afiseaza sanatatea, mana si o imagine reprezentativa.
- In centru, utilizatorul poate alege sa atace sau sa foloseasca o abilitate.
- Mesajele despre actiuni sunt afisate in sectiunea inferioara.
- Include intarzieri intre actiuni pentru a oferi un ritm mai natural luptei.