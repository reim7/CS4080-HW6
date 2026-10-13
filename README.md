# CS4080-HW6
CS 4080 - Homework 6
The implementation builds on my previous Homework 4 and 5 submissions and preserves the existing interpreter functionality.

Chapter 12 - Classes

### Challenge 1: Static Methods
Extended Lox to support static methods that can be called directly on a class without creating an instance. Implemented metaclass-based method storage and lookup.

### Challenge 2: Getters
Added support for getter methods that execute automatically when accessed as properties, without requiring parentheses.

### Challenge 3: Public vs. Private Properties
Discussed the advantages and disadvantages of public fields, private fields, and access modifiers in the written homework document.

Chapter 13 - Inheritance

### Challenge 1: Traits
Selected traits as an alternative mechanism for code reuse and explained their advantages and limitations. This challenge is answered conceptually, as implementation is optional.

### Challenge 2: BETA-Style Inheritance
Modified method inheritance to support superclass-first execution and the `inner()` mechanism for invoking subclass implementations.

### Challenge 3: Arrays
Implemented fixed-length arrays with an Array(size) constructor, get(index), set(index, value), and a length property.

## Previous Homework Features Preserved

- REPL expression evaluation
- Uninitialized variable runtime errors
- Break statements
- Function expressions and closures
- Local-variable resolution

The repository includes the Java interpreter source code and the additional classes required for class behavior, inheritance, and arrays.

See the Homework 6 written submission for explanations, code examples, expected results, and the files modified for each challenge.

## Reference

Crafting Interpreters by Robert Nystrom, Chapters 12 and 13.
