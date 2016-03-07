Payment Tracker README
======================

Spuštění programu
-----------------
$ mvn clean package
$ java -cp "target/payment-tracker.jar:target/dependency/*" cz.mamuf.test.paymenttracker.Main


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