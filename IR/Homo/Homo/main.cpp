#include "ast.h"
#include <stdlib.h>

int main() {
	tina::Token* plus = new tina::Token(tina::Token::PLUS, "+");
	tina::Token* one = new tina::Token(tina::Token::INT, "1");
	tina::Token* two = new tina::Token(tina::Token::INT, "2");
	tina::Ast* root = new tina::Ast(plus);
	root->addChild(new tina::Ast(one));
	root->addChild(new tina::Ast(two));

	std::cout << "1+2 tree: " << root->toStringTree() << std::endl;

	tina::Ast* list = new tina::Ast();
	list->addChild(new tina::Ast(one));
	list->addChild(new tina::Ast(two));
	std::cout << "1 and 2 in list: " + list->toStringTree() << std::endl;
	system("pause");
}