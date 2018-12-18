#include-once
AutoItSetOption ("MustDeclareVars", 1)
#cs -----------------------------------------------------------------------------------------------------------------------------

 AutoIt Version: 3.3.14.2
 Author:         B.J. Black

Description:
	This script counts the number of .java files within this directory, and the number of comments in them.

#ce -----------------------------------------------------------------------------------------------------------------------------

#include <FileConstants.au3>
#include <StringConstants.au3>

GLOBAL $COMMENT
GLOBAL $STRINGFLAG
GLOBAL $CHARFLAG

main ()
Func main ()
	
	countall ()
	Exit
	
EndFunc

Func ScriptExit ()
	exit
EndFunc

Func countall ($tabs = 0)
	
	local $filecount = 0
	local $linecommentcount = 0
	local $blockcommentcount = 0
	local $javadoccommentcount = 0
	local $nothingtodocount = 0
	
	; First, count all the .java files in this directory.
	local $filesearch = FileFindFirstFile ("*.java")
	
	local $filename = FileFindNextFile ($filesearch)
	while NOT @error
		
		$filecount += 1
		Tabs ($tabs)
		ConsoleWrite ("File number " & $filecount & " = " & $filename & @CRLF)
		
		local $commentcounts = CountComments ($filename)
		Tabs ($tabs + 1)
		ConsoleWrite ("Line Comments: " & $commentcounts[0] & ", Block Comments: " & $commentcounts[1] & ", JavaDoc Comments: " & $commentcounts[2] & ", ""nothing to do"" count = " & $commentcounts[3] & @CRLF)
		
		$linecommentcount += $commentcounts[0]
		$blockcommentcount += $commentcounts[1]
		$javadoccommentcount += $commentcounts[2]
		$nothingtodocount += $commentcounts[3]
		
		$filename = FileFindNextFile ($filesearch)
		
	wend
	
	FileClose ($filesearch)
	
	; Then, recurse on each subdirectory.
	$filesearch = FileFindFirstFile ("*")
	
	$filename = FileFindNextFile ($filesearch)
	while NOT @error
		
		if @extended = 1 then
			
			Tabs ($tabs)
			ConsoleWrite ("-->" & $filename & "-->" & @CRLF)
			FileChangeDir ($filename)
			
			local $dirarray = countall ($tabs + 1)
			
			$filecount += $dirarray[0]
			$linecommentcount += $dirarray[1]
			$blockcommentcount += $dirarray[2]
			$javadoccommentcount += $dirarray[3]
			$nothingtodocount += $dirarray[4]
			
			FileChangeDir ("..")
			Tabs ($tabs)
			ConsoleWrite ("<--" & $filename & "<--" & @CRLF)
			
			Tabs ($tabs)
			ConsoleWrite ("File count = " & $filecount & ", Line Comment count = " & $linecommentcount & ", Block Comment count = " & $blockcommentcount & ", JavaDoc Comment count = " & $javadoccommentcount & ", ""nothing to do"" count = " & $nothingtodocount & @CRLF)
			
		endif
		
		$filename = FileFindNextFile ($filesearch)
		
	wend
	
	FileClose ($filesearch)
	
	local $retarray[5] = [$filecount, $linecommentcount, $blockcommentcount, $javadoccommentcount, $nothingtodocount]
	
	return $retarray
	
EndFunc

Func CountComments ($filename)
	
	$COMMENT = FALSE
	$STRINGFLAG = FALSE
	$CHARFLAG = FALSE
	
	local $file = FileOpen ($filename)
	
	local $linecommentcount = 0
	local $blockcommentcount = 0
	local $javadoccommentcount = 0
	local $nothingtodocount = 0
	
	local $line = FileReadLine ($file)
	
	while NOT @error
		
;~ 		ConsoleWrite ("`" & $line & "`" & @CRLF)
		
		; Find the next relevant token in the string.
		local $info = whatandwhere ($line)
		
		; Print multi-line comments.
		if $info[0] = 0 AND $COMMENT then
			ConsoleWrite ($line & @CRLF)
		endif
		
		while $info[0] <> 0
			
;~ 			ConsoleWrite ("$info = [" & $info[0] & ", " & $info[1] & "]" & @CRLF)
			
			switch $info[0]
				case 1
					; A multi-line comment is closing.
					ConsoleWrite ($line & @CRLF)
					$line = StringTrimLeft ($line, $info[1] + 1)
					$COMMENT = FALSE
					
				case 5
					; A line comment is present.
					ConsoleWrite ($line & @CRLF)
					$linecommentcount += 1
					if StringInStr ($line, "nothing to do") > $info[1] then
						$nothingtodocount += 1
					endif
					exitloop
					
				case 6
					; A multi-line comment is opening.
					ConsoleWrite ($line & @CRLF)
					$line = StringTrimLeft ($line, $info[1] + 1)
					$COMMENT = TRUE
					$blockcommentcount += 1
					
				case 7
					; A JavaDoc comment is opening.
					ConsoleWrite ($line & @CRLF)
					$line = StringTrimLeft ($line, $info[1] + 2)
					$COMMENT = TRUE
					$javadoccommentcount += 1
					
				case 8
					; A string is opening.
					$line = StringTrimLeft ($line, $info[1])
					$STRINGFLAG = TRUE
					
				case 9
					; A string is closing.
					$line = StringTrimLeft ($line, $info[1])
					$STRINGFLAG = FALSE
					
				case 10
					; A character is opening.
					$line = StringTrimLeft ($line, $info[1])
					$CHARFLAG = TRUE
					
				case 11
					; A character is closing.
					$line = StringTrimLeft ($line, $info[1])
					$CHARFLAG = FALSE
					
			endswitch
			
			$info = whatandwhere ($line)
			
		wend
		
		$line = FileReadLine ($file)
		
	wend
	
	FileClose ($file)
	
	local $retarray[4] = [$linecommentcount, $blockcommentcount, $javadoccommentcount, $nothingtodocount]
	
	return $retarray
	
