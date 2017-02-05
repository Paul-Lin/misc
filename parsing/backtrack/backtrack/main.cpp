#include <stdio.h>
#include "lexer.h"
#include "Parser.h"
#include "BacktrackLexer.h"

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
int main() {
	
	system("pause");
	return 0;
}