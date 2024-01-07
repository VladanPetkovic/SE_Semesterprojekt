# Monster Trading Cards Game (MTCG) - Protocol

Author: Vladan Petkovic

Winter semester 2023

Submit-date: 07.01.2024

---
---

## Design

Die grundsätzliche Projektstruktur wurde in frontend und backend unterteilt. Während nur die Spiellogik "frontend" passiert/dort implementiert wurde, wird im backend der Server, die Verbindung zur Datenbank und alle HTTP-Requests abgehandelt.

Es handelt sich um einen Multithreaded-Server: Es können mehrere Spieler gleichzeitig Abfragen an den Server schicken, ohne jeweils auf die Fertigstellung zuvor eingekommener Abfragen zu warten. Besonderes Augenmerk liegt in der Parallelisierung der Battle-Logik: Der erste Spieler, der ein Battle startet, gelangt in die Battle-Lobby und wird vom nächsten Spieler aus der Battle-Lobby geholt und das Spiel beginnt.

Der zweite Spieler beginnt das Battle und lässt den anderen solange warten bis ein Datenbank-Eintrag vom Battle erstellt wurde.

---
---

## Lessons Learned

### DAOs/Repositories/Models

Durch die dauernde Verwendung der Datenbank und Behandlung von HTTP-Requests, habe ich durch die Erstellung vieler DAOs, Repositories und Models eine wichtige Projektstruktur kennengelernt und werde die genaue Aufteilung von Datenschnittstellen in weiteren Projekten mitnehmen.

### Unit-Tests

Die Verwendung von Unit-Tests war mir bis zu diesem Projekt sehr unbekannt - nach diesem Projekt werde ich stets in größeren Projekten Unit-Tests verwenden, weil explizit kleinere Funktionen sehr gut mit Unit-Tests erprobt werden können. Ich habe die Unit-Tests erst zum Projektende implementiert und dennoch weitere Bugs endeckt, welche durch herkömmliches Debuggen schwierig und langwieriger zu beheben wären.

### Verwendung von locks, synchronized, Threads, Tasks in Java

Sehr interessant finde ich auch die Parallelisierung der Battle-Logik, welche durchaus nicht trivial ist. Derzeit, zum Projektende, würde ich eventuell mit Semaphoren arbeiten und nicht nur locks und synchronized functions verwenden.

### GIT, Branches, ...

Besonders zu Beginn des Semester wiederholte und lernte ich durchaus viele GIT-commands und werde in den nächsten Projekten auch weiter mit GIT-commands arbeiten. Grund dafür ist die Effizienz und Schnelligkeit; ohne das Programm wechseln zu müssen (GithubDesktop) kann sofort commited, gepushed werden.

---
---

## Unit-Test Design

Für die Testung von Funktionen habe ich mich für Unit-Tests entschieden - deshalb wurden ausschließlich Unit-Tests für den "frontend"-Bereich geschrieben, weil im "backend" alle Abfragen zu Datenbank-Abfragen resultieren und diese sofort mittels Select-Statement überprüft werden können.

Für die Gesamtfunktionalität habe ich das bereitgestellte curl-Skript der Lektoren übernommen und nochmals erweitert und abgeändert.

---
---

## Time spent

- UML-Diagramm/Design-Entwurf: 5h
- Erstellung der GIT-commands-Liste: 3h
- Implementierung der frontend Logik mit User-Interface (CMD): 3h
- ELO-Berechnung: 2h
- [verworfene] weitere Implementierung (User-Interface wurde fälschlicherweise begonnen): 5h
- Erweiterung: HTTP-Server: 2h
- [verworfen] Battle-Menu, Game-Menu, Shop-Menu (User-Interface): 5h
- Implementierung der User-DAOs, Repositories, Models und Routen: 6h
- Authentifizierung mit einem Token: 2h
- Implementierung der Card-DAOs, Repositories, Models und Routen: 10h
- Implementierung der Battle-DAOs, Repositories, Models und Routen: 4h
- Multithreaded-Server: 5h
- Frontend-Battle-Logik: 8h
- Unit-Tests, Curl-Skript: 4h
- Protokoll-Erstellung: 2h

> GESAMT: 66 Stunden 

---
---


## Unique Feature

Auf das Unique Feature wurde verzichtet - besonders interessant ist jedoch die Implementierung der Battle-Logik mit einer symmetrischen 3x3 Matrix. Diese kommt zum Einsatz bei der Behandlung der Fälle: water > fire, fire > normal, normal > water. Für weitere Informationen, in der Klasse Battle mit dem untenstehenden Link anschauen.

---
---

## Link to git
https://github.com/VladanPetkovic/SE_Semesterprojekt.git