EndFunc

Func whatandwhere ($str)
	#cs
		Returns a type code and an index. The index is the place in the string where the first relevant token is.
		Returns type 0 if there is no relevent item (explicitly, it returns this if $COMMENT is true and no comment closer is present).
		Returns type 1 if $COMMENT is true and a comment closer is present.
		Returns type 5 if a line comment is present.
		Returns type 6 if a multi-line comment opener is present.
		Returns type 7 if a JavaDoc comment opener is present.
		Returns type 8 if a string opener is present.
		Returns type 9 if a string closer is present.
		Returns type 10 if a character opener is present.
		Returns type 11 if a character closer is present.
	#ce
	
;~ 	ConsoleWrite ("whatandwhere (flags " & ($COMMENT ? "1" : "0") & ($STRINGFLAG ? "1" : "0") & ($CHARFLAG ? "1" : "0") & ") ")
	
	local $output[2] = [0, StringLen($str) + 1]
	
	local $index
	
	; Check for a comment state.
	if $COMMENT then
		
		; Look for a comment closer.
		$index = StringInStr ($str, "*/")
		
		; If it's found.
		if $index <> 0 then
			
			; Return it.
			$output[0] = 1
			$output[1] = $index
			return $output
			
		else
			
			; Nothing relevant.
			return $output
			
		endif
		
	endif
	
	; Check for a string state.
	if $STRINGFLAG then
		
		; Check for string closers. This is a little complicated, because """" means something different if it ends "\""".
		local $occurence = 1
		$index = StringInStr ($str, """", 0, $occurence)
		while $index <> 0
			
			; Check for "\""".
			local $loc = StringInStr ($str, "\""", 0, $occurence)
			if $loc <> 0 AND $loc = $index - 1 then
				
				; Run this loop again, looking for the next occurence.
				$occurence += 1
				$index = StringInStr ($str, """", 0, $occurence)
				continueloop
				
			endif
			
			; The item closes a string.
			$output[0] = 9
			$output[1] = $index
			return $output
			
		wend
	
	endif
	
	; Check for a character state.
	if $CHARFLAG then
		
		; Check for character closers. This is a little complicated, because "'" means something different if it ends "\'".
		local $occurence = 1
		$index = StringInStr ($str, "'", 0, $occurence)
		while $index <> 0
			
			; Check for "\""".
			if StringInStr ($str, "\'", 0, $occurence) = $index - 1 then
				
				; Run this loop again, looking for the next occurence.
				$occurence += 1
				$index = StringInStr ($str, "'", 0, $occurence)
				continueloop
				
			endif
			
			; The item closes a character.
			$output[0] = 11
			$output[1] = $index
			return $output
			
		wend
	
	endif
	
	; Check for a line comment.
	$index = StringInStr ($str, "//")
	if $index <> 0 then
		if $index < $output[1] then
			$output[0] = 5
			$output[1] = $index
		endif
	endif
	
	; Check for an opening quote.
	$index = StringInStr ($str, """")
	if $index <> 0 then
		if $index < $output[1] then
			$output[0] = 8
			$output[1] = $index
		endif
	endif
	
	; Check for an opening apostrophe.
	$index = StringInStr ($str, "'")
	if $index <> 0 then
		if $index < $output[1] then
			$output[0] = 10
			$output[1] = $index
		endif
	endif
	
	; Check for comment openers. This is a little complicated, because "/*" means something different if it begins "/**" or "/**/".
	local $occurence = 1
	$index = StringInStr ($str, "/*", 0, $occurence)
	while $index <> 0
		
		; Easy out: If it's after the current relevant item, we can return.
		if $index > $output[1] then
			return $output
		endif
		
		; Check for "/**/".
		if StringInStr ($str, "/**/", 0, $occurence) = $index then
			
			; Run this loop again, looking for the next occurence.
			$occurence += 1
			$index = StringInStr ($str, "/*", 0, $occurence)
			continueloop
			
		endif
		
		; Check for "/**".
		if StringInStr ($str, "/**", 0, $occurence) = $index then
			
			; This opens a Javadoc comment.
			$output[0] = 7
			$output[1] = $index
			return $output
			
		endif
		
		; The item opens a multi-line comment.
		$output[0] = 6
		$output[1] = $index
		return $output
		
	wend
	
	return $output
	
EndFunc

Func Tabs ($tabs)
	
	for $i = 0 to $tabs - 1
		ConsoleWrite (@TAB)
	next
	
EndFunc
