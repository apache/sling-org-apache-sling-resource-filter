/* Generated By:JavaCC: Do not edit this line. FilterParser.java */
package org.apache.sling.resource.filter.impl.script;

import java.util.ArrayList;
import java.util.List;
import org.apache.sling.resource.filter.impl.node.*;

public final class FilterParser implements FilterParserConstants {

  final public Node parse() throws ParseException {
  final Node node;
    node = or();
    jj_consume_token(0);
    {if (true) return node;}
    throw new Error("Missing return statement in function");
  }

  final public Node or() throws ParseException {
  final List < Node > nodes = new ArrayList < Node > (3);
  Node node;
    node = and();
    nodes.add(node);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      jj_consume_token(OR);
      node = and();
      nodes.add(node);
    }
    {if (true) return nodes.size() != 1 ? new Node(FilterParserConstants.OR, nodes) : nodes.get(0);}
    throw new Error("Missing return statement in function");
  }

  final public Node and() throws ParseException {
  final List < Node > nodes = new ArrayList < Node > (3);
  Node node;
    node = constraint();
    nodes.add(node);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      jj_consume_token(AND);
      node = constraint();
      nodes.add(node);
    }
    {if (true) return nodes.size() != 1 ? new Node(FilterParserConstants.AND, nodes) : nodes.get(0);}
    throw new Error("Missing return statement in function");
  }

  final public Node constraint() throws ParseException {
  final Node node;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAREN:
      node = group();
      break;
    case OFFSETDATETIME:
    case DATETIME:
    case DATE:
    case NUMBER:
    case STRING:
    case NULL:
    case BOOLEAN:
    case DYNAMIC_ARG:
    case FUNCTION_NAME:
    case PROPERTY:
      node = comparison();
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return node;}
    throw new Error("Missing return statement in function");
  }

  final public Node group() throws ParseException {
  final Node node;
    jj_consume_token(LPAREN);
    node = or();
    jj_consume_token(RPAREN);
    {if (true) return node;}
    throw new Error("Missing return statement in function");
  }

  final public Node comparison() throws ParseException {
  Node leftValue;
  Token op;
  Node rightValue;
    leftValue = argument();
    op = comparisonValue();
    rightValue = argument();
    {if (true) return new Node(op.kind, op.image, leftValue, rightValue);}
    throw new Error("Missing return statement in function");
  }

  final public Token comparisonValue() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EQUAL:
      jj_consume_token(EQUAL);
      break;
    case NOT_EQUAL:
      jj_consume_token(NOT_EQUAL);
      break;
    case GREATER_THAN:
      jj_consume_token(GREATER_THAN);
      break;
    case GREATER_THAN_OR_EQUAL:
      jj_consume_token(GREATER_THAN_OR_EQUAL);
      break;
    case LESS_THAN:
      jj_consume_token(LESS_THAN);
      break;
    case LESS_THAN_OR_EQUAL:
      jj_consume_token(LESS_THAN_OR_EQUAL);
      break;
    case LIKE:
      jj_consume_token(LIKE);
      break;
    case LIKE_NOT:
      jj_consume_token(LIKE_NOT);
      break;
    case CONTAINS:
      jj_consume_token(CONTAINS);
      break;
    case CONTAINS_NOT:
      jj_consume_token(CONTAINS_NOT);
      break;
    case CONTAINS_ANY:
      jj_consume_token(CONTAINS_ANY);
      break;
    case CONTAINS_NOT_ANY:
      jj_consume_token(CONTAINS_NOT_ANY);
      break;
    case IN:
      jj_consume_token(IN);
      break;
    case NOT_IN:
      jj_consume_token(NOT_IN);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return token;}
    throw new Error("Missing return statement in function");
  }

  final public List < Node > Arguments() throws ParseException {
  Object value = new ArrayList();
    jj_consume_token(LPAREN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OFFSETDATETIME:
    case DATETIME:
    case DATE:
    case NUMBER:
    case STRING:
    case NULL:
    case BOOLEAN:
    case DYNAMIC_ARG:
    case FUNCTION_NAME:
    case PROPERTY:
      value = commaSepArguments();
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    jj_consume_token(RPAREN);
    {if (true) return (List) value;}
    throw new Error("Missing return statement in function");
  }

  final public List < Node > commaSepArguments() throws ParseException {
  final List < Node > list = new ArrayList < Node > (3);
  Node arg;
    arg = argument();
    list.add(arg);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
      arg = argument();
      list.add(arg);
    }
    {if (true) return list;}
    throw new Error("Missing return statement in function");
  }

  final public Node argument() throws ParseException {
  Node selector = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OFFSETDATETIME:
    case DATETIME:
    case DATE:
    case NUMBER:
    case STRING:
    case NULL:
    case BOOLEAN:
      selector = literal();
      break;
    case PROPERTY:
      selector = property();
      break;
    case DYNAMIC_ARG:
      selector = dynamicArg();
      break;
    case FUNCTION_NAME:
      selector = function();
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return selector;}
    throw new Error("Missing return statement in function");
  }

  final public Node function() throws ParseException {
  String functionName = null;
  List < Node > children = null;
    jj_consume_token(FUNCTION_NAME);
    functionName = token.image;
    jj_consume_token(LPAREN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OFFSETDATETIME:
    case DATETIME:
    case DATE:
    case NUMBER:
    case STRING:
    case NULL:
    case BOOLEAN:
    case DYNAMIC_ARG:
    case FUNCTION_NAME:
    case PROPERTY:
      children = commaSepArguments();
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
    jj_consume_token(RPAREN);
    {if (true) return new Node(FilterParserConstants.FUNCTION_NAME, functionName, children);}
    throw new Error("Missing return statement in function");
  }

  final public Node dynamicArg() throws ParseException {
  String functionName = null;
    jj_consume_token(DYNAMIC_ARG);
    jj_consume_token(FUNCTION_NAME);
    functionName = token.image;
    {if (true) return new Node(FilterParserConstants.DYNAMIC_ARG, functionName);}
    throw new Error("Missing return statement in function");
  }

  final public Node literal() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STRING:
      jj_consume_token(STRING);
      break;
    case NUMBER:
      jj_consume_token(NUMBER);
      break;
    case NULL:
      jj_consume_token(NULL);
      break;
    case BOOLEAN:
      jj_consume_token(BOOLEAN);
      break;
    case DATE:
      jj_consume_token(DATE);
      break;
    case DATETIME:
      jj_consume_token(DATETIME);
      break;
    case OFFSETDATETIME:
      jj_consume_token(OFFSETDATETIME);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return new Node(token.kind, token.image);}
    throw new Error("Missing return statement in function");
  }

  final public Node property() throws ParseException {
    jj_consume_token(PROPERTY);
    {if (true) return new Node(token.kind, token.image);}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public FilterParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[9];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x400000,0x200000,0x9843c00,0xf0000000,0x8843c00,0x4000000,0x8843c00,0x8843c00,0x8843c00,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x1c00,0x3ff,0x1c00,0x0,0x1c00,0x1c00,0x0,};
   }

  /** Constructor with InputStream. */
  public FilterParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public FilterParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new FilterParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public FilterParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new FilterParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public FilterParser(FilterParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(FilterParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[46];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 9; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 46; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
