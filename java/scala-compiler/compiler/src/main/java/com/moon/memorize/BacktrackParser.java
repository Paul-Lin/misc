package com.moon.memorize;

import java.util.*;

/**
 * Created by lin on 2/11/17.
 */
public class BacktrackParser {
    public static final int FAILED=-1;
    BacktrackLexer input;
    List<Integer> markers;
    List<Token> lookahead;
    Map<Integer,Integer> listMemo=new HashMap<Integer,Integer>();
    int mark=0;

    public BacktrackParser(BacktrackLexer input) {
        this.input = input;
        markers=new ArrayList<>();
        lookahead=new ArrayList<>();
        sync(1);
    }

    public void stat(){
        if(speculate_stat_alt_1()){
            list();
            match(BacktrackLexer.EOF_TYPE);
        }else if(speculate_stat_alt_2()){
            System.out.println("predict alternative 2");
            assign();
            match(BacktrackLexer.EOF_TYPE);
        }
    }
    public void sync(int i){
        if(mark+i-1>(lookahead.size()-1)){
            int n=(mark+i-1)-(lookahead.size()-1);
            fill(n);
        }
    }

    public void fill(int n){
        for(int i=1;i<=n;i++){
            lookahead.add(input.nextToken());
        }
    }

    public Token token(int i){
        sync(i);
        return lookahead.get(mark+i-1);
    }

    public int type(int i){
        return token(i).getType();
    }

    public void match(int x){
        if(type(1)==x)consume();
        else throw new MismatchedTokenException("excepting "+BacktrackLexer.tokenNames[x]+"; found "+type(1));
    }

    public void consume(){
        mark++;
        if(mark==lookahead.size()&&!(markers.size()>0)){
            mark=0;
            lookahead.clear();
            listMemo.clear();
        }
    }

    public void mark(){
        markers.add(mark);
    }

    public void release(){
        mark=markers.get(markers.size()-1);
        markers.clear();
    }

    public boolean alreadyParsedRule(Map<Integer,Integer> memorization){
        Integer memoi=memorization.get(mark);
        if(Objects.isNull(memoi))
            return false;
        System.out.println("parsed list before at index "+mark+"; skip ahead to token index "+memoi+":"+lookahead.get(memoi).getValue());
        if(memoi==FAILED)
            throw new PreviousParsedException();
        mark=memoi;
        return true;
    }

    public void memorize(Map<Integer,Integer> memorization,int startTokenIndex,boolean failed){
        int stopTokenIndex=failed?FAILED:mark;
        memorization.put(startTokenIndex,stopTokenIndex);
    }

    public boolean speculate_stat_alt_1(){
        System.out.println("attempt alternative 1");
        boolean success=true;
        mark();
        try{
            list();
            match(BacktrackLexer.EOF_TYPE);
        }catch(RuntimeException e){
            success=false;
        }
        release();
        return success;
    }

    public boolean speculate_stat_alt_2(){
        System.out.println("attempt alternative 2");
        boolean success=true;
        mark();
        try{
            assign();
            match(BacktrackLexer.EOF_TYPE);
        }catch (RuntimeException e){
            success=false;
        }
        release();
        return success;
    }
    public void assign(){
        list();
        match(BacktrackLexer.EQUALS);
        list();
    }
    public void list(){
        boolean failed=false;
        int startTokenIndex=mark;
        if(markers.size()>0&&alreadyParsedRule(listMemo))
            return;
        try{
            _list();
        }catch(RuntimeException e){
            failed=true;
            throw e;
        }finally {
            if(markers.size()>0)
                memorize(listMemo,startTokenIndex,failed);
        }

    }

    public void _list(){
        System.out.println("parsed list rule at token index: "+mark);
        match(BacktrackLexer.LBRACK);
        elements();
        match(BacktrackLexer.RBRACK);
    }

    public void elements(){
        element();
        while(type(1)==BacktrackLexer.COMMA){
            match(BacktrackLexer.COMMA);
            element();
        }
    }

    public void element(){
        if(type(1)==BacktrackLexer.NAME&&type(2)==BacktrackLexer.EQUALS){
            match(BacktrackLexer.NAME);
            match(BacktrackLexer.EQUALS);
            match(BacktrackLexer.NAME);
        }else if(type(1)==BacktrackLexer.NAME){
            match(BacktrackLexer.NAME);
        }else if(type(1)==BacktrackLexer.EQUALS){
            match(BacktrackLexer.EQUALS);
        }else throw new NoViableException("expecting element found "+type(1));
    }
}
