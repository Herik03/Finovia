# Code-Analyse: Zeilenanzahl nach Autor

Dieses Dokument bietet eine Analyse des Codes, wobei die Codezeilen nach Dateityp und Autorenzuordnung gezählt werden.

## Zusammenfassung der Ergebnisse

Das Projekt enthält insgesamt **589.569 Codezeilen** über alle Dateitypen hinweg. Hier ist die Aufschlüsselung nach Dateityp:

| Dateityp    | Zeilen   | Prozentsatz |
|-------------|----------|-------------|
| JavaScript  | 582.329  | 98,77%      |
| Java        | 4.323    | 0,73%       |
| TypeScript  | 1.890    | 0,32%       |
| XML         | 889      | 0,15%       |
| Properties  | 60       | 0,01%       |
| HTML        | 47       | 0,01%       |
| CSS         | 31       | 0,01%       |

### Autorenzuordnung

Die folgenden Autoren haben zum Codebase beigetragen:

| Autor       | Zeilen   | Prozentsatz |
|-------------|----------|-------------|
| Henrik      | 1.838    | 0,31%       |
| BenHuebert  | 1.553    | 0,26%       |
| soeren-h    | 711      | 0,12%       |
| Jan         | 693      | 0,12%       |
| Guevercin   | 207      | 0,04%       |
| Sören Heß   | 2        | 0,00%       |

## Analyse nur des Java-Codes

Da JavaScript-Dateien den Großteil des Codes ausmachen (98,77%) und wahrscheinlich generiert oder Drittanbieter-Code sind, ist es sinnvoller, den Java-Code separat zu betrachten:

| Autor       | Zeilen   | Prozentsatz |
|-------------|----------|-------------|
| BenHuebert  | 1.531    | 35,42%      |
| Henrik      | 1.245    | 28,80%      |
| Jan         | 687      | 15,89%      |
| soeren-h    | 677      | 15,66%      |
| Guevercin   | 181      | 4,19%       |
| Sören Heß   | 2        | 0,05%       |

## Wie man die Analyse ausführt

Die Analyse wurde mit einem PowerShell-Skript durchgeführt, das:
1. Alle Codedateien im Projekt findet
2. Die Gesamtzeilen in diesen Dateien zählt
3. Git blame verwendet, um Zeilen den Autoren zuzuordnen

Um die Analyse selbst auszuführen:

```powershell
.\count_lines.ps1
```

## Hinweise und Einschränkungen

- Die große Anzahl von JavaScript-Zeilen (582.329) deutet darauf hin, dass viele dieser Dateien möglicherweise generiert, minimiert oder Drittanbieter-Code sind, was erklärt, warum die Prozentsätze der Autorenzuordnung niedrig sind, wenn sie gegen die Gesamtsumme berechnet werden.
- Git blame spiegelt möglicherweise nicht die wahre Urheberschaft aller Zeilen wider, insbesondere wenn Code refaktoriert, verschoben oder kopiert wurde.
- Das Skript zählt nur Zeilen in textbasierten Dateien, die von Git blame verarbeitet werden können.