# Get the directory of the script
$scriptDirectory = Split-Path -Parent -Path $MyInvocation.MyCommand.Definition

# User-defined inputs
$newWoodFilePath = Join-Path -Path $scriptDirectory -ChildPath "new_wood.txt"

# Check if new_wood.txt exists
if (-not (Test-Path -Path $newWoodFilePath)) {
    Write-Host "Error: new_wood.txt not found in the script directory."
    Exit
}

# Read replacement values from new_wood.txt
$replacementValues = Get-Content -Path $newWoodFilePath

# Define keys to replace
$keys = @(
    "modid_wood",
    "modid:blocks/log_texture",
    "modid:blocks/planks_texture",
    "modid:blocks/leaves_texture",
    '"item": "modid:log"',
    '"data": log',
    '"item": "modid:planks"',
    '"data": planks',
    '"item": "modid:leaves"',
    '"data": leaves',
    '"item": "modid:fence"',
    '"data": fence',
    '"item": "modid:gate"',
    '"data": gate',
    "Mod Wood"
)

# Function to recursively replace text in files and file names
function RecursivelyReplaceText ($directoryPath) {
    Get-ChildItem -Path $directoryPath -Exclude $MyInvocation.MyCommand.Name, "new_wood.txt" -Recurse | ForEach-Object {
        if ($_.PSIsContainer) {
            RecursivelyReplaceText -directoryPath $_.FullName
        }
        else {
            # Check if it's a text file
            if ($_.Extension -eq ".json" -or $_.Extension -eq ".lang") {
                # Replace text inside the file
                $fileContent = Get-Content -Path $_.FullName -Raw
                for ($i = 0; $i -lt $keys.Count; $i++) {
                    $fileContent = $fileContent -replace [regex]::Escape($keys[$i]), $replacementValues[$i]
                }
                Set-Content -Path $_.FullName -Value $fileContent
            }
            # Replace text in the file name
            $newFileName = $_.Name
            for ($i = 0; $i -lt $keys.Count; $i++) {
                $newFileName = $newFileName -replace [regex]::Escape($keys[$i]), $replacementValues[$i]
            }
            if ($newFileName -ne $_.Name) {
                Rename-Item -Path $_.FullName -NewName $newFileName
            }
        }
    }
}

# Start replacing text recursively from the script's root directory
RecursivelyReplaceText -directoryPath $scriptDirectory
