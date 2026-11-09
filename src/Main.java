



import javafx.application.Application;				// Basis-Klasse,				Ausgangspunkt für JavaFX-Anwendung
import javafx.application.Platform;					// Plattform-Klasse,			Verwaltet die JavaFX-Anwendung und ermöglicht z. B. das Beenden
import javafx.animation.KeyFrame;					// Zeit-Rahmen,					definiert einzelnen Zeitpunkt für Animation oder Timer-Tick
import javafx.animation.Timeline;					// ZeitLeiste,					spielt eine Abfolge von KeyFrames ab (eigentlicher Timer)
import javafx.geometry.Insets;						// Innenabstand,				definiert Ränder (Padding) für Container in Pixeln
import javafx.geometry.Pos;							// Positionierung,				bestimmt die Ausrichtung von Elementen (z.B. CENTER)
import javafx.scene.Scene;							// Scene-Klasse,				für den Fensterinhalt
import javafx.scene.control.*;						// Steuerelemente,				importiert alle GUI-Komponenten -> Buttons, Labels und TextFields
import javafx.scene.layout.VBox;					// Layout-Klasse,				ordnet GUI-Elemente automatisch vertikal (untereinander)
import javafx.stage.Stage;							// Bühne,						das eigentliche Windows-Fenster
import javafx.util.Duration;						// Zeitdauer,					gibt Zeitabstände an (z.B. Sekunden oder Millisekunden)
import java.io.IOException;							// IO-Ausnahme,					Fängt Fehler bei der Ausführung externer Betriebssystembefehle ab
import java.time.LocalTime;							// Lokale Uhrzeit,				fragt nach der aktuellen SystemUhrzeit
import java.time.temporal.ChronoUnit;				// Datum/Zeitberechnung			Bietet Funktionen zur Berechnung von Zeit- und Datumsdifferenzen

//=======================================================================================================================

// Main erbt von Application -> um JavaFX zu nutzen:
public class Main extends Application {
	
	// Variablen deklarieren:
    int restZeitSekunden;
    Timeline zeitLeiste;
    Label titleLabel;
    Label countdownAnzeigen;
    Button startBtn;
    Button pauseBtn;
    Button abbruchBtn;
    VBox minutenVbox;
    VBox uhrzeitVbox;
    
