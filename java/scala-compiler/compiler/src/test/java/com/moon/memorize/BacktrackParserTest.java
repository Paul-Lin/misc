package com.moon.memorize;

import org.junit.Test;

/**
 * Created by lin on 2/11/17.
 */
public class BacktrackParserTest {
    @Test
    public void test(){
        BacktrackLexer lexer=new BacktrackLexer("[a,b]=[c,d]");
        BacktrackParser parser=new BacktrackParser(lexer);
        parser.stat();
    }
}
