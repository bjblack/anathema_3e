#include-once
AutoItSetOption("MustDeclareVars", 1)
#cs -----------------------------------------------------------------------------------------------------------------------------

 AutoIt Version: 3.3.14.2
 Author:         B.J. Black

Java Formatter Description:
	Java is a programming language. Depending on the programmer, the coding style varies.
	
	This program formats Java code to my coding style.
	
My Java Coding Style:
	Indents are always tabs, never spaces, and those tabs are the only whitespace that starts lines, except that new lines within JavaDoc notes (/**) also get one space before the text proper.
	Opening and closing braces always get their own line (Stripping trailing spaces on the previous line, if a newline is added). Opening braces do not get empty lines after them, and closing braces do not get empty lines before them.
	Newlines are otherwise preserved as is.
	Opening parentheses are preceded by a space.
	
	NOTE: These do not apply within string or character literals, or line notes.

This Script:
	This script takes the contents of the clipboard, formats it, then copies it to the clipboard, when the user presses F5.

#ce -----------------------------------------------------------------------------------------------------------------------------

GLOBAL $TABCOUNT
GLOBAL $COMMENT
GLOBAL $JAVADOC
GLOBAL $STRINGFLAG
GLOBAL $CHARFLAG
GLOBAL $OPENBRACEFLAG
GLOBAL $CLOSEBRACEFLAG

#include <FileConstants.au3>
#include <StringConstants.au3>

main ()
Func main ()
;~ 	
;~ 	HotKeySet ("{F5}", "javaclipboard")
;~ 	HotKeySet ("{F8}", "ScriptExit")
;~ 	
;~ 	ConsoleWrite ("Ready" & @CRLF)
;~ 	
;~ 	while 1
;~ 		sleep (3600000)
;~ 	wend
;~ 	
	
	fixall ()
	Exit
	
EndFunc

Func ScriptExit ()
	exit
EndFunc

Func fixall ()
	
	; First, fix all the .java files in this directory.
	local $filesearch = FileFindFirstFile ("*.java")
	
	local $filename = FileFindNextFile ($filesearch)
	while NOT @error
		
		ConsoleWrite ("--------" & $filename & "--------" & @CRLF)
		processjavafile ($filename)
		ConsoleWrite ("========" & $filename & "========" & @CRLF)
		
		$filename = FileFindNextFile ($filesearch)
		
	wend
	
	FileClose ($filesearch)
	
	; Then, recurse on each subdirectory.
	$filesearch = FileFindFirstFile ("*")
	
	$filename = FileFindNextFile ($filesearch)
	while NOT @error
		
		if @extended = 1 then
			ConsoleWrite ("-->" & $filename & "-->" & @CRLF)
			FileChangeDir ($filename)
			fixall ()
			FileChangeDir ("..")
			ConsoleWrite ("<--" & $filename & "<--" & @CRLF)
		endif
		
		$filename = FileFindNextFile ($filesearch)
		
	wend
	
	FileClose ($filesearch)
	
EndFunc

Func processjavafile ($filename)
	
	local $file = FileOpen ($filename)
	local $newfiletext = ""
	
	$TABCOUNT = 0
	$COMMENT = FALSE
	$JAVADOC = FALSE
	$STRINGFLAG = FALSE
	$CHARFLAG = FALSE
	$OPENBRACEFLAG = FALSE
	$CLOSEBRACEFLAG = FALSE
	
	local $line = FileReadLine ($file)
	
	; Curse the constraints of the autoit for loop!
	if NOT @error then
		while 1
			
			local $tmpline = processjavaline ($line)
			
			; Drop empty lines before code blocks close.
			if $CLOSEBRACEFLAG <> FALSE then
				
				; If a brace opens immediately (for instance, per standard style of a catch statement), then $TABCOUNT needs adjustment for the following test.
				local $tmptabcount = $tabcount
				if $OPENBRACEFLAG then
					$tmptabcount -= 1
				endif
				
				; Due to the way this is formatted, a prior empty line can be detected when ($TABCOUNT + 5) characters back, there is a @CRLF.
				; (The last two characters are @CRLF, then $TABCOUNT + 1 tabs ($TABCOUNT was decremented when the closing brace was detected), then the two-character @CRLF again.)
				; However, if the previous line also opened the code block (that is, the code block is empty), this test will return a false positive, hence the latter test.
				while StringLeft (StringRight ($newfiletext, $tmptabcount + 5), 2) = @CRLF AND StringLeft (StringRight ($newfiletext, 3), 1) <> "{"
					ConsoleWrite ("Deleting previous data: `" & StringRight ($newfiletext, $tmptabcount + 3) & "`" & @CRLF)
					$newfiletext = StringTrimRight ($newfiletext, $tmptabcount + 3)
				wend
				
				$CLOSEBRACEFLAG = FALSE
				
			endif
			
			$newfiletext &= $tmpline
			
			$line = FileReadLine ($file)
			
			; Drop empty lines after code blocks open.
			if $OPENBRACEFLAG then
				
				$line = StringStripWS ($line, $STR_STRIPLEADING)
				
				while StringLen ($line) = 0
					
					$line = FileReadLine ($file)
					if @error then
						exitloop 2
					endif
			
					$line = StringStripWS ($line, $STR_STRIPLEADING)
					
				wend
				
				$OPENBRACEFLAG = FALSE
				
			endif
			
			if @error then
				exitloop
			endif
			
			$newfiletext &= @CRLF
			
		wend
	endif
	
	; Add a newline if it doesn't already end with one.
	if StringRight ($newfiletext, 2) <> @CRLF then
		$newfiletext &= @CRLF
	endif
	
	ConsoleWrite ($newfiletext)
	
	$file = FileOpen ($filename, $FO_OVERWRITE)
	FileWrite ($file, $newfiletext)
	FileClose ($file)
	
EndFunc

Func javaclipboard ()
	
	ConsoleWrite ("Beginning processing..." & @CRLF)
	
	$TABCOUNT = 0
	$COMMENT = FALSE
	$JAVADOC = FALSE
	$STRINGFLAG = FALSE
	$CHARFLAG = FALSE
	
	local $clipboard = ClipGet ()
	$clipboard = processjavafiletext ($clipboard)
	
	ConsoleWrite ("Processing complete" & @CRLF)
	ConsoleWrite ($clipboard)
	
	ClipPut ($clipboard)
	
EndFunc

Func processjavafiletext ($filetext)
	
	local $filelines = StringSplit ($filetext, @CRLF, $STR_ENTIRESPLIT + $STR_NOCOUNT)
	local $linecount = UBound ($filelines)
	
	local $output = ""
	
	; Curse the constraints of the autoit for loop!
	local $i = 0
	while 1
		
		ConsoleWrite ("File Line " & $i & @CRLF)
		
		$output &= processjavaline ($filelines[$i])
		
		$i += 1
		
		if ($i >= $linecount) then
			exitloop
		endif
		
		$output &= @CRLF
		
	wend
	
	return $output
	
EndFunc

Func processjavaline ($line)
	#cs
		Returns the line, as processed.
	#ce
	
;~ 	ConsoleWrite ("`" & $line & "`" & @CRLF)
	
	; Strip the leading whitespace.
	$line = StringStripWS ($line, $STR_STRIPLEADING)
	
	local $output = ""
	
	; First, the proper leading whitespace.
	$output &= leadingwhitespace ()
	
	; Find the next relevant token in the string.
	local $info = whatandwhere ($line)
	while $info[0] <> 0
		
;~ 		ConsoleWrite ("$info = [" & $info[0] & ", " & $info[1] & "]" & @CRLF)
		
		switch $info[0]
			case 1
				; A multi-line comment is closing.
				$output &= StringLeft ($line, $info[1] + 1)
				$line = StringTrimLeft ($line, $info[1] + 1)
				$COMMENT = FALSE
				$JAVADOC = FALSE
				
			case 2
				; A code block is opening.
				
				; Check if this is at the beginning of a line.
				if $info[1] = 1 AND (StringLen ($output) = $TABCOUNT OR StringLeft (StringRight ($output, $TABCOUNT + 2), 2) = @CRLF) then
					
					; All is well.
					$output &= "{"
					
				else
					
					; It needs a newline.
					$output &= StringStripWS (StringLeft ($line, $info[1] - 1), $STR_STRIPTRAILING) & @CRLF & leadingwhitespace () & "{"
					
				endif
				
				; Prepare to remove empty lines.
				$OPENBRACEFLAG = TRUE
				
				; Update $line.
				$line = StringTrimLeft ($line, $info[1])
				$line = StringStripWS ($line, $STR_STRIPLEADING)
				
				; Increment $TABCOUNT.
				$TABCOUNT += 1
				
				; Check if there is more data in the line.
				if StringLen ($line) > 0 then
					
					; It needs a newline.
					$output &= @CRLF & leadingwhitespace ()
					
				endif
				
			case 3
				; A code block is closing.
				
				; Decrement $TABCOUNT.
				$TABCOUNT -= 1
				
				; Check if this is at the beginning of a line.
				if $info[1] = 1 AND (StringLen ($output) = $TABCOUNT + 1 OR StringLeft (StringRight ($output, $TABCOUNT + 3), 2) = @CRLF) then
					
					; There is one too many tabs on this line.
					$output = StringTrimRight ($output, 1)
					$output &= "}"
					
				else
					
					; It needs a newline.
					$output &= StringStripWS (StringLeft ($line, $info[1] - 1), $STR_STRIPTRAILING) & @CRLF & leadingwhitespace () & "}"
					
				endif
				
				; Prepare to remove empty lines.
				$CLOSEBRACEFLAG = TRUE
				
				; Update $line.
				$line = StringTrimLeft ($line, $info[1])
				$line = StringStripWS ($line, $STR_STRIPLEADING)
				
				; Check if there is more data in the line.
				if StringLen ($line) > 0 then
					
					; It needs a newline.
					$output &= @CRLF & leadingwhitespace ()
					
				endif
				
			case 4
				; A parenthesis is opening.
				
				; Check for a preceding space.
				if StringMid ($line, $info[1] - 1, 1) = " " then
					
					; Output as normal.
					$output &= StringLeft ($line, $info[1])
					
				else
					
					; Output with an interposed space.
					$output &= StringLeft ($line, $info[1] - 1) & " ("
					
				endif
				
				$line = StringTrimLeft ($line, $info[1])
				
			case 5
				; A line comment is present.
				exitloop
				
			case 6
				; A multi-line comment is opening.
				$output &= StringLeft ($line, $info[1] + 1)
				$line = StringTrimLeft ($line, $info[1] + 1)
				$COMMENT = TRUE
				
			case 7
				; A JavaDoc comment is opening.
				$output &= StringLeft ($line, $info[1] + 2)
				$line = StringTrimLeft ($line, $info[1] + 2)
				$COMMENT = TRUE
				$JAVADOC = TRUE
				
			case 8
				; A string is opening.
				$output &= StringLeft ($line, $info[1])
				$line = StringTrimLeft ($line, $info[1])
				$STRINGFLAG = TRUE
				
			case 9
				; A string is closing.
				$output &= StringLeft ($line, $info[1])
				$line = StringTrimLeft ($line, $info[1])
				$STRINGFLAG = FALSE
				
			case 10
				; A character is opening.
				$output &= StringLeft ($line, $info[1])
				$line = StringTrimLeft ($line, $info[1])
				$CHARFLAG = TRUE
				
			case 11
				; A character is closing.
				$output &= StringLeft ($line, $info[1])
				$line = StringTrimLeft ($line, $info[1])
				$CHARFLAG = FALSE
				
		endswitch
		
		$info = whatandwhere ($line)
		
	wend
	
	$output &= $line
	
	ConsoleWrite ("->`" & $output & "`" & @CRLF)
	
	return $output
	
EndFunc

Func whatandwhere ($str)
	#cs
		Returns a type code and an index. The index is the place in the string where the first relevant token is.
		Returns type 0 if there is no relevent item (explicitly, it returns this if $COMMENT is true and no comment closer is present).
		Returns type 1 if $COMMENT is true and a comment closer is present.
		Returns type 2 if an opening brace is present.
		Returns type 3 if a closing brace is present.
		Returns type 4 if an opening parenthesis is present.
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
	
	; Check for an opening brace.
	$index = StringInStr ($str, "{")
	if $index <> 0 then
		$output[0] = 2
		$output[1] = $index
	endif
	
	; Check for a closing brace.
	$index = StringInStr ($str, "}")
	if $index <> 0 then
		if $index < $output[1] then
			$output[0] = 3
			$output[1] = $index
		endif
	endif
	
	; Check for an opening parenthesis.
	$index = StringInStr ($str, "(")
	if $index <> 0 then
		if $index < $output[1] then
			$output[0] = 4
			$output[1] = $index
		endif
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

Func leadingwhitespace ()
	local $output = ""
	for $i = 0 to $TABCOUNT - 1
		$output &= @TAB
	next
	if ($JAVADOC) then
		$output &= " "
	endif
	return $output
EndFunc
