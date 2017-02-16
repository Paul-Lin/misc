#include "hetero.h"

tina::HeteroAst::HeteroAst(tina::Token* t):token(t) {}

tina::HeteroAst::HeteroAst(int tokenType) :token(new tina::Token(tokenType)) {}

std::string tina::HeteroAst::toString() {
	return token->toString();
}

std::string tina::HeteroAst::toStringTree() {
	return toString();
}

int tina::HeteroAst::type() {
	return token->type;
}