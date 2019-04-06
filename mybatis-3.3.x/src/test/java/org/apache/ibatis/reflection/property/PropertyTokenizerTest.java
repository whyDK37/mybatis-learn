package org.apache.ibatis.reflection.property;

import org.junit.Test;

import java.util.Iterator;

public class PropertyTokenizerTest {

    @Test
    public void test() {
        //   orders [OJ . items [1] . name
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer("orders[].items[1].name");

        propertyTokenizer.getName();
        propertyTokenizer.getIndexedName();
        propertyTokenizer.next().getName();
        propertyTokenizer.next().next().getName();

    }

}