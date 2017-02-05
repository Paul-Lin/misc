#include "Parser.h"

tina::Parser::Parser(Lexer *in):input(in) {
	markers = new std::vector<int>();
	lookahead = new std::vector<Token*>();
	sync(1);
}

// ȷ����ǰλ��p֮ǰ��i���ʷ���Ԫ
void tina::Parser::sync(int i) {
	int t1 = p + i - 1;
	int t2 = lookahead->size() - 1;
	if (t1>t2) {
		int n = (p + i - 1) - (lookahead->size() - 1);
		fill(n);
	}
}

// ����n���ʷ���Ԫ
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
	// ���ƶ�״̬�����ҵ�����ǰ����������ĩβ
	if (p == lookahead->size() && !isSpeculating()) {
		// ����ĩβ���͸����´�0��ʼ�����µĴʷ���Ԫ
		p = 0;
		// ��С��0�����������ڴ�
		lookahead->clear();
	}
	// ȡһ���ʷ���Ԫ
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
	// ��ǵ�ǰλ���Ա�Ż�����
	mark();
	try {
		list();
		match(Lexer::EOF_TYPE);
	}
	catch (std::string& e) {
		success = false;
		std::cout << e << std::endl;
	}
	release(); // �����Ƿ�ƥ�䣬��Ҫ�Ż�����
	return success;
}

bool tina::Parser::speculate_stat_alt2() {
	bool success = true;
	mark(); // ��ǵ�ǰλ���Ա�Ż�����
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


