#include <iostream>
#include "token.h"
#include "list_lexer.h"

int main() {
	tina::ListLexer lexer(std::string("[           \n a           , btyyy ]"));
	tina::Token t = lexer.nextToken();
	while (t.type != tina::Lexer::EOF_TYPE) {
		std::cout << t.toString() << std::endl;
		t = lexer.nextToken();
	}
	std::cout << t.toString() << std::endl;
	system("pause");
	return 0;
}