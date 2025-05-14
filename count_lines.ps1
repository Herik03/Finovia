# PowerShell script to count lines of code in the project by author
# This script counts lines in various file types and attributes them to authors

# Initialize counters
$totalStats = @{}
$authorStats = @{}
$grandTotal = 0

# Define file types to count
$fileTypes = @{
    "Java" = "*.java"
    "XML" = "*.xml"
    "JavaScript" = "*.js"
    "TypeScript" = "*.ts"
    "CSS" = "*.css"
    "HTML" = "*.html"
    "Properties" = "*.properties"
}

# Function to process files of a specific type
function Process-FileType {
    param (
        [string]$fileType,
        [string]$filter
    )

    $files = Get-ChildItem -Path . -Filter $filter -Recurse -File
    $typeTotal = 0
    $typeAuthorStats = @{}

    Write-Host "`nCounting lines in $fileType files..."
    Write-Host "Found $($files.Count) $fileType files"

    # Skip processing if no files found
    if ($files.Count -eq 0) {
        $totalStats[$fileType] = 0
        return 0
    }

    # Process each file (with less verbose output)
    $fileCount = 0
    foreach ($file in $files) {
        $fileCount++
        if ($fileCount % 10 -eq 0 -or $fileCount -eq 1 -or $fileCount -eq $files.Count) {
            Write-Host "Processing file $fileCount of $($files.Count)..."
        }

        # Count total lines in the file
        $fileLines = (Get-Content $file.FullName -ErrorAction SilentlyContinue).Count
        if ($null -eq $fileLines) { $fileLines = 0 }
        $typeTotal += $fileLines

        # Use Git blame to get author information (only for text files that can be blamed)
        try {
            $blameOutput = git blame --line-porcelain $file.FullName 2>$null

            # Process blame output to count lines by author
            foreach ($line in $blameOutput) {
                if ($line -match "^author (.+)$") {
                    $author = $matches[1]

                    # Update type-specific author stats
                    if (-not $typeAuthorStats.ContainsKey($author)) {
                        $typeAuthorStats[$author] = 0
                    }
                    $typeAuthorStats[$author]++

                    # Update global author stats
                    if (-not $authorStats.ContainsKey($author)) {
                        $authorStats[$author] = 0
                    }
                    $authorStats[$author]++
                }
            }
        }
        catch {
            # Silently continue if blame fails
        }
    }

    # Store the total for this file type
    $totalStats[$fileType] = $typeTotal
    $script:grandTotal += $typeTotal  # Use script: scope to ensure the global variable is updated

    # Display results for this file type
    Write-Host "`nTotal lines of $fileType code: $typeTotal"

    if ($typeAuthorStats.Count -gt 0 -and $typeTotal -gt 0) {
        Write-Host "Lines by author for $fileType files:"
        $typeAuthorStats.GetEnumerator() | Sort-Object -Property Value -Descending | ForEach-Object {
            $percentage = [math]::Round(($_.Value / $typeTotal) * 100, 2)
            Write-Host "  $($_.Key): $($_.Value) lines ($percentage%)"
        }
    }

    return $typeTotal
}

# Process each file type
foreach ($type in $fileTypes.Keys) {
    Process-FileType -fileType $type -filter $fileTypes[$type]
}

# Display overall results
Write-Host "`n========== SUMMARY =========="
Write-Host "Lines of code by file type:"
if ($grandTotal -gt 0) {
    $totalStats.GetEnumerator() | Sort-Object -Property Value -Descending | ForEach-Object {
        if ($_.Value -gt 0) {  # Only show file types with lines
            $percentage = [math]::Round(($_.Value / $grandTotal) * 100, 2)
            Write-Host "$($_.Key): $($_.Value) lines ($percentage%)"
        }
    }

    Write-Host "`nTotal lines of code across all file types: $grandTotal"

    Write-Host "`nLines by author across all file types:"
    $authorStats.GetEnumerator() | Sort-Object -Property Value -Descending | ForEach-Object {
        $percentage = [math]::Round(($_.Value / $grandTotal) * 100, 2)
        Write-Host "$($_.Key): $($_.Value) lines ($percentage%)"
    }
} else {
    Write-Host "No lines of code found in the project."
}
