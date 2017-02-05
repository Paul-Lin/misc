#include "Parser.h"

tina::Parser::Parser(Lexer *in):input(in) {
	markers = new std::vector<int>();
	lookahead = new std::vector<Token*>();
	sync(1);
}

// 确保当前位置p之前有i个词法单元
void tina::Parser::sync(int i) {
	int t1 = p + i - 1;
	int t2 = lookahead->size() - 1;
	if (t1>t2) {
		int n = (p + i - 1) - (lookahead->size() - 1);
		fill(n);
	}
}

// 加入n个词法单元
void tina::Parser::fill(int n) {
	for (int i = 1; i <= n; i++)
		lookahead->push_back(input->nextToken());
}

tina::Token* tina::Parser::LT(int i) {
	sync(i);
	return lookahead->at(p + i - 1);
}

int tina::Parser::LA(int i) {
	return LT(i)->type;
}

void tina::Parser::consume() {
	p++;
	// 非推断状态，而且到达向前看缓冲区的末尾
	if (p == lookahead->size() && !isSpeculating()) {
		// 到了末尾，就该重新从0开始填入新的词法单元
		p = 0;
		// 大小清0，但不回收内存
		lookahead->clear();
	}
	// 取一个词法单元
	sync(1);
}

bool tina::Parser::isSpeculating() {
	return markers->size() > 0;
}

void tina::Parser::match(int x) throw (std::string){
	if (LA(1) == x)consume();
	else throw std::string("expecting ").append(Lexer::tokenNames[x]).append(" found ").append(LT(1)->toString());
}

int tina::Parser::mark() {
	markers->push_back(p);
	return p;
}

void tina::Parser::release() {
	int marker = markers->back();
	markers->pop_back();
	seek(marker);
}

void tina::Parser::seek(int index) {
	p = index;
}

void tina::Parser::stat() {
	if (speculate_stat_alt2()) {
		assign();
		match(Lexer::EOF_TYPE);
		std::cout << "like [a,b]=[a,b]" << std::endl;
	}
	else if (speculate_stat_alt1()) {
		list();
		match(Lexer::EOF_TYPE);
		std::cout << "like [a,b]" << std::endl;
	}
	else throw std::string("expecting stat found " + LT(1)->toString());
}

bool tina::Parser::speculate_stat_alt1() {
	bool success = true;
	// 标记当前位置以便放回输入
	mark();
	try {
		list();
		match(Lexer::EOF_TYPE);
	}
	catch (std::string& e) {
		success = false;
		std::cout << e << std::endl;
	}
	release(); // 不管是否匹配，都要放回输入
	return success;
}

bool tina::Parser::speculate_stat_alt2() {
	bool success = true;
	mark(); // 标记当前位置以便放回输入
	try {
		assign();
		match(Lexer::EOF_TYPE);
	}
	catch (std::runtime_error& e) {
		success = false;
		std::cout << e.what() << std::endl;
	}
	release();
	return success;

}

void tina::Parser::list() {
	match(Lexer::LBRACK);
	elements();
	match(Lexer::RBRACK);
}

void tina::Parser::assign() {
	list();
	match(Lexer::EQUALS);
	list();
}

void tina::Parser::elements() {
	element();
	while (LA(1) == Lexer::COMMA) {
		match(Lexer::COMMA);
		element();
	}
}

void tina::Parser::element() {
	if (LA(1) == Lexer::NAME&&LA(2) == Lexer::EQUALS) {
		match(Lexer::NAME);
		match(Lexer::EQUALS);
		match(Lexer::NAME);
	}
	else if (LA(1) == Lexer::NAME)
		match(Lexer::NAME);
	else if (LA(1) == Lexer::LBRACK)
		list();
	else throw std::string("expecting element found " + LT(1)->toString());
}


