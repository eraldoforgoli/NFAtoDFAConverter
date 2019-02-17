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

```

## Usage

git clone https://github.com/eraldoforgoli/NFAtoDFAConverter
import project to your workspace

Application gets the input from nfa file 
```

example nfa.file inputs: 
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
0 a 0 -> from node a, given input 0 > state goes to node 1


## Contributing
Pull requests are welcome. 


## License
MIT
