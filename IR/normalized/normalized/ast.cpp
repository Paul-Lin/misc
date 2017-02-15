#include "ast.h"

tina::Ast::Ast() :token(nullptr) {
	list = new std::vector<Ast*>();
}

tina::Ast::Ast(Token* tk) : token(tk) {
	list = new std::vector<Ast*>();
}

std::string tina::Ast::toString() {
	return token->toString();
}

bool tina::Ast::isNil() {
	return token == nullptr;
}

std::string tina::Ast::toStringTree() {
	if (isNil() || list->size() == 0)
		return this->toString();
	std::string str;
	if (!isNil()) {
		str = str + "( ";
		str = str + this->toString();
	}
	for (int i = 0; i < list->size(); i++) {
		Ast* tk = list->at(i);
		str = str + " " + tk->toStringTree();
	}
	if (!isNil()) {
		str = str + " )";
	}
	return str;
}

void tina::Ast::addChild(tina::Ast *ast) {
	list->push_back(ast);
}