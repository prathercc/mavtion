




## Get Mavtion

**Latest Build:** 12/23/2021
<p>
<a href="https://github.com/prathercc/mavtion/raw/main/Mavtion.jar"><img src="https://img.shields.io/badge/Mavtion.jar-Download-blue.svg?style=plastic&logo=java"></a>
<a href="https://github.com/prathercc/mavtion/raw/main/Sample%20Script.mvsf"><img src="https://img.shields.io/badge/Sample Script.mvsf-Download-red.svg?style=plastic"></a>
</p>

#  Mavtion

 **Mavtion** is a [Java](https://java.com/en/)  written script software that reads from Mavtion Script Files (.mvsf) to perform automated tasks.
<p align="center">
<img src="https://raw.githubusercontent.com/prathercc/mavtion/main/screenshots/1.png">
</p>


## Load Scripts
Locate a .mvsf file and load the script into memory.

<p align="center">
<img src="https://raw.githubusercontent.com/prathercc/mavtion/main/screenshots/2.png">
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/prathercc/mavtion/main/screenshots/3.png">
</p>
<p align="center">
</p>

## Toggle Script Start/Stop
Use the assigned hot-key or manually click the Start and Stop button.
<p align="center">
<img src="https://raw.githubusercontent.com/prathercc/mavtion/main/screenshots/4.png">
</p>
<p align="center">
<img src="https://raw.githubusercontent.com/prathercc/mavtion/main/screenshots/5.png">
</p>

## Writing Scripts
Scripts should look similar to the following code:
<p align="center">

    LOOP FOREVER // This line is required, use a non-negative integer or FOREVER as the value.
    MOVEMOUSETO:BOXPOINT(218,246)->(234,253)
    WAIT:BETWEEN(50,200)
    CLICKRIGHTMOUSE
    MOVEMOUSETO:BOXPOINT(174,292)->(237,294)
    MOVEMOUSETO(174,294)
    WAIT:BETWEEN(50,200)
    CLICKLEFTMOUSE
    WAIT:BETWEEN(4000,8000)
    WAIT:4000
    PRESSKEY:1
    HOLDKEY:SPACE
    RELEASEKEY:SPACE
    HOLDLEFTMOUSE
    RELEASELEFTMOUSE
    SETMOUSETO:BOXPOINT(100,200)->(400,500)
    SETMOUSETO(100,500)
    TYPETEXT:testing123
    DEBUG:This is a test message!
    AFTER[0]:(1000,5000, 5) //After a time between 1000 and 5000 milliseconds, loop 5x.
    _A[0]:WAIT:4000 // Instructions to be performed when the AFTER instruction runs.
    _A[0]:WAIT:5000
    AFTER[1]:(1000,5000, 5)
    _A[1]:WAIT:4000
    _A[1]:PRESSKEY:1
### Instructions
 1. LOOP - Tells the program how many times the script will run before automatically stopping.
> *LOOP is required for each script file.*
 2. WAIT:BETWEEN - Pauses the script for a random amount of time (in milliseconds) that is selected between the two interval parameter values.
 3. WAIT - Pauses the script for the specified amount of milliseconds.
 4. MOVEMOUSETO:BOXPOINT - Moves the mouse to a random position within a given box. 
> *When selecting your box coordinates, measure from the top-left down to the bottom right.*
5. MOVEMOUSETO - Moves the mouse to the given position.
6. SETMOUSETO:BOXPOINT - Sets the position of the mouse to a random position within a given box.
7. SETMOUSETO - Sets the position of the mouse to the given position.
8. CLICKRIGHTMOUSE - Simulates a right mouse click.
9. CLICKLEFTMOUSE - Simulates a left mouse click.
10. PRESSKEY - Simulates a key press.
11. HOLDKEY - Holds a specified key down.
12. RELEASEKEY - Releases a specified key.
13. HOLDLEFTMOUSE - Holds the left mouse button down.
14. RELEASELEFTMOUSE - Releases the left mouse button. 
15. AFTER[index] - After a time selected from a given interval (in milliseconds), perform instructions prefixed by _A[index]: .
16. DEBUG - Debugs a message in the console area.
17. TYPETEXT - Types an array of characters
</p>

## Contributing

Feel free to create an [Issue](https://github.com/prathercc/mavtion/issues) if you have any ideas for improvement or notice any bugs that need to addressed.

 

