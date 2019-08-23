/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.scripting.xmltags;

/**
 * @author Frank D. Martinez [mnesarco]
 */
public class VarDeclSqlNode implements SqlNode {

  /**
   * 名字
   */
  private final String name;
  /**
   * 表达式
   */
  private final String expression;

  public VarDeclSqlNode(String var, String exp) {
    name = var;
    expression = exp;
  }

  /**
   * <1> 处，调用 OgnlCache#getValue(String expression, Object root) 方法，获得表达式对应的值。
   *     详细解析，见 「7.1 OgnlCache」 。
   * <2> 处，调用 DynamicContext#bind(String name, Object value) 方法，绑定到上下文。
   * @param context
   * @return
   */
  @Override
  public boolean apply(DynamicContext context) {
    //<1> 获得值
    final Object value = OgnlCache.getValue(expression, context.getBindings());
    // <2> 绑定到上下文
    context.bind(name, value);
    return true;
  }

}
