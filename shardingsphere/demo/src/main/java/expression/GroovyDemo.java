package expression;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class GroovyDemo {


  public static void main(String[] args) {
    GroovyShell groovyShell = new GroovyShell();

    String expression = "ds${2019..2020}.trans_account_${['07','08']}";
    String expression2 = "ds${2015..2020}.trans_account_${2015..2020}${['07','08']}";

    final String s = "x  > 2  &&  y  > 1";
    Script script = groovyShell.parse(s);
    Binding binding = new Binding();
    binding.setProperty("x",5);
    binding.setProperty("y",3);
    script.setBinding(binding);
    System.out.println(script.run());

    Script parse = groovyShell.parse("ds${2019..2020}");
    parse.run();
    System.out.println();
  }

}
