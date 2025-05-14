# PowerShell script to count ONLY lines of code with author attribution
# This script counts only lines that have an author specified via git blame

# Initialize counters
$authorStats = @{}
$fileTypeAuthorStats = @{}
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

    Write-Host "`nCounting lines with author attribution in $fileType files..."
    Write-Host "Found $($files.Count) $fileType files"

    # Skip processing if no files found
    if ($files.Count -eq 0) {
        return 0
    }

    # Initialize file type stats if not exists
    if (-not $fileTypeAuthorStats.ContainsKey($fileType)) {
        $fileTypeAuthorStats[$fileType] = @{}
    }

    # Process each file
    $fileCount = 0
    foreach ($file in $files) {
        $fileCount++
        if ($fileCount % 10 -eq 0 -or $fileCount -eq 1 -or $fileCount -eq $files.Count) {
            Write-Host "Processing file $fileCount of $($files.Count)..."
        }

        # Use Git blame to get author information (only for text files that can be blamed)
        try {
            $blameOutput = git blame --line-porcelain $file.FullName 2>$null
            $fileAuthorLines = 0

            # Process blame output to count lines by author
            foreach ($line in $blameOutput) {
                if ($line -match "^author (.+)$") {
                    $author = $matches[1]
                    $fileAuthorLines++

                    # Update file type-specific author stats
                    if (-not $fileTypeAuthorStats[$fileType].ContainsKey($author)) {
                        $fileTypeAuthorStats[$fileType][$author] = 0
                    }
                    $fileTypeAuthorStats[$fileType][$author]++

                    # Update global author stats
                    if (-not $authorStats.ContainsKey($author)) {
                        $authorStats[$author] = 0
                    }
                    $authorStats[$author]++
                }
            }

            # Add to type total
            $typeTotal += $fileAuthorLines
        }
        catch {
            # Silently continue if blame fails
        }
    }

    $script:grandTotal += $typeTotal  # Use script: scope to ensure the global variable is updated

    # Display results for this file type
    Write-Host "`nTotal lines with author attribution in $fileType code: $typeTotal"

    if ($fileTypeAuthorStats[$fileType].Count -gt 0 -and $typeTotal -gt 0) {
        Write-Host "Lines by author for $fileType files:"
        $fileTypeAuthorStats[$fileType].GetEnumerator() | Sort-Object -Property Value -Descending | ForEach-Object {
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
Write-Host "Lines with author attribution by file type:"
$fileTypeStats = @{}

# Calculate totals by file type
foreach ($type in $fileTypeAuthorStats.Keys) {
    $typeTotal = 0
    foreach ($author in $fileTypeAuthorStats[$type].Keys) {
        $typeTotal += $fileTypeAuthorStats[$type][$author]
    }
    $fileTypeStats[$type] = $typeTotal
}

# Display file type statistics
if ($grandTotal -gt 0) {
    $fileTypeStats.GetEnumerator() | Sort-Object -Property Value -Descending | ForEach-Object {
        if ($_.Value -gt 0) {  # Only show file types with lines
            $percentage = [math]::Round(($_.Value / $grandTotal) * 100, 2)
            Write-Host "$($_.Key): $($_.Value) lines ($percentage%)"
        }
    }

    Write-Host "`nTotal lines with author attribution across all file types: $grandTotal"

    Write-Host "`nLines by author across all file types:"
    $authorStats.GetEnumerator() | Sort-Object -Property Value -Descending | ForEach-Object {
        $percentage = [math]::Round(($_.Value / $grandTotal) * 100, 2)
        Write-Host "$($_.Key): $($_.Value) lines ($percentage%)"
    }
} else {
    Write-Host "No lines with author attribution found in the project."
}