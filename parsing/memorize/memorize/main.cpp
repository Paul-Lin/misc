#include <iostream>
#include "BacktrackLexer.h"
#include "BacktrackParser.h"

int main() {
	tina::BacktrackLexer* lexer = new tina::BacktrackLexer("[a,b]=[c,d]");
	tina::BacktrackParser* parser = new tina::BacktrackParser(lexer);
	parser->stat();
	system("pause");
	return 0;
}