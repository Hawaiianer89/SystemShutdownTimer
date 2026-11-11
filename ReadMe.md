

# ShutDown-Timer

Ein kleines JavaFX-Programm, das den Computer nach einer eingegebenen Zeit oder Uhrzeit herunterfährt.

Praktisch zum Beispiel, wenn du abends noch einen Film am Computer schaust und nicht noch einmal aufstehen möchtest, um den Computer manuell herunterzufahren.

![Programmstart](Screenshots_md/Start.png)

## Funktionen

- **Zwei Eingabearten** (es darf immer nur eines der beiden Felder ausgefüllt sein):
  - **Minuten:** Der PC fährt nach X Minuten herunter (1 bis 1440 Minuten, also maximal 24 Stunden).
  - **Uhrzeit:** Der PC fährt zu einer bestimmten Uhrzeit herunter (Format `hh:mm`). Liegt die Uhrzeit bereits in der Vergangenheit, gilt sie für den nächsten Tag.
- **Live-Countdown:** Anzeige im Format `hh:mm:ss`, die sofort beim Start sichtbar ist.
- **Farbwechsel nach Restzeit:** Damit man die verbleibende Zeit auch aus der Ferne einschätzen kann:



   - 🔵 **Blau:** mehr als 10 Minuten
   - 🟡 **Gelb:** weniger als 10 Minuten
   - 🔴 **Rot:** weniger als 2 Minuten


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

- **JDK:** getestet mit JDK 26.0.1 (JavaFX 21 benötigt mindestens JDK 17)
- **JavaFX:** getestet mit 21.0.6

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
Das Projekt ist bewusst **nicht-modular** aufgebaut (ohne `module-info.java`). Die JavaFX-JARs liegen im Eclipse-**Modulepath**.

### Warnungen in der Konsole
Beim Start können Warnungen erscheinen, zum Beispiel zu `restricted method` oder `sun.misc.Unsafe`.
Sie stammen von JavaFX selbst und treten auf, wenn die JDK neuer ist als die JavaFX-Version. Das Programm läuft trotzdem.
Die erste Warnung lässt sich mit diesem VM-Argument ausblenden (Run Configurations → Arguments → VM arguments):

```
--enable-native-access=javafx.graphics
```

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







