# LidlNFC

Eine Android-App für Lidl, die im Kiosk-Modus als Device Owner betrieben wird.

## Funktionalität

### Kiosk-Modus
- Die App läuft im Kiosk-Modus als Device Owner
- Benutzer können die App nicht verlassen (kein Zurück-Button, kein Home-Button)
- Nur durch ein verstecktes Passwort kann der Kiosk-Modus verlassen werden

### Login-System
- Benutzer müssen sich zunächst per Login in die App einloggen
- Nach erfolgreichem Login wird ein Hintergrundbild angezeigt

### NFC-Scanning
- Sobald ein NFC-Chip gescannt wird, wird ein anderes Bild angezeigt
- Das angezeigte Bild ist zoombbar für bessere Betrachtung
- Nach dem Scan wird wieder zum Hintergrundbild zurückgekehrt

## Installation

### QR-Code Setup (Erforderlich für Kiosk-Modus)
**Wichtig**: Die App muss über einen QR-Code während der Handy-Setup-Funktion installiert werden, um den Kiosk-Modus zu aktivieren. Eine normale Installation über den Play Store oder APK-Installation reicht nicht aus, da Device Owner-Berechtigungen nur während des initialen Setup-Prozesses vergeben werden können.

Der QR-Code wird mit einer JSON-Konfigurationsdatei generiert:

- `provisioning_config_simple.json` - Einfache Konfiguration
- `provisioning_config.json` - Erweiterte Konfiguration

### Installationsschritte
1. QR-Code mit der entsprechenden JSON-Konfiguration generieren
2. Während des initialen Android-Setups (beim ersten Einschalten des Geräts) den QR-Code scannen
3. Die App wird automatisch als Device Owner installiert und konfiguriert
4. Kiosk-Modus wird automatisch aktiviert

### Voraussetzungen
- Android-Gerät mit NFC-Unterstützung
- QR-Code Scanner für die Installation
- Initiales Setup des Android-Geräts (Factory Reset erforderlich, falls bereits eingerichtet)

## Technische Details

### Komponenten
- `MainActivity.java` - Hauptaktivität mit Login und Hintergrundbild
- `ScanResultActivity.java` - Aktivität für NFC-Scan-Ergebnisse
- `MyDeviceAdminReceiver.java` - Device Admin Receiver für Kiosk-Modus
- `activity_main.xml` - Layout für die Hauptaktivität

### Berechtigungen
- NFC-Zugriff
- Device Owner-Berechtigungen (nur über QR-Code Setup möglich)
- Kiosk-Modus-Berechtigungen

## Verwendung

1. App über QR-Code während Handy-Setup installieren
2. Als Device Owner einrichten (automatisch durch QR-Code Setup)
3. In Kiosk-Modus starten (automatisch aktiviert)
4. Mit Login-Daten anmelden
5. NFC-Chips scannen für Bildanzeige
6. Verstecktes Passwort verwenden um Kiosk-Modus zu verlassen
