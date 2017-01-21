#include <iostream>
#include "token.h"
#include "list_lexer.h"

int main() {
	std::cout << "+++++++++++++++++++++++++++++++++++++++++++++++++++++" << std::endl;
	tina::ListLexer lexer(std::string("[           \n a           , btyyy ]"));
	tina::Token* t = lexer.nextToken();
	while (t->type != tina::Lexer::EOF_TYPE) {
		std::cout << t->toString() << std::endl;
		t = lexer.nextToken();
	}
	std::cout << t->toString() << std::endl;
	std::cout << "+++++++++++++++++++++++++++++++++++++++++++++++++++++" << std::endl;

	tina::Lexer* input=new tina::ListLexer(std::string("[a,=a,d]"));
	tina::ListParser* parser = new tina::ListParser(input,2);
	try {
		parser->list();
	}
	catch (std::string& str) {
		std::cout << str << std::endl;
	}
	system("pause");
	return 0;
}