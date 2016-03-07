Payment Tracker README
======================

Spuštění programu
-----------------
$ mvn clean package
$ java -cp "target/payment-tracker.jar:target/dependency/*" cz.mamuf.test.paymenttracker.Main

nebo pro načtení/zápis do souboru:

$ java -cp "target/payment-tracker.jar:target/dependency/*" cz.mamuf.test.paymenttracker.Main [cesta k souboru]


Používání
---------
Program dle zadání očekává vstup '<CCC> <value>', kde <CCC> je měna a <value> 
částka, nebo 'quit' pro ukončení.


Ošetření vstupů a chyb
----------------------
 - Zadá-li uživatel neplatný vstup, program jej na to upozorní a vyzve jej
	k novému zadání.
 - Chyby přístupu k souborům nejsou ošetřeny. 
 - Pokud zadaný soubor neexistuje, je vytvořen po zadání první platby. 
 - Pokud již existuje, načtou se stávající záznamy. 
 - Pokud je stávající formát souboru neplatný, program skončí s výjimkou.


Poznámky ke kódu
----------------
V návaznosti na náš pohovor jsem se začal učit nové vlastnosti Javy 1.8 
a rozhodl jsem se tuto úlohu naprogramovat s jejich pomocí. Proto používám 
streamy a lambda výrazy, kde je to vhodné.

PaymentStore - Bylo by vhodnější, aby metody vyhazovaly potomka výjimky
PaymentTrackerException namísto IOException, ale pro účely tohoto úkolu je to
zbytečné. V případě rozšíření programu bych to v rámci refaktoringu zřejmě
udělal.

ConsoleUI - Uznávám, že to není úplně nejlepší řešení. Bohužel v čisté Javě
zřejmě nelze snadno kombinovat načítání vstupu uvozené popiskem a průběžný
výstup. Nepodařilo se mi zařídit to tak, aby uživatel vždy viděl, co napsal,
a to pohromadě. Pokud dojde k výpisu na výstup uprostřed rozepsaného vstupu,
vstupní text bude na obrazovce rozdělen.