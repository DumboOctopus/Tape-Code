# Tape-Code
Its an assembly language parser for this tapecode computer my friend (Skye Bedard) and I are making.. no we are not insane. Mostly. I think

About the computer: 
  it has 16 memory cells of 4 bit size. It has a simple alu that can do addition, subtraction, bitwise and, bitwise or, bitwise xnor
  the first 2 alu operation memory cell. when an alu operation is done, it operates on these 2 memory cells. The only issue is how the alu output pin is read, we have not decided on a command for that. 

File types that this program process:
  .atp:
    The assembly code that can be converted directly .tp. It is one- one. The main difference between the 2 langs is .atp more readable.:
    all cmd has either of 2 forms. "CMD NUM", "CMD" where CMD is a valid command (see list below), NUM is a number 0 and 15 inclusively and in base10.
    Label are in the form "NUM:" NUM is a number 0 and 15 inclusively
    Example:
      
      wadrs 5
      wl 15
      1:
      wadrs 0
      wl 1
      setif 2
      doif 5
      wadrs 5
      wl 0
      goto 1:
      2:
      
  .tp:
    The byte code that can be processed by the tape code. Can be read by tape code computer. In the form:
      100001010
      100011011
      .
      .
      .
      100100000
    end of file.
  .ctp
    
    var x = 12;
    if( x > 5){
      x += 1;
    }
    setOutput1(x);
    setOutput2(x + 1);
    if(x == 0)
    {
    	var y = 0;
    	if(y == 9)
    	{
    		setOutput2(y + (x - 1));
    	}
    }
    while(x)
    {
    	setOutput1(x);
    	x ++;
    }
    
    
    

Usage: 	java -jar tapecode.jar [-options] [files...
		(to compile [files...)
where options include:
	-name:<name>
		set the name of the file produced. Default is a.tp
Other details:
	1. All arguments are written in decimal.
		e.g: goto 4
	2. All assembly tape code files must be suffixed with .atp
		e.g: ImmaCoolKID.atp
Commands:
	radrs (Set Read Address): Sets the address of memory that comp will read from. Use alongside trans.

	wadrs (Set Write Address): Sets the address of memory to which the comp will do any writing operations. Used alongside wl and trans

	wl (Write Literal): Writes the argument to the memory address the comp is currently writing to (set by wadrs).

	trans (Transfer): Copies data from writing address to the reading address. Used alongside radrs and wadrs

	goto (Goto Line): Goes to the label specified by the argument

	add (Add): Adds the values in memory address 0 and memory address 1

	sub (Subtract): Subtracts the values in memory address 0 and memory address 1

	! (NOT): Preforms a bitwise NOT on the contents of memory address 0

	& (AND): Preforms a bitwise AND using memory address 0 and memory address 1

	| (OR): Preforms a bitwise OR using memory address 0 and memory address 1

	!^ (XNOR): Preforms a bitwise XNOR using memory address 0 and memory address 1

	oadd (Add Return Overflow): Adds the values in memory address 0 and memory address 1 and returns 1111 if overflow occurs, 0000 if not

	osub (Subtract Return Underflow): Subtracts the values in memory address 0 and memory address 1 and returns 1111 if underflow occurs, 0000 if not

	setif (Set "If" Label): Sets the Label that represents the end of an if statement. Called before doif

	doif (Set "If" Address and Execute): Set the address of memory that the if will read and executes the if. The if will either jump to the Label (if not 1111) or execute the code inside it (if 1111)



//================================LATER FEATURES++++++++++++++++++++++++++++++++++++++++++//

new Options:
  -v: verbose
  -r: reverse assembly. turns a .tp into .atp. Turns .atp into .ctp
  -s: simulate immediately. After it compiles any .tp and emulates it.
  
  
