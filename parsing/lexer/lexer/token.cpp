#include "token.h"
#include "list_lexer.h"

std::string tina::Token::toString() {
	std::string tname=ListLexer::tokenNames[type];
	return std::string("<' ")+text+" ', "+tname+">";
}