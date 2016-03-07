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