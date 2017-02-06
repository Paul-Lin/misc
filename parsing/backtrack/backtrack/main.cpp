#include <stdio.h>
#include "lexer.h"
#include "Parser.h"
#include "BacktrackLexer.h"
#include "BacktrackParser.h"

void func() {
	tina::Lexer* lexer = new tina::Lexer(std::string("[a,b]=[c,d]"));
	tina::Parser* parser = new tina::Parser(lexer);
	try {
		parser->stat();
	}
	catch (std::string& e) {
		std::cout << e << std::endl;
	}
}

void func1() {
	t::BacktrackLexer* lexer = new t::BacktrackLexer(std::string("[a=f,b]=[c,d]"));
	t::BacktrackParser* parser = new t::BacktrackParser(lexer);
	parser->stat();
}
int main() {
	func1();
	system("pause");
	return 0;
}