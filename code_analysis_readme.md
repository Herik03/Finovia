# Code Analysis: Line Count by Author

This document provides an analysis of the codebase, counting lines of code by file type and author attribution.

## Summary of Results

The project contains a total of **589,569 lines of code** across all file types. Here's the breakdown by file type:

| File Type   | Lines    | Percentage |
|-------------|----------|------------|
| JavaScript  | 582,329  | 98.77%     |
| Java        | 4,323    | 0.73%      |
| TypeScript  | 1,890    | 0.32%      |
| XML         | 889      | 0.15%      |
| Properties  | 60       | 0.01%      |
| HTML        | 47       | 0.01%      |
| CSS         | 31       | 0.01%      |

### Author Attribution

The following authors have contributed to the codebase:

| Author      | Lines    | Percentage |
|-------------|----------|------------|
| Henrik      | 1,838    | 0.31%      |
| BenHuebert  | 1,553    | 0.26%      |
| soeren-h    | 711      | 0.12%      |
| Jan         | 693      | 0.12%      |
| Guevercin   | 207      | 0.04%      |
| Sören Heß   | 2        | 0.00%      |

## Analysis of Java Code Only

Since JavaScript files make up the vast majority of the codebase (98.77%) and are likely generated or third-party code, it's more meaningful to look at the Java code separately:

| Author      | Lines    | Percentage |
|-------------|----------|------------|
| BenHuebert  | 1,531    | 35.42%     |
| Henrik      | 1,245    | 28.80%     |
| Jan         | 687      | 15.89%     |
| soeren-h    | 677      | 15.66%     |
| Guevercin   | 181      | 4.19%      |
| Sören Heß   | 2        | 0.05%      |

## How to Run the Analysis

The analysis was performed using a PowerShell script that:
1. Finds all code files in the project
2. Counts the total lines in these files
3. Uses Git blame to attribute lines to authors

To run the analysis yourself:

```powershell
.\count_lines.ps1
```

## Notes and Limitations

- The large number of JavaScript lines (582,329) suggests that many of these files might be generated, minified, or third-party code, which explains why author attribution percentages are low when calculated against the total.
- Git blame may not accurately reflect the true authorship of all lines, especially if code has been refactored, moved, or copied.
- The script only counts lines in text-based files that can be processed by Git blame.