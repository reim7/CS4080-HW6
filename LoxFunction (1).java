package com.craftinginterpreters.lox;

import java.util.List;
import java.util.Collections;

class LoxFunction implements LoxCallable {
  private final String name;
  private final Expr.Function declaration;
  private final Environment closure;
  private final boolean initializer;
  private LoxClass owner;

  LoxFunction(String name, Expr.Function declaration, Environment closure) {
    this(name, declaration, closure, false);
  }
  LoxFunction(String name, Expr.Function declaration, Environment closure,
              boolean initializer) {
    this.name = name;
    this.declaration = declaration;
    this.closure = closure;
    this.initializer = initializer;
  }
  void setOwner(LoxClass owner) { this.owner = owner; }
  boolean isGetter() { return declaration.parameters == null; }

  LoxFunction bind(LoxInstance instance) {
    Environment bound = new Environment(closure);
    bound.setSlot(0, instance); // implicit 'this' scope
    LoxFunction result = new LoxFunction(name, declaration, bound, initializer);
    result.owner = owner;
    // Ch13 #2: bind 'inner' to the nearest lower-level implementation.
    LoxFunction next = instance.klass.findNextMethod(name, owner);
    LoxCallable inner = next == null ? new LoxCallable() {
      @Override public int arity() { return 0; }
      @Override public Object call(Interpreter i, List<Object> args) { return null; }
      @Override public String toString() { return "<empty inner>"; }
    } : next.bind(instance);
    bound.setSlot(1, inner);
    return result;
  }

  @Override public int arity() {
    return declaration.parameters == null ? 0 : declaration.parameters.size();
  }
  @Override public Object call(Interpreter interpreter, List<Object> arguments) {
    Environment local = new Environment(closure);
    if (declaration.parameters != null) {
      for (int i = 0; i < declaration.parameters.size(); i++)
        local.setSlot(i, arguments.get(i));
    }
    try { interpreter.executeBlock(declaration.body, local); }
    catch (Return result) {
      return initializer ? closure.getAt(0, 0) : result.value;
    }
    return initializer ? closure.getAt(0, 0) : null;
  }
  @Override public String toString() { return name == null ? "<fn>" : "<fn " + name + ">"; }
}