    // Info an Compiler -> start-Methode von Application überschreiben:
    @Override
    // Haupteinstiegspunkt für die GUI:
	public void start(Stage primaryStage) {

//=======================================================================================================================
    	
//    	    _____ _    _ _____                 _       _ _              
//    	   / ____| |  | |_   _|               | |     | | |           _ 
//    	  | |  __| |  | | | |     ___ _ __ ___| |_ ___| | | ___ _ __ (_)
//    	  | | |_ | |  | | | |    / _ \ '__/ __| __/ _ \ | |/ _ \ '_ \   
//    	  | |__| | |__| |_| |_  |  __/ |  \__ \ ||  __/ | |  __/ | | |_ 
//    	   \_____|\____/|_____|  \___|_|  |___/\__\___|_|_|\___|_| |_(_)  
    	
    	// Hinweistext:
        titleLabel = new Label("bis PC sich herunter fährt:");
        // Style ändern:	 - Schriftgröße; 	  - Fett gedruckt;		 - SchriftFarbe;
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4FC3F7;");
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // Label für Text unter dem Textfeld erstellen:
        Label minutenLabel = new Label ("Minuten");
        minutenLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;");
        
   
        // Textfeld für MinutenEingabe:
        TextField minutenEingabe = new TextField();    
        // Breite des Eingabefeldes:
        minutenEingabe.setMaxWidth(100);
        // Style ändern:		 - Schriftgröße		  - Hintergrundfarbe			 - TextFarbe 			 - Ecken abrunden;
        minutenEingabe.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;");
        // Schrift mittig auslegen:
        minutenEingabe.setAlignment(Pos.CENTER);
        
        
        // Label und Textfeld in VBox packen:
        minutenVbox = new VBox(2);
        minutenVbox.setAlignment(Pos.CENTER);
        minutenVbox.setStyle("-fx-background-color: #2B3345; -fx-background-radius: 4px;");
        minutenVbox.setMaxWidth(100);
        
        
        // Elemente der Vbox hinzufügen:
        minutenVbox.getChildren().addAll(minutenEingabe, minutenLabel); 
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // Label für Text unter dem Textfeld erstellen:
        Label uhrzeitLabel = new Label ("Uhr");
        uhrzeitLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;");
        
        
        // Textfeld für Uhrzeit:
        TextField uhrzeitEingabe = new TextField();
        // Breite des Eingabefeldes:
        uhrzeitEingabe.setMaxWidth(100);
        // Schriftgröße + Schriftfarbe für Zeiteingabe:
        uhrzeitEingabe.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;");
        // Schrift mittig auslegen:
        uhrzeitEingabe.setAlignment(Pos.CENTER);
        
        
        // Label und Textfeld in VBox packen:
        uhrzeitVbox = new VBox(2);
        uhrzeitVbox.setAlignment(Pos.CENTER);
        uhrzeitVbox.setStyle("-fx-background-color: #2B3345; -fx-background-radius: 4px;");
        uhrzeitVbox.setMaxWidth(100);
        
        
        // Elemente der Vbox hinzufügen:
        uhrzeitVbox.getChildren().addAll(uhrzeitEingabe, uhrzeitLabel); 
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // leeres Label -> zeigt dann den LiveCountdown an:
        countdownAnzeigen = new Label("");
        // Style ändern:	 		- Schriftgröße; 	 - Fett gedruckt;		- TextFarbe;
        countdownAnzeigen.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4FC3F7;");
        
        
        // nicht Anzeigen:
        countdownAnzeigenFalse();

// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   
        
        // Button erstellen:
        startBtn = new Button("Start");
        // Größe in Pixel:	 <->  ↕
        startBtn.setPrefSize(100, 20);
        // Style ändern:   - Schriftgröße; 	    - HintergrundFarbe		       - TextFarbe;			   - Ecken leicht abrunden; 	-> StandardStyle
        startBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;");
        // Hover Effekt -> Maus auf Button:
        startBtn.setOnMouseEntered(e -> startBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #4FC3F7; -fx-text-fill: #1E2430; -fx-background-radius: 4px; -fx-font-weight: bold;"));
        // Hover Effekt -> Maus weg von Button:
        startBtn.setOnMouseExited(e -> startBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;"));

       
        pauseBtn = new Button("Pause");
        pauseBtn.setPrefSize(100, 20);
	    pauseBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;");

	    pauseBtn.setOnMouseEntered(e -> pauseBtn.setStyle("-fx-font-size: 12px; -fx-background-color: " + countdownFarbe() + "; -fx-text-fill: #1E2430; -fx-background-radius: 4px; -fx-font-weight: bold;"));
	    pauseBtn.setOnMouseExited(e -> pauseBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;"));

	    
        abbruchBtn = new Button("Abbrechen");
        abbruchBtn.setPrefSize(100, 20);
        abbruchBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;");

        abbruchBtn.setOnMouseEntered(e -> abbruchBtn.setStyle("-fx-font-size: 12px; -fx-background-color: " + countdownFarbe() + "; -fx-text-fill: #1E2430; -fx-background-radius: 4px; -fx-font-weight: bold;"));
        abbruchBtn.setOnMouseExited(e -> abbruchBtn.setStyle("-fx-font-size: 12px; -fx-background-color: #3A4459; -fx-text-fill: #E6EAF2; -fx-background-radius: 4px;"));

        
        // vorerst nicht benötigte Buttons ausblenden:
        pauseBtnAnzeigenFalse();
        abbruchBtnAnzeigenFalse();
        
//=======================================================================================================================

//         _______ _                _      _              
//        |__   __(_)              | |    (_)           _ 
//           | |   _ _ __ ___   ___| |     _ _ __   ___(_)
//           | |  | | '_ ` _ \ / _ \ |    | | '_ \ / _ \  
//           | |  | | | | | | |  __/ |____| | | | |  __/_ 
//           |_|  |_|_| |_| |_|\___|______|_|_| |_|\___(_)

        
        // Zeitleiste: Führt den Countdown aus und aktualisiert verbleibende Zeit -> jede Sekunde minus eine Sekunde:
        // Keyframe feuert jede Sekunde - 1 Sekunde:
        zeitLeiste = new Timeline(new KeyFrame(Duration.seconds(1), aktion -> {

	    	// noch Sekunden übrig:
	        if (restZeitSekunden > 0) {
	        	// Sekunde - 1
	            restZeitSekunden--;
	            
	            // Methode zur Textformatierung abrufen:
	            // Hier drin wird auch die Zeit berechnet:
	            textFormatierung();
	          
	            
	        // Wenn Timeline bei 0 angekommen:
	        } else {
	        	// TimeLine stoppen -> verhindert weiteres herunter zählen:
	            zeitLeiste.stop();
	            countdownAnzeigen.setText("Fahre herunter...");
	            // Funktion aufrufen:
	            sendeShutDown();
	        }
	    }));
        
	    // Timeline unendlich laufen lassen -> bis gestoppt wird:
	    zeitLeiste.setCycleCount(Timeline.INDEFINITE);

//=======================================================================================================================
        
//         ____        _   _                _                 _ _      
//        |  _ \      | | | |              | |               (_) |   _ 
//        | |_) |_   _| |_| |_ ___  _ __   | |     ___   __ _ _| | _(_)
//        |  _ <| | | | __| __/ _ \| '_ \  | |    / _ \ / _` | | |/ /  
//        | |_) | |_| | |_| || (_) | | | | | |___| (_) | (_| | |   < _ 
//        |____/ \__,_|\__|\__\___/|_| |_| |______\___/ \__, |_|_|\_(_)
//                                                       __/ |         
//                                                      |___/                   
        
//         ______           __      ___       __  __            _ 
//        / __/ /____ _____/ /_____/ _ )__ __/ /_/ /____  ___  (_)
//       _\ \/ __/ _ `/ __/ __/___/ _  / // / __/ __/ _ \/ _ \_   
//      /___/\__/\_,_/_/  \__/   /____/\_,_/\__/\__/\___/_//_(_)  

//      __  __ _          _            _ 
//     |  \/  (_)_ _ _  _| |_ ___ _ _ (_)
//     | |\/| | | ' \ || |  _/ -_) ' \ _ 
//     |_|  |_|_|_||_\_,_|\__\___|_||_(_)
        
    	startBtn.setOnAction(aktion -> {	                                    
    	// .trim()	-> entfernt leerzeichen am Anfang des Textes
    	if (!minutenEingabe.getText().trim().isEmpty() && uhrzeitEingabe.getText().trim().isEmpty()) {
    		
        	// versuche Benutzereingabe in Integer umzuwandeln:
            try {
            	// Liest den Text aus Feld und konvertiert in 'int'
                int minutes = Integer.parseInt(minutenEingabe.getText().trim());
                
                
                // Zahl muss zwischen 1 und 1440 liegen (24 Stunden):
                if (minutes <= 0 || minutes > 1440 ) {
                	throw new NumberFormatException();
                }

                restZeitSekunden = minutes * 60;
                
                // Methode zur Textformatierung abrufen:
                // Hier drin wird auch die Zeit berechnet:
                textFormatierung();
                
                // VBox/Textfeld sperren/freigeben:
                minutenVboxAnzeigenFalse();
                uhrzeitVboxAnzeigenFalse();
                
                // TextLabel sperren/freigeben:
                titleLabelAnzeigenFalse();
                
                // Buttons sperren/freigeben:
                startBtnAnzeigenFalse();
                abbruchBtnAnzeigenTrue();
                pauseBtnAnzeigenTrue();
                
                // Countdown anzeigen:
                countdownAnzeigenTrue();
                
                // Zeitleiste abspielen:
                zeitLeiste.play();
                
            // Fehlermeldung bei falscher Eingabe:
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte eine Zahl zwischen 1 und 1440 eingeben!");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }
	            
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
	            
//         _   _ _               _ _   _ 
//        | | | | |_  _ _ ______(_) |_(_)
//        | |_| | ' \| '_|_ / -_) |  _|_ 
//        \___/|_||_|_| /__\___|_|\__(_)
	            
    	} else if (!uhrzeitEingabe.getText().trim().isEmpty() && minutenEingabe.getText().trim().isEmpty()) {      
    		
            try {
        		// Aktuelle Lokale Uhrzeit holen:
        		LocalTime aktuelleUhrzeit = LocalTime.now();
        		
	            // Liest den Text aus TextFeld:
                String uhrzeitText = uhrzeitEingabe.getText().trim();
            	// .split()	-> TextfeldEingabe wird am ":" geteilt:
            	// wird immer in einem Array gespeichert.
            	String[] aufteilen = uhrzeitText.split(":");
            	
            	// String in int parsen:
            	int stunden = Integer.parseInt(aufteilen[0]);
            	int minuten = Integer.parseInt(aufteilen[1]);
            	// int Werte jetzt als LocalTime speichern:
            	LocalTime shutdownUhrzeit = LocalTime.of(stunden,minuten);
            	
            	// ChronoUnit berechnet die differenz zwischen der aktuellen SystemZeit und der gewünschten Shutdown Uhrzeit:
            	// between() gibt immer ein Long zurück
            	long sekundenBisShutdown = ChronoUnit.SECONDS.between(aktuelleUhrzeit, shutdownUhrzeit);
            	
            	
            	// Wenn Sekunden <= 0 -> neuen Tag dazu rechnen -> Shutdwon nach 00:00 Uhr:
              	if (sekundenBisShutdown <= 0) {
            		sekundenBisShutdown += 86400;
              	}
              	
              	// Wandelt den long-Wert durch einen Cast in einen int-Wert um
            	restZeitSekunden = (int)sekundenBisShutdown;
                
                // Methode zur Textformatierung abrufen:
                // Hier drin wird auch die Zeit berechnet:
                textFormatierung();
                
                // VBox/Textfeld sperren/freigeben:
                minutenVboxAnzeigenFalse();
                uhrzeitVboxAnzeigenFalse();
                
                // TextLabel sperren/freigeben:
                titleLabelAnzeigenFalse();
                
                // Buttons sperren/freigeben:
                startBtnAnzeigenFalse();
                abbruchBtnAnzeigenTrue();
                	                
                // Countdown anzeigen:
                countdownAnzeigenTrue();
                
                zeitLeiste.play();

            } catch (Exception f) {
                // Fängt Formatfehler (kein ":") und Textfehler bei der Verarbeitung der Uhrzeiteingabe ab:
                Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte gültiges Uhrzeit Format eingeben! hh:mm");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }
                
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -

//         _              __        _ _ _ 
//        | |___ ___ _ _ / /_ _____| | (_)
//        | / -_) -_) '_/ /\ V / _ \ | |_ 
//        |_\___\___|_|/_/  \_/\___/_|_(_)                                              

        // wenn beide oder kein Feld ausgefüllt:
    	} else {
	        Alert alert = new Alert(Alert.AlertType.ERROR, "Bitte nur ein Feld ausfüllen!");
	        alert.setHeaderText(null);
	        alert.showAndWait();	        	
    	}		
    });

// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
//         ___   __   __                __       ___       __  __            _ 
//        / _ | / /  / /  ______ ______/ /  ____/ _ )__ __/ /_/ /____  ___  (_)
//       / __ |/ _ \/ _ \/ __/ // / __/ _ \/___/ _  / // / __/ __/ _ \/ _ \_   
//      /_/ |_/_.__/_.__/_/  \_,_/\__/_//_/   /____/\_,_/\__/\__/\___/_//_(_)  
                                                                             
        abbruchBtn.setOnAction(aktion -> {
            zeitLeiste.stop();
            restZeitSekunden = 0;
            
        // Textfelder ein/ausblenden
        minutenVboxAnzeigenTrue();
        uhrzeitVboxAnzeigenTrue();

        // Buttons ein/ausblenden:
        startBtnAnzeigenTrue();
        abbruchBtnAnzeigenFalse();
        pauseBtnAnzeigenFalse();
    
        titleLabelAnzeigenTrue();
        
        // Countdown verbergen:
        countdownAnzeigenFalse();
           
        // wenn bei Abbruch PauseBtn noch "Fortsetzen" -> ändern zu "Pause":
        if (pauseBtn.getText().equals("Fortsetzen")) {
        	pauseBtn.setText("Pause");
        }
        
    });
                
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
//         ___                        ___       __  __            _ 
//        / _ \___ ___ _____ ___ ____/ _ )__ __/ /_/ /____  ___  (_)
//       / ___/ _ `/ // (_-</ -_)___/ _  / // / __/ __/ _ \/ _ \_   
//      /_/   \_,_/\_,_/___/\__/   /____/\_,_/\__/\__/\___/_//_(_)  

        pauseBtn.setOnAction(aktion -> {        	
        	// Buttons sperren/freigeben:
        	if (pauseBtn.getText().equals("Pause")) {
            	zeitLeiste.pause();
        		pauseBtn.setText("Fortsetzen");
        	}else {
        		pauseBtn.setText("Pause");
        		zeitLeiste.play();
        	}
        });
        
//=======================================================================================================================

//         ______             _                                                                 _                                
//        |  ____|           | |                                                               | |                           _ _ 
//        | |__ ___ _ __  ___| |_ ___ _ __   _____   _ ___  __ _ _ __ ___  _ __ ___   ___ _ __ | |__   __ _ _   _  ___ _ __ (_|_)
//        |  __/ _ \ '_ \/ __| __/ _ \ '__| |_  / | | / __|/ _` | '_ ` _ \| '_ ` _ \ / _ \ '_ \| '_ \ / _` | | | |/ _ \ '_ \     
//        | | |  __/ | | \__ \ ||  __/ |     / /| |_| \__ \ (_| | | | | | | | | | | |  __/ | | | |_) | (_| | |_| |  __/ | | |_ _ 
//        |_|  \___|_| |_|___/\__\___|_|    /___|\__,_|___/\__,_|_| |_| |_|_| |_| |_|\___|_| |_|_.__/ \__,_|\__,_|\___|_| |_(_|_)
                                                                                                                               
        // root stapelt mit vbox alle Elemente untereinander an:
        // Abstand zwischen Vertikalen Elementen:
        VBox root = new VBox(15);
        // Hintergrundfarbe der VBox:
        root.setStyle("-fx-background-color: #1E2430;");
        
        // Abstand vom Rand:
        root.setPadding(new Insets(20));
        
        // Inhalt mittig ausrichten:
        root.setAlignment(Pos.CENTER);
        
        // Elemente dem root hinzufügen:
        root.getChildren().addAll(minutenVbox, uhrzeitVbox, titleLabel, countdownAnzeigen, startBtn, pauseBtn, abbruchBtn);

        // Größe des Fensters:
        Scene scene = new Scene(root, 400, 300);
        
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
        
        // Fenster final vorbereiten:
        primaryStage.setTitle("ShutDown-Timer");
        primaryStage.setScene(scene);
//        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
//=======================================================================================================================

//     __  __      _   _               _              
//    |  \/  |    | | | |             | |           _ 
//    | \  / | ___| |_| |__   ___   __| | ___ _ __ (_)
//    | |\/| |/ _ \ __| '_ \ / _ \ / _` |/ _ \ '_ \   
//    | |  | |  __/ |_| | | | (_) | (_| |  __/ | | |_ 
//    |_|  |_|\___|\__|_| |_|\___/ \__,_|\___|_| |_(_)
                                                    

    // Methode für ShutDown:
    
    // Java schickt Befehl direkt an Eingabeaufforderung/Console/Terminal:
    private void sendeShutDown() {

        // getProperty(); -> Name des Betriebssystems abrufen:
        String betriebsSystem = System.getProperty("os.name").toLowerCase();

        // Befehl als Array: jedes Element ist ein Teil des Befehls:
        // Array wird erst innerhalb der Verzweigung mit Werten gefüllt:
        // null = "noch kein Befehl gewählt"
        String[] befehl = null;

        // Windows:
        if (betriebsSystem.contains("win")) {
            befehl = new String[]{"shutdown", "-s", "-f", "-t", "0"};
            
        // Mac:
        } else if (betriebsSystem.contains("mac")) {
            befehl = new String[]{"osascript", "-e", "tell app \"System Events\" to shut down"};

        // Linux / Unix:
        } else if (betriebsSystem.contains("nix") || betriebsSystem.contains("nux") || betriebsSystem.contains("aix")) {
            befehl = new String[]{"systemctl", "poweroff"};
        }

        try {
            // nur ausführen wenn Betriebssystem erkannt wurde:
            if (befehl != null) {
                Runtime.getRuntime().exec(befehl);
                // schließt alle JavaFX-Prozesse:
                Platform.exit();
                // beendet das Java-Programm:
                System.exit(0);
            } else {
            	Alert alert = new Alert(Alert.AlertType.ERROR, "unbekanntes Betriebssystem!\nShutdown leider nicht möglich!");
                alert.setHeaderText(null);
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -

    // Methoden für Sichtbarkeit der Elemente:
    
	private void countdownAnzeigenTrue() {
        countdownAnzeigen.setVisible(true);
        countdownAnzeigen.setManaged(true);
	}
	private void countdownAnzeigenFalse() {
        countdownAnzeigen.setVisible(false);
        countdownAnzeigen.setManaged(false);
	}
	
	
	private void minutenVboxAnzeigenTrue() {
        minutenVbox.setVisible(true);
        minutenVbox.setManaged(true);	
	}
	private void minutenVboxAnzeigenFalse() {
        minutenVbox.setVisible(false);
        minutenVbox.setManaged(false);	
	}
	
	
	private void uhrzeitVboxAnzeigenTrue() {
		uhrzeitVbox.setVisible(true);
		uhrzeitVbox.setManaged(true);
	}
	private void uhrzeitVboxAnzeigenFalse() {
		uhrzeitVbox.setVisible(false);
		uhrzeitVbox.setManaged(false);
	}


	private void titleLabelAnzeigenTrue() {
		titleLabel.setVisible(true);
		titleLabel.setManaged(true);
	}
	private void titleLabelAnzeigenFalse() {
		titleLabel.setVisible(false);
		titleLabel.setManaged(false);
	}
	
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
	
	private void startBtnAnzeigenTrue() {	
		startBtn.setVisible(true);
		startBtn.setManaged(true);
	}
	private void startBtnAnzeigenFalse() {	
		startBtn.setVisible(false);
		startBtn.setManaged(false);
	}
	
	
	private void pauseBtnAnzeigenTrue() {
		pauseBtn.setVisible(true);
	    pauseBtn.setManaged(true);	
	}
	private void pauseBtnAnzeigenFalse() {
		pauseBtn.setVisible(false);
	    pauseBtn.setManaged(false);	
	}
	
	
    private void abbruchBtnAnzeigenTrue() {
    	abbruchBtn.setVisible(true);
    	abbruchBtn.setManaged(true);
    }
    private void abbruchBtnAnzeigenFalse() {
    	abbruchBtn.setVisible(false);
    	abbruchBtn.setManaged(false);
    } 
    
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -  
    
    // Methoden zur Berechnung der Anzeige aus restZeitSekunden:
    // Wir haben nur eine Klasse, daher nehmen wir private anstatt public:
	private int zeitStundenBerechnen() {
        // volle Stunden berechnen (1 Stunde = 3600 Sekunden):
		return restZeitSekunden / 3600;
	}
        
    private int zeitMinutenBerechnen() {
    	// Minuten berechnen -> erst die vollen Stunden abziehen (% 3600), dann durch 60:
    	return (restZeitSekunden % 3600) / 60;
     }
     
     private int zeitSekundenBerechnen() {
    	// verbleibende RestSekunden:
    	 return restZeitSekunden % 60;
     }
     
 // -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -  
     
     // setzt den Countdown-Text aus restZeitSekunden:
     private void textFormatierung() {
         // Formatiert Text im Label neu -> %02d erzwingt eine zweistellige Anzeige mit führender Null:
         // (Pos.CENTER) -> richtet mehrzeiligen Text innerhalb des Labels mittig aus:
         countdownAnzeigen.setAlignment(Pos.CENTER);
         // Zentriert mehrzeiligen Text mittig:
         countdownAnzeigen.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
         // TextAnzeige:
         countdownAnzeigen.setText(String.format("Computer fährt herunter in: \n%02d:%02d:%02d",
         // Methoden rufen um restZeitSekunden zu verrechnen:
         zeitStundenBerechnen(), zeitMinutenBerechnen(), zeitSekundenBerechnen()));
         
         // aktualisiert LabelStyle  - Schriftgröße; 	  - Fett gedruckt        - Textfarbe dynamisch (MethodeString)
         countdownAnzeigen.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + countdownFarbe() + ";");
     }
     
// -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -
     
     private String countdownFarbe() {
    	 // weniger als 2 Minuten -> Rot
    	 if (restZeitSekunden < 120) {
    		 return "#E53935";
    		 
    	 // weniger als 10 Minuten -> Gelb
    	 } else if (restZeitSekunden < 600) {
    		 return "#FFD54F";
    	 
    	 // -> standard -> Blau
    	 } else {
    		 return "#4FC3F7";
    	 }
	}
     
//=======================================================================================================================
    
    public static void main(String[] args) {
        launch(args);

	}
}