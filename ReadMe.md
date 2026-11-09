

# ShutDown-Timer

Ein kleines JavaFX-Programm, das den Computer nach einer eingegebenen Zeit oder Uhrzeit herunterfährt.

Praktisch zum Beispiel, wenn du abends noch einen Film am Computer schaust und nicht noch einmal aufstehen möchtest, um den Computer manuell herunterzufahren.

![Programmstart](Screenshots_md/Start.png)

## Funktionen

- **Zwei Eingabearten** (es darf immer nur eines der beiden Felder ausgefüllt sein):
  - **Minuten:** Der PC fährt nach X Minuten herunter (1 bis 1440 Minuten, also maximal 24 Stunden).
  - **Uhrzeit:** Der PC fährt zu einer bestimmten Uhrzeit herunter (Format `hh:mm`). Liegt die Uhrzeit bereits in der Vergangenheit, gilt sie für den nächsten Tag.
- **Live-Countdown:** Anzeige im Format `hh:mm:ss`, der sofort beim Start sichtbar ist.
- **Farbwechsel nach Restzeit:** Damit man die verbleibende Zeit auch aus der Ferne einschätzen kann:


<div style="display: flex; justify-content: space-around; text-align: center; font-weight: bold; margin-bottom: 10px;">
    <div style="flex: 1; color: #3498db;">Blau: mehr als 10 Minuten</div>
    <div style="flex: 1; color: #f1c40f;">Gelb: weniger als 10 Minuten</div>
    <div style="flex: 1; color: #e74c3c;">Rot: weniger als 2 Minuten</div>
</div>

![Farbliche Statusanzeige](Screenshots_md/Farben.png)

- **Pause und Fortsetzen:** Nur im Minuten-Modus möglich.
- **Abbrechen:** Jederzeit möglich.
- **Eingabeprüfung:** Fehlermeldung bei ungültigen Werten.
- **Dunkles Design:** Benutzeroberfläche mit dunklem Farbschema.

## Unterstützte Betriebssysteme


| System | Befehl |
| :--- | :--- |
| Windows | `shutdown -s -f -t 0` |
| Linux | `systemctl poweroff` |
| macOS | `osascript -e 'tell application "System Events" to shut down'` |

Wird das Betriebssystem **nicht erkannt**, erscheint eine Fehlermeldung und es passiert nichts.


## Voraussetzungen

- **Java Development Kit (JDK):** Getestet mit **OpenJDK 26** (mindestens Java 17/21 empfohlen).
- **JavaFX:** Passend zur JDK-Version eingebunden.

Du kannst deine installierte Java-Version im Terminal mit folgendem Befehl überprüfen:

```bash
java -version
```


## Starten & Einrichten (Eclipse)

Da JavaFX nicht mehr standardmäßig im JDK enthalten ist, müssen die Bibliotheken manuell im Projekt hinterlegt werden:

1. **Projekt vorbereiten:** Projekt klonen oder herunterladen und in Eclipse öffnen.
2. **JavaFX SDK herunterladen:** Das passende *JavaFX SDK* (z. B. Version 21+) passend zu deinem Betriebssystem herunterladen und entpacken.
3. **Build Path konfigurieren:**
    - Rechtsklick auf das Projekt -> **Properties** -> **Java Build Path**.
    - Auf den Reiter **Libraries** wechseln.
    - Den **Modulepath** auswählen und rechts auf **Add External JARs...** klicken.
    - In den entpackten JavaFX-Ordner in das `lib`-Verzeichnis navigieren und alle `.jar`-Dateien auswählen.
    - Mit **Apply and Close** bestätigen.
4. **Ausführen:** Die `Main.java` mit Rechtsklick -> *Run As* -> *Java Application* starten.


### Hinweis zum Projekt-Setup
Das Projekt ist bewusst **nicht-modular** aufgebaut (ohne `module-info.java`), um Konfigurationskonflikte zu vermeiden. Die JavaFX-JARs werden beim Import in Eclipse automatisch über den Classpath geladen.


## Starten

1. Projekt klonen oder herunterladen.
2. Projekt in einer IDE öffnen (z. B. Eclipse) und JavaFX einbinden.
3. `Main.java` ausführen.

## Bedienung

1. **Minuten** oder **Uhrzeit** (`hh:mm`) eintragen (nicht beides).
2. Auf **Start** klicken.
3. Der Countdown läuft. Mit **Pause** (nur im Minuten-Modus) oder **Abbrechen** lässt er sich steuern.

## Wichtige Hinweise

- **Alles vorher speichern!** Der Befehl enthält `-f` (erzwingt das Schließen). Programme werden ohne Rückfrage beendet. Nicht gespeicherte Arbeit geht verloren.
- Der Timer zählt Sekunden-Ticks. Geht der Rechner zwischendurch in den Ruhezustand, kann der Shutdown später als geplant erfolgen, besonders im Uhrzeit-Modus.
- Wird das Fenster geschlossen, läuft der Timer nicht weiter und es erfolgt kein Shutdown.

## Mögliche Erweiterungen

- [ ] Enter-Taste startet den Timer.
- [ ] Styles in eine CSS-Datei auslagern.
- [ ] Anzeige Uhrzeit wann Gerät herunter fährt.

## Technik

- Java
- JavaFX (`Timeline` für den Countdown, `VBox` für das Layout)
- `java.time` (`LocalTime`, `ChronoUnit`) für die Uhrzeitberechnung







