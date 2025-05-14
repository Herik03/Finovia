# Zusammenfassung der Codezeilen mit Autorenangabe

Dieses Dokument fasst die Ergebnisse der Analyse zusammen, bei der nur Codezeilen gezählt wurden, bei denen ein Autor angegeben ist (über Git-Blame).

## Gesamtergebnis

**Insgesamt 5.004 Codezeilen mit Autorenangabe** wurden im Projekt gefunden.

## Aufschlüsselung nach Dateityp

| Dateityp    | Zeilen mit Autor | Prozentsatz |
|-------------|------------------|-------------|
| Java        | 4.323            | 86,39%      |
| XML         | 563              | 11,25%      |
| Properties  | 39               | 0,78%       |
| CSS         | 30               | 0,60%       |
| TypeScript  | 26               | 0,52%       |
| HTML        | 23               | 0,46%       |
| JavaScript  | 0                | 0,00%       |

## Aufschlüsselung nach Autor

| Autor       | Zeilen | Prozentsatz |
|-------------|--------|-------------|
| Henrik      | 1.838  | 36,73%      |
| BenHuebert  | 1.553  | 31,04%      |
| soeren-h    | 711    | 14,21%      |
| Jan         | 693    | 13,85%      |
| Guevercin   | 207    | 4,14%       |
| Sören Heß   | 2      | 0,04%       |

## Hinweise

- Diese Analyse zählt nur Zeilen, die durch Git-Blame einem Autor zugeordnet werden können.
- JavaScript-Dateien haben keine Autorenangaben, obwohl sie den größten Teil des Codes im Projekt ausmachen (582.329 Zeilen). Dies deutet darauf hin, dass diese Dateien wahrscheinlich generiert oder von Drittanbietern stammen.
- Die Gesamtzahl der Zeilen mit Autorenangabe (5.004) ist deutlich geringer als die Gesamtzahl aller Codezeilen im Projekt (589.569), was darauf hindeutet, dass der Großteil des Codes nicht durch Git-Blame einem Autor zugeordnet werden kann.

## Wie man die Analyse ausführt

Die Analyse wurde mit einem PowerShell-Skript durchgeführt:

```powershell
.\count_authors_only.ps1
```