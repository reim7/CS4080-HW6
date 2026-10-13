package com.craftinginterpreters.lox;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

// Ch12 #1: class objects are instances of metaclasses.
class LoxClass extends LoxInstance implements LoxCallable {
  final String name;
  final LoxClass superclass;
  private final Map<String, LoxFunction> methods;

  LoxClass(LoxClass metaclass, String name, LoxClass superclass,
           Map<String, LoxFunction> methods) {
    super(metaclass);
    this.name = name;
    this.superclass = superclass;
    this.methods = methods;
    for (LoxFunction method : methods.values()) method.setOwner(this);
  }

  // Ch13 #2: BETA lookup prefers highest ancestor with this method.
  LoxFunction findMethod(String methodName) {
    LoxFunction inherited = superclass == null ? null : superclass.findMethod(methodName);
    return inherited != null ? inherited : methods.get(methodName);
  }

  // The receiver class searches for the nearest subclass implementation
  // following a currently executing method's defining class.
  LoxFunction findNextMethod(String methodName, LoxClass owner) {
    if (this == owner) return null;
    if (superclass == null) return null;
    LoxFunction between = superclass.findNextMethod(methodName, owner);
    if (between != null) return between;
    if (!isDescendantOf(owner)) return null;
    return methods.get(methodName);
  }

  private boolean isDescendantOf(LoxClass ancestor) {
    for (LoxClass at = this; at != null; at = at.superclass)
      if (at == ancestor) return true;
    return false;
  }

  @Override public int arity() {
    LoxFunction initializer = findMethod("init");
    return initializer == null ? 0 : initializer.arity();
  }

  @Override public Object call(Interpreter interpreter, List<Object> arguments) {
    LoxInstance instance = new LoxInstance(this);
    LoxFunction initializer = findMethod("init");
    if (initializer != null) initializer.bind(instance).call(interpreter, arguments);
    return instance;
  }

  @Override public String toString() { return name; }
}
