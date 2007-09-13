/*****************************************************************************
 * BooleanExpressionEvaluator.java
 *****************************************************************************
 * Copyright (C) 2007 Daniel Dreibrodt
 *
 * This file is part of vlcskineditor
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/

package vlcskineditor;

import java.util.*;

/**
 * BooleanExpressionEvaluator
 * conversion of /trunk/modules/gui/skins2/parser/expr_evaluator.cpp
 * @author Daniel Dreibrodt
 */
public class BooleanExpressionEvaluator {
  
  List<String> m_stack = new ArrayList<String>();
  
  
  /** Creates a new instance of BooleanExpressionEvaluator */
  public BooleanExpressionEvaluator() {
    
  }
  public void parse(String rExpr) {
    m_stack.clear();   
    
    char[] pString = rExpr.toCharArray();
    List<String> opStack = new ArrayList<String>(); // operator stack    
    String token;
    
    // Tokenize the expression
    int begin = 0;
    int end = 0;
    while(pString[begin]==' ') {
      begin++;
    }
    if( pString[begin] == '(' ) {
      // '(' found: push it on the stack and continue
      opStack.add( "(" );
      begin++;
    }
    else if( pString[begin] == ')' ) {
      // ')' found: pop the stack until a '(' is found
      while(!opStack.isEmpty()) {
        // Pop the stack
        String lastOp = opStack.get(opStack.size()-1);
        opStack.remove(opStack.size()-1);
        if (lastOp=="(") break;
        // Push the operator on the RPN stack
        m_stack.add(lastOp);
      }
      begin++;
    }
    else {
       // Skip white spaces
      end = begin;
      while( pString[end]!=' ' && pString[end]!=' ' && pString[end]!=')' ) end++;
      // Get the next token
      token = rExpr.substring(begin,end-begin);
      begin = end;
      if( token.equals("not") || token.equals("or") || token.equals("and") ) {
        // Pop the operator stack while the operator has a higher
        // precedence than the top of the stack
        while(!opStack.isEmpty() && hasPrecedency(token,opStack.get(opStack.size()-1))) {
          // Pop the stack
          String lastOp = opStack.get(opStack.size()-1);
          opStack.remove(opStack.size()-1);
          m_stack.add( lastOp );
        }
        opStack.add(token);
      }
      else {
        m_stack.add(token);
      }      
    }
    // Finish popping the operator stack
    while( !opStack.isEmpty() )
    {
        String lastOp = opStack.get(opStack.size()-1);
        opStack.remove(opStack.size()-1);
        m_stack.add( lastOp );
    }
  }
  String getToken() {
    if( !m_stack.isEmpty() )
    {
        String token = m_stack.get(0);
        m_stack.remove(0);
        return token;
    }
    return "";
  }
  boolean hasPrecedency(String op1, String op2) {
    // FIXME
    if( op1.equals("(") )
    {
        return true;
    }
    if( op1.equals("and") )
    {
        return (op2.equals("or")) || (op2.equals("not"));
    }
    if( op1.equals("or") )
    {
        return (op2.equals("not"));
    }
    return false;
  }
  
  
}
