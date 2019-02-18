# NFA to DFA Converter

Theory of Computation | Conversion from NFA to DFA



## Explanation
An NFA can have zero, one or more than one move from a given state on a given input symbol. An NFA can also have NULL moves (moves without input symbol). On the other hand, DFA has one and only one move from a given state on a given input symbol.

Conversion from NFA to DFA
Suppose there is an NFA N < Q, ∑, q0, δ, F > which recognizes a language L. Then the DFA D < Q’, ∑, q0, δ’, F’ > can be constructed for language L as:  

Step 1: Initially Q’ = ɸ.  
Step 2: Add q0 to Q’.  
Step 3: For each state in Q’, find the possible set of states for each input symbol using transition function of NFA. If this set of states is not in Q’, add it to Q’.  
Step 4: Final state of DFA will be all states with contain F (final states of NFA)

See the example below to understand how the algorithm works:  
https://www.geeksforgeeks.org/theory-of-computation-conversion-from-nfa-to-dfa/

## Usage
```
git clone https://github.com/eraldoforgoli/NFAtoDFAConverter  
import project to your workspace  
Run As Java Application  
```

## Application inputs
Application gets the inputs from *nfa.file* file, which holds all data about NFA automata.  
Example nfa.file inputs: 
```
4
start 0 
0 a 0
0 b 1
1 a 2
1 b 1
2 a 1
2 b 2
3 a 1
3 b 2
final 3
```

The first line represents the number of states.  
The second line shows the start node.   
The last line shows the final node (multiple values accepted too)  
The other lines show transition states, for example:

Start Node | Alphabet | Destination Node
------------ | ------------- | -------------
0 | a | 0
0 | b | 1
1 | a | 2
1 | b | 1
2 | a | 1
2 | b | 2
3 | a | 1
3 | b | 2

0 a 0 -> from node a, given input 0 > state goes to node 1  
Several test cases are given in *inputs* folder.

## Testing app with custom automata
Edit *nfa.file* file, add your graph as shown above.  
Run application.    
After compiling, the NFA and its according DFA will be drawn.  
Please read the [Notice](README.md/Application inputs) and Application Inputs paragraphs before testing the application.

## Notice:  
- The nodes should be named from 0 to n, otherwise an exception will be thrown.  
- As alphabet you can use any character.  
- The first line should always contain the number of nodes you wish to add, otherwise an exception will be thrown.  
- The last line should always specify the accepting state/s.  

## Contributing
Pull requests are welcome. 

## License
MIT
