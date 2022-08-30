/* Generated By:JavaCC: Do not edit this line. FilterParserConstants.java */
package org.apache.sling.resource.filter.impl.script;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface FilterParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int PLUS = 3;
  /** RegularExpression Id. */
  int MINUS = 4;
  /** RegularExpression Id. */
  int DIGIT = 5;
  /** RegularExpression Id. */
  int EXP = 6;
  /** RegularExpression Id. */
  int OFFSET = 7;
  /** RegularExpression Id. */
  int YYYYMMDD = 8;
  /** RegularExpression Id. */
  int TIME = 9;
  /** RegularExpression Id. */
  int OFFSETDATETIME = 10;
  /** RegularExpression Id. */
  int DATETIME = 11;
  /** RegularExpression Id. */
  int DATE = 12;
  /** RegularExpression Id. */
  int NUMBER = 13;
  /** RegularExpression Id. */
  int INTEGER = 14;
  /** RegularExpression Id. */
  int FRACTIONAL_DIGITS = 15;
  /** RegularExpression Id. */
  int EXPONENT = 16;
  /** RegularExpression Id. */
  int DIGITS = 17;
  /** RegularExpression Id. */
  int STRING = 18;
  /** RegularExpression Id. */
  int SQUOTE = 19;
  /** RegularExpression Id. */
  int DQUOTE = 20;
  /** RegularExpression Id. */
  int AND = 21;
  /** RegularExpression Id. */
  int OR = 22;
  /** RegularExpression Id. */
  int NULL = 23;
  /** RegularExpression Id. */
  int LPAREN = 24;
  /** RegularExpression Id. */
  int RPAREN = 25;
  /** RegularExpression Id. */
  int COMMA = 26;
  /** RegularExpression Id. */
  int BOOLEAN = 27;
  /** RegularExpression Id. */
  int EQUAL = 28;
  /** RegularExpression Id. */
  int NOT_EQUAL = 29;
  /** RegularExpression Id. */
  int GREATER_THAN = 30;
  /** RegularExpression Id. */
  int GREATER_THAN_OR_EQUAL = 31;
  /** RegularExpression Id. */
  int LESS_THAN = 32;
  /** RegularExpression Id. */
  int LESS_THAN_OR_EQUAL = 33;
  /** RegularExpression Id. */
  int LIKE = 34;
  /** RegularExpression Id. */
  int LIKE_NOT = 35;
  /** RegularExpression Id. */
  int CONTAINS = 36;
  /** RegularExpression Id. */
  int CONTAINS_NOT = 37;
  /** RegularExpression Id. */
  int CONTAINS_ANY = 38;
  /** RegularExpression Id. */
  int CONTAINS_NOT_ANY = 39;
  /** RegularExpression Id. */
  int IN = 40;
  /** RegularExpression Id. */
  int NOT_IN = 41;
  /** RegularExpression Id. */
  int DYNAMIC_ARG = 42;
  /** RegularExpression Id. */
  int FUNCTION_NAME = 43;
  /** RegularExpression Id. */
  int PROPERTY = 44;
  /** RegularExpression Id. */
  int UNKNOWN = 45;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"+\"",
    "\"-\"",
    "<DIGIT>",
    "<EXP>",
    "<OFFSET>",
    "<YYYYMMDD>",
    "<TIME>",
    "<OFFSETDATETIME>",
    "<DATETIME>",
    "<DATE>",
    "<NUMBER>",
    "<INTEGER>",
    "<FRACTIONAL_DIGITS>",
    "<EXPONENT>",
    "<DIGITS>",
    "<STRING>",
    "<SQUOTE>",
    "<DQUOTE>",
    "<AND>",
    "<OR>",
    "\"null\"",
    "\"(\"",
    "\")\"",
    "\",\"",
    "<BOOLEAN>",
    "<EQUAL>",
    "<NOT_EQUAL>",
    "<GREATER_THAN>",
    "\">=\"",
    "<LESS_THAN>",
    "\"<=\"",
    "<LIKE>",
    "\"not like\"",
    "\"contains\"",
    "\"contains not\"",
    "\"contains any\"",
    "\"contains not any\"",
    "<IN>",
    "\"not in\"",
    "\"$\"",
    "<FUNCTION_NAME>",
    "<PROPERTY>",
    "<UNKNOWN>",
  };

}
