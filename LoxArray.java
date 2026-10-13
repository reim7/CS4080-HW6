package com.craftinginterpreters.lox;

import java.util.List;
import java.util.Arrays;

// Ch13 #3: native, fixed-length indexed arrays; get/set/length properties.
class LoxArray extends LoxInstance {
  private final Object[] elements;
  LoxArray(int size) { super(null); elements = new Object[size]; }
  @Override Object get(Token name) {
    switch (name.lexeme) {
      case "length": return (double)elements.length;
      case "get": return new LoxCallable() {
        public int arity() { return 1; }
        public Object call(Interpreter interpreter, List<Object> arguments) {
          return elements[index(arguments.get(0), name)];
        }
      };
      case "set": return new LoxCallable() {
        public int arity() { return 2; }
        public Object call(Interpreter interpreter, List<Object> arguments) {
          int index = index(arguments.get(0), name);
          elements[index] = arguments.get(1);
          return arguments.get(1);
        }
      };
      default: return super.get(name);
    }
  }
  @Override void set(Token name, Object value) {
    throw new RuntimeError(name, "Array properties are read-only; use set(index, value).");
  }
  private int index(Object value, Token token) {
    if (!(value instanceof Double) || !Double.isFinite((Double)value) ||
        ((Double)value) % 1 != 0 || (Double)value < 0 || (Double)value >= elements.length)
      throw new RuntimeError(token, "Array index must be a valid integer within bounds.");
    return ((Double)value).intValue();
  }
  @Override public String toString() {
    StringBuilder s = new StringBuilder("[");
    for (int i = 0; i < elements.length; i++) {
      if (i > 0) s.append(", ");
      s.append(elements[i] == null ? "nil" : elements[i]);
    }
    return s.append("]").toString();
  }
}